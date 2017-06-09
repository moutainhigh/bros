package bros.p.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.exception.ServiceException;
import bros.p.monitor.service.IPApplicationAliveMonitorFinancialServiceFacade;
import bros.provider.parent.sys.apppar.service.IApplicationAliveMonitorEntityService;
/**
 * 
 * @ClassName: PApplicationAliveMonitorFinancialServiceFacadeImpl 
 * @Description: 监听bros-provider-financial模块是否存活
 * @author 何鹏
 * @date 2016年12月19日 下午12:09:57 
 * @version 1.0
 */
@Component("papplicationAliveMonitorFinancialServiceFacade")
public class PApplicationAliveMonitorFinancialServiceFacadeImpl implements
		IPApplicationAliveMonitorFinancialServiceFacade {
	@Autowired
	private IApplicationAliveMonitorEntityService applicationAliveMonitorEntityService;
	@Override
	public List<Map<String,Object>> applicationAliveMonitorMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    String modelName = "bros-provider-financial";
		String flagDb = String.valueOf(bodyMap.get("flagDb"));//是否查询数据库  
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
		returnMap.put("returnMsg", "模块正常");
		returnMap.put("modelName", modelName);
		list.add(returnMap);
		
		Map<String,Object> dbReturnMap = new HashMap<String, Object>();
		
		if("1".equals(flagDb)){
			dbReturnMap.put("modelName", modelName);
			try{
				applicationAliveMonitorEntityService.makeSureDbIsAlive();
				dbReturnMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
				dbReturnMap.put("returnMsg", "数据库操作正常");
			}catch(ServiceException se){
				dbReturnMap.put("returnCode", se.getErrorCode());
				dbReturnMap.put("returnMsg", se.getErrorMsg());
				
			}
			list.add(dbReturnMap);
		}
		return list;
	}

}
