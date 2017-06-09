package bros.p.login.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.login.facade.service.IPEBLoginServiceFacade;
import bros.provider.login.constants.LoginErrorCodeConstants;
import bros.provider.login.constants.MbankLoginFormatCodeConstants;

/**
 * 
 * @ClassName: PMBLoginServiceFacadeImpl 
 * @Description: 登录
 * @author 马志磊
 * @date 2016年7月19日 下午4:02:43 
 * @version 1.0
 */
@Component("pebloginServiceFacade")
public class PEBLoginServiceFacadeImpl implements IPEBLoginServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(PEBLoginServiceFacadeImpl.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	
	/**
	 * 
	 * @Title: mbankLogin 
	 * @Description: 网银银行登录
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity ebankLogin(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, MbankLoginFormatCodeConstants.EBANKLOGIN_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s mbankLogin method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s mbankLogin method.", ex);
			throw new ServiceException(LoginErrorCodeConstants.PLON0014, "登录失败", ex);
		}
		
	}

	@Override
	public ResponseEntity ebankLogout(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}



}
