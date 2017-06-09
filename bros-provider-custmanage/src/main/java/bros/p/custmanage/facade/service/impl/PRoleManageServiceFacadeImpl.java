package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPRoleManageServiceFacade;
import bros.provider.parent.custmanage.role.IRoleManageService;

/**
 * 
 * @ClassName: PRoleManageServiceFacadeImpl 
 * @Description: 角色管理实现对外接口
 * @author pengxiangnan 
 * @date 2016年7月13日 下午2:51:25 
 * @version 1.0
 */
@Component("proleManageServiceFacade")
public class PRoleManageServiceFacadeImpl implements IPRoleManageServiceFacade {
	/**
	 * 角色管理实现
	 */
	@Autowired
	private IRoleManageService  roleManage;
	
	/**
	 * 
	 * @Title: addRoleManageMethod 
	 * @Description: 添加角色信息方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000315")
	public ResponseEntity addRoleManageMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity responseEntity = new ResponseEntity();
		roleManage.addRoleManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
	/**
	 * 
	 * @Title: queryCorporationRoleMethod 
	 * @Description: 查询企业客户下所有角色信息
     * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000316")
	public ResponseEntity  queryCorporationRoleMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		Map<String, Object> paramOut = new HashMap<String, Object>();
		paramOut= roleManage.queryCorporationRolePageMethod(headMap, bodyMap);
		entity = new ResponseEntity(paramOut);	
		return entity;
	}
    
	/**
	 * 
	 * @Title: queryRoleDetailMethod 
	 * @Description: 查询角色详细信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000317")
	public ResponseEntity queryRoleDetailMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return roleManage.queryRoleDetailMethod(headMap, bodyMap);
	}
    
	/**
	 * 
	 * @Title: updateRoleManageMethod 
	 * @Description: 更新角色方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000318")
	public ResponseEntity updateRoleManageMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity responseEntity = new ResponseEntity();
		roleManage.updateRoleManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
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
	@Override
	@Validation(value="p0000319")
	public ResponseEntity updateRoleBasicManageMethod(String roleId, String treName,String treDesc, String treModitime, String treModiuser,String treState, String treType) throws ServiceException {
		ResponseEntity responseEntity = new ResponseEntity();
		roleManage.updateRoleBasicManageMethod(roleId, treName, treDesc, treModitime, treModiuser, treState, treType);
		return responseEntity;
	}
    
	/**
	 * 
	 * @Title: deleteRoleManageMethod 
	 * @Description: 删除角色方法
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000320")
	public ResponseEntity deleteRoleManageMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity responseEntity = new ResponseEntity();
		roleManage.deleteRoleManageMethod(headMap, bodyMap);
		return responseEntity;
	}
    
	/**
	 * 
	 * @Title: deleteRoleBsnRelMethod 
	 * @Description: 删除角色对应功能方法
	 * @param roleId 角色编码
	 * @throws ServiceException
	 */
	@Override
	@Validation(value="p0000321")
	public ResponseEntity deleteRoleBsnRelMethod(String roleId) throws ServiceException {
		ResponseEntity responseEntity = new ResponseEntity();
		roleManage.deleteRoleBsnRelMethod(roleId);
		return responseEntity;
	}

}
