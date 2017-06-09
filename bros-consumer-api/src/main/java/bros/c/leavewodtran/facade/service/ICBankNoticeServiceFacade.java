package bros.c.leavewodtran.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICBankNoticeServiceFacade 
 * @Description: 银行资讯接口
 * @author lichen 
 * @date 2016年10月18日 上午10:15:46 
 * @version 1.0
 */
public interface ICBankNoticeServiceFacade {

	/**
	 * 
	 * @Title: queryBankNotice 
	 * @Description: 银行资讯查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryBankNotice (Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: addBankNotice 
	 * @Description: 银行资讯添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addBankNotice (Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBankNotice 
	 * @Description: 银行资讯修改
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
