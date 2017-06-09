package bros.p.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:IPMenudefServiceFacade  
 * @Description:菜单服务提供方对外暴露服务接口
 * @author  haojinhui
 * @date 2016年7月6日 上午10:40:28 
 * @version V1.0  
 */
public interface IPMenudefServiceFacade {
	/**
	 * 
	 * @Title: addMenudef
	 * @Description: 增加菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity addMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: queryMenudef
	 * @Description: 菜单查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: updateMenudef 
	 * @Description: 修改菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: deleteMenudef 
	 * @Description: 删除菜单
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity deleteMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: setMenuRole
	 * @Description: 设置菜单角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity setMenuRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: updateMenuRole
	 * @Description: 修改菜单角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateMenuRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: queryMenuRole
	 * @Description: 查询菜单角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryMenuRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
}
