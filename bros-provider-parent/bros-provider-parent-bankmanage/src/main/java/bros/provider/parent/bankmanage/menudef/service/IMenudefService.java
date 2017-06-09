package bros.provider.parent.bankmanage.menudef.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:IMenudefService  
 * @Description:菜单系统分组接口
 * @author  haojinhui
 * @date 2016年7月6日 上午10:57:07 
 * @version V1.0  
 */
public interface IMenudefService {
	/**
	 * 
	 * @Title: addMenudef
	 * @Description: 增加菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMenudef
	 * @Description: 菜单查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateMenudef 
	 * @Description: 修改菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> updateMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;

	/**
	 * 
	 * @Title: deleteMenudef 
	 * @Description: 删除菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> deleteMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;

	/**
	 * 
	 * @Title: addMenuRole
	 * @Description: 设置菜单角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addMenuRole(Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMenuRole
	 * @Description: 查询菜单角色关联
	 * @param bmrlMenuId  菜单id
	 * @param bmrlRoleId  角色id
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryMenuRole(String bmrlMenuId,String bmrlRoleId)throws ServiceException;
	/**
	 * 
	 * @Title: queryMenuRoleNum
	 * @Description: 查询菜单角色关联笔数
	 * @param bmrlMenuId  菜单id
	 * @param bmrlRoleId  角色id
	 * @return
	 * @throws ServiceException
	 */
	public int queryMenuRoleNum(String bmrlMenuId,String bmrlRoleId)throws ServiceException;
	/**
	 * 
	 * @Title: deleteMenuRole
	 * @Description: 删除菜单角色关联
	 * @param bmrlMenuId  菜单id
	 * @param bmrlRoleId  角色id
	 * @return
	 * @throws ServiceException
	 */
	public int deleteMenuRole(String bmrlMenuId,String bmrlRoleId)throws ServiceException;
	

}
