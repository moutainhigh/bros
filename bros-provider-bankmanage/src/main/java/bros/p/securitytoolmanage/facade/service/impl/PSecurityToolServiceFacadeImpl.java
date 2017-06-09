package bros.p.securitytoolmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.securitytoolmanage.facade.service.IPSecurityToolServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.bankmanage.constants.SecurityToolManageFormatCodeConstants;

/**
 * 
 * @ClassName: PSecurityToolServiceFacadeImpl 
 * @Description: 渠道客户信息管理对外暴露接口实现
 * @author MacPro 
 * @date 2016年9月12日 下午3:05:53 
 * @version 1.0
 */
@Component("psecurityToolServiceFacade")
public class PSecurityToolServiceFacadeImpl implements IPSecurityToolServiceFacade{
	
	private static final  Logger logger = LoggerFactory.getLogger(PSecurityToolServiceFacadeImpl.class);
	
	/**
	 * 通讯服务
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	
	/**
	 * 
	 * @Title: addCertificateSign 
	 * @Description: 证书签发
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity addCertificateSign(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.ADDCERTIFICATESIGN_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s addCertificateSign method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s addCertificateSign method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0057, "证书签发失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: bindOTPTokenService 
	 * @Description: OTP令牌绑定服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity bindOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.BINDOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s bindOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s bindOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0058, "OTP令牌绑定服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: avoidRepeatedRandomNo 
	 * @Description: 防重放随机数服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity avoidRepeatedRandomNo(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.AVOIDREPEATEDRANDOMNO_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s avoidRepeatedRandomNo method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s avoidRepeatedRandomNo method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0059, "防重放随机数服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: checkAccountPassword 
	 * @Description: 校验账户密码
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity checkAccountPassword(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CHECKACCOUNTPASSWORD_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s checkAccountPassword method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s checkAccountPassword method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0060, "校验账户密码失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: changeOTPTokenService 
	 * @Description: OTP更换令牌服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity changeOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CHANGEOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s changeOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s changeOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0061, "OTP更换令牌服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: updateCertificateService 
	 * @Description: 证书更新（自助）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity updateCertificateService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.UPDATECERTIFICATESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s updateCertificateService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s updateCertificateService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0062, "证书更新服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: syncOTPTokenService 
	 * @Description: OTP令牌同步服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity syncOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.SYNCOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s syncOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s syncOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0063, "OTP令牌同步服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: checkReferenceValueAndLicenseKeyService
	 * @Description: 验证参考码和授权码
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity checkReferenceValueAndLicenseKeyService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CHECKREFERENCEVALUEANDLICENSEKEYSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s checkReferenceValueAndLicenseKeyService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s checkReferenceValueAndLicenseKeyService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0064, "验证参考码和授权码失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: makeCertificateService
	 * @Description: 证书制证
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity makeCertificateService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.MAKECERTIFICATESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s makeCertificateService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s makeCertificateService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0065, "证书制证失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: setOTPTokenKeyService
	 * @Description: OTP令牌设置激活码服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity setOTPTokenKeyService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.SETOTPTOKENKEYSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s setOTPTokenKeyService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s setOTPTokenKeyService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0066, "OTP令牌设置激活码服务失败", ex);
		}
		
	}
		
	/**
	 * 
	 * @Title: cancelCertificateService
	 * @Description: 证书注销
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity cancelCertificateService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CANCELCERTIFICATESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelCertificateService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelCertificateService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0067, "证书注销服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: cancelOTPTokenService
	 * @Description: OTP令牌作废服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity cancelOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CANCELOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0068, "OTP令牌作废服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: cancelSMSValidateCodeService
	 * @Description: 作废短信验证码服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity cancelSMSValidateCodeService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CANCELSMSVALIDATECODESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelSMSValidateCodeService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s cancelSMSValidateCodeService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0075, "作废短信验证码服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: reportTheLossOfCertificateService
	 * @Description: 证书挂失
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity reportTheLossOfCertificateService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.REPORTTHELOSSOFCERTIFICATESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s reportTheLossOfCertificateService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s reportTheLossOfCertificateService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0069, "证书挂失服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: hangUpOTPTokenService
	 * @Description: OTP令牌挂起服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity hangUpOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.HANGUPOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s hangUpOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s hangUpOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0070, "OTP令牌挂起服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: queryOTPTokenSignInfoService
	 * @Description: OTP签约信息查询服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryOTPTokenSignInfoService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.QUERYOTPTOKENSIGNINFOSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryOTPTokenSignInfoService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryOTPTokenSignInfoService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0071, "OTP签约信息查询服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: unlockOTPTokenPINService
	 * @Description: OTP令牌PIN码解锁服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity unlockOTPTokenPINService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.UNLOCKOTPTOKENPINSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s unlockOTPTokenPINService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s unlockOTPTokenPINService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0072, "OTP令牌PIN码解锁服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: checkOTPTokenService
	 * @Description: OTP令牌动态口令认证（验证签约关系）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity checkOTPTokenService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.CHECKOTPTOKENSERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s checkOTPTokenService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s checkOTPTokenService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0073, "OTP令牌动态口令认证服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: queryCertificateService
	 * @Description: 证书查询（根据客户信息查询证书）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryCertificateService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.QUERYCERTIFICATESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryCertificateService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryCertificateService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0074, "证书查询服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: removeLossSMSValidateCodeService
	 * @Description: 解挂短信验证码服务
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity removeLossSMSValidateCodeService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.REMOVELOSSSMSVALIDATECODESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s removeLossSMSValidateCodeService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s removeLossSMSValidateCodeService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0076, "解挂短信验证码服务失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: querySMSValidateCodeService
	 * @Description: 获取短信验证码服务签约手机号、邮箱
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity querySMSValidateCodeService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			Map<String,Object> respMap = new HashMap<String,Object>();
			// 与后台通讯
			respMap = httpClientRouter.send(headMap, bodyMap, SecurityToolManageFormatCodeConstants.QUERYSMSVALIDATECODESERVICE_CODE);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s querySMSValidateCodeService method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s querySMSValidateCodeService method.", ex);
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0077, "获取短信验证码服务签约手机号、邮箱失败", ex);
		}
		
	}

}
