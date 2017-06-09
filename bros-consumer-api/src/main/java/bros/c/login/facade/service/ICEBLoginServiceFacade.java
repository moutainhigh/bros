package bros.c.login.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: ICPLoginServiceFacade 
 * @Description: 网银手机银行登录、登出服务接口
 * @author wuchenglong
 * @date 2016年7月12日 上午11:06:44 
 * @version 1.0 
 */
public interface ICEBLoginServiceFacade {

	/**
	 * 
	 * @Title: ebankLogin 
	 * @Description: 网银手机银行登录、登出
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity ebankLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
     * 
     * @Title: manageLogout 
     * @Description: 网银手机银行登录、登出
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity ebankLogout(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
