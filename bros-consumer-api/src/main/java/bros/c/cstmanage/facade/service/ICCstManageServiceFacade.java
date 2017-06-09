package bros.c.cstmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICCstManageServiceFacade 
 * @Description: 渠道客户信息管理接口
 * @author liwei 
 * @date 2016年9月12日 下午2:02:06 
 * @version 1.0
 */
public interface ICCstManageServiceFacade {
	
	/**
	 * 
	 * @Title: queryCstInfo 
	 * @Description: 渠道客户信息查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryCstInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	
	/**
	 * 
	 * @Title: saveCstInfoChannel 
	 * @Description: 渠道客户签约-开户
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity saveCstInfoChannel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
