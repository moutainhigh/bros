package bros.manage.common.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bros.common.core.annotation.MethodAttribute;
import bros.common.core.annotation.NeedInSessionType;
import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.session.manager.SessionManager;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.StringUtil;
import bros.common.core.util.TokenUtil;
import bros.pre.common.web.controller.CommonNoSessionController;

/**
 * 
 * @ClassName: LoginController
 * @Description: 登录控制器
 * @author 卫燕成
 * @date 2016年5月12日 上午9:29:16
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/common")
public class LoginController extends CommonNoSessionController {
	
	private static final  Logger logger = LoggerFactory.getLogger(LoginController.class);
	
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
	 * token工具类
	 */
	@Autowired
	private TokenUtil tokenUtil;
	
	/**
	 * 登录
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.NO)
//	@Validation(value="00000001" ,paramName={"loginMap"})//value属性值对应模板文件名，paramName属性对应方法参数，如果有一个参数不想校验，用""代替
	public @ResponseBody Map<String, Object> logon(@RequestBody Map<String, Object> loginMap,HttpServletRequest request) throws ServiceException 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> headMap = this.handleReqHeadData(loginMap);
			Map<String, Object> bodyMap = this.handleReqBodyData(loginMap);
			
			//调用渠道统一接入
			resultMap = clientRouteTransService.route(headMap,bodyMap);

			//返回数据
			Map<String, Object> rspHeadMap = this.handleRspHeadData(resultMap);
			Map<String, Object> rspBodyMap = this.handleRspBodyData(resultMap);
			
			if(rspBodyMap == null || rspBodyMap.size()<=0){
				throw new ServiceException("返回信息报文体不存在");
			}
			
			Map<String,Object> branchMap = (Map<String, Object>) rspBodyMap.get("branchMap");
			if(branchMap == null || branchMap.size()<=0){
				throw new ServiceException("机构信息不存在");
			}
			
			//渠道私有会话数据
			Map<String, Object> privateSessionMap = new HashMap<String, Object>();
			
			String tellerNo = StringUtil.nvl(bodyMap.get("tellerCode"), "");//柜员编号
			String branchCode = StringUtil.nvl(branchMap.get("branchCode"), "");//机构编号
			String legalId = StringUtil.nvl(rspBodyMap.get(HeadParameterDefinitionConstants.REC_LEGALID), "");//法人ID
			String legalCode = StringUtil.nvl(rspBodyMap.get(HeadParameterDefinitionConstants.REC_LEGALCODE), "");//法人编号
			
			privateSessionMap.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO, tellerNo);//柜员号
			privateSessionMap.put(HeadParameterDefinitionConstants.REC_BRANCHID, branchCode);//机构
			privateSessionMap.put(HeadParameterDefinitionConstants.REC_LEGALCODE, legalCode);//法人编号
			privateSessionMap.put(HeadParameterDefinitionConstants.REC_LEGALID, legalId);//法人id
			
			//会话唯一标识
			//String sessionId = request.getSession().getId();
			String uuid = BaseUtil.createUUID();
			String sessionId = uuid.replaceAll("-", "");
			String token = tokenUtil.createToken(uuid);
			rspHeadMap.put(HeadParameterDefinitionConstants.PRE_TOKEN, token);
			//创建渠道私有会话数据
			sessionManager.addSession(sessionId, privateSessionMap);
		}catch(ServiceException e){
			logger.error("登录异常", e);
			//清空会话信息
			this.clearSession(request);
			this.handleError(e, resultMap);
		}catch(Exception e){
			logger.error("登录异常", e);
			//清空会话信息
			this.clearSession(request);
			this.handleError(e, resultMap);
		}
		
		return resultMap;
	}
}
