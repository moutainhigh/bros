package bros.provider.parent.activiti.delegate;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.json.JSONArray;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.ITradeParamService;

/** 
 * @ClassName: CallBackServiceImpl 
 * @Description: 工作流业务回调服务
 * @author weiyancheng
 * @date 2016年8月3日 下午3:48:12 
 * @version 1.0 
 */
@Component("authFLowCallBackNoContextBase")
public class AuthFLowCallBackNoContextBase extends ApplicationObjectSupport {

	private static final Logger logger = LoggerFactory.getLogger(AuthFLowCallBackNoContextBase.class);
	
	/** 
	 * @Description: 工作流业务回调方法
	 */
	@SuppressWarnings("unchecked")
	public void callBack(DelegateExecution execution) throws Exception {
		try {
	    	//流程变量
			Map<String, Object> data = execution.getVariables();
			//取回调服务所需要的数据
			ITradeParamService tradeParamServiceImpl = (ITradeParamService) this.getApplicationContext().getBean("tradeParamService");
			String businessKey = (String) data.get("businessKey");
			Map<String, Object> serviceMap = tradeParamServiceImpl.qryTradeParam(businessKey);
			
			//回调bean id
			String springId = (String) serviceMap.get("beanId");
			//回调方法名
			String classMethod = (String) serviceMap.get("method");
			//回调方法类型
			String paramterTypesStr = (String) serviceMap.get("paramTypes");
			JSONArray paramterTypesJson = JSONArray.fromObject(paramterTypesStr);
			Object[] object =  (Object[]) paramterTypesJson.toArray();
			Class<?>[] parameterTypes = new Class<?>[object.length];
			for(int i=0;i<object.length;i++){
				parameterTypes[i] = Class.forName((String)object[i]) ;
			}
			//取回调方法的参数
			String paramterStr = (String) serviceMap.get("bizData");
			JSONArray array = JSONArray.fromObject(paramterStr);
			Object[] parameter = array.toArray();
			//取报文头域
			Map<String, Object> headMap =  (Map<String, Object>) parameter[0];
			//放入回调标识
			headMap.put(ActivitiParamsConstants.ACT_CALLBACK_FALG, true);
			
			//动态获取目标bean
			Object serviceObj = this.getApplicationContext().getBean(springId);
			//反射取目标方法
			Method method = serviceObj.getClass().getDeclaredMethod(classMethod, parameterTypes );		
			//目标方法调用
			try{
				method.invoke(serviceObj, parameter);
			}catch(Exception e){
				logger.error("回调方法报错后，不处理异常...", e);
			}
			//把服务回调数据放到流程变量中,待流程执行完之后存入历史流程变量中
			serviceMap.put("bizData", parameter);
			data.putAll(serviceMap);
			execution.setVariables(data);
			//删除服务回调数据表中的记录
			tradeParamServiceImpl.deleteTradeParam(businessKey);
			
		}catch (Exception e){
			logger.error("回调业务逻辑服务异常...", e);
			throw new BpmnError(ActivitiErrorCodeConstants.PPAI0015,"回调业务逻辑服务异常");
		}
	}

}
