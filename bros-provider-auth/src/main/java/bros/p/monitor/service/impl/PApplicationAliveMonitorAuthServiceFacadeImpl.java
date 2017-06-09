package bros.p.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.p.monitor.service.IPApplicationAliveMonitorAuthServiceFacade;
import bros.provider.parent.sys.apppar.service.IApplicationAliveMonitorEntityService;
/**
 * 
 * @ClassName: PApplicationAliveMonitorAuthServiceFacadeImpl 
 * @Description:  监听bros-provider-auth模块是否存活
 * @author 何鹏
 * @date 2016年12月19日 上午10:34:14 
 * @version 1.0
 */
@Component("papplicationAliveMonitorAuthServiceFacade")
public class PApplicationAliveMonitorAuthServiceFacadeImpl implements
		IPApplicationAliveMonitorAuthServiceFacade {
	private static final Logger logger = LoggerFactory.getLogger(PApplicationAliveMonitorAuthServiceFacadeImpl.class);
	@Autowired
	private IApplicationAliveMonitorEntityService applicationAliveMonitorEntityService;
	/**
	 * 数据库操作服务
	 */
	@Autowired
	private IMyBatisDaoSysDao activitiMyBatisDaoSysDao;
	/**
	 * 
	 * @Title: applicationAliveMonitorMethod 
	 * @Description: 检查应用是否存活
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public List<Map<String,Object>> applicationAliveMonitorMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    String modelName = "bros-provider-auth";
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
				logger.info("检查应用是否存活，不影响交易",se);
				dbReturnMap.put("returnCode", se.getErrorCode());
				dbReturnMap.put("returnMsg", se.getErrorMsg());
				
			}
			list.add(dbReturnMap);
		}
		
		Map<String,Object> dbActivitiReturnMap = new HashMap<String, Object>();
		try{
			dbActivitiReturnMap.put("modelName", modelName);
			activitiMyBatisDaoSysDao.selectTotalNum("mybatis.mapper.activiti.inside.table.teller-role-branch.selectCountMonitorApplicationAlive");
			dbActivitiReturnMap.put("returnCode", BaseParamsConstants.TRADE_SUCCESS_FLAG);
			dbActivitiReturnMap.put("returnMsg", "workflow实例数据库操作正常");
		}catch(Exception e){
			logger.info("检查应用是否存活，不影响交易",e);
			dbActivitiReturnMap.put("returnCode", "0001");
			dbActivitiReturnMap.put("returnMsg", "workflow实例数据库操作不正常");
		}
		list.add(dbActivitiReturnMap);
		return list;
	}
}
