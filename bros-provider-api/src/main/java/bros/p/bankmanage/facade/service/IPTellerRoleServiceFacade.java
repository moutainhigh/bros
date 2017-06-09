package bros.p.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:	IPTellerRoleServiceFacade
 * @Description:柜员角色服务提供方对外暴露服务接口
 * @author  haojinhui
 * @date 2016年6月30日 下午3:03:34 
 * @version V1.0  
 */
public interface IPTellerRoleServiceFacade {
	
	/**
	 * 
	 * @Title: addTellerRole 
	 * @Description: 增加柜员角色
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity addTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: updateTellerRole 
	 * @Description: 修改柜员角色
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: deleteTellerRole 
	 * @Description: 删除柜员角色
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity deleteTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: setTellerRole 
	 * @Description: 设置柜员角色关联
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity setTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
   	 * 
   	 * @Title: queryTellerRole 
   	 * @Description: 查询柜员角色
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return
   	 * @throws ServiceException
   	 */
     public ResponseEntity queryTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
     /**
 	 * 
 	 * @Title: updateSetTellerRole 
 	 * @Description: 更新柜员角色关联
 	 * @param headMap  头信息
 	 * @param bodyMap  上送报文
 	 * @return
 	 * @throws ServiceException
 	 */
     public ResponseEntity updateSetTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
     /**
  	 * 
  	 * @Title: querySetTellerRole 
  	 * @Description: 查询柜员已分配角色信息
  	 * @param headMap  头信息
  	 * @param bodyMap  上送报文
  	 * @return
  	 * @throws ServiceException
  	 */
      public ResponseEntity querySetTellerRole(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
      
}
