package bros.p.auth.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;

/** 
 * @ClassName: TestService 
 * @Description: 这里用一句话描述这个类的作用
 * @author weiyancheng
 * @date 2016年8月2日 下午3:29:31 
 * @version 1.0 
 */
@Component("testService")
public class TestService implements IPInsideAuthServiceFacade {

	/** 
	 * @Title: qryTaskCenterView 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map1.put("taskId", "4567890pfghjkjhy78jngyu");
		map2.put("taskId", "0okm hy7gvcr5ddrtgbnkokm,");
		list.add(map2);
		list.add(map1);
		map.put("resultList", list);
		return new ResponseEntity(map);
	}

	/** 
	 * @Title: qryTaskCenterViewByBsnType 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryTaskCenterViewByBsnType(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: qryAuthQueueList 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: unClaimTaskJob 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: singleCompleteTask 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity singleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: authSingleCompleteTask 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity authSingleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: batchCompleteTask 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity batchCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: qryOrderDetailListByBatchNo 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryOrderDetailListByBatchNo(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @Title: qryCommentListByTaskId 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity qryAuthDetailPageDataByBusinessKey(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity queryCancelProcessInstanceList(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity cancelProcessInstance(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity addAuthProcessPhotoToFtpServer(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity queryAuthProcessNodeDetail(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
