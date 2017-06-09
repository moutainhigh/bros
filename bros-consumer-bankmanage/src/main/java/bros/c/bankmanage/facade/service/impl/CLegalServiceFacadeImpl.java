package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICLegalServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPLegalServiceFacade;
/** 
 * @ClassName:CLegalServiceFacadeImpl  
 * @Description:法人系统对外接口实现类
 * @author  haojinhui
 * @date 2016年7月7日 下午3:09:35 
 * @version V1.0  
 */
@Component("clegalServiceFacade")
public class CLegalServiceFacadeImpl implements ICLegalServiceFacade  {
	/**
	 * 法人系统实现类
	 */
	@Autowired
	private IPLegalServiceFacade plegalServiceFacade;

	/**
	 * 
	 * @Title: addLegal
	 * @Description: 增加法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000027")
	public ResponseEntity addLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return plegalServiceFacade.addLegal(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryLegal
	 * @Description: 法人查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return plegalServiceFacade.queryLegal(headMap, bodyMap);
	}
	
	/**
	 * 分页法人查询
	 */
	public ResponseEntity queryLegalPage(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return plegalServiceFacade.queryLegalPage(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateLegal
	 * @Description: 法人修改
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000029")
	public ResponseEntity updateLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return plegalServiceFacade.updateLegal(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: deleteLegal
	 * @Description: 删除法人信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000030")
	public ResponseEntity deleteLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {

		return plegalServiceFacade.deleteLegal(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: querytreeLegal
	 * @Description: 查询法人树形数据
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000026")
	public ResponseEntity querytreeLegal(Map<String,Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return plegalServiceFacade.querytreeLegal(headMap, bodyMap);
	}
	
}
