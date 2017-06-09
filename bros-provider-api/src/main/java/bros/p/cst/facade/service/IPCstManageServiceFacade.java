package bros.p.cst.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPCstManageServiceFacade 
 * @Description: 渠道客户信息管理对外暴露接口
 * @author liwei 
 * @date 2016年9月12日 下午3:04:06 
 * @version 1.0
 */
public interface IPCstManageServiceFacade {
	/**
	 * 
	 * @Title: queryCstInfo 
	 * @Description: 客户信息查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	 public ResponseEntity queryCstInfo(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	 
	 /**
	  * 
	  * @Title: saveCstInfo 
	  * @Description: 保存渠道客户签约信息
	  * @param headMap
	  * @param bodyMap
	  * @return
	  * @throws ServiceException
	  */
	 public ResponseEntity saveCstInfo(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
