package bros.c.login.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.login.facade.service.ICEBLoginServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.login.facade.service.IPEBLoginServiceFacade;
import bros.p.login.facade.service.IPLoginServiceFacade;

/** 
 * @ClassName: CLoginServiceFacadeImpl 
 * @Description: 登录服务
 * @author weiyancheng
 * @date 2016年7月12日 下午12:48:23 
 * @version 1.0 
 */
@Component("cebloginServiceFacade")
public class CEBLoginServiceFacadeImpl implements ICEBLoginServiceFacade {
	
	@Autowired
	private IPEBLoginServiceFacade pebloginServiceFacade;
	
	/**
	 * 
	 * @Title: ebankLogin 
	 * @Description: 网银手机银行登录、登出
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */

	@Override
	public ResponseEntity ebankLogin(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pebloginServiceFacade.ebankLogin(headMap, bodyMap);
	}
	
	/**
     * 
     * @Title: manageLogout 
     * @Description: 网银手机银行登录、登出
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
	@Override
	public ResponseEntity ebankLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pebloginServiceFacade.ebankLogout(headMap, bodyMap);
	}

}
