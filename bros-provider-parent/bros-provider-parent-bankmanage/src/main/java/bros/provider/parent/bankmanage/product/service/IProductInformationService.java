package bros.provider.parent.bankmanage.product.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IProductInformationService 
 * @Description: 产品信息基础服务实现接口
 * @author huangdazhou
 * @date 2016年6月30日 下午5:07:24 
 * @version 1.0
 */
public interface IProductInformationService {
	/**
	 * 
	 * @Title: addProductInformationMethod 
	 * @Description: 添加产品信息
	 * @param bodyMap 报文体信息map
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int addProductInformationMethod(Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: addAttributeParameterMethod 
	 * @Description: 添加产品属性参数
	 * @param parmINList 
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int addAttributeParameterMethod(List<Map<String,Object>> parmINList) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateProductInformationMethod 
	 * @Description: 修改产品信息
	 * @param bodyMap 报文体信息map
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int updateProductInformationMethod(Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteAttributeParameterMethod 
	 * @Description: 删除产品属性参数
	 * @param productCode 产品ID
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int deleteAttributeParameterMethod(String productCode) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteProductInformationMethod 
	 * @Description: 删除产品信息
	 * @param productCode 产品ID
	 * @return int 返回信息
	 * @throws ServiceException
	 */
	public int deleteProductInformationMethod(String productCode) throws ServiceException;
	
	/**
	 * @Title: queryProductInformationMethod 
	 * @Description: 查询产品信息(可选择传值)
	 * @param productCode 产品ID
	 * @param prdName 产品名称
	 * @param cateCode 产品目录编号
	 * @param prdTypeCode 产品分类编号
	 * @param modifiedBy 维护操作员
	 * @param modifiedDate 维护日期
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductInformationMethod(String productCode,String prdName,
			String cateCode,String prdTypeCode,String modifiedBy,String modifiedDate) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryPropertyValueByProductTypeCodeMethod 
	 * @Description: 根据产品分类编码、产品属性查询规格属性参数信息
	 * @param headMap 报文头信息map
	 * @param bodyMap 报文体信息map 包含以下参数
	 * @param prdTypeCode 产品分类编号
	 * @param property 产品属性
	 * @return List<Map<String, Object>> 属性参数值列表信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryPropertyValueByProductTypeCodeMethod(Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * @Title: queryProductInformationPageMethod 
	 * @Description: 分页查询产品信息(可选择传值)
	 * @param productCode 产品ID
	 * @param prdName 产品名称
	 * @param cateCode 产品目录编号
	 * @param prdTypeCode 产品分类编号
	 * @param modifiedBy 维护操作员
	 * @param modifiedDate 维护日期
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return Map<String, Object> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductInformationPageMethod(String productCode,String prdName,
			String cateCode,String prdTypeCode,String modifiedBy,String modifiedDate,
			int pageNo,int pageSize) throws ServiceException;
	
	/**
	 * @Title: queryProductInformationOneMethod 
	 * @Description: 单笔查询产品信息
	 * @param productCode 产品ID
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductInformationOneMethod(String productCode) throws ServiceException;
	
	/**
	 * @Title: queryProductInformationNumMethod 
	 * @Description: 查询产品信息笔数
	 * @param productCode 产品ID
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	public int queryProductInformationNumMethod(String productCode,String prdName,
			String cateCode,String prdTypeCode,String modifiedBy,String modifiedDate) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAttributeParameterMethod 
	 * @Description: 查询规格属性参数信息(可选择传值)
	 * @param productCode 产品编号
	 * @param property 产品属性编码
	 * @param propertyName 属性名称
	 * @param setValue 属性值
	 * @param description 属性描述
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryAttributeParameterMethod(String productCode,String property,
			String propertyName,String setValue,String description) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAttributePageMethod 
	 * @Description: 分页查询规格属性参数信息(可选择传值)
	 * @param productCode 产品编号
	 * @param property 产品属性编码
	 * @param propertyName 属性名称
	 * @param setValue 属性值
	 * @param description 属性描述
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryAttributePageMethod(String productCode,String property,
			String propertyName,String setValue,String description,
			int pageNo,int pageSize) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAttributeOneMethod 
	 * @Description: 单笔查询规格属性参数信息(可选择传值)
	 * @param productCode 产品编号
	 * @param property 产品属性编码
	 * @param setValue 属性值
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryAttributeOneMethod(String productCode,String property,
			String setValue) throws ServiceException;
	/**
	 * 
	 * @Title: queryAttributeNumMethod 
	 * @Description: 查询规格属性参数信息笔数(可选择传值)
	 * @param productCode 产品编号
	 * @param property 产品属性编码
	 * @param propertyName 属性名称
	 * @param setValue 属性值
	 * @param description 属性描述
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	public int queryAttributeNumMethod(String productCode,String property,
			String propertyName,String setValue,String description) throws ServiceException;
	/**
	 * 
	 * @Title: queryAttributeInfoMethod 
	 * @Description: 根据产品分类编码、产品编码查询产品属性信息
	 * @param prdTypeCode 产品分类编码
	 * @param productCode 产品编码
	 * @return List<Map<String, Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryAttributeInfoMethod(String prdTypeCode,String productCode) throws ServiceException;
}
