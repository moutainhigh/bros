package bros.provider.parent.bankmanage.tellerrole.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.tellerrole.service.ITellerRoleEntitySerivce;

/**
 * 
 * @ClassName: TellerRoleEntityServiceImpl 
 * @Description: 柜员角色实体服务
 * @author huangcanhui 
 * @date 2016年7月19日 上午10:44:38 
 * @version 1.0
 */
@Component(value="tellerRoleEntitySerivce")
public class TellerRoleEntityServiceImpl implements ITellerRoleEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(TellerRoleEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 

	/**
	 * 根据角色编号列表查询角色信息列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBmaRoleByRoleIdList(List<String> roleIdList) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String[] roleIdArray = roleIdList.toArray(new String[roleIdList.size()]);
			parmIN.put("roleIdArray", roleIdArray);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmarole.queryBmaRoleByRoleIdList", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaRoleByRoleIdList method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0214, "获取角色信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaRoleByRoleIdList method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0214, "获取角色信息失败", ex);
		}
	}
}
