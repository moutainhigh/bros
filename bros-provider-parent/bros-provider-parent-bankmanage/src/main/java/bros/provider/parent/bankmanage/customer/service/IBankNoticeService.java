package bros.provider.parent.bankmanage.customer.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IBankNoticeService 
 * @Description: 银行资讯接口
 * @author lichen 
 * @date 2016年10月17日 下午3:21:07 
 * @version 1.0
 */
public interface IBankNoticeService {
	
	/**
	 * 
	 * @Title: selectTotalNum 
	 * @Description: 查询银行资讯总记录数
	 * @param pageNo   请求第几页
	 * @param pageSize  每页请求记录数
	 * @param bodyMap   map
	 * @return
	 * @throws ServiceException
	 */
	public int selectTotalNum(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: queryBankNotice 
	 * @Description: 银行资讯查询
	 * @param pageNo
	 * @param pageSize
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryBankNotice(Integer pageNo,Integer pageSize,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: addBankNotice 
	 * @Description: 银行资讯添加
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void addBankNotice(Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBankNotice 
	 * @Description: 修改银行资讯
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void updateBankNotice(Map<String, Object> bodyMap)throws ServiceException;
}
