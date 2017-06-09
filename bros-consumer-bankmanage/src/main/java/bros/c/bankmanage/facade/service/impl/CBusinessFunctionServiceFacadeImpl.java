package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICBusinessFunctionServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPBusinessFunctionServiceFacade;
/**
 * 
 * 
 * @ClassName: CBusinessFunctionServiceFacadeImpl 
 * @Description: 业务功能管理服务实现接口
 * @author huangdazhou 
 * @date 2016年12月23日 下午5:18:10 
 * @version 1.0
 */
@Component("cbusinessFunctionServiceFacade")
public class CBusinessFunctionServiceFacadeImpl implements
		ICBusinessFunctionServiceFacade {

	@Autowired
	IPBusinessFunctionServiceFacade pbusinessFunctionServiceFacade;
	/**
	 *  查询业务功能信息
	 */
	public ResponseEntity queryBsnFun(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.queryBsnFun(headMap, bodyMap);
	}
	/**
	 * 查询业务与菜单关联功能
	 */
	public ResponseEntity queryBsnFunRelMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.queryBsnFunRelMenudef(headMap, bodyMap);
	}
	/**
	 * 修改业务菜单关联
	 */
	public ResponseEntity updateBsnFunRelMenu(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.updateBsnFunRelMenu(headMap, bodyMap);
	}
	/**
	 * 添加业务功能信息
	 */
	public ResponseEntity insertOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.insertOneBsndef(headMap, bodyMap);
	}
	/**
	 * 修改业务功能信息
	 */
	public ResponseEntity updateOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.updateOneBsndef(headMap, bodyMap);
	}
	/**
	 * 删除业务功能信息
	 */
	public ResponseEntity deleteOneBsndef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pbusinessFunctionServiceFacade.deleteOneBsndef(headMap, bodyMap);
	}

}
