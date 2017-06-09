package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:IPTellerServiceFacade 
 * @Description:柜员服务提供方对外暴露服务接口
 * @author  haojinhui
 * @date 2016年6月28日 上午9:42:33 
 * @version V1.0  
 */
public interface ICTellerServiceFacade {
	/**
	 * 
	 * @Title: addTeller 
	 * @Description: 增加柜员
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity addTeller(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: queryTellerById 
	 * @Description: 柜员信息查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryTellerById(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: queryTeller 
	 * @Description: 修改柜员信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateTeller(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: deleteTeller 
	 * @Description: 删除柜员信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity deleteTeller(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: tellerLogin
	 * @Description: 柜员登录
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateTellerLogin(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: tellerLogout
	 * @Description: 柜员签退
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateTellerLogout(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
  	 * 
  	 * @Title: queryTellerRoleMenu
  	 * @Description: 柜员查询菜单
  	 * @param headMap  头信息
  	 * @param bodyMap  上送报文
  	 * @return
  	 * @throws ServiceException
  	 */
      public ResponseEntity queryTellerRoleMenu(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
      
      /**
    	 * 
    	 * @Title: queryMenudefPro
    	 * @Description: 根据法人id，菜单性质，系统标识查询货架编码
    	 * @param headMap  头信息
    	 * @param bodyMap  上送报文
    	 * @return
    	 * @throws ServiceException
    	 */
        public ResponseEntity queryMenudefPro(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}