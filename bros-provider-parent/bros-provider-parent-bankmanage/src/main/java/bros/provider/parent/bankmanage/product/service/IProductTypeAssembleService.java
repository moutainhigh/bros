package bros.provider.parent.bankmanage.product.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IProductTypeEntityService 
 * @Description: 产品分类组装实现接口
 * @author huangdazhou
 * @date 2016年6月27日 下午2:24:51 
 * @version 1.0
 */
public interface IProductTypeAssembleService {
	/**
	 * 
	 * @Title: addProductTypeAssembleMethod 
	 * @Description: 添加产品分类信息方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	public void addProductTypeAssembleMethod(Map<String,Object> parmIN) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateProductTypeAssembleMethod 
	 * @Description: 修改产品分类信息方法
	 * @param bodyMap 报文体信息map
	 * @return int 返回执行影响条数
	 * @throws ServiceException
	 */
	public void updateProductTypeAssembleMethod(Map<String,Object> parmIN) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteProductTypeAssembleMethod 
	 * @Description: 删除产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @return int 返回执行影响条数
	 * @throws ServiceException
	 */
	public void deleteProductTypeAssembleMethod(String prdTypeCode) throws ServiceException;

}
