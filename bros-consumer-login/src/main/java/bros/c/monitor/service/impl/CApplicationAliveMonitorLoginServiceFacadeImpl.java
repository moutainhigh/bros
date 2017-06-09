package bros.c.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.monitor.service.ICApplicationAliveMonitorLoginServiceFacade;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.p.monitor.service.IPApplicationAliveMonitorLoginServiceFacade;
/**
 * 
 * @ClassName: CApplicationAliveMonitorLoginServiceFacadeImpl 
 * @Description: 监听bros-consumer-login模块是否存活
 * @author 何鹏
 * @date 2016年12月19日 下午2:38:43 
 * @version 1.0
 */
@Component("capplicationAliveMonitorLoginServiceFacade")
public class CApplicationAliveMonitorLoginServiceFacadeImpl implements
		ICApplicationAliveMonitorLoginServiceFacade {
	private static final Logger logger = LoggerFactory.getLogger(CApplicationAliveMonitorLoginServiceFacadeImpl.class);
	@Autowired
	private IPApplicationAliveMonitorLoginServiceFacade papplicationAliveMonitorLoginServiceFacade;
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	@Override
	public List<Map<String,Object>> applicationAliveMonitorMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String modelName = "bros-consumer-login";
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
			list.addAll(papplicationAliveMonitorLoginServiceFacade.applicationAliveMonitorMethod(headMap, bodyMap));
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			Map<String,Object> callMap = new HashMap<String, Object>();
			callMap.put("returnCode", "0001");
			callMap.put("returnMsg", "状态不正常");
			callMap.put("modelName", "bros-provider-login");
			list.add(callMap);
		}
		return list;
	}

}
