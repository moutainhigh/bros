package bros.provider.parent.bankmanage.product.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IProductServiceService 
 * @Description: 产品服务实现接口
 * @author huangdazhou
 * @date 2016年6月30日 上午10:21:15 
 * @version 1.0
 */
public interface IProductServiceService {
	/**
	 * 
	 * @Title: addProductServiceMethod 
	 * @Description: 新增产品服务方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int addProductServiceMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateProductServiceMethod 
	 * @Description: 修改产品服务方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int updateProductServiceMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductServiceMethod 
	 * @Description: 删除产品服务方法
	 * @param prdSvrCode 产品服务编码
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	public int deleteProductServiceMethod(String prdSvrCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServiceMethod 
	 * @Description: 查询产品服务方法
	 * @param prdSvrCode 服务编号
	 * @param prdTypeCode 产品分类编号
	 * @param prdSvrName 服务名称
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductServiceMethod(String prdSvrCode,
			String prdTypeCode,String prdSvrName) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServicePageMethod 
	 * @Description: 分页查询产品服务方法
	 * @param prdSvrCode 服务编号
	 * @param prdTypeCode 产品分类编号
	 * @param prdSvrName 服务名称
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductServicePageMethod(String prdSvrCode,
			String prdTypeCode,String prdSvrName,
			int pageNo, int pageSize) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServiceOneMethod 
	 * @Description: 单笔查询产品服务方法
	 * @param prdSvrCode 服务编号
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductServiceOneMethod(String prdSvrCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServiceNumMethod 
	 * @Description: 查询产品服务笔数方法
	 * @param prdSvrCode 服务编号
	 * @param prdTypeCode 产品分类编号
	 * @param prdSvrName 服务名称
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public int queryProductServiceNumMethod(String prdSvrCode,
			String prdTypeCode,String prdSvrName) throws ServiceException;

}
