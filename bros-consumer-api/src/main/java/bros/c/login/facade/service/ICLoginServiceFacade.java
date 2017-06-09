package bros.c.login.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: ICLoginServiceFacade 
 * @Description: 登录、登出服务接口
 * @author weiyancheng
 * @date 2016年7月12日 上午11:06:44 
 * @version 1.0 
 */
public interface ICLoginServiceFacade {

	/**
	 * 
	 * @Title: manageLogin 
	 * @Description: 内管登录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity manageLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
     * 
     * @Title: manageLogout 
     * @Description: 内管登出
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity manageLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
