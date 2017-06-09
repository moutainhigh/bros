package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICSecurityServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPSecurityServiceFacade;

/**
 * 
 * @ClassName: CSecurityServiceFacadeImpl 
 * @Description:安保服务实现（包括短信服务、证书服务等）
 * @author gaoyongjing 
 * @date 2016年9月13日 下午2:44:40 
 * @version 1.0
 */
@Component("csecurityServiceFacade")
public class CSecurityServiceFacadeImpl implements ICSecurityServiceFacade {
	/**
	 * 通讯
	 */
	@Autowired
	private IPSecurityServiceFacade psecurityServiceFacade;
	/**
	 * 
	 * @Title: querySafetyToolListMethod 
	 * @Description: 查询客户安全工具列表
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity querySafetyToolListMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psecurityServiceFacade.querySafetyToolList(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: createChallengeCodeMethod 
	 * @Description: 生成挑战码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity createChallengeCodeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psecurityServiceFacade.createChallengeCode(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: sendMessageCodeMethod 
	 * @Description: 发送短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity sendMessageCodeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psecurityServiceFacade.sendMessageCode(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: validateMessageCodeMethod 
	 * @Description: 验证短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity validateMessageCodeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		return psecurityServiceFacade.validateMessageCode(headMap, bodyMap);
	}
}
