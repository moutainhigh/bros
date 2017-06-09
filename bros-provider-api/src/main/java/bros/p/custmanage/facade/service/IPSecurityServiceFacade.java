package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPSecurityServiceFacade 
 * @Description: 安保服务接口（包括短信服务、证书服务等）
 * @author gaoyongjing 
 * @date 2016年9月13日 下午2:44:40 
 * @version 1.0
 */
public interface IPSecurityServiceFacade {
	
	/**
	 * 
	 * @Title: querySafetyToolList 
	 * @Description: 查询客户安全工具列表
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity querySafetyToolList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: createChallengeCode 
	 * @Description: 生成挑战码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity createChallengeCode(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: sendMessageCode 
	 * @Description: 发送短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity sendMessageCode(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: validateMessageCode 
	 * @Description: 验证短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity validateMessageCode(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
