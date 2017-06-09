package bros.provider.parent.login.person.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.login.constants.LoginErrorCodeConstants;
import bros.provider.parent.login.person.service.ITprCstLogonCtrlEntityService;

/**
 * 
 * @ClassName: TprCstLogonCtrlEntityServiceImpl 
 * @Description: 个人客户登录控制信息实体服务
 * @author huangcanhui 
 * @date 2016年10月9日 上午10:24:21 
 * @version 1.0
 */
@Repository(value = "tprCstLogonCtrlEntityService")
public class TprCstLogonCtrlEntityServiceImpl implements ITprCstLogonCtrlEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprCstLogonCtrlEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据客户标识+渠道编号查询个人客户登录控制信息
	 */
	@Override
	public Map<String, Object> queryTprCstLogonCtrlByCstIdAndChannel(String cstId, String channel) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstlogonctrl.queryTprCstLogonCtrlByCstIdAndChannel", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户登录控制信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户登录控制信息失败 ", e);
			throw new ServiceException(LoginErrorCodeConstants.PPLC0002, "查询个人客户登录控制信息失败", e);
		}
	}
	
	/**
	 * 保存个人客户登录控制信息
	 */
	public int insertTprCstLogonCtrl(String id, String cstId, String channel, int failToday, int failSum, int count) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("id", id);
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("failToday", failToday);
			parmIN.put("failSum", failSum);
			parmIN.put("count", count);
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.tprcstlogonctrl.insertTprCstLogonCtrl", parmIN);
		}catch(BrosBaseException se){
			logger.error("保存个人客户登录控制信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("保存个人客户登录控制信息失败 ", e);
			throw new ServiceException(LoginErrorCodeConstants.PPLC0003, "保存个人客户登录控制信息失败", e);
		}
	}
	
	/**
	 * 更新个人客户登录控制信息
	 */
	public int updateTprCstLogonCtrl(String cstId, String channel, int failToday, int failSum, String firstLogon, 
			String lastLogon, String freezeDate) throws ServiceException 
	{
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("failToday", failToday);
			parmIN.put("failSum", failSum);
			parmIN.put("firstLogon", firstLogon);
			parmIN.put("lastLogon", lastLogon);
			parmIN.put("freezeDate", freezeDate);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstlogonctrl.updateTprCstLogonCtrl", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户登录控制信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户登录控制信息失败 ", e);
			throw new ServiceException(LoginErrorCodeConstants.PPLC0003, "更新个人客户登录控制信息失败", e);
		}
	}
	
	/**
	 * 密码验证失败，更新个人客户登录控制信息
	 */
	public int updateTprCstLogonCtrlFail(String cstId, String channel, int failToday, String lastFail, String freezeDate) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("failToday", failToday);			
			parmIN.put("lastFail", lastFail);
			parmIN.put("freezeDate", freezeDate);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstlogonctrl.updateTprCstLogonCtrlFail", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户登录控制信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户登录控制信息失败 ", e);
			throw new ServiceException(LoginErrorCodeConstants.PPLC0003, "更新个人客户登录控制信息失败", e);
		}
	}
	
	/**
	 * 更新个人客户临时停用日期
	 */
	public int updateTprCstLogonCtrlStopDate(String cstId, String channel, String stopStart, String stopEnd) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			parmIN.put("channel", channel);
			parmIN.put("stopStart", stopStart);			
			parmIN.put("stopEnd", stopEnd);

			return myBatisDaoSysDao.update("mybatis.mapper.single.table.tprcstlogonctrl.updateTprCstLogonCtrlStopDate", parmIN);
		}catch(BrosBaseException se){
			logger.error("更新个人客户登录控制信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("更新个人客户登录控制信息失败 ", e);
			throw new ServiceException(LoginErrorCodeConstants.PPLC0003, "更新个人客户登录控制信息失败", e);
		}
	}
	
}
