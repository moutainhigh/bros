package bros.provider.parent.custmanage.role;

import java.util.List;
import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IRoleManageService 
 * @Description: 角色操作接口
 * @author mazhilei 
 * @date 2016年6月28日 下午2:34:04 
 * @version 1.0
 */
public interface IRoleManageService {

	/**
	 * 
	 * @Title: addRoleManageMethod 
	 * @Description: 添加角色信息方法
	 */
	public void addRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @return 
	 * @Title: addRoleMethod 
	 * @Description: 添加ttp_role表
	 */
	public String addRoleTableMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryRoleMethod 
	 * @Description: 查询企业客户下所有角色信息
	 */
	public List<Map<String,Object>> queryCorporationRoleMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

	
	/**
	 * 
	 * @Title: queryRoleMethod 
	 * @Description: 分页查询企业客户下所有角色信息
	 */
	public Map<String,Object> queryCorporationRolePageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: queryRoleMethod 
	 * @Description: 查询角色详细信息
	 */
	public ResponseEntity queryRoleDetailMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: updateRoleMethod 
	 * @Description: 更新角色方法
	 */
	public void updateRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateRoleMethod 
	 * @Description: 更新角色基本信息
	 */
	public void updateRoleBasicManageMethod(String roleId,String treName,String treDesc,String treModitime,String treModiuser,String treState,String treType) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteRoleMethod 
	 * @Description: 删除角色方法
	 */
	public void deleteRoleManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteRoleMethod 
	 * @Description: 删除角色对应功能方法
	 */
	public void deleteRoleBsnRelMethod(String roleId) throws ServiceException;
}
