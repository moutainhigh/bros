
package bros.pre.common.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.TokenUtil;
import bros.common.core.web.controller.SessionController;

/** 
 * @ClassName: CommonSessionController 
 * @Description: 前置公共基础控制器
 * @author weiyancheng
 * @date 2016年7月14日 上午11:14:33 
 * @version 1.0 
 */
@Controller
public class CommonSessionController extends SessionController {	
	/**
	 * Shiro会话管理器
	 */
	@Autowired
	private SessionManager sessionManager;
	/**
	 * token工具类
	 */
	@Autowired
	private TokenUtil tokenUtil;
	
	public Map<String, Object> handleReqHeadData(Map<String, Object> dataMap) throws ServiceException{
		Map<String, Object> headMap = super.handleReqHeadData(dataMap);
		//----------------上生成需要放开                开始
		/*
		String token = (String) (headMap.get(HeadParameterDefinitionConstants.PRE_TOKEN)==null?"":headMap.get(HeadParameterDefinitionConstants.PRE_TOKEN));
		if(ValidateUtil.isEmpty(token)){
			//throw new ServiceException(CommonErrorCodeConstants.PCOO0004,"token不存在");
			throw new ServiceException(CommonErrorCodeConstants.PCOO0003,"会话失效");
		}
		
		String sessionId = tokenUtil.getRedisSessionId(token);
		Map<String, Object> privateSessionMap = sessionManager.getSession(sessionId);
		if(privateSessionMap==null){
			throw new ServiceException(CommonErrorCodeConstants.PCOO0003,"会话失效");
		}
		*/
		//----------------上生成需要放开               结束
		return headMap;
	}
}
