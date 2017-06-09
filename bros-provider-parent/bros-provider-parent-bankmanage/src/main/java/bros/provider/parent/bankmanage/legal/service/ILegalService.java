package bros.provider.parent.bankmanage.legal.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName:ILegalService  
 * @Description:法人系统分组接口
 * @author  haojinhui
 * @date 2016年7月7日 下午3:13:49 
 * @version V1.0  
 */
public interface ILegalService {
	/**
	 * 
	 * @Title: addLegal
	 * @Description: 增加法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addLegal(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryLegal
	 * @Description:法人查询
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryLegal(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryLegalPage 
	 * @Description: 法人分页查询
	 * @param cllCode 法人编号
	 * @param cllStatus 法人状态
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryLegalPage(String cllCode,String cllStatus,
			int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: querytreeLegal
	 * @Description: 查询法人树形数据
	 * @param cllCode  法人编号
	 * @param cllStatus  法人状态
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> querytreeLegal(String cllCode,String cllStatus)throws ServiceException;
	
	/**
	 * 
	 * @Title: updateLegal 
	 * @Description: 修改法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> updateLegal(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;

	/**
	 * 
	 * @Title: deleteLegal 
	 * @Description: 删除法人
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> deleteLegal(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException;

}
