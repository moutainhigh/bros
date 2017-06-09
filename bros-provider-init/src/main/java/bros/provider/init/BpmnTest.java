package bros.provider.init;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.stream.FileImageOutputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * 
 * @ClassName: BpmnTest 
 * @Description: 展示已经经过了的流程历史
 * @author 何鹏
 * @date 2016年10月8日 上午10:45:14 
 * @version 1.0
 */
public class BpmnTest {

	public static void main(String[] args) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		HistoryService historyService = processEngine.getHistoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
		
		
		
		List<Task> tasks = taskService.createTaskQuery()
										 .orderByTaskCreateTime()
										 .desc()
										 .list();
		int i=0;
		for(Task task : tasks){
			i++;
			String processDefinitionId = task.getProcessDefinitionId();//流程定义id
			
			
			BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);  
			// 经过的节点  
	        List<String> activeActivityIds = new ArrayList<>();  
	        List<String> finishedActiveActivityIds = new ArrayList<>();  
	  
	        // 已执行完的任务节点  
	        List<HistoricActivityInstance> finishedInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().list();  
	        for (HistoricActivityInstance hai : finishedInstances) {  
	            finishedActiveActivityIds.add(hai.getActivityId());  
	        }  
	  
	        // 已完成的节点+当前节点  
	        activeActivityIds.addAll(finishedActiveActivityIds);  
	        activeActivityIds.addAll(runtimeService.getActiveActivityIds(task.getProcessInstanceId()));  
	  
	        // 经过的流  
	        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());  
	        List<String> highLightedFlows = new ArrayList<>();  
	        getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activeActivityIds);  
	  
	        
	        
	        ProcessDiagramGenerator pdg = processEngineConfiguration.getProcessDiagramGenerator();  

	        InputStream inputStream = pdg.generateDiagram(bpmnModel, "PNG", finishedActiveActivityIds, highLightedFlows, 
	        		processEngineConfiguration.getProcessEngineConfiguration().getActivityFontName(), 
	        		processEngineConfiguration.getProcessEngineConfiguration().getLabelFontName(), 
	        		processEngineConfiguration.getProcessEngineConfiguration().getAnnotationFontName(),
	        		processEngineConfiguration.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 
	        		1.0);
	        
	        // 将图片流生成byte[]数组 
			byte[] diagramBytes = IoUtil.readInputStream(inputStream,null); 
			String path = "D:/cc/test_"+i+".png";
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(diagramBytes, 0, diagramBytes.length);
			imageOutput.close();
			
			break;
		}
		
		
		System.out.println("=======over");
	}
	
    /* 
     * 递归查询经过的流 
     */  
    public static void getHighLightedFlows(List<ActivityImpl> activityList, List<String> highLightedFlows, List<String> historicActivityInstanceList) {  
        for (ActivityImpl activity : activityList) {  
            if (activity.getProperty("type").equals("subProcess")) {  
                // get flows for the subProcess  
                getHighLightedFlows(activity.getActivities(), highLightedFlows, historicActivityInstanceList);  
            }  
            
            if (historicActivityInstanceList.contains(activity.getId())) {  
                List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();  
                for (PvmTransition pvmTransition : pvmTransitionList) {  
                    String destinationFlowId = pvmTransition.getDestination().getId();  
                    if (historicActivityInstanceList.contains(destinationFlowId)) {  
                        highLightedFlows.add(pvmTransition.getId());  
                    }  
                }  
            }  
        }  
    }  

}
