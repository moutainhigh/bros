package bros.c.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.monitor.service.ICApplicationAliveMonitorCommonServiceFacade;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.p.monitor.service.IPApplicationAliveMonitorAccountServiceFacade;
import bros.p.monitor.service.IPApplicationAliveMonitorCommonServiceFacade;
import bros.p.monitor.service.IPApplicationAliveMonitorCustomerServiceFacade;
import bros.p.monitor.service.IPApplicationAliveMonitorFinancialServiceFacade;
import bros.p.monitor.service.IPApplicationAliveMonitorNetbankServiceFacade;
import bros.p.monitor.service.IPApplicationAliveMonitorTransferServiceFacade;
/**
 * 
 * @ClassName: CApplicationAliveMonitorCommonServiceFacadeImpl 
 * @Description: 监听bros-consumer-common模块是否存活
 * @author 何鹏
 * @date 2016年12月19日 下午2:24:53 
 * @version 1.0
 */
@Component("capplicationAliveMonitorCommonServiceFacade")
public class CApplicationAliveMonitorCommonServiceFacadeImpl implements
		ICApplicationAliveMonitorCommonServiceFacade {
	private static final Logger logger = LoggerFactory.getLogger(ICApplicationAliveMonitorCommonServiceFacade.class);
	@Autowired
	private IPApplicationAliveMonitorCommonServiceFacade papplicationAliveMonitorCommonServiceFacade;
	@Autowired
	private IPApplicationAliveMonitorAccountServiceFacade papplicationAliveMonitorAccountServiceFacade;
	@Autowired
	private IPApplicationAliveMonitorCustomerServiceFacade papplicationAliveMonitorCustomerServiceFacade;
	@Autowired
	private IPApplicationAliveMonitorFinancialServiceFacade papplicationAliveMonitorFinancialServiceFacade;
	@Autowired
	private IPApplicationAliveMonitorNetbankServiceFacade papplicationAliveMonitorNetbankServiceFacade;
	@Autowired
	private IPApplicationAliveMonitorTransferServiceFacade papplicationAliveMonitorTransferServiceFacade;
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	@Override
	public List<Map<String,Object>> applicationAliveMonitorMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String modelName = "bros-consumer-common";
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
		returnMap.put("returnMsg", "状态正常");
		returnMap.put("modelName", modelName);
		list.add(returnMap);
		
		Map<String,Object> callConsumerMap = new HashMap<String, Object>();
		try{
			myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.puberrcode.selectCountMonitorApplicationAlive");
			callConsumerMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
			callConsumerMap.put("returnMsg", "数据库状态正常");
			callConsumerMap.put("modelName", modelName);
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			callConsumerMap.put("returnCode", "0001");
			callConsumerMap.put("returnMsg", "数据库状态不正常");
			callConsumerMap.put("modelName", modelName);
		}
		list.add(callConsumerMap);
		
		try{
			list.addAll(papplicationAliveMonitorCommonServiceFacade.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-provider-common");
			list.add(callMap);
		}
		
		try{
			list.addAll(papplicationAliveMonitorCustomerServiceFacade.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-provider-customer");
			list.add(callMap);
		}
		
		try{
			list.addAll(papplicationAliveMonitorTransferServiceFacade.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-provider-transfer");
			list.add(callMap);
		}
		return list;
	}
	
}
