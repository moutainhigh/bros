package bros.provider.parent.custmanage.role.impl;

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
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.role.ITtpRoleEntitySerivce;

/**
 * 
 * @ClassName: TtpRoleEntityServiceImpl 
 * @Description: 操作员角色实体服务
 * @author huangcanhui 
 * @date 2016年7月20日 下午6:39:53 
 * @version 1.0
 */
@Component(value="ttpRoleEntitySerivce")
public class TtpRoleEntityServiceImpl implements ITtpRoleEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(TtpRoleEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 

	/**
	 * 根据角色编号列表查询角色信息列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpRoleByRoleIdList(List<String> roleIdList) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String[] roleIdArray = (String[])roleIdList.toArray(new String[roleIdList.size()]);
			parmIN.put("roleIdArray", roleIdArray);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttprole.queryTtpRoleByRoleIdList", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpRoleByRoleIdList method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0027, "获取角色信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpRoleByRoleIdList method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0027, "获取角色信息失败", ex);
		}
	}
}
