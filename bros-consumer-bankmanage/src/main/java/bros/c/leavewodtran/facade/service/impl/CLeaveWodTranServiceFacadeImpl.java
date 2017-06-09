package bros.c.leavewodtran.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.leavewodtran.facade.service.ICLeaveWodTranServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.leavewodtran.facade.service.IPLeaveWordTranServiceFacade;

/**
 * 
 * @ClassName: CLeaveWodTranServiceFacadeImpl 
 * @Description: 客户之声接口实现
 * @author lichen 
 * @date 2016年10月9日 下午2:03:54 
 * @version 1.0
 */
@Component("cleaveWodTranServiceFacade")
public class CLeaveWodTranServiceFacadeImpl implements ICLeaveWodTranServiceFacade{
	
	@Autowired
	private IPLeaveWordTranServiceFacade pleaveWordTranServiceFacade;

	/**
	 * 
	 * @Title: queryTranCstleaveWords 
	 * @Description: 客户之声查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryTranCstleaveWords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pleaveWordTranServiceFacade.queryTranCstleaveWords(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: addTranCstleaveWords 
	 * @Description: 客户之声留言
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity addTranCstleaveWords(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pleaveWordTranServiceFacade.addTranCstleaveWords(headMap, bodyMap);
	}

	/**
	 * 
	 * @Title: updateCstleavewords 
	 * @Description: 客户之声处理状态更新
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity updateCstleavewords(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pleaveWordTranServiceFacade.updateCstleavewords(headMap, bodyMap);
	}

}
