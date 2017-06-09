package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPRoleManageService 
 * @Description:角色管理对外接口
 * @author pengxiangnan 
 * @date 2016年7月13日 下午2:37:35 
 * @version 1.0
 */
public interface IPRoleManageServiceFacade {

	/**
	 * 
	 * @Title: addRoleManageMethod 
	 * @Description: 添加角色信息方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity addRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCorporationRoleMethod 
	 * @Description: 查询企业客户下所有角色信息
     * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	public ResponseEntity queryCorporationRoleMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: queryRoleDetailMethod 
	 * @Description: 查询角色详细信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity queryRoleDetailMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: updateRoleManageMethod 
	 * @Description: 更新角色方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity updateRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateRoleBasicManageMethod 
	 * @Description:  更新角色基本信息
	 * @param roleId 角色编码
	 * @param treName 名称
	 * @param treDesc 描述
	 * @param treModitime 修改时间
	 * @param treModiuser 修改人
	 * @param treState 状态
	 * @param treType 类型
	 * @throws ServiceException
	 */
	public ResponseEntity updateRoleBasicManageMethod(String roleId,String treName,String treDesc,String treModitime,String treModiuser,String treState,String treType) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteRoleManageMethod 
	 * @Description: 删除角色方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity deleteRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteRoleBsnRelMethod 
	 * @Description: 删除角色对应功能方法
	 * @param roleId 角色编码
	 * @throws ServiceException
	 */
	public ResponseEntity deleteRoleBsnRelMethod(String roleId) throws ServiceException;
}
