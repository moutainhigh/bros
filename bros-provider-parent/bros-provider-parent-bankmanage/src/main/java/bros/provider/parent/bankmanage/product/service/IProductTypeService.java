package bros.provider.parent.bankmanage.product.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IProductTypeService 
 * @Description: 产品分类基础实现接口
 * @author huangdazhou
 * @date 2016年6月27日 下午2:24:51 
 * @version 1.0
 */
public interface IProductTypeService {
	/**
	 * 
	 * @Title: addProductTypeMethod 
	 * @Description: 添加产品分类信息实体方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	public int addProductTypeMethod(Map<String,Object> parmIN) throws ServiceException;
	
	/**
	 * 
	 * @Title: addSpecificationMethod 
	 * @Description: 添加规格属性信息实体方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	public int addSpecificationMethod(List<Map<String,Object>> parmIN) throws ServiceException;
	
	/**
	 * 
	 * @Title: addInstanceMethod 
	 * @Description: 添加实例属性信息实体方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	public int addInstanceMethod(List<Map<String,Object>> parmIN) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateProductTypeMethod 
	 * @Description: 修改产品分类信息实体方法
	 * @param bodyMap 报文体信息map
	 * @return int 返回执行影响条数
	 * @throws ServiceException
	 */
	public int updateProductTypeMethod(Map<String,Object> parmIN) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductTypeMethod 
	 * @Description: 删除产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @return int 返回执行影响条数
	 * @throws ServiceException
	 */
	public int deleteProductTypeMethod(String prdTypeCode) throws ServiceException;
	

	/**
	 * 
	 * @Title: deleteSpecificationMethod 
	 * @Description: 删除规格属性信息实体方法
	 * @param prdTypeCode 参数信息
	 * @throws ServiceException
	 */
	public int deleteSpecificationMethod(String prdTypeCode) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteInstanceMethod 
	 * @Description: 删除实例属性信息实体方法
	 * @param prdTypeCode 参数信息
	 * @throws ServiceException
	 */
	public int deleteInstanceMethod(String prdTypeCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductTypeMethod 
	 * @Description: 查询产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @param parentCode 上级产品分类编号
	 * @param prdTypeName 分类名称
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductTypeMethod(String prdTypeCode,String parentCode,String prdTypeName) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductTypePageMethod 
	 * @Description: 分页查询产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @param parentCode 上级产品分类编号
	 * @param prdTypeName 分类名称
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductTypePageMethod(String prdTypeCode,String parentCode,String prdTypeName,int pageNo,int pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductTypeOneMethod 
	 * @Description: 单笔查询产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductTypeOneMethod(String prdTypeCode) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryProductTypeNumMethod 
	 * @Description: 查询产品分类信息笔数
	 * @param bodyMap 报文体信息map
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	public int queryProductTypeNumMethod(String prdTypeCode,String parentCode,String prdTypeName) throws ServiceException;
	/**
	 * 
	 * @Title: querySpecificationMethod 
	 * @Description: 查询规格属性信息
	 * @param prdTypeCode 产品分类编号
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> querySpecificationMethod(String prdTypeCode) throws ServiceException;
	/**
	 * 
	 * @Title: querySpecificationPageMethod 
	 * @Description: 分页查询规格属性信息
	 * @param prdTypeCode 产品分类编号
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> querySpecificationPageMethod(String prdTypeCode,int pageNo,int pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: querySpecificationOneMethod 
	 * @Description: 单笔查询规格属性信息
	 * @param prdTypeCode 产品分类编号
	 * @param property 属性编号
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> querySpecificationOneMethod(String prdTypeCode,String property) throws ServiceException;
	/**
	 * 
	 * @Title: querySpecificationNumMethod 
	 * @Description: 查询规格属性信息笔数
	 * @param prdTypeCode 产品分类编号
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	public int querySpecificationNumMethod(String prdTypeCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryInstanceMethod 
	 * @Description: 查询实例属性信息
	 * @param prdTypeCode 产品分类编号
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryInstanceMethod(String prdTypeCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryInstanceMethod 
	 * @Description: 分页查询实例属性信息
	 * @param prdTypeCode 产品分类编号
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryInstancePageMethod(String prdTypeCode,int pageNo,int pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: queryInstanceOneMethod 
	 * @Description: 单笔查询实例属性信息
	 * @param prdTypeCode 产品分类编号
	 * @param property 属性编号
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryInstanceOneMethod(String prdTypeCode,String property) throws ServiceException;
	/**
	 * 
	 * @Title: queryInstanceNumMethod 
	 * @Description: 查询实例属性信息笔数
	 * @param prdTypeCode 产品分类编号
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	public int queryInstanceNumMethod(String prdTypeCode) throws ServiceException;

}
