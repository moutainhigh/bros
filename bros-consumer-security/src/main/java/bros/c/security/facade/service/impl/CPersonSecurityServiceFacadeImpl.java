package bros.c.security.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.security.facade.service.ICPersonSecurityServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.security.facade.service.IPPersonSecurityServiceFacade;

/**
 * 
 * @ClassName: CPersonSecurityServiceFacadeImpl 
 * @Description: 个人安全设置服务消费方实现类
 * @author huangcanhui 
 * @date 2016年10月10日 下午5:56:12 
 * @version 1.0
 */
@Component("cPersonSecurityServiceFacade")
public class CPersonSecurityServiceFacadeImpl implements ICPersonSecurityServiceFacade {
	
	/**
	 * 个人安全设置服务
	 */
	@Autowired
	private IPPersonSecurityServiceFacade pPersonSecurityServiceFacade;
	
	/**
	 * 修改登录密码
	 */
	@Override
	public ResponseEntity modifyPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonSecurityServiceFacade.modifyPassword(headMap, bodyMap);
	}
	
	/**
	 * 重置私密问题
	 */
	@Override
	public ResponseEntity resetPrivateQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pPersonSecurityServiceFacade.resetPrivateQuestion(headMap, bodyMap);
	}

}
