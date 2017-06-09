package bros.provider.parent.bankmanage.shelf.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IShelfInfoService 
 * @Description: 货架基本信息接口
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
public interface IShelfInfoService {
	/**
	 * 
	 * @Title: addShelfInfoMethod
	 * @Description: 新增货架信息
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return 
	 * @throws ServiceException
	 */
	public void addShelfInfoMethod(String shelfCode,String shelfName,String shelfDesc,
			String parentShelf,String mallId) throws ServiceException;
	/**
	 * 
	 * @Title: updateShelfInfoMethod
	 * @Description: 修改货架信息
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 货架上下架标志
	 * @return 
	 * @throws ServiceException
	 */
	public void updateShelfInfoMethod(String shelfCode,String shelfName,String shelfDesc,
			String parentShelf,String mallId,String shelfFlag) throws ServiceException;
	/**
	 * 
	 * @Title: deleteShelfInfoMethod
	 * @Description: 删除货架信息
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID 
	 * @return 
	 * @throws ServiceException
	 */
	public void deleteShelfInfoMethod (String shelfCode,String mallId)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryShelfInfoMethod
	 * @Description: 查询货架信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return List<Map<String,Object>>货架信息列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryShelfInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfInfoPageMethod
	 * @Description: 分页查询货架信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return List<Map<String,Object>> 货架信息列表  
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryShelfInfoPageMethod(Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfTotalNumMethod
	 * @Description: 查询货架总条数
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return int 总条数
	 * @throws ServiceException
	 */
	public int queryShelfTotalNumMethod(Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfInfoByObjectIdMethod
	 * @Description: 校验货架信息是否存在
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID
	 * @return Map<String,Object> 货架信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryShelfInfoByObjectIdMethod(String shelfCode, String mallId) throws ServiceException;
	/**
	 * 
	 * @Title: queryShowShelfInfoMethod
	 * @Description: 根据渠道编码、法人id、机构号查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 信息,包含以下参数
	 * @param chlCode 渠道编码
	 * @param legalPersonId 法人id
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表
	 * @throws ServiceException
	 */
	public Map<String, Object> queryShowShelfInfoMethod(Map<String, Object> paramMap)throws ServiceException;
}
