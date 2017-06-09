package bros.provider.parent.custmanage.authorize.service.impl;

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
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelEntitySerivce;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: TtpAutModelEntityServiceImpl 
 * @Description: 对客授权模型实体服务
 * @author pengxiangnan 
 * @date 2016年7月19日 下午3:40:12 
 * @version 1.0
 */
@Component(value="ttpAuthModelEntitySerivce")
public class TtpAutModelEntityServiceImpl implements ITtpAuthModelEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(TtpAutModelEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	
	/**
	 * 保存授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int saveTtpAuthModel(String authorizeId, String name, String legalId, String channel, String cstNo, String moneyType, 
			String orderly, String send, String state) throws ServiceException 
	{
		try{
			 Map<String, Object> paramIN = new HashMap<String, Object>();
			 paramIN.put("authorizeId", authorizeId);
			 paramIN.put("name", name);
			 paramIN.put("legalId", legalId);
			 paramIN.put("channel", channel);
			 paramIN.put("cstNo", cstNo);
			 paramIN.put("moneyType", moneyType);
			 paramIN.put("orderly", orderly);
			 paramIN.put("send", send);
			 paramIN.put("state", state);
			 
			 return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpauthmodel.insertTtpAuthModel", paramIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveTtpAuthModel method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0027, "保存授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveTtpAuthModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0027, " 保存授权模型失败", ex);
		}
	}
	
	/**
	 * 更新授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int updateTtpAuthModel(String authorizeId, String name, String legalId, String channel, String cstNo, String moneyType, 
			String orderly, String send, String state) throws ServiceException {
		try{
			 Map<String, Object> paramIN = new HashMap<String, Object>();
			 paramIN.put("authorizeId", authorizeId);
			 paramIN.put("name", name);
			 paramIN.put("legalId", legalId);
			 paramIN.put("channel", channel);
			 paramIN.put("cstNo", cstNo);
			 paramIN.put("moneyType", moneyType);
			 paramIN.put("orderly", orderly);
			 paramIN.put("send", send);
			 paramIN.put("state", state);
			 
			 return myBatisDaoSysDao.update("mybatis.mapper.single.table.ttpauthmodel.updateTtpAuthModel", paramIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateTtpAuthModel method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0016, "更新授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateTtpAuthModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0016, "更新授权模型失败", ex);
		}
	}
	
	/**
	 * 删除授权模型
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int deleteTtpAuthModel(String channel, String legalId, String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new  HashMap<String, Object>();
			parmIN.put("authorizeId", authorizeId);
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			
			return myBatisDaoSysDao.delete("mybatis.mapper.single.table.ttpauthmodel.deleteTtpAuthModelByAuthorizeId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteTtpAuthModel method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0015, "删除授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteTtpAuthModel method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0015, "删除授权模型失败", ex);
		}
	}
	
	/**
	 * 根据授权模型名称查询授权模型
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> queryTtpAuthModelByName(String channel,String legalId, String cstNo, String authModelName) 
			throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			parmIN.put("name", authModelName);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.ttpauthmodel.queryTtpAuthModelIsExist", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelByName method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelByName method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
	
	/**
	 * 根据渠道编号+法人记录ID+客户编号分页查询授权模型列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpAuthModelListForPage(String channel, String legalId, String cstNo,
			int pageNo , int pageSize) throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);

			return myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.ttpauthmodel.queryTtpAuthModel", parmIN, pageNo, pageSize);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelListForPage method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelListForPage method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
	/**
	 * 根据渠道编号+法人记录ID+客户编号查询授权模型列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpAuthModelList(String channel, String legalId, String cstNo) throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);

			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpauthmodel.queryTtpAuthModel", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelListForPage method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelListForPage method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
	
	/**
	 * 根据渠道编号+法人记录ID+客户编号查询满足条件记录总数
	 */
	@Transactional(readOnly=true)
	public int queryTtpAuthModelTotalNum(String channel, String legalId, String cstNo) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			
			return myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.ttpauthmodel.queryTtpAuthModelTotalNum", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelTotalNum method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelTotalNum method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号查询指定法人授权模型信息
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> queryTtpAuthModelById(String channel, String legalId, String cstNo, String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("channel", channel);
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			parmIN.put("authorizeId", authorizeId);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.ttpauthmodel.queryTtpAuthModelById", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelById method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelById method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0017, "查询授权模型失败", ex);
		}
	}
}
