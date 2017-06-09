package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:ICLegalServiceFacade  
 * @Description:法人服务提供方对外暴露服务接口
 * @author  haojinhui
 * @date 2016年7月7日 下午2:57:18 
 * @version V1.0  
 */
public interface ICLegalServiceFacade {
	
	/**
	 * 
	 * @Title: addLegal 
	 * @Description: 增加法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity addLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * 
	 * @Title: queryLegal 
	 * @Description: 法人信息查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: queryLegalPage 
	 * @Description: 分页查询法人信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity queryLegalPage(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
 	 * 
 	 * @Title: querytreeLegal 
 	 * @Description: 查询法人树形数据
 	 * @param headMap  头信息
 	 * @param bodyMap  上送报文
 	 * @return
 	 * @throws ServiceException
 	 */
     public ResponseEntity querytreeLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
     
    /**
	 * 
	 * @Title: updateLegal 
	 * @Description: 修改法人信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity updateLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    /**
	 * 
	 * @Title: deleteLegal 
	 * @Description: 删除法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
    public ResponseEntity deleteLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
