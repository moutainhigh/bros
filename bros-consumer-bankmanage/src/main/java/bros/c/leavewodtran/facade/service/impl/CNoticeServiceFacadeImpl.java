package bros.c.leavewodtran.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.leavewodtran.facade.service.ICNoticeServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.leavewodtran.facade.service.IPNoticeServiceFacade;

/**
 * 
 * @ClassName: CNoticeServiceFacadeImpl 
 * @Description: 公告管理接口实现
 * @author lichen 
 * @date 2016年10月21日 下午2:11:05 
 * @version 1.0
 */
@Component("cnoticeServiceFacade")
public class CNoticeServiceFacadeImpl implements ICNoticeServiceFacade {
	@Autowired
	private IPNoticeServiceFacade noticeServiceFacade;
	/**
	 * 
	 * @Title: queryNotice 
	 * @Description: 公告查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return noticeServiceFacade.queryNotice(headMap, bodyMap);
	}

	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 公告添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity addNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return noticeServiceFacade.addNotice(headMap, bodyMap);
	}

	/**
	 * 
	 * @Title: updateNotice 
	 * @Description: 公告修改
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity updateNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return noticeServiceFacade.updateNotice(headMap, bodyMap);
	}

}
