package bros.p.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPOperatorManageServiceFacade;
import bros.provider.parent.custmanage.operator.IOperatorManageService;

/**
 * 
 * @ClassName: POperatorManageServiceFacadeImpl 
 * @Description: 操作员管理实现类对外接口
 * @author pengxiangnan 
 * @date 2016年7月13日 下午2:51:03 
 * @version 1.0
 */
@Component("poperatorManageServiceFacade")
public class POperatorManageServiceFacadeImpl implements IPOperatorManageServiceFacade {
	/**
	 * 渠道系统分组服务
	 */
	@Autowired
	private IOperatorManageService operatorManage;
    
	/**
	 * 
	 * @Title: addOperatorManageMethod 
	 * @Description: 添加操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000311")
	public ResponseEntity addOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		bodyMap.put("cstNo", (String)bodyMap.get("custNo"));
		operatorManage.addOperatorManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
	/**
	 * 
	 * @Title: queryOperatorManageMethod 
	 * @Description: 查询操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
//		bodyMap.put("cstNo", (String)bodyMap.get("custNo"));
		return operatorManage.queryOperatorManageMethod(headMap, bodyMap);
	}
    
	/**
	 * 
	 * @Title: updateOperatorManageMethod 
	 * @Description: 更新操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000313")
	public ResponseEntity updateOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		bodyMap.put("cstNo", (String)bodyMap.get("custNo"));
		operatorManage.updateOperatorManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
	/**
	 * 
	 * @Title: deleteOperatorManageMethod 
	 * @Description: 删除操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000314")
	public ResponseEntity deleteOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		operatorManage.updateOperatorSttManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
	/**
	 * @Title: queryOperatorListForPage 
	 * @Description: 查询操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000328")
	public ResponseEntity queryOperatorListForPage(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity(operatorManage.queryOperatorListForPage(headMap, bodyMap));
		return responseEntity;
	}
}
