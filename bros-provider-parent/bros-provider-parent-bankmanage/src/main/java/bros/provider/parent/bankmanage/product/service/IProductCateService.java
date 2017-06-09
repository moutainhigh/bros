package bros.provider.parent.bankmanage.product.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IProductCateService 
 * @Description: 产品目录基础服务实现接口
 * @author huangdazhou
 * @date 2016年6月30日 下午2:31:20 
 * @version 1.0
 */
public interface IProductCateService {
	/**
	 * 
	 * @Title: addProductCateMethod 
	 * @Description: 新增产品目录方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int addProductCateMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateProductCateMethod 
	 * @Description: 修改产品目录方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int updateProductCateMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductCateMethod 
	 * @Description: 删除产品目录方法
	 * @param cateCode 产品目录编码
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int deleteProductCateMethod(String cateCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCateMethod 
	 * @Description: 查询产品目录目录方法
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @return List<Map<String, Object>> 返回结果信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductCateMethod(String cateCode,String cateName,String parentCate) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCatePageMethod 
	 * @Description: 分页查询产品目录方法
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductCatePageMethod(String cateCode,String cateName,String parentCate,int pageNo,int pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCateOneMethod 
	 * @Description: 单笔查询产品目录方法
	 * @param cateCode 产品目录编号
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductCateOneMethod(String cateCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCateNumMethod 
	 * @Description: 查询产品目录笔数
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public int queryProductCateNumMethod(String cateCode,String cateName,String parentCate) throws ServiceException;

}
