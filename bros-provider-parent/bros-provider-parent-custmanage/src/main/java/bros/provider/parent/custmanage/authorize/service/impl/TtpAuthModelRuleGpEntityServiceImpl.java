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
import bros.provider.parent.custmanage.authorize.service.ITtpAuthModelRuleGpEntitySerivce;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: TtpAuthModelRuleGpEntityServiceImpl 
 * @Description: 对客授权模型规则实体服务
 * @author pengxiangnan 
 * @date 2016年7月19日 下午3:43:09 
 * @version 1.0
 */
@Component(value="ttpAuthModelRuleGpEntitySerivce")
public class TtpAuthModelRuleGpEntityServiceImpl implements ITtpAuthModelRuleGpEntitySerivce {
	
	private static final Logger logger = LoggerFactory.getLogger(TtpAuthModelRuleGpEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 

	/**
	 * 保存授权模型规则
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int saveTtpAuthModelRuleGp(List<Map<String,Object>> parmINList) throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.ttpauthmodelrulegp.insertTtpAuthModelRuleGp", parmINList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveTtpAuthModelRuleGp method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0025, "保存授权模型规则失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveTtpAuthModelRuleGp method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0025, "保存授权规则失败", ex);
		}
	}
	
	/**
	 * 删除授权模型规则
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteTtpAuthModelRuleGp(String authorizeId) throws ServiceException {
		try{
			Map<String, Object> paramIN = new HashMap<String, Object>();
			paramIN.put("authorizeId", authorizeId);
			
			return myBatisDaoSysDao.delete("mybatis.mapper.single.table.ttpauthmodelrulegp.deleteTtpAuthModelRuleByAuthModelId", paramIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteTtpAuthModelRuleGp method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0032, "删除授权模型规则失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteTtpAuthModelRuleGp method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0032, "删除授权模型规则失败", ex);
		}
	}
	
	/**
	 * 根据授权模型ID查询授权规则
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpAuthModelRuleByAuthModelId (String authorizeId) throws ServiceException {
		try{
			Map<String, Object> paramIN = new HashMap<String, Object>();
			paramIN.put("authModelId", authorizeId);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpauthmodelrulegp.queryTtpAuthModelRuleByAuthModelId", paramIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelRuleByAuthModelId method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权规则列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelRuleByAuthModelId method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权规则列表失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号和金额查询授权规则
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpAuthModelRuleByAuthModelIdAndAmount(String authorizeId, String amount) throws ServiceException {
		try{
			Map<String, Object> paramIN =new HashMap<String, Object>();
			paramIN.put("authModelId", authorizeId);
			paramIN.put("amount", amount);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpauthmodelrulegp.queryTtpAuthModelRuleByAuthModelIdAndAmount", paramIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelRuleByAuthModelIdAndAmount method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权规则列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpAuthModelRuleByAuthModelIdAndAmount method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权规则列表失败", ex);
		}
	}
	
}
