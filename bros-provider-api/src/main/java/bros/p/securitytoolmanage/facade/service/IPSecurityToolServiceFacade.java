package bros.p.securitytoolmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * @ClassName: IPSecurityToolServiceFacade 
 * @Description:安全工具 实现服务 
 * @author guosuwei
 * @date 2016年09月14日16:06:39
 * @version 1.0
 */
public interface IPSecurityToolServiceFacade {
	/**
	 * 
	 * @Title: addCertificateSign 
	 * @Description: 证书签发
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addCertificateSign(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: bindOTPTokenService 
	 * @Description: OTP令牌绑定服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity bindOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: avoidRepeatedRandomNo 
	 * @Description: 防重放随机数服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity avoidRepeatedRandomNo(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAccountPassword 
	 * @Description: 校验账户密码
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity checkAccountPassword(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: changeOTPTokenService 
	 * @Description: OTP更换令牌服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity changeOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateCertificateService 
	 * @Description: 证书更新（自助）
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateCertificateService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: syncOTPTokenService 
	 * @Description:OTP令牌同步服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity syncOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkReferenceValueAndLicenseKeyService
	 * @Description: 验证参考码和授权码
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity checkReferenceValueAndLicenseKeyService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: makeCertificateService
	 * @Description: 证书制证
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity makeCertificateService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: setOTPTokenKeyService
	 * @Description: OTP令牌设置激活码服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity setOTPTokenKeyService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: cancelCertificateService
	 * @Description: 证书注销
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity cancelCertificateService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: cancelOTPTokenService
	 * @Description: OTP令牌作废服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity cancelOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: cancelSMSValidateCodeService
	 * @Description: 作废短信验证码服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity cancelSMSValidateCodeService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: reportTheLossOfCertificateService
	 * @Description: 证书挂失
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity reportTheLossOfCertificateService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: hangUpOTPTokenService
	 * @Description: OTP令牌挂起服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity hangUpOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryOTPTokenSignInfoService
	 * @Description: OTP签约信息查询服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity queryOTPTokenSignInfoService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: unlockOTPTokenPINService
	 * @Description: OTP令牌PIN码解锁服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity unlockOTPTokenPINService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkOTPTokenService
	 * @Description: OTP令牌动态口令认证（验证签约关系）
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity checkOTPTokenService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCertificateService
	 * @Description: 证书查询（根据客户信息查询证书）
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity queryCertificateService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: removeLossSMSValidateCodeService
	 * @Description: 解挂短信验证码服务
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity removeLossSMSValidateCodeService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: querySMSValidateCodeService
	 * @Description: 获取短信验证码服务签约手机号、邮箱
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity querySMSValidateCodeService(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
		
}
