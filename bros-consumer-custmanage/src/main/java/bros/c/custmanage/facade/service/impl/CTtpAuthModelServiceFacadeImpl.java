package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICTtpAuthModelSerivceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPTtpAuthModelSerivceFacade;
/**
 * 
 * @ClassName: CTtpAuthModelServiceFacadeImpl 
 * @Description: 对客授权模型管理对外发布服务
 * @author pengxiangnan 
 * @date 2016年7月21日 上午10:18:45 
 * @version 1.0
 */
@Component("cttpAuthModelSerivceFacade")
public class CTtpAuthModelServiceFacadeImpl implements ICTtpAuthModelSerivceFacade {
	
	/**
	 * 提供者接口
	 */
	@Autowired
	private IPTtpAuthModelSerivceFacade pttpAuthModelSerivceFacade;
    
	/**
	 * 新增授权模型
	 */
	@Validation(value="c0000322")
	@Override
	public ResponseEntity saveAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.saveAuthorizationModel(headMap, bodyMap);
	}
   
	/**
	 * 删除授权模型
	 */
	@Validation(value="c0000323")
	@Override
	public ResponseEntity deleteAuthorizationModel(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.deleteAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 修改授权模型
	 */
	@Validation(value="c0000324")
	@Override
	public ResponseEntity updateAuthorizationModel(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.updateAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 分页查询授权模型列表
	 */
	@Validation(value="c0000325")
	@Override
	public ResponseEntity queryAuthorizationModelListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.queryAuthorizationModelListForPage(headMap, bodyMap);
	}
    
	/**
	 * 根据授权模型编号查询授权模型详细信息
	 */
	@Validation(value="c0000326")
	@Override
	public ResponseEntity queryAuthorizationModelDetail(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pttpAuthModelSerivceFacade.queryAuthorizationModelDetail(headMap, bodyMap);
	}
    
	/**
	 * 授权模型分配
	 */
	@Validation(value="c0000327")
	@Override
	public ResponseEntity updateFunctionAuthorizationModel(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pttpAuthModelSerivceFacade.updateFunctionAuthorizationModel(headMap, bodyMap);
	}
   
	/**
	 * 查询授权列表不分页
	 */
	@Override
	public ResponseEntity queryAuthorizationModelList(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pttpAuthModelSerivceFacade.queryAuthorizationModelList(headMap, bodyMap);
	}

	@Override
	public ResponseEntity queryTtpFuncAuthList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.queryTtpFuncAuthList(headMap, bodyMap);
	}

	@Override
	public ResponseEntity queryMenuUnionFuncion(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pttpAuthModelSerivceFacade.queryMenuUnionFuncion(headMap, bodyMap);
	}

}
