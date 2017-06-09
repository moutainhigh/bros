package bros.provider.parent.activiti.intercepter;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.common.core.activiti.outside.service.IOutSideActivitiRuntimeService;
import bros.common.core.activiti.service.IActivitiRepositoryService;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.TradeStatusParamsConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.IChannelService;
import bros.provider.parent.activiti.relation.service.ITradeParamService;
import bros.provider.parent.activiti.service.IAuthProcessorService;



/** 
 * @ClassName: AuthInterceptor 
 * @Description: 外部授权拦截器
 * @author weiyancheng
 * @date 2016年6月30日 上午10:43:44 
 * @version 1.0 
 */
public class OutSideAuthInterceptor implements MethodInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(OutSideAuthInterceptor.class);
	
	/**
	 * 工作流仓库服务
	 */
	@Autowired
	private IActivitiRepositoryService activitiRepositoryService;
	/**
	 * 查询渠道标识服务
	 */
	@Autowired
	private IChannelService channelService;
	/**
	 * 回调服务数据处理服务
	 **/
	@Autowired
	private ITradeParamService tradeParamService;
	/**
	 * 外部授权处理服务
	 */
	@Autowired
	private IAuthProcessorService outSideAuthProcessorService;
	/**
	 * 管理类授权流程定义ID
	 */
	private static final String MANAGE_AUTH_PROCEDEF_ID = "manageAuthProcess";
	/**
	 * 业务审核式授权流程定义ID
	 */
	private static final String BUSINESS_AUTH_PROCEDEF_ID = "auditAuthProcess";
	
	@Autowired
	private IOutSideActivitiRuntimeService outSideActivitiRuntimeService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try{
			//获取要执行Service的类名
			String className = invocation.getThis().getClass().getName();
			//获取要执行Service的方法名
			String methodName = invocation.getMethod().getName();
			//获取要执行Service的方法名的参数对应值
			Object[] parameterValues = invocation.getArguments();
			
			//启动流程用到的流程变量
			Map<String, Object> varialable = new HashMap<String, Object>();
			
			//获取报文头数据域
			Map<String, Object> headMap = (Map<String, Object>) parameterValues[0];
			//获取报文体数据域
			Map<String, Object> bodyMap = (Map<String, Object>) parameterValues[0];
			//判断是否是回调
			Object flag = headMap.get(ActivitiParamsConstants.ACT_CALLBACK_FALG);
			if(null!=flag && (boolean)flag){
				return invocation.proceed();
			}
			logger.info("外部授权拦截器拦截"+className+"的"+methodName+"方法...");
			//渠道
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			//流水号
			String globalSeqNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_GLOBALSEQNO);
			//根据法人和渠道编号查询渠道信息渠道类型
			Map<String, Object> channelMap = channelService.qryChannelFlag(channel);
			String channelFlag = (String) channelMap.get("flag");
			String checkAuth = (String) channelMap.get("checkAuth");
			
			//外部渠道授权处理（企业网银渠道）
			if(!ValidateUtil.isEmpty(channelFlag) && BaseParamsConstants.TRADE_CHANNEL_TYPE_EXTERNAL.equals(channelFlag)
					&& !ValidateUtil.isEmpty(checkAuth) && BaseParamsConstants.TRADE_CHANNEL_AUTH_YES.equals(checkAuth))
			{
				//外部授权处理（判断是否需要授权以及流程变量处理）
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, Object> activitiMap = new HashMap<String, Object>();
				boolean auth = outSideAuthProcessorService.authProcess(varialable, headMap, bodyMap, resultMap, activitiMap);
				
				if(!auth){//无需授权
					Object obj = invocation.proceed();
					if(obj!=null && obj instanceof ResponseEntity){
						ResponseEntity result = (ResponseEntity) obj;
						Map<String, Object> transResultMap = result.getResponseMap();
						if(transResultMap==null){
							transResultMap = new HashMap<String, Object>();
						}
						//返回结果中放入指令状态
						transResultMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, TradeStatusParamsConstants.AUTH_RESULT_2);
					}
					return obj;
				}else{//需要授权
					//把回调服务需要的数据存入数据库
					tradeParamService.insertCallBackServiceData(invocation);
					//判断是管理授权还是业务类授权
					String authType = (String) activitiMap.get("authType");
					//流程定义ID
					String proceDefId = "";
					if("0".equals(authType)){//管理类授权
						proceDefId = MANAGE_AUTH_PROCEDEF_ID;
					}else{
						proceDefId = BUSINESS_AUTH_PROCEDEF_ID;
					}
					//查询流程定义是否存在
					ProcessDefinition pd = activitiRepositoryService.queryProcessDefinitionById(proceDefId);
					if(null==pd){
						throw new ServiceException(ActivitiErrorCodeConstants.PPAI0012,"流程定义不存在");
					}
					String userId = (String) activitiMap.get("userId");
					//启动流程
					ProcessInstance pi = outSideActivitiRuntimeService.startAuthWorkFlowByKey(proceDefId, userId, globalSeqNo, varialable);
					logger.info("启动企业授权流程，流程定义ID:"+proceDefId+"，流程实例ID:"+pi.getId());
					//设置指令状态为等待授权
					resultMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, TradeStatusParamsConstants.ORDER_STATE_10);
					ResponseEntity entity = new ResponseEntity(resultMap);
					return entity;
				}
			}
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".invoke");
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".invoke");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000, "授权处理失败", e);
		}
		return null;
	}

}
