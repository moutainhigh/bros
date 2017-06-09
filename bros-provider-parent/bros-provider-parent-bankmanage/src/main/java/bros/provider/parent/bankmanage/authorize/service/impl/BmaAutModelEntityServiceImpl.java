package bros.provider.parent.bankmanage.authorize.service.impl;

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
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelEntitySerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: BmaAutModelEntityServiceImpl 
 * @Description: 内部授权模型实体服务
 * @author pengxiangnan 
 * @date 2016年6月24日 上午11:15:46 
 * @version 1.0
 */
@Component(value="bmaAuthModelEntitySerivce")
public class BmaAutModelEntityServiceImpl implements IBmaAuthModelEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(BmaAutModelEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	
	/**
	 * 保存授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int saveBmaAuthModel(String authorizeId, String name, String orderly, String state, String legalId, 
			String channel, String authMode, String authType, String authShape, String authRole, String authLevel) throws ServiceException 
	{
		try{
		    Map<String, Object> parmIN = new HashMap<String, Object>();
		    parmIN.put("authorizeId", authorizeId);
		    parmIN.put("name", name);
		    parmIN.put("orderly", orderly);
		    parmIN.put("state", state);
		    parmIN.put("legalId", legalId);
		    parmIN.put("channel", channel);
		    parmIN.put("authMode", authMode);
		    parmIN.put("authType", authType);
		    parmIN.put("authShape", authShape);
		    parmIN.put("authRole", authRole);
		    parmIN.put("authLevel", authLevel);

			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmaauthmodel.insertBmaAuthModel", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaAuthModel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "保存授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaAuthModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, " 保存授权模型失败", ex);
		}
	}
	
	/**
	 * 更新授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int updateBmaAuthModel(String authorizeId, String name, String orderly, String state, String legalId, 
			String channel, String authMode, String authType, String authShape, String authRole, String authLevel) throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("name", name);
			parmIN.put("orderly", orderly);
			parmIN.put("state", state);
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			parmIN.put("authMode", authMode);
			parmIN.put("authType", authType);
			 parmIN.put("authLevel", authLevel);
			
			return myBatisDaoSysDao.update("mybatis.mapper.single.table.bmaauthmodel.updateBmaAuthModel", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateBmaAuthModel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0203, "更新授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateBmaAuthModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0203, "更新授权模型失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号删除授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int deleteBmaAuthModel(String channel, String legalId, String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new  HashMap<String, Object>();
			parmIN.put("authorizeId", authorizeId);
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			
			return myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmaauthmodel.deleteBmaAuthModelByAuthorizeId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaAuthModel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "删除授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaAuthModel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "删除授权模型失败", ex);
		}
	}
	
	/**
	 * 根据授权模型名称查询指定法人授权模型信息
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> queryBmaAuthModelByName(String channel,String legalId, String authModelName) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("name", authModelName);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.bmaauthmodel.queryBmaAuthModelByName", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthModelByName method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "授权模型查询失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAuthModelByName method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "授权模型查询失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号查询指定法人授权模型信息
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> queryBmaAuthModelById(String channel,String legalId, String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("authorizeId", authorizeId);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.bmaauthmodel.queryBmaAuthModelById", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelById method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "授权模型查询失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelById method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "授权模型查询失败", ex);
		}
	}
	
	/**
	 * 根据渠道编号+法人记录ID分页查询授权模型列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBmaAuthModelListForPage(String channel, String legalId, 
			int pageNo , int pageSize) throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			
			return myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmaauthmodel.queryBmaAuthModel", parmIN, pageNo, pageSize);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelListForPage method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelListForPage method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", ex);
		}
	}
	
	/**
	 * 根据渠道编号+法人记录ID查询满足条件记录总数
	 */
	@Transactional(readOnly=true)
	public int queryBmaAuthModelTotalNum(String channel, String legalId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			
			return myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmaauthmodel.queryBmaAuthModelTotalNum", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelTotalNum method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelTotalNum method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", ex);
		}
	}
    
	/**
	 * 授权列表不分页
	 */
	@Override
	public List<Map<String, Object>> queryBmaAuthModelList(String channel, String legalId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmaauthmodel.queryBmaAuthModel", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelList method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelList method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0204, "查询授权模型失败", ex);
		}
	}
}
