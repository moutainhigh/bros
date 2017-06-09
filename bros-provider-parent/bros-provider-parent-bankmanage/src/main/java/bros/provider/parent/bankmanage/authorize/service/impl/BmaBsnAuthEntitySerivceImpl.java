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
import bros.provider.parent.bankmanage.authorize.service.IBmaBsnAuthEntitySerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: BmaBsnAuthEntitySerivceImpl 
 * @Description: 开通功能与授权模型关系实体服务接口
 * @author pengxiangnan 
 * @date 2016年7月27日 下午6:51:55 
 * @version 1.0
 */
@Component(value="bmaBsnAuthEntitySerivce")
public class BmaBsnAuthEntitySerivceImpl implements IBmaBsnAuthEntitySerivce {

	private static final  Logger logger = LoggerFactory.getLogger(BmaBsnAuthEntitySerivceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	
	/**
	 * 根据渠道和法人查找功能列表
	 */
	@Override
	public List<Map<String, Object>> queryBmaBsnAuthListByChannelAndLegalId(
			String channel, String legalId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmabsnauth.queryBmaBsnAuthByChannelAndLegalId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询功能列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询功能列表失败", ex);
		}
	}

	@Override
	public int updateBmaBsnAuth(List<Map<String, Object>> funcAuthList)
			throws ServiceException {
		try{
			return myBatisDaoSysDao.updateBatchList("mybatis.mapper.single.table.bmabsnauth.updateBmaBsnAuth", funcAuthList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0223, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0223, "业务授权模型分配失败", ex);
		}
	}
	/**
	 * 
	 * @Title: insertBmaBsnAuth 
	 * @Description: 添加授权配置信息
	 * @param funcAuthList
	 * @return
	 * @throws ServiceException    设定文件
	 */
	public int insertBmaBsnAuth(List<Map<String, Object>> funcAuthList)
			throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmabsnauth.insertBmaBsnAuth", funcAuthList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0223, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0223, "业务授权模型分配失败", ex);
		}
	}

	/**
	* 查询模型分配信息
	*/
	public List<Map<String, Object>> queryBsbAuth(String channel, String legalId)
			throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-bmabsnauth.queryBsnAuthMessage", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询功能列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询功能列表失败", ex);
		}
	}

	/**
	 * 授权模型配置信息笔数
	 */
	public int queryBsbAuthNum(String channel, String legalId, String bsnCode)
			throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			parmIN.put("bsnCode", bsnCode);
			
			return  myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmabsnauth.queryBmaBsnAuthNum", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBsbAuthNum method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询授权模型配置信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBsbAuthNum method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0222, "查询授权模型配置信息失败", ex);
		}
	}

}
