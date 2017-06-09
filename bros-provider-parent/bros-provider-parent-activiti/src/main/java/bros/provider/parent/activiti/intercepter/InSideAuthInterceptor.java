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
import bros.common.core.activiti.inside.service.IInSideActivitiRuntimeService;
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
 * @ClassName: InSideAuthInterceptor 
 * @Description: 内部授权拦截器
 * @author weiyancheng
 * @date 2016年6月30日 上午10:43:44 
 * @version 1.0 
 */
public class InSideAuthInterceptor implements MethodInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(InSideAuthInterceptor.class);
	
	/**
	 * 工作流仓库服务
	 */
	@Autowired
	private IActivitiRepositoryService activitiRepositoryService;
	/**
	 * 内部授权处理器服务
	 */
	@Autowired
	private IAuthProcessorService innerAuthProcessorService;
	/**
	 * 内部授权之工作流RuntimeService
	 */
	@Autowired
	private IInSideActivitiRuntimeService inSideActivitiRuntimeService;
	/**
	 * 回调服务数据处理服务
	 **/
	@Autowired
	private ITradeParamService tradeParamService;
	/**
	 * 查询渠道标识服务
	 */
	@Autowired
	private IChannelService channelServiceImpl;
	
	@SuppressWarnings({ "unchecked"})
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
			//判断是否是回调
			Map<String, Object> bodyMap = (Map<String, Object>) parameterValues[0];
			Object flag = headMap.get(ActivitiParamsConstants.ACT_CALLBACK_FALG);
			if(null!=flag && (boolean)flag){
				return invocation.proceed();
			}
			logger.info("内部授权拦截器拦截"+className+"的"+methodName+"方法...");
			//渠道
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			
			//流水号
			String globalSeqNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_GLOBALSEQNO);
			//根据法人和渠道编号查询渠道信息渠道类型
			Map<String, Object> channelMap = channelServiceImpl.qryChannelFlag(channel);
			if(null == channelMap || channelMap.size()<1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0019,"渠道未定义");
			}
			String channelFlag = (String) channelMap.get("flag");
			String checkAuth = (String) channelMap.get("checkAuth");
			
			//以下为内管、柜面渠道（对内渠道）授权处理
			if(!ValidateUtil.isEmpty(channelFlag) && BaseParamsConstants.TRADE_CHANNEL_TYPE_INNER.equals(channelFlag)
					&& !ValidateUtil.isEmpty(checkAuth) && BaseParamsConstants.TRADE_CHANNEL_AUTH_YES.equals(checkAuth))
			{
				//内部授权处理（判断是否需要授权以及流程变量处理）
				Map<String, Object> activitiMap = new HashMap<String, Object>();
				Map<String, Object> returnMap = new HashMap<String, Object>();
				boolean AuthFlag = innerAuthProcessorService.authProcess(varialable, headMap, bodyMap,returnMap,activitiMap);
				if(!AuthFlag){//无需授权
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
					//如果是条件授权，直接返回
					String authMode = (String) returnMap.get(ActivitiParamsConstants.ACT_FIELD_AUTHMODE);
					if(!ValidateUtil.isEmpty(authMode) && authMode.equals(ActivitiParamsConstants.ACT_AUTHMODE_2)){
						ResponseEntity entity = new ResponseEntity(returnMap);
						return entity;
					}
					//流程定义id
					String procDefId = (String) activitiMap.get("procDefId");
					//查询流程定义是否存在
					ProcessDefinition pd = activitiRepositoryService.queryProcessDefinitionLastByKey(procDefId);
					if(null==pd){
						throw new ServiceException(ActivitiErrorCodeConstants.PPAI0012,"流程定义不存在");
					}
					String userId = (String) activitiMap.get("userId");
					//启动流程
					ProcessInstance pi = inSideActivitiRuntimeService.startAuthWorkFlowByKey(procDefId, userId, globalSeqNo, varialable);
					logger.info("启动内部授权流程，流程定义ID:"+procDefId+"，流程实例ID:"+pi.getId());
					//流程实例id
					String pid = pi.getId();
					//组装返回给前台的数据
					String authShap = (String) returnMap.get("authShap");//授权形式
					//如果是临柜授权、要把流程实例id返给前台
					if(ActivitiParamsConstants.ACT_AUTHSHAP_1.equals(authShap)){
						returnMap.put(ActivitiParamsConstants.ACT_FIELD_PROCESSINSTANCEID, pid);
					}
					//设置指令状态为等待授权
					returnMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, TradeStatusParamsConstants.ORDER_STATE_10);
					ResponseEntity entity = new ResponseEntity(returnMap);
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
