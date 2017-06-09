package bros.provider.parent.bankmanage.authorize.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.authorize.service.IBmaActRoleRelSerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: IBmaActRoleRelSerivce 
 * @Description: 内部授权模型--授权角色关联实现类
 * @author pengxiangnan 
 * @date 2016年7月25日 下午2:46:33 
 * @version 1.0
 */
@Component(value="bmaActRoleRelSerivce")
public class BmaActRoleRelSerivceImpl implements IBmaActRoleRelSerivce {
	private static final  Logger logger = LoggerFactory.getLogger(BmaActRoleRelSerivceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
    
	/**
	 * 批量保存模型角色关联表
	 */
	@Override
	public int saveBmaActRoleRel(List<Map<String, Object>> roleList) throws ServiceException {
			
		try{
			return  myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmaactroleref.insertBatchBmaActRoleRef", roleList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220, "保存关联角色失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220, " 保存关联角色失败", ex);
		}

	}
    
	/**
	 * 删除关联角色
	 */
	@Override
	public int deleteBmaActRoleRel(String barId) throws ServiceException {
		try{
			  Map<String, Object> parmIN = new HashMap<String, Object>();
			   parmIN.put("barId", barId);
			   return  myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmaactroleref.deleteBmaActRoleRefById", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0221, "删除关联角色失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0221, " 删除关联角色失败", ex);
		}
	}
    
	/**
	 * 查询授权角色
	 */
	@Override
	public List<Map<String, Object>> queryBmaActRoleRefById( String authRole) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("barId", authRole);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmaactroleref.queryBmaActRoleRefById", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelId method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询授权模型规则列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelId method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询授权模型规则列表失败", ex);
		}
	}
    
	/**
	 * 批量删除数据
	 */
	@Override
	public int deleteBatchBmaActRoleRel(List<String> roleIdList) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String[] roleIdArray = roleIdList.toArray(new String[roleIdList.size()]);
			parmIN.put("roleIdArray", roleIdArray);
			
		   return  myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmaactroleref.deleteBatchBmaActRoleRefById", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0221, "删除关联角色失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0221, " 删除关联角色失败", ex);
		}
	}

}
