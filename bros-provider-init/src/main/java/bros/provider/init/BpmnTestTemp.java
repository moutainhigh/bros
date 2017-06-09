package bros.provider.init;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.util.ValidateUtil;
/**
 * 
 * @ClassName: BpmnTest 
 * @Description: 展示已经经过了的流程历史
 * @author 何鹏
 * @date 2016年10月8日 上午10:45:14 
 * @version 1.0
 */
public class BpmnTestTemp {
	private static final Logger logger = LoggerFactory.getLogger(BpmnTestTemp.class);
	public static void main(String[] args) throws Exception{
		new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
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
			if(i!= 9){
				continue;
			}
			String taskId = task.getId();
			String processDefinitionId = task.getProcessDefinitionId();//流程定义id
			String showProcessDefName = getMatchShowPhotoTemplate(taskService,taskId,processDefinitionId);
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(showProcessDefName).active().latestVersion().singleResult();
			String processDefinitionIdTemp = processDefinition.getId();
			BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionIdTemp);  
			
			//当前活动的节点
	        List<String> activeActivityIds = runtimeService.getActiveActivityIds(task.getProcessInstanceId());
	  
	        // 经过的流  
	        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());  
	        List<String> highLightedFlows = new ArrayList<>();  
	        getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activeActivityIds);  
	  
	        
	        
	        ProcessDiagramGenerator pdg = processEngineConfiguration.getProcessDiagramGenerator();  

	        InputStream inputStream = pdg.generateDiagram(bpmnModel, "PNG", activeActivityIds, highLightedFlows, 
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
			
		}
		
		
		logger.info("=======over");
	}
	
    /* 
     * 递归查询经过的流 
     */  
    public static void getHighLightedFlows(List<ActivityImpl> activityList, List<String> highLightedFlows, List<String> historicActivityInstanceList) {  
        for (ActivityImpl activity : activityList) {  
            if ("subProcess".equals(activity.getProperty("type").toString())) {  
                // get flows for the subProcess  
                getHighLightedFlows(activity.getActivities(), highLightedFlows, historicActivityInstanceList);  
            }  
            logger.info(activity.getProperty("type").toString());
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
    /**
     * 获取流程显示模板名称
     */
    public static String getMatchShowPhotoTemplate(TaskService taskService,String taskId,String processDefinitionId){
    	String processName = "";
    	if(processDefinitionId.startsWith("auditAuthProcess")){//审核式授权
			List<String> variableNames = new ArrayList<String>();
			variableNames.add("orderly");//有序还是无序号       1有序   0无需
			variableNames.add("roleNumber1");//
			variableNames.add("roleNumber2");//
			variableNames.add("roleNumber3");//
			variableNames.add("roleNumber4");//
			variableNames.add("roleNumber5");//
			Map<String,Object> varsMap = taskService.getVariables(taskId, variableNames);
			
			String orderly = String.valueOf(varsMap.get("orderly"));
			String roleNumber1 = String.valueOf(varsMap.get("roleNumber1"));
			String roleNumber2 = String.valueOf(varsMap.get("roleNumber2"));
			String roleNumber3 = String.valueOf(varsMap.get("roleNumber3"));
			String roleNumber4 = String.valueOf(varsMap.get("roleNumber4"));
			String roleNumber5 = String.valueOf(varsMap.get("roleNumber5"));
			
			if(ValidateUtil.isEmpty(roleNumber1)){
				roleNumber1 = "0";
			}
			if(ValidateUtil.isEmpty(roleNumber2)){
				roleNumber2 = "0";
			}
			if(ValidateUtil.isEmpty(roleNumber3)){
				roleNumber3 = "0";
			}
			if(ValidateUtil.isEmpty(roleNumber4)){
				roleNumber4 = "0";
			}
			if(ValidateUtil.isEmpty(roleNumber5)){
				roleNumber5 = "0";
			}
			
			if("1".equals(orderly)){//有序
				if(!"0".equals(roleNumber1) && "0".equals(roleNumber2)){//一级授权
					processName = "OneOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && "0".equals(roleNumber3)){//二级授权
					processName = "TwoOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						"0".equals(roleNumber4)){//三级授权
					processName = "ThreeOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						!"0".equals(roleNumber3) && "0".equals(roleNumber5)){//四级授权
					processName = "FourOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						!"0".equals(roleNumber3) && !"0".equals(roleNumber5)){//五级授权
					processName = "FiveOrderProcessShow";
				}
			}
			
		}
    	
    	return processName;
		
    }

}
