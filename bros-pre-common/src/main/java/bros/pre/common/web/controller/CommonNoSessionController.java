package bros.pre.common.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.TokenUtil;
import bros.common.core.util.ValidateUtil;
import bros.common.core.web.controller.NoSessionController;
import bros.pre.common.constants.CommonErrorCodeConstants;

/** 
 * @ClassName: CommonNoSessionController 
 * @Description: 前置公共控制器
 * @author weiyancheng
 * @date 2016年7月14日 上午11:15:34 
 * @version 1.0 
 */
@Controller
public class CommonNoSessionController extends NoSessionController {
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
	
	public Map<String, Object> handleReqHeadData(HttpServletRequest request,Map<String, Object> dataMap) throws ServiceException{
		Map<String, Object> headMap = super.handleReqHeadData(dataMap);
		//----------------上生成需要放开                开始
		/*
		String token = (String) (headMap.get(HeadParameterDefinitionConstants.PRE_TOKEN)==null?"":headMap.get(HeadParameterDefinitionConstants.PRE_TOKEN));
		if(!ValidateUtil.isEmpty(token)){//token存在就表示已经登录了
			String sessionId = tokenUtil.getRedisSessionId(token);
			Map<String, Object> privateSessionMap = sessionManager.getSession(sessionId);
			if(privateSessionMap==null){
				throw new ServiceException(CommonErrorCodeConstants.PCOO0003,"会话失效");
			}
		}
		*/
		//----------------上生成需要放开               结束
		return headMap;
	}
}
