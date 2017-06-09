package bros.unified.receive.business.detail.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.c.monitor.service.ICApplicationAliveMonitorAuthServiceFacade;
import bros.c.monitor.service.ICApplicationAliveMonitorBankmanageServiceFacade;
import bros.c.monitor.service.ICApplicationAliveMonitorCommonServiceFacade;
import bros.c.monitor.service.ICApplicationAliveMonitorCustmanageServiceFacade;
import bros.c.monitor.service.ICApplicationAliveMonitorLoginServiceFacade;
import bros.c.monitor.service.ICApplicationAliveMonitorSecurityServiceFacade;
import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.constants.HeadRecieveConstants;
import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.generator.flow.FlowGenerator;
import bros.common.core.util.DubboUtil;
import bros.common.core.util.ValidateUtil;
import bros.unified.receive.cache.IBmaVersionConfigCacheInfo;
import bros.unified.receive.constants.RecieveErrorCodeConstants;
/**
 * 
 * @ClassName: BusinessProcessServiceImpl 
 * @Description: 业务处理调度中心
 * @author 何鹏
 * @date 2016年7月1日 下午3:38:29 
 * @version 1.0
 */
public class BusinessProcessServiceImpl implements IBusinessProcessService{
	private static final Logger logger = LoggerFactory.getLogger(BusinessProcessServiceImpl.class);
	/**
	 * 
	 * @Title: businessDetail 
	 * @Description: 业务处理器
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> businessDetail(final ApplicationContext context,Map<String,Object> params,AppConfig config) throws ServiceException{
		if(params == null){//监控服务需要直接调用服务进行处理数据
			return applicationAliveMonitorMethod(context,config);
		}
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Map<String,Object> returnHeadMap = new HashMap<String, Object>();
		Map<String,Object> returnBodyMap = new HashMap<String, Object>();
		
		String channelDate = "";
		String tranDate = "";
		String tranTime = "";
		String backendSeqNo = "";
		String globalSeqNo = "";
		try{
			Object objHead = params.get(HeadRecieveConstants.REQ_HEAD_PARAMS_NAME);
			Object objBody = params.get(HeadRecieveConstants.REQ_BODY_PARAMS_NAME);
			if(objHead == null){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0005,"请求报文头（"+HeadRecieveConstants.REQ_HEAD_PARAMS_NAME+"）不存在");
			}
			if(objBody == null){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0006,"请求报文体（"+HeadRecieveConstants.REQ_BODY_PARAMS_NAME+"）不存在");
			}
			if(!(objHead instanceof Map)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0007,"请求报文头格式不正确");
			}
			if(!(objBody instanceof Map)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0008,"请求报文体格式不正确");
			}
			
			Map<String,Object> headMap = (Map<String, Object>) objHead;//报文头
			Map<String,Object> bodyMap = (Map<String, Object>) objBody;//报文体
			
			channelDate = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CHANNELDATE));
			tranDate = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANDATE));
			tranTime = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANTIME));
			backendSeqNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANSEQNO));
			globalSeqNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_GLOBALSEQNO));
			
			String serviceName = String.valueOf(headMap.get(HeadRecieveConstants.REC_SERVICE_NAME));
			String version = String.valueOf(headMap.get(HeadRecieveConstants.REC_SERVICE_VERSION));
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			String channelCode = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
			if(ValidateUtil.isEmpty(serviceName)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0003,"serviceName字段不能为空");
			}
			if(ValidateUtil.isEmpty(channelCode)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0016,"channel字段不能为空");
			}
			if(ValidateUtil.isEmpty(legalId)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0014,"legalId字段不能为空");
			}
			
			//是否由渠道集成平台生成流水号
			Object objectFlowProductFlag = config.getFlowProductFlag();
			if(objectFlowProductFlag!=null && objectFlowProductFlag instanceof Map){
				Map<String,String> flowProductFlag = (Map<String, String>) objectFlowProductFlag;
				Set<String> set = flowProductFlag.keySet();
				for(Iterator<String> it = set.iterator();it.hasNext();){
					String key = it.next();
					String value = flowProductFlag.get(key);
					if(channelCode.equals(value)){//由渠道集成平台生成流水号
						if(ValidateUtil.isEmpty(backendSeqNo) || backendSeqNo.length() <30){
							Object objectEntity = context.getBean("flowGenerator");
							if(objectEntity != null && objectEntity instanceof FlowGenerator){
								FlowGenerator flowGenerator = (FlowGenerator) objectEntity;
								String taxi = String.valueOf(headMap.get(HeadParameterDefinitionConstants.PRE_TAXI));
								String sendSystemId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CONSUMERID));
								String sceneCode = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_SCENECODE));
								String stepCode = String.valueOf(headMap.get(HeadParameterDefinitionConstants.PRE_STEP_CODE));
								String fillRightStr = config.getFillRightStr();
								globalSeqNo = flowGenerator.getGlobalSeqNo(taxi, sendSystemId, sceneCode, stepCode);
								backendSeqNo = globalSeqNo+channelCode+fillRightStr;
								
								headMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO,backendSeqNo);
								headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,globalSeqNo);
							}
						}else{
							if(ValidateUtil.isEmpty(globalSeqNo)){
								globalSeqNo = backendSeqNo.substring(0,30);
								headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,globalSeqNo);
							}
						}
						break;
					}
				}
				
			}
			
			
			if(ValidateUtil.isEmpty(version)){
				//版本管理工具
				IBmaVersionConfigCacheInfo bmaVersionConfigCacheInfo = (IBmaVersionConfigCacheInfo) context.getBean("bmaVersionConfigCacheInfo");
				List<Map<String,Object>> list = bmaVersionConfigCacheInfo.queryVersionCongfig(legalId, serviceName);
				if(list == null || list.size()<=0){
					throw new ServiceException(RecieveErrorCodeConstants.UREE0015,"未配置服务版本信息，请到消费方的bma_version_config表中进行配置");
				}
				
				Map<String,Object> mapAll = null;
				for(Map<String,Object> map : list){
					String channelCodeTemp = (String) (map.get("channelCode")==null?"":map.get("channelCode"));
					String versionTemp = (String) (map.get("version")==null?"":map.get("version"));
					if(channelCode.equals(channelCodeTemp)){
						version = versionTemp;
						break;
					}else if("".equals(channelCodeTemp)){
						mapAll = map;
					}
				}
				
				if(ValidateUtil.isEmpty(version)){
					if(mapAll != null && mapAll.size()>0){
						version = (String) (mapAll.get("version")==null?"":mapAll.get("version"));
					}
				}
				
			}
			
			if(ValidateUtil.isEmpty(version)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0004,"version字段不能为空");
			}
			String beanId = serviceName+"_"+version;
			String methodName = DubboUtil.getMethodNameByBeanId(context, beanId);
			if(ValidateUtil.isEmpty(methodName)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0011,"统一接入服务配置不正确");
			}
			//调用业务服务处理
			Object serviceObj = context.getBean(beanId);
			Method method = serviceObj.getClass().getDeclaredMethod(methodName, new Class[]{Map.class,Map.class} );
			Object[] objArrys = {headMap,bodyMap};
			
			Object obj = method.invoke(serviceObj, objArrys);
			
			if(obj == null){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0009,"返回对象不能为空");
			}
			
			if(!(obj instanceof ResponseEntity)){
				throw new ServiceException(RecieveErrorCodeConstants.UREE0010,"返回对象不为ResponseEntity");
			}
			
			ResponseEntity entity = (ResponseEntity) obj;
			
			return getResultMapFromResponseEntity(entity);
		}catch(ServiceException ex){
			logger.error("unified receive call service exception："+ex.getErrorCode()+"|"+ex.getErrorMsg(), ex);
			Map<String,Object> resultMap = ex.getResult();
			if(resultMap != null && resultMap.size()>0){
				ResponseEntity responseEntity = new ResponseEntity(resultMap);
				try {
					return getResultMapFromResponseEntity(responseEntity);
				} catch (Exception e) {
					returnHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, RecieveErrorCodeConstants.UREE0012);
					returnHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, "封装异常返回信息失败");
					logger.error("unified receive call service exception："+RecieveErrorCodeConstants.UREE0012+"|封装异常返回信息失败", e);
				}
			}else{
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, ex.getErrorCode());
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, ex.getErrorMsg());
			}
		}catch(Exception e){
			try{
				ServiceException se = (ServiceException) e.getCause();
				logger.error("unified receive call service exception："+se.getErrorCode()+"|"+se.getErrorMsg(), e);
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, se.getErrorCode());
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, se.getErrorMsg());
			}catch(Exception exx){
				logger.error("unified receive call service exception："+RecieveErrorCodeConstants.UREE0002+"|业务处理未知错误", exx);
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, RecieveErrorCodeConstants.UREE0002);
				returnHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, "业务处理未知错误");
			}
		}
		
		returnHeadMap.put(HeadRecieveConstants.SEN_CHANNELDATE, channelDate);
		returnHeadMap.put(HeadRecieveConstants.SEN_TRANDATE, tranDate);
		returnHeadMap.put(HeadRecieveConstants.SEN_TRANTIME, tranTime);
		returnHeadMap.put(HeadRecieveConstants.SEN_BACKENDSEQNO, backendSeqNo);
		returnHeadMap.put(HeadRecieveConstants.SEN_GLOBALSEQNO, globalSeqNo);
		
		//返回错误报文头
		returnMap.put(HeadRecieveConstants.RSP_HEAD_PARAMS_NAME, returnHeadMap);
		returnMap.put(HeadRecieveConstants.RSP_BODY_PARAMS_NAME, returnBodyMap);
		return returnMap;
		
	}
	
	private Map<String,Object> getResultMapFromResponseEntity(ResponseEntity entity) throws Exception{
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Map<String,Object> returnHeadMap = new HashMap<String, Object>();//返回报文头
		//返回报文头
		returnHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, entity.getReturnCode());
		returnHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, entity.getReturnMsg());
		returnHeadMap.put(HeadRecieveConstants.SEN_CHANNELDATE, entity.getChannelDate());
		returnHeadMap.put(HeadRecieveConstants.SEN_TRANDATE, entity.getTranDate());
		returnHeadMap.put(HeadRecieveConstants.SEN_TRANTIME, entity.getTranTime());
		returnHeadMap.put(HeadRecieveConstants.SEN_BACKENDSEQNO, entity.getBackendSeqNo());
		returnHeadMap.put(HeadRecieveConstants.SEN_BACKENDSYSID, entity.getBackendSysId());
		returnHeadMap.put(HeadRecieveConstants.SEN_GLOBALSEQNO, entity.getGlobalSeqNo());
		returnHeadMap.put(HeadRecieveConstants.SEN_ACCDATE, entity.getAcctDate());
		returnHeadMap.put(HeadRecieveConstants.SEN_LANGCODE, entity.getLangCode());
		returnHeadMap.put(HeadRecieveConstants.SEN_RSRVCONTENT, entity.getRsrvContent());
		
		//返回报文体
		Map<String,Object> returnBodyMap = entity.getResponseMap();
		
		returnMap.put(HeadRecieveConstants.RSP_HEAD_PARAMS_NAME, returnHeadMap);
		returnMap.put(HeadRecieveConstants.RSP_BODY_PARAMS_NAME, returnBodyMap);
		
		return returnMap;
	}
	
	
	private Map<String,Object> applicationAliveMonitorMethod(final ApplicationContext context,AppConfig configApp){
		ICApplicationAliveMonitorAuthServiceFacade authService = (ICApplicationAliveMonitorAuthServiceFacade) context.getBean("applicationAliveMonitorAuthAction_1.0.0");
		ICApplicationAliveMonitorBankmanageServiceFacade bankmanageService = (ICApplicationAliveMonitorBankmanageServiceFacade) context.getBean("applicationAliveMonitorBankmanageAction_1.0.0");
		ICApplicationAliveMonitorCommonServiceFacade commonService = (ICApplicationAliveMonitorCommonServiceFacade) context.getBean("applicationAliveMonitorCommonAction_1.0.0");
		ICApplicationAliveMonitorCustmanageServiceFacade custmanageService = (ICApplicationAliveMonitorCustmanageServiceFacade) context.getBean("applicationAliveMonitorCustmanageAction_1.0.0");
		ICApplicationAliveMonitorLoginServiceFacade loginService = (ICApplicationAliveMonitorLoginServiceFacade) context.getBean("applicationAliveMonitorLoginAction_1.0.0");
		ICApplicationAliveMonitorSecurityServiceFacade securityService = (ICApplicationAliveMonitorSecurityServiceFacade) context.getBean("applicationAliveMonitorSecurityAction_1.0.0");
		IMyBatisDaoSysDao myBatisDaoSysDao = (IMyBatisDaoSysDao) context.getBean("myBatisDaoSysDao");
		
		Map<String,Object> headMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		bodyMap.put("flagDb", "1");
		try {
			list.addAll(authService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-auth");
			list.add(callMap);
		}
		
		try {
			list.addAll(bankmanageService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-bankmanage");
			list.add(callMap);
		}
		try {
			list.addAll(commonService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-common");
			list.add(callMap);
		}
		try {
			list.addAll(custmanageService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-custmanage");
			list.add(callMap);
		}
		try {
			list.addAll(loginService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-login");
			list.add(callMap);
		}
		try {
			list.addAll(securityService.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-consumer-security");
			list.add(callMap);
		}
		
		Map<String,Object> callRecieveMap = new HashMap<String, Object>();
		try{
			myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.puberrcode.selectCountMonitorApplicationAlive");
			callRecieveMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
			callRecieveMap.put("returnMsg", "统一接入数据库状态正常");
			callRecieveMap.put("modelName", "bros-unified-receive");
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			callRecieveMap.put("returnCode", "0001");
			callRecieveMap.put("returnMsg", "统一接入数据库状态不正常");
			callRecieveMap.put("modelName", "bros-unified-receive");
		}
		list.add(callRecieveMap);
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result", list);
		return returnMap;
		
	}
}
