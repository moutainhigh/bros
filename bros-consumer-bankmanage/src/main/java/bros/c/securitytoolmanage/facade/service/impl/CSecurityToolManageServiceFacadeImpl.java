package bros.c.securitytoolmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.securitytoolmanage.facade.service.ICSecurityToolManageServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.securitytoolmanage.facade.service.IPSecurityToolServiceFacade;

/**
 * 
 * @ClassName: CSecurityToolManageServiceFacadeImpl 
 * @Description: 安全工具管理服务实现类
 * @author guosuwei 
 * @date 2016年09月19日09:26:18
 * @version 1.0
 */
@Component("csecurityToolManageServiceFacade")
public class CSecurityToolManageServiceFacadeImpl implements ICSecurityToolManageServiceFacade{
	
	@Autowired
	private IPSecurityToolServiceFacade securityToolServiceFacade;
	
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
		return securityToolServiceFacade.addCertificateSign(headMap, bodyMap);
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
		return securityToolServiceFacade.bindOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.avoidRepeatedRandomNo(headMap, bodyMap);
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
		return securityToolServiceFacade.checkAccountPassword(headMap, bodyMap);
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
		return securityToolServiceFacade.changeOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.updateCertificateService(headMap, bodyMap);
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
		return securityToolServiceFacade.syncOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.checkReferenceValueAndLicenseKeyService(headMap, bodyMap);
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
		return securityToolServiceFacade.makeCertificateService(headMap, bodyMap);
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
		return securityToolServiceFacade.setOTPTokenKeyService(headMap, bodyMap);
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
		return securityToolServiceFacade.cancelCertificateService(headMap, bodyMap);
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
		return securityToolServiceFacade.cancelOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.cancelSMSValidateCodeService(headMap, bodyMap);
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
		return securityToolServiceFacade.reportTheLossOfCertificateService(headMap, bodyMap);
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
		return securityToolServiceFacade.hangUpOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.queryOTPTokenSignInfoService(headMap, bodyMap);
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
		return securityToolServiceFacade.unlockOTPTokenPINService(headMap, bodyMap);
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
		return securityToolServiceFacade.checkOTPTokenService(headMap, bodyMap);
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
		return securityToolServiceFacade.queryCertificateService(headMap, bodyMap);
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
		return securityToolServiceFacade.removeLossSMSValidateCodeService(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: querySMSValidateCodeService
	 * @Description:获取短信验证码服务签约手机号、邮箱
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity querySMSValidateCodeService(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return securityToolServiceFacade.querySMSValidateCodeService(headMap, bodyMap);
	}

}
