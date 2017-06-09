package bros.c.login.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.login.facade.service.ICPersonLoginServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.login.facade.service.IPPersonLoginServiceFacade;

/**
 * 
 * @ClassName: CPersonLoginServiceFacadeImpl 
 * @Description: 个人登录服务消费方实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午5:43:28 
 * @version 1.0
 */
@Component("cPersonLoginServiceFacade")
public class CPersonLoginServiceFacadeImpl implements ICPersonLoginServiceFacade {
	
	/**
	 * 个人登录服务
	 */
	@Autowired
	private IPPersonLoginServiceFacade pPersonLoginServiceFacade;
	
	/** 
	 * 登录
	 */
	@Override
	public ResponseEntity login(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonLoginServiceFacade.login(headMap, bodyMap);
	}

	/**
	 * 签退
	 */
	@Override
	public ResponseEntity logout(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonLoginServiceFacade.logout(headMap, bodyMap);
	}
	
	/**
	 * 首次登录
	 */
	@Override
	public ResponseEntity firstLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonLoginServiceFacade.firstLogin(headMap, bodyMap);
	}
	
	/**
	 * 临时停用登录
	 */
	@Override
	public ResponseEntity pauseLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonLoginServiceFacade.pauseLogin(headMap, bodyMap);
	}

}
