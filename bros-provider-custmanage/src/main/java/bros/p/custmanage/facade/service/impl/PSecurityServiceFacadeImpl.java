package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPSecurityServiceFacade;
import bros.provider.custmanage.constants.CustManageErrorCodeConstants;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;

/**
 * 
 * @ClassName: PSecurityServiceFacadeImpl 
 * @Description: 安保服务实现（包括短信服务、证书服务等）
 * @author gaoyongjing 
 * @date 2016年9月13日 下午2:44:40 
 * @version 1.0
 */
@Component("psecurityServiceFacade")
public class PSecurityServiceFacadeImpl implements IPSecurityServiceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(PSecurityServiceFacadeImpl.class);
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	  
	/**
	 * 
	 * @Title: querySafetyToolList 
	 * @Description: 查询客户安全工具列表
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity querySafetyToolList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
			try {
				Map<String,Object> recvMap = new HashMap<String, Object>();
				
				recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.QUERYSAFETYTOOL_CODE);
				
				ResponseEntity entity = new ResponseEntity(recvMap);
				
				return entity;
			} catch (ServiceException e) {
				logger.error("Exception from " + this.getClass().getName() + "'s querySafetyToolList method.", e);
				throw e;
			} catch (Exception ex) {
				logger.error("Exception from " + this.getClass().getName() + "'s querySafetyToolList method.", ex);
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0009,"查询客户安全工具列表失败", ex);
			}
		
	}
	/**
	 * 
	 * @Title: createChallengeCode 
	 * @Description: 生成挑战码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity createChallengeCode(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CREATECHALLENGECODE_CODE);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s createChallengeCode method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s createChallengeCode method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0010,"生成挑战码失败", ex);
		}
	}
	/**
	 * 
	 * @Title: sendMessageCode 
	 * @Description: 发送短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity sendMessageCode(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SENDMESSAGE_CODE);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s sendMessageCode method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s sendMessageCode method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0011,"发送短信验证码失败", ex);
		}
	}
	/**
	 * 
	 * @Title: validateMessageCode 
	 * @Description: 验证短信验证码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity validateMessageCode(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			Map<String,Object> recvMap = new HashMap<String, Object>();
			
			recvMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.VALIDATEMESSAGECODE_CODE);
			
			ResponseEntity entity = new ResponseEntity(recvMap);
			
			return entity;
		} catch (ServiceException e) {
			logger.error("Exception from " + this.getClass().getName() + "'s validateMessageCode method.", e);
			throw e;
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName() + "'s validateMessageCode method.", ex);
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0016,"验证短信验证码失败", ex);
		}
	}
}
