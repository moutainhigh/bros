package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPRecvPersonInfoServiceFacade 
 * @Description: 收款人信息维护接口
 * @author gaoyongjing 
 * @date 2016年9月21日 下午2:44:40 
 * @version 1.0
 */
public interface IPRecvPersonInfoServiceFacade {
	
	/**
	 * 
	 * @Title: addRecvPersonInfoMethod
	 * @Description: 新增收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addRecvPersonInfoMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateRecvPersonInfoMethod
	 * @Description: 修改收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateRecvPersonInfoMethod(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:     删除收款人信息
	 * @param headMap    公共报文头
	 * @param bodyMap    报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity deleteRecvPersonInfoMethod (Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllRecvPersonInfoMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表去账号重复
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryDistinctRecvPersonInfoPageMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryRecvPersonInfoPageMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
}
