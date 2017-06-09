package bros.c.leavewodtran.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.leavewodtran.facade.service.ICBankNoticeServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.leavewodtran.facade.service.IPBankNoticeServiceFacade;

/**
 * 
 * @ClassName: CBankNoticeServiceFacadeImpl 
 * @Description: 银行资讯接口实现
 * @author lichen 
 * @date 2016年10月18日 上午10:25:13 
 * @version 1.0
 */
@Component("cbankNoticeServiceFacade")
public class CBankNoticeServiceFacadeImpl implements ICBankNoticeServiceFacade {
	
	@Autowired
	private IPBankNoticeServiceFacade bankNoticeServiceFacade;
	/**
	 * 
	 * @Title: queryBankNotice 
	 * @Description: 银行资讯查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return bankNoticeServiceFacade.queryBankNotice(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: addBankNotice 
	 * @Description: 银行资讯添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity addBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return bankNoticeServiceFacade.addBankNotice(headMap, bodyMap);
	}

	/**
	 * 
	 * @Title: updateBankNotice 
	 * @Description: 银行资讯修改
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity updateBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		return bankNoticeServiceFacade.updateBankNotice(headMap, bodyMap);
	}

}
