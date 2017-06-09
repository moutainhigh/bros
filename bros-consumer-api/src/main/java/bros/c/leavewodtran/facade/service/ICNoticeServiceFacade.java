package bros.c.leavewodtran.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICNoticeServiceFacade 
 * @Description: 公告管理接口
 * @author lichen 
 * @date 2016年10月21日 下午2:04:44 
 * @version 1.0
 */
public interface ICNoticeServiceFacade {
	/**
	 * 
	 * @Title: queryNotice 
	 * @Description: 公告查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryNotice (Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 公告添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addNotice (Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateNotice 
	 * @Description: 公告修改
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;

}
