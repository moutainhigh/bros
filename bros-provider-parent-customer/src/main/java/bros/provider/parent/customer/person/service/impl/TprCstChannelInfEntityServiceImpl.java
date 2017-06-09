package bros.provider.parent.customer.person.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.customer.constants.CustomerErrorCodeConstants;
import bros.provider.parent.customer.person.service.ITprCstChannelInfEntityService;

/**
 * 
 * @ClassName: TprCstChannelInfEntityServiceImpl 
 * @Description: 个人客户渠道信息实体服务
 * @author huangcanhui 
 * @date 2016年10月8日 下午2:48:44 
 * @version 1.0
 */
@Repository(value = "tprCstChannelInfEntityService")
public class TprCstChannelInfEntityServiceImpl implements ITprCstChannelInfEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprCstChannelInfEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据法人ID+渠道编号+别名查询个人客户渠道信息
	 */
	@Override
	public Map<String, Object> queryTprCstChannelInfByAlias(String legalId, String channel, String alias) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
	        parmIN.put("legalId", legalId);
	        parmIN.put("channel", channel);
			parmIN.put("alias", alias);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfByAlias", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户渠道信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户渠道信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0002, "查询个人客户渠道信息失败", e);
		}
	}
	
	/**
	 * 根据法人ID+渠道编号+手机号查询个人客户渠道信息
	 */
	@Override
	public Map<String, Object> queryTprCstChannelInfByMachineCode(String legalId, String channel, String machineCode) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
	        parmIN.put("channel", channel);
			parmIN.put("machineCode", machineCode);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfByMachineCode", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户渠道信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户渠道信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0002, "查询个人客户渠道信息失败", e);
		}
	}
	
	/**
	 * 根据客户标识+查询个人客户渠道信息
	 */
	public Map<String, Object> queryTprCstChannelInfByCstIdAndChannel(String cstId, String channel) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
	        
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.queryTprCstChannelInfByCstIdAndChannel", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户渠道信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户渠道信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0002, "查询个人客户渠道信息失败", e);
		}
	}
	
	/**
	 * 更新个人客户渠道状态
	 */
	public int updateTprCstChannelSttByCstIdAndChannel(String cstId, String channel, String stt) throws ServiceException 
	{
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("stt", stt);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstchannelinf.updateTprCstChannelSttByCstIdAndChannel", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户渠道状态异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户渠道状态失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0004, "更新个人客户渠道状态失败", e);
		}
	}
	
	/**
	 * 验证个人客户昵称
	 */
	public boolean checkAliasIsExist(String alias) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("alias", alias);
			Map<String, Object> dataMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstchannelinf.checkAliasIsExist", parmIN);
			boolean result = true;
			if(null==dataMap || dataMap.size()<=0){
				result = false;
			}
			return result;
		}catch(BrosBaseException se){
			logger.error("验证客户昵称异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("验证客户昵称失败", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0005, "验证客户昵称失败", e);
		}
	}
	
	/**
	 * 更新个人客户昵称和登录密码
	 */
	public int updateTprCstChannelAliasAndPwdByCstIdAndChannel(String cstId, String channel, String alias, 
			String newPassword, String oldPassword, String pwdTime) throws ServiceException 
	{
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("alias", alias);
			parmIN.put("newPassword", newPassword);
			parmIN.put("oldPassword", oldPassword);
			parmIN.put("pwdTime", pwdTime);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstchannelinf.updateTprCstChannelAliasAndPwdByCstIdAndChannel", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户渠道信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户渠道信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0006, "更新个人客户渠道信息失败", e);
		}
	}
	
	/**
	 * 更新个人客户登录密码
	 */
	public int updateTprCstChannelPwdByKey(String cstId, String channel, String newPassword, String oldPassword, 
			String pwdTime) throws ServiceException 
	{
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("newPassword", newPassword);
			parmIN.put("oldPassword", oldPassword);
			parmIN.put("pwdTime", pwdTime);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstchannelinf.updateTprCstChannelPwdByKey", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户渠道信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户渠道信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0006, "更新个人客户渠道信息失败", e);
		}
	}
	
}
