package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICOperatorManageServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPOperatorManageServiceFacade;

/**
 * 
 * @ClassName: COperatorManageServiceFacadeImpl 
 * @Description: 操作员管理实现类对外接口
 * @author pengxiangnan 
 * @date 2016年7月13日 下午2:51:03 
 * @version 1.0
 */
@Component("coperatorManageServiceFacade")
public class COperatorManageServiceFacadeImpl implements ICOperatorManageServiceFacade {
	/**
	 * 渠道系统分组服务
	 */
	@Autowired
	private IPOperatorManageServiceFacade poperatorManageServiceFacade;
    
	/**
	 * 
	 * @Title: addOperatorManageMethod 
	 * @Description: 添加操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="c0000311")
	public ResponseEntity addOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		bodyMap.put("cstNo", (String)bodyMap.get("custNo"));
		return poperatorManageServiceFacade.addOperatorManageMethod(headMap, bodyMap);
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
		return poperatorManageServiceFacade.queryOperatorManageMethod(headMap, bodyMap);
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
	@Validation(value="c0000313")
	public ResponseEntity updateOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		bodyMap.put("cstNo", (String)bodyMap.get("custNo"));
		return poperatorManageServiceFacade.updateOperatorManageMethod(headMap, bodyMap);
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
	@Validation(value="c0000314")
	public ResponseEntity deleteOperatorManageMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return poperatorManageServiceFacade.deleteOperatorManageMethod(headMap, bodyMap);
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
	@Validation(value="c0000328")
	public ResponseEntity queryOperatorListForPageMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return poperatorManageServiceFacade.queryOperatorListForPage(headMap, bodyMap);
	}
}
