package bros.c.login.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.login.facade.service.ICLoginServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.login.facade.service.IPLoginServiceFacade;

/** 
 * @ClassName: CLoginServiceFacadeImpl 
 * @Description: 登录服务
 * @author weiyancheng
 * @date 2016年7月12日 下午12:48:23 
 * @version 1.0 
 */
@Component("cloginServiceFacade")
public class CLoginServiceFacadeImpl implements ICLoginServiceFacade {
	
	@Autowired
	private IPLoginServiceFacade ploginServiceFacade;
	/** 
	 * @Title: manageLogin 
	 * @Description: 内管管理登录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000204")
	@Override
	public ResponseEntity manageLogin(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return ploginServiceFacade.manageLogin(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: manageLogout 
	 * @Description: 内管登出
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity manageLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return ploginServiceFacade.manageLogout(headMap, bodyMap);
	}

}
