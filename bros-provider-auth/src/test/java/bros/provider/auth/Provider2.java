package bros.provider.auth;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.util.DateUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.activiti.user.service.IQryInnerUserService;
import bros.provider.parent.activiti.user.service.IQryOutSideUserService;


public class Provider2 {
	private static final  Logger logger = LoggerFactory.getLogger(Provider2.class);
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws Exception {
		
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");

		//System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		HistoryService historyService = processEngine.getHistoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
		
		IQryInnerUserService qryInnerUserService = (IQryInnerUserService) context.getBean("qryInnerUserService");
		IQryOutSideUserService qryOutSideUserService = (IQryOutSideUserService) context.getBean("qryOutSideUserService");
		
		
		List<Task> tasks = taskService.createTaskQuery()
										 .orderByTaskCreateTime()
										 .desc()
										 .list();
		int i=0;
		for(Task task : tasks){
			String taskId = task.getId();
			String legalId = "80fb68c1-fce5-440d-85a3-9c392ba1ba83";
			String cstNo = "";
			String type = "";
			
			String processDefinitionId = task.getProcessDefinitionId();//流程定义id
			if(!processDefinitionId.startsWith("auditAuthProcess")){
				continue;
			}
			i++;	
			if(i!=53){
				continue;
			}
			
			String processInstanceId = task.getProcessInstanceId();
			String showProcessDefName = getMatchShowPhotoTemplate(taskService,taskId,processDefinitionId);
			ProcessDefinition processDefinitionTemp = repositoryService.createProcessDefinitionQuery().processDefinitionKey(showProcessDefName).active().latestVersion().singleResult();
			String processDefinitionIdTemp = processDefinitionTemp.getId();
			
			//获取当前节点数据信息
			Map<String, Map<String, Object>> mapTemp = getShowNodePosition(repositoryService,processDefinitionIdTemp);
			Map<String,Object> infoUserTask = new HashMap<String, Object>();//客户信息
			
			
			//============================
			List<String> variableNames = new ArrayList<String>();
			variableNames.add("authUserId1List");//审核一级
			variableNames.add("authUserId2List");//审核二级
			variableNames.add("authUserId3List");//审核三级
			variableNames.add("authUserId4List");//审核四级
			variableNames.add("authUserId5List");//审核五级
			variableNames.add("roleNumber1");//一级授权人数
			variableNames.add("roleNumber2");//二级授权人数
			variableNames.add("roleNumber3");//三级授权人数
			variableNames.add("roleNumber4");//四级授权人数
			variableNames.add("roleNumber5");//五级授权人数
			variableNames.add("orderly");//审核有序无序
			variableNames.add("userIdList");//对客双人管理(管理类授权)
			//获取流程变量
	        Map<String,Object> varsMap = taskService.getVariables(taskId, variableNames);
	       
            if(processDefinitionId.startsWith("auditAuthProcess")){//审核式
            	String roleNumber1Str = String.valueOf(varsMap.get("roleNumber1"));
    	        String roleNumber2Str = String.valueOf(varsMap.get("roleNumber2"));
    	        String roleNumber3Str = String.valueOf(varsMap.get("roleNumber3"));
    	        String roleNumber4Str = String.valueOf(varsMap.get("roleNumber4"));
    	        String roleNumber5Str = String.valueOf(varsMap.get("roleNumber5"));
    	        String orderly = String.valueOf(varsMap.get("orderly"));
    	        if(ValidateUtil.isEmpty(roleNumber1Str)){
    	        	 roleNumber1Str = "0";
    	        }
    	        if(ValidateUtil.isEmpty(roleNumber2Str)){
    	        	 roleNumber2Str = "0";
    	        }
    	        if(ValidateUtil.isEmpty(roleNumber3Str)){
    	        	 roleNumber3Str = "0";
    	        }
    	        if(ValidateUtil.isEmpty(roleNumber4Str)){
    	        	 roleNumber4Str = "0";
    	        }
    	        if(ValidateUtil.isEmpty(roleNumber5Str)){
    	        	 roleNumber5Str = "0";
    	        }
    	         
    	        int roleNumber1 = Integer.parseInt(roleNumber1Str);
    	        int roleNumber2 = Integer.parseInt(roleNumber2Str);
    	        int roleNumber3 = Integer.parseInt(roleNumber3Str);
    	        int roleNumber4 = Integer.parseInt(roleNumber4Str);
    	        int roleNumber5 = Integer.parseInt(roleNumber5Str);
    	        
    	        List<String> userIdList = new ArrayList<String>();
    	        List<String> authUserId1ListTemp = null;
    	        List<String> authUserId2ListTemp = null;
    	        List<String> authUserId3ListTemp = null;
    	        List<String> authUserId4ListTemp = null;
    	        List<String> authUserId5ListTemp = null;
    	        //查询柜员操作基本信息和角色
    	        if(roleNumber1 > 0){
    	        	authUserId1ListTemp = (List<String>) varsMap.get("authUserId1List");
    	        	userIdList.addAll(authUserId1ListTemp);
    	        	if(roleNumber2>0){
    	        		authUserId2ListTemp = (List<String>) varsMap.get("authUserId2List");
    	        		userIdList.addAll(authUserId2ListTemp);
    	        		if(roleNumber3>0){
    	        			authUserId3ListTemp = (List<String>) varsMap.get("authUserId3List");
    	        			userIdList.addAll(authUserId3ListTemp);
    	        			if(roleNumber4>0){
    	        				authUserId4ListTemp = (List<String>) varsMap.get("authUserId4List");
    	        				userIdList.addAll(authUserId4ListTemp);
    	        				if(roleNumber5>0){
    	        					authUserId5ListTemp = (List<String>) varsMap.get("authUserId5List");
    	        					userIdList.addAll(authUserId5ListTemp);
    	        				}
    	        			}
    	        		}
    	        	}
    	        }
    	        //查询柜员角色信息
    	        List<Map<String,Object>> userRoleInfoList = qryInnerUserService.qryTellerRoleInfoByUserId(legalId, userIdList);
    	        if(userRoleInfoList == null || userRoleInfoList.size()<=0){
    	        	continue;
    	        }
    	        
    	        
            	ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
    			List<ActivityImpl> list = pd.getActivities();
    			for(ActivityImpl activityImpl : list){
    				 Object taskDefinitionObj = activityImpl.getProperty("taskDefinition");
    				 if(taskDefinitionObj != null && taskDefinitionObj instanceof TaskDefinition){
    					 TaskDefinition taskDefinition = (TaskDefinition) taskDefinitionObj;
    			         String key = taskDefinition.getKey();
    			         if("1".equals(orderly)){//有序
    			        	 if(!("usertask2".equals(key) || "usertask8".equals(key) || "usertask9".equals(key) || 
    			        			 "usertask10".equals(key) ||"usertask11".equals(key))){
    			        		 continue;
    			        	 }
    			         }else{//无序
    			        	 if(!("usertask3".equals(key) || "usertask4".equals(key) || "usertask5".equals(key) || 
    			        			 "usertask6".equals(key) ||"usertask7".equals(key))){
    			        		 continue;
    			        	 }
    			         }
    			         
    			         List<Map<String,Object>> listTemp = new ArrayList<Map<String,Object>>();
			        	 if("usertask2".equals(key) || "usertask3".equals(key)){//有序一级(usertask2)   无序一级(usertask3)
			        		 if(authUserId1ListTemp != null && authUserId1ListTemp.size()>0){
			        			 userTaskShowMethod(historyService,processInstanceId, authUserId1ListTemp,userRoleInfoList, listTemp,type);
			        		 }
			        	 }else if("usertask8".equals(key) || "usertask4".equals(key)){//有序二级(usertask8)   无序二级(usertask4)
			        		 if(authUserId2ListTemp != null && authUserId2ListTemp.size()>0){
			        			 userTaskShowMethod(historyService,processInstanceId, authUserId2ListTemp,userRoleInfoList, listTemp,type);
			        		 }
			        	 }else if("usertask9".equals(key) || "usertask5".equals(key)){//有序三级(usertask9)   无序三级(usertask5)
			        		 if(authUserId3ListTemp != null && authUserId3ListTemp.size()>0){
			        			 userTaskShowMethod(historyService,processInstanceId, authUserId3ListTemp,userRoleInfoList, listTemp,type);
			        		 }
			        	 }else if("usertask10".equals(key) || "usertask6".equals(key)){//有序四级(usertask10)   无序四级(usertask6)
			        		 if(authUserId4ListTemp != null && authUserId4ListTemp.size()>0){
			        			 userTaskShowMethod(historyService,processInstanceId, authUserId4ListTemp,userRoleInfoList, listTemp,type);
			        		 }
			        	 }else if("usertask11".equals(key) || "usertask7".equals(key)){//有序五级(usertask11)   无序五级(usertask7)
			        		 if(authUserId5ListTemp != null && authUserId5ListTemp.size()>0){
			        			 userTaskShowMethod(historyService,processInstanceId, authUserId5ListTemp,userRoleInfoList, listTemp,type);
			        		 }
			        	 }
    			         if(listTemp != null && listTemp.size()>0){
    			        	 infoUserTask.put(key, listTemp);
    			         }
    				 }
    			}
            }else if(processDefinitionId.startsWith("manageAuthProcess")){//管理类授权
            	List<String> userIdList = (List<String>) varsMap.get("userIdList");//当前授权操作员
            	List<Map<String,Object>> userInfoList = qryOutSideUserService.qryManageUserRoleInfo(legalId, cstNo, userIdList);
            	//查询管理类授权角色信息
            	if(userInfoList == null || userInfoList.size()<=0){
    	        	continue;
    	        }
            	
            	ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
    			List<ActivityImpl> list = pd.getActivities();
    			for(ActivityImpl activityImpl : list){//遍历流程
    				 Object taskDefinitionObj = activityImpl.getProperty("taskDefinition");
    				 if(taskDefinitionObj != null && taskDefinitionObj instanceof TaskDefinition){
    					 TaskDefinition taskDefinition = (TaskDefinition) taskDefinitionObj;
    			         String key = taskDefinition.getKey();
    			         List<Map<String,Object>> listTemp = new ArrayList<Map<String,Object>>();
    			         if("usertask12".equals(key)){//查询柜员数据信息
    			        	 userTaskShowMethod(historyService,processInstanceId, userIdList,userInfoList, listTemp,type);
    			         }
    				 }
    			}   
            }			
			
            //进行数据组装
            if(mapTemp != null && mapTemp.size()>0 && infoUserTask !=null && infoUserTask.size()>0){
            	Set<String> set = mapTemp.keySet();
            	for(Iterator<String> it = set.iterator();it.hasNext();){
            		String key = it.next();
            		Map<String,Object> result = mapTemp.get(key);
            		Object objectUserInfo = infoUserTask.get(key);
            		result.put("userInfo", objectUserInfo);
            	}
            }
            
            logger.info("=======over="+mapTemp);
			
		}
		
		
		
		
		
	}

 
    /**
     * 
     * @Title: userTaskShowMethod 
     * @Description: 审核式授权流程图，明细数据展示封装
     * @param historyService   工作流历史查询服务
     * @param processInstanceId  流程实例id
     * @param authUserIdListTemp  当前节点授权用户id
     * @param userRoleInfoList   授权用户、角色信息
     * @param listTemp   返回结果
     * @param type   操作类型    1：行内    2：对客
     */
	private static void userTaskShowMethod(HistoryService historyService,
			String processInstanceId, List<String> authUserIdListTemp,
			List<Map<String, Object>> userRoleInfoList,List<Map<String, Object>> listTemp,String type) {
		if("1".equals(type) || "2".equals(type)){
			return;
		}
		for(String assignee : authUserIdListTemp){
			 Map<String,Object> resultTempTemp = new HashMap<String, Object>();
			 String branchCode = "";//机构编号
			 String tellerCode = "";//柜员编号
			 String tellerName = "";//柜员名称
			 String roleName = "";//角色名称
			 
			 String endDateStr = "";//操作时间
			 
			 HistoricTaskInstance historicTaskInstance= historyService.createHistoricTaskInstanceQuery()
					  .processInstanceId(processInstanceId)
					  .taskAssignee(assignee)
					  .taskDeleteReason("completed")
					  .singleResult();
			if(historicTaskInstance != null){
				endDateStr = DateUtil.formatDate(historicTaskInstance.getEndTime(), DateUtil.DEFAULT_TIME_FORMAT_DB);
			}
			
			for(Map<String,Object> temp : userRoleInfoList){//变量操作员信息
				String userId = String.valueOf(temp.get("userId"));
				if(!userId.equals(assignee)){
					continue;
				}
				if(!ValidateUtil.isEmpty(roleName)){
					roleName = roleName+",";
				}
				
				String roleNameTemp = "";
				if("1".equals(type)){//行内
					branchCode = String.valueOf(temp.get("branchCode"));
					tellerCode = String.valueOf(temp.get("tellerCode"));
					tellerName = String.valueOf(temp.get("tellerName"));
					
					roleNameTemp = String.valueOf(temp.get("roleName"));
				}else if("2".equals(type)){//对客
					branchCode = String.valueOf(temp.get("cstNo"));
					tellerCode = String.valueOf(temp.get("userNo"));
					tellerName = String.valueOf(temp.get("userName"));
					
					roleNameTemp = String.valueOf(temp.get("name"));
				}
				
				String compareRoleName = ","+roleName+",";
				if(compareRoleName.indexOf(","+roleNameTemp+",") == -1){//不存在
					roleName = roleName+roleNameTemp;
				}
			}
			if("1".equals(type)){
				resultTempTemp.put("branchCode", branchCode);
				resultTempTemp.put("tellerCode", tellerCode);
				resultTempTemp.put("tellerName", tellerName);
			}else if("2".equals(type)){
				resultTempTemp.put("cstNo", branchCode);
				resultTempTemp.put("userNo", tellerCode);
				resultTempTemp.put("userName", tellerName);
			}
			resultTempTemp.put("roleName", roleName);
			resultTempTemp.put("endDateStr", endDateStr);
			if(!listTemp.contains(resultTempTemp)){
				listTemp.add(resultTempTemp);
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
			}else{//无序
				if(!"0".equals(roleNumber1) && "0".equals(roleNumber2)){//一级授权
					processName = "OneNotOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && "0".equals(roleNumber3)){//二级授权
					processName = "TwoNotOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						"0".equals(roleNumber4)){//三级授权
					processName = "ThreeNotOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						!"0".equals(roleNumber3) && "0".equals(roleNumber5)){//四级授权
					processName = "FourNotOrderProcessShow";
				}else if(!"0".equals(roleNumber1) && !"0".equals(roleNumber2) && !"0".equals(roleNumber3) && 
						!"0".equals(roleNumber3) && !"0".equals(roleNumber5)){//五级授权
					processName = "FiveNotOrderProcessShow";
				}
			}
			
		}else if(processDefinitionId.startsWith("manageAuthProcess")){//管理类授权
			processName = "manageAuthProcessShow";
		}else if(processDefinitionId.startsWith("counterAuthProcess")){//临柜授权
			processName = "counterAuthProcessShow";
		}
    	
    	return processName;
    }
    
    /**
     * 获取前端展示的坐标节点
     */
    public static Map<String,Map<String,Object>> getShowNodePosition(RepositoryService repositoryService,String processDefinitionId){
    	 ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		 List<ActivityImpl> list = pd.getActivities();
		 Map<String,Map<String,Object>> resultPosition = new HashMap<String, Map<String,Object>>();
		 for(ActivityImpl activityImpl : list){
			 Object taskDefinitionObj = activityImpl.getProperty("taskDefinition");
			 if(taskDefinitionObj != null && taskDefinitionObj instanceof TaskDefinition){
				 TaskDefinition taskDefinition = (TaskDefinition) taskDefinitionObj;
		         String key = taskDefinition.getKey();
		         int x = activityImpl.getX();
				 int y = activityImpl.getY();
				 int height = activityImpl.getHeight();
				 int width = activityImpl.getWidth();
				 Map<String,Object> mapTemp = new HashMap<String, Object>();
				 mapTemp.put("x", x);
				 mapTemp.put("y", y);
				 mapTemp.put("height", height);
				 mapTemp.put("width", width);
				 resultPosition.put(key, mapTemp);
			 }
		 }
		 return resultPosition;
    }
}