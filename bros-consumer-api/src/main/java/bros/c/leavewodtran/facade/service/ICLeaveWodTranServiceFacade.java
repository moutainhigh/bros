package bros.c.leavewodtran.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICLeaveWodTranServiceFacade 
 * @Description: 客户之声接口
 * @author lichen
 * @date 2016年10月9日 下午1:54:53 
 * @version 1.0
 */
public interface ICLeaveWodTranServiceFacade {
	
	/**
	 * 
	 * @Title: queryTranCstleaveWords 
	 * @Description: 客户之声查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryTranCstleaveWords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: addTranCstleaveWords 
	 * @Description: 添加客户之声留言
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addTranCstleaveWords (Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateCstleavewords 
	 * @Description: 客户之声处理状态更新
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateCstleavewords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
