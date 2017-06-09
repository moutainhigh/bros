package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICBmaAuthModelSerivceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBmaAuthModelSerivceFacade;

/**
 * 
 * @ClassName: PBmaAuthModelServiceFacadeImpl 
 * @Description: 内部授权模型管理对外发布服务
 * @author pengxiangnan 
 * @date 2016年7月18日 下午2:34:35 
 * @version 1.0
 */
@Component("cbmaAuthModelSerivceFacade")
public class CBmaAuthModelServiceFacadeImpl implements ICBmaAuthModelSerivceFacade {
	
	/**
	 * 内部授权模型
	 */
	@Autowired
	private IPBmaAuthModelSerivceFacade pbmaAuthModelSerivceFacade;
	
	/**
	 * 新增授权模型
	 */
	@Validation(value="c0000333")
	public ResponseEntity saveAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.saveAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 删除授权模型
	 */
	@Validation(value="c0000334")
	public ResponseEntity deleteAuthorizationModel(Map<String, Object> headMap,	Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.deleteAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 修改授权模型
	 */
	@Validation(value="c0000335")
	public ResponseEntity updateAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.updateAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 分页查询授权模型列表
	 */
	@Validation(value="c0000336")
	public ResponseEntity queryAuthorizationModelListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.queryAuthorizationModelListForPage(headMap, bodyMap);
	}
    
	/**
	 * 根据授权模型编号查询授权模型详细信息
	 */
	@Validation(value="c0000337")
	public ResponseEntity queryAuthorizationModelDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.queryAuthorizationModelDetail(headMap, bodyMap);
	}
	
	/**
	 * 分配授权模型
	 */
	@Override
	public ResponseEntity updateFunctionAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pbmaAuthModelSerivceFacade.updateFunctionAuthorizationModel(headMap, bodyMap);
	}
    
	/**
	 * 查询列表不分页
	 */
	@Override
	public ResponseEntity queryAuthorizationModelList(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pbmaAuthModelSerivceFacade.queryAuthorizationModelList(headMap, bodyMap);
	}

    /**
     * 查询菜单列表
     */
	@Override
	public ResponseEntity queryMenuUnionFuncion(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbmaAuthModelSerivceFacade.queryMenuUnionFuncion(headMap, bodyMap);
	}

}
