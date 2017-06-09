package bros.provider.parent.custmanage.operator;

import java.util.List;
import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IOperatorManageService 
 * @Description: 操作员信息业务接口
 * @author mazhilei 
 * @date 2016年7月4日 上午9:34:55 
 * @version 1.0
 */
public interface IOperatorManageService {
	
	/**
	 * 
	 * @Title: addOperatorManageMethod 
	 * @Description: 添加操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void addOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryOperatorManageMethod 
	 * @Description: 查询操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateOperatorManageMethod 
	 * @Description: 更新操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void updateOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteOperatorManageMethod 
	 * @Description: 删除操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException
	 */
	public void updateOperatorSttManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * @Title: queryOperatorListForPage 
	 * @Description: 查询操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryOperatorListForPage(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
