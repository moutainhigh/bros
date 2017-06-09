package bros.manage.bankmanage.securityToolManage.controller;

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
import bros.common.core.exception.ServiceException;
import bros.pre.common.web.controller.CommonSessionController;

/**
 * 
 * @ClassName: SecurityToolManageController 
 * @Description: 安全工具管理控制器
 * @author guosuwei 
 * @date 2016年09月18日09:21:11
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/bankmanage")
public class SecurityToolManageController extends CommonSessionController{
	
	private static final  Logger logger = LoggerFactory.getLogger(SecurityToolManageController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	
	/**
	 * 
	 * @Title: addCertificateSign 
	 * @Description: 证书发放、签约
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCertificateSign" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> addCertificateSign(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("证书发放、签约失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("证书发放、签约失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: bindOTPTokenService 
	 * @Description: OTP令牌绑定服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bindOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> bindOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌绑定服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌绑定服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: avoidRepeatedRandomNo 
	 * @Description: 防重放随机数服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/avoidRepeatedRandomNo" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> avoidRepeatedRandomNo(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("防重放随机数服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("防重放随机数服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: checkAccountPassword 
	 * @Description: 校验账户密码
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkAccountPassword" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> checkAccountPassword(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("校验账户密码服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("校验账户密码服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: changeOTPTokenService 
	 * @Description: OTP更换令牌服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> changeOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP更换令牌服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP更换令牌服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: updateCertificateService 
	 * @Description: 证书更新（自助）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCertificateService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> updateCertificateService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("证书更新失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("证书更新失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: syncOTPTokenService 
	 * @Description: OTP令牌同步服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/syncOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> syncOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌同步服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌同步服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: checkReferenceValueAndLicenseKeyService
	 * @Description: 验证参考码和授权码
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkReferenceValueAndLicenseKeyService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> checkReferenceValueAndLicenseKeyService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("验证参考码和授权码失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("验证参考码和授权码失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: makeCertificateService
	 * @Description: 证书制证
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/makeCertificateService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> makeCertificateService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("证书制证失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("证书制证失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: setOTPTokenKeyService
	 * @Description: OTP令牌设置激活码服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setOTPTokenKeyService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> setOTPTokenKeyService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌设置激活码服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌设置激活码服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: cancelCertificateService
	 * @Description: 证书注销
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelCertificateService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> cancelCertificateService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("证书注销失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("证书注销失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: cancelOTPTokenService
	 * @Description: OTP令牌作废服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> cancelOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌作废服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌作废服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: cancelSMSValidateCodeService
	 * @Description: 作废短信验证码服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelSMSValidateCodeService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> cancelSMSValidateCodeService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("作废短信验证码服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("作废短信验证码服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: hangUpOTPTokenService
	 * @Description: OTP令牌挂起服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hangUpOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> hangUpOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌挂起服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌挂起服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: queryOTPTokenSignInfoService
	 * @Description: OTP签约信息查询服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryOTPTokenSignInfoService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryOTPTokenSignInfoService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP签约信息查询服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP签约信息查询服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: unlockOTPTokenPINService
	 * @Description: OTP令牌PIN码解锁服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/unlockOTPTokenPINService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> unlockOTPTokenPINService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌PIN码解锁服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌PIN码解锁服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: checkOTPTokenService
	 * @Description: OTP令牌动态口令认证（验证签约关系）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkOTPTokenService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> checkOTPTokenService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("OTP令牌动态口令认证服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("OTP令牌动态口令认证服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: queryCertificateService
	 * @Description: 证书查询（根据客户信息查询证书）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryCertificateService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> queryCertificateService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("证书查询服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("证书查询服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: removeLossSMSValidateCodeService
	 * @Description: 解挂短信验证码服务
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/removeLossSMSValidateCodeService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> removeLossSMSValidateCodeService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("解挂短信验证码服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("解挂短信验证码服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Title: querySMSValidateCodeService
	 * @Description: 获取短信验证码服务签约手机号、邮箱
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querySMSValidateCodeService" ,method=RequestMethod.POST)
	@MethodAttribute(needInSession=NeedInSessionType.YES)
	public @ResponseBody Map<String, Object> querySMSValidateCodeService(@RequestBody Map<String, Object> requestMap,HttpServletRequest request) throws ServiceException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
		Map<String, Object> headMap = this.handleReqHeadData(requestMap);
		Map<String, Object> bodyMap = this.handleReqBodyData(requestMap);
		
		//客户信息查询
		resultMap = clientRouteTransService.route(headMap,bodyMap);
		
		}catch(ServiceException e){
			logger.error("获取短信验证码服务签约手机号、邮箱服务失败"+e);
			this.handleError(e,resultMap);
		} catch (Exception ex) {
			logger.error("获取短信验证码服务签约手机号、邮箱服务失败"+ex);
			this.handleError(ex,resultMap);
		}
		
		return resultMap;
	}

}
