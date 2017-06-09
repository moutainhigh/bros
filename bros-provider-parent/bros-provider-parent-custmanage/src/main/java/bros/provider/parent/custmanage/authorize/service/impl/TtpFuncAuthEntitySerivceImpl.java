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
import bros.provider.parent.custmanage.authorize.service.ITtpFuncAuthEntitySerivce;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: TtpFuncAuthEntitySerivceImpl 
 * @Description: 开通功能与授权模型关系实体服务
 * @author huangcanhui 
 * @date 2016年7月20日 下午6:54:06 
 * @version 1.0
 */
@Component(value="ttpFuncAuthEntitySerivce")
public class TtpFuncAuthEntitySerivceImpl implements ITtpFuncAuthEntitySerivce {
	
	private static final  Logger logger = LoggerFactory.getLogger(TtpFuncAuthEntitySerivceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	
	/**
	 * 根据客户号与法人唯一标识查找功能关系列表
	 */
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpFuncAuthListByCstNoAndLegalId(String legalId, String cstNo) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpfuncauth.queryTtpFuncAuthByCstNoAndLegalId", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询功能列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询功能列表失败", ex);
		}
	}
	
	/**
	 * 修改开通功能与授权模型关系
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public int updateTtpFuncAuth(List<Map<String, Object>> funcAuthList) throws ServiceException {
		try{
			return myBatisDaoSysDao.updateBatchList("mybatis.mapper.single.table.ttpfuncauth.updateBathchTtpFuncAuth", funcAuthList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0029, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0029, "业务授权模型分配失败", ex);
		}
	}
	/**
	 *  添加授权配置信息
	 */
	public int insertTtpFunAuth(List<Map<String, Object>> funcAuthList)
			throws ServiceException {
		try{
			return myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.ttpfuncauth.insertTtpFunAuth", funcAuthList);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "业务授权模型分配失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateFunctionAuthorizationModels method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "业务授权模型分配失败", ex);
		}
	}
    
	/**
	 * 授权模型配置信息笔数
	 */
	public int queryTtpFunAuthNum(String cstNo, String legalId, String bsnCode)
			throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			parmIN.put("bsnCode", bsnCode);
			
			return  myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.ttpfuncauth.queryTtpFunAuthNum", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBsbAuthNum method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权模型配置信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBsbAuthNum method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询授权模型配置信息失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryTtpFuncAuthListByCstNoAndLegalId 
	 * @Description: 根据客户号与法人唯一标识查找功能关系列表
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @return List<Map<String, Object>> 功能与授权模型关系列表
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> queryTtpFuncAuthList(String legalId,String cstNo) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.ttpbsndefttpfuncauth.queryBsnListByAuth", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询功能列表失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryTtpFuncAuthByCstNoAndLegalId method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0031, "查询功能列表失败", ex);
		}
	}
}
