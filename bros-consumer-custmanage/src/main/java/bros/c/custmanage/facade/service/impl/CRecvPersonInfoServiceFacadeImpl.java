package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICRecvPersonInfoServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPRecvPersonInfoServiceFacade;
/**
 * 
 * @ClassName: CRecvPersonInfoServiceFacadeImpl 
 * @Description: 收款人信息维护接口实现
 * @author gaoyongjing 
 * @date 2016年9月21日 下午2:44:40 
 * @version 1.0
 */
@Component("crecvPersonInfoServiceFacade")
public class CRecvPersonInfoServiceFacadeImpl implements
		ICRecvPersonInfoServiceFacade {
	
	@Autowired
	private IPRecvPersonInfoServiceFacade precvPersonInfoServiceFacade;
	/**
	 * 
	 * @Title: addRecvPersonInfoMethod
	 * @Description: 新增收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity addRecvPersonInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return precvPersonInfoServiceFacade.addRecvPersonInfoMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateRecvPersonInfoMethod
	 * @Description: 修改收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateRecvPersonInfoMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		 
		return precvPersonInfoServiceFacade.updateRecvPersonInfoMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:     删除收款人信息
	 * @param headMap    公共报文头
	 * @param bodyMap    报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteRecvPersonInfoMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		 
		return precvPersonInfoServiceFacade.deleteRecvPersonInfoMethod(headMap, bodyMap);
	}
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllRecvPersonInfoMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		 
		return precvPersonInfoServiceFacade.queryAllRecvPersonInfoMethod(headMap, bodyMap);
	}
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表去账号重复
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryDistinctRecvPersonInfoPageMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException{
		return precvPersonInfoServiceFacade.queryDistinctRecvPersonInfoPageMethod(headMap, bodyMap);
	}
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryRecvPersonInfoPageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		 
		return precvPersonInfoServiceFacade.queryRecvPersonInfoPageMethod(headMap, bodyMap);
	}

}
