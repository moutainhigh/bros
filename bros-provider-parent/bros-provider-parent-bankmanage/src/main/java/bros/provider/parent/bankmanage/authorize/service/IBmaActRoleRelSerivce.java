package bros.provider.parent.bankmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaActRoleRelSerivce 
 * @Description: 内部授权模型--授权角色接口
 * @author pengxiangnan 
 * @date 2016年7月25日 下午2:39:33 
 * @version 1.0
 */
public interface IBmaActRoleRelSerivce {
	
	/**
	 * 
	 * @Title: saveBmaActRoleRel 
	 * @Description: 新增授权角色
	 * @param roleList 角色列表
	 * @throws ServiceException
	 */
	public int saveBmaActRoleRel(List<Map<String,Object>> roleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBmaActRoleRel 
	 * @Description: 删除授权角色
	 * @param barId 关联表ID
	 * @throws ServiceException
	 */
	public int deleteBmaActRoleRel (String barId) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBatchBmaActRoleRel 
	 * @Description: 批量删除授权角色
	 * @param roleIdList 角色关联id
	 * @throws ServiceException
	 */
	public int deleteBatchBmaActRoleRel (List<String> roleIdList) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaActRoleRefByAuthModelId 
	 * @Description: 查询授权角色
	 * @param authRole 关联角色id
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaActRoleRefById (String authRole) throws ServiceException;
}
