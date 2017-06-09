package bros.provider.parent.bankmanage.product.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IProductInformationAssembleService 
 * @Description: 产品信息基础服务组装实现接口
 * @author huangdazhou
 * @date 2016年6月30日 下午5:07:24 
 * @version 1.0
 */
public interface IProductInformationAssembleService {
	/**
	 * 
	 * @Title: addProductInformationAssembleMethod 
	 * @Description: 添加产品信息
	 * @param bodyMap 报文体信息map
	 * @throws ServiceException
	 */
	public void addProductInformationAssembleMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateProductInformationAssembleMethod 
	 * @Description: 修改产品信息
	 * @param bodyMap 报文体信息map
	 * @throws ServiceException
	 */
	public void updateProductInformationAssembleMethod(Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductInformationMethod 
	 * @Description: 删除产品信息
	 * @param productCode 产品ID
	 * @throws ServiceException
	 */
	public void deleteProductInformationAssembleMethod(String productCode) throws ServiceException;
	
}
