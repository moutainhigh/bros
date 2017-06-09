package bros.provider.parent.bankmanage.customer.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: INoticeService 
 * @Description: 公告管理接口
 * @author lichen 
 * @date 2016年10月21日 上午10:50:53 
 * @version 1.0
 */
public interface INoticeService {
	/**
	 * 
	 * @Title: selectTotalNum 
	 * @Description: 查询公告总记录数
	 * @param pageNo   请求第几页
	 * @param pageSize  每页请求记录数
	 * @param bodyMap   map
	 * @return
	 * @throws ServiceException
	 */
	public int selectTotalNum(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: queryNotice 
	 * @Description: 公告查询
	 * @param pageNo
	 * @param pageSize
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryNotice(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 公告添加
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void addNotice(Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateNotice 
	 * @Description: 修改公告
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void updateNotice(Map<String, Object> bodyMap)throws ServiceException;

}
