package bros.provider.parent.bankmanage.customer.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: ICstLeaveWordTranService 
 * @Description: 客户之声接口
 * @author lichen 
 * @date 2016年10月9日 上午9:49:27 
 * @version 1.0
 */
public interface ICstLeaveWordTranService {
	
	/**
	 * 
	 * @Title: selectTotalNum 
	 * @Description: 查询客户之声总记录数
	 * @param pageNo 	  请求第几页
	 * @param pageSize	  每页请求记录数
	 * @param bodyMap	 map
	 * @return
	 * @throws ServiceException
	 */
	public int selectTotalNum(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 *
	 * @Title: queryTranCstleaveWords 
	 * @Description: 客户之声查询
	 * @param bodyMap pageNo pageSize
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryTranCstleaveWords(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap)throws ServiceException;
	
	
	/**
	 * 
	 * @Title: addTranCstleaveWords 
	 * @Description: 客户之声提交
	 * @param bodyMap	入参
	 * @throws ServiceException
	 */
	public void addTranCstleaveWords(Map<String, Object> bodyMap)throws ServiceException;
	
/**
 * 
 * @Title: updateCstleavewords 
 * @Description: 更新客户之声处理状态
 * @throws ServiceException
 */
	public void updateCstleavewords(Map<String, Object> bodyMap)throws ServiceException;
}
