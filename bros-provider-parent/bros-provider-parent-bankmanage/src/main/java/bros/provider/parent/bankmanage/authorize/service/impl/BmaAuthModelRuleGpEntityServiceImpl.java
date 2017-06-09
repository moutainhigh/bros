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
import bros.provider.parent.bankmanage.authorize.service.IBmaAuthModelRuleGpEntitySerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: BmaAuthModelRuleGpEntityServiceImpl 
 * @Description: 内部授权模型规则实体服务
 * @author pengxiangnan 
 * @date 2016年7月18日 上午10:38:11 
 * @version 1.0
 */
@Component(value="bmaAuthModelRuleGpEntitySerivce")
public class BmaAuthModelRuleGpEntityServiceImpl implements IBmaAuthModelRuleGpEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(BmaAuthModelRuleGpEntityServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 

	/**
	 * 保存授权模型规则
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int saveBmaAuthModelRuleGp(List<Map<String,Object>> parmINList) throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmaauthmodelrulegp.insertBmaAuthModelRuleGp", parmINList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaAuthModelRuleGp method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "保存授权模型规则失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s saveBmaAuthModelRuleGp method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0201, "保存授权规则失败", ex);
		}
	}
	
	/**
	 * 删除授权模型规则
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteBmaAuthModelRuleGp(String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("authorizeId", authorizeId);
			
			return myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmaauthmodelrulegp.deleteBmaAuthModelRuleByAuthModelId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaAuthModelRuleGp method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "删除授权模型规则失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteBmaAuthModelRuleGp method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0202, "删除授权模型规则失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号和金额查询授权规则
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBmaAuthModelRuleByAuthModelIdAndAmount(String authorizeId, String amount) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("authorizeId", authorizeId);
			parmIN.put("amount", amount);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmaauthmodelrulegp.queryBmaAuthModelRuleByAuthModelIdAndAmount", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelIdAndAmount method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询规则列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelIdAndAmount method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询规则列表失败", ex);
		}
	}
	
	/**
	 * 根据授权模型编号查询授权规则定义列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBmaAuthModelRuleByAuthModelId (String authorizeId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("authorizeId", authorizeId);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmaauthmodelrulegp.queryBmaAuthModelRuleByAuthModelId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelId method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询授权模型规则列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaAuthModelRuleByAuthModelId method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0218, "查询授权模型规则列表失败", ex);
		}
	}
	
}
