package bros.c.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICSecurityServiceFacade 
 * @Description: 安保服务接口（包括短信服务、证书服务等）
 * @author gaoyongjing 
 * @date 2016年9月13日 下午2:44:40 
 * @version 1.0
 */
public interface ICSecurityServiceFacade {
	
	/**
	 * 
	 * @Title: querySafetyToolListMethod 
	 * @Description: 查询客户安全工具列表
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity querySafetyToolListMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: createChallengeCodeMethod 
	 * @Description: 生成挑战码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity createChallengeCodeMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: sendMessageCodeMethod 
	 * @Description: 发送短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity sendMessageCodeMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: validateMessageCodeMethod 
	 * @Description: 验证短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity validateMessageCodeMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
