package bros.manage.common.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import bros.common.core.annotation.MethodAttribute;
import bros.common.core.annotation.NeedInSessionType;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.TokenUtil;
import bros.common.core.util.ValidateUtil;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: LogoutController
 * @Description: 登录控制器
 * @author 卫燕成
 * @date 2016年5月12日 上午9:29:16
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/common")
public class LogoutController extends CommonSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(LogoutController.class);
	
	/**
	 * 渠道私有会话管理器
	 */
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * token管理工具
	 */
	@Autowired
	private TokenUtil tokenUtil;
	
	/**
	 * 签退
	 */
	@RequestMapping(value = "/logout" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
	public Map<String, Object> logout(@RequestBody Map<String, Object> logoutMap, HttpServletRequest request) 
			throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sessionHttpId = "";//session中的sessionId
		String sessionId = "";
		String token = "";
		try {
			Map<String,Object> reqHeadMap = this.handleReqHeadData(logoutMap);
			Map<String,Object> reqBodyMap = this.handleReqBodyData(logoutMap);
			token = (String) (reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TOKEN)==null?"":reqHeadMap.get(HeadParameterDefinitionConstants.PRE_TOKEN));
			resultMap = clientRouteTransService.route(reqHeadMap, reqBodyMap);
			
			//HTTP会话标识
			sessionHttpId = WebUtils.getSessionId(request);
			
			logger.info("[" + sessionHttpId + "] logout successfully.");
			
		}catch(ServiceException e){
			logger.error("退出系统异常", e);
			this.handleError(e, resultMap);
		}catch(Exception e){
			logger.error("退出系统异常", e);
			this.handleError(e, resultMap);
		}finally{
			try{
				sessionId = tokenUtil.getRedisSessionId(token);
			}catch(Exception e){
				logger.error("Get SessionID Exception.",e);
			}
			
			try{
				if(!ValidateUtil.isEmpty(sessionId)){
					//销毁渠道私有会话对象
					sessionManager.removeSession(sessionId);
				}
			}catch(Exception e){
				logger.error("Destroy Private Session failed.", e);
			}
			
			//清空会话信息
			this.clearSession(request);
		}
		return resultMap;
	}

}
