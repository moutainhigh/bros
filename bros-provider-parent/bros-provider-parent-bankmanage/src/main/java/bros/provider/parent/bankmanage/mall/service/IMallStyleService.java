package bros.provider.parent.bankmanage.mall.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IMallStyleService 
 * @Description: 商城样式接口
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
public interface IMallStyleService {
	/**
	 * 
	 * @Title: addMallStyleMethod
	 * @Description: 新增商城样式
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return 
	 * @throws ServiceException
	 */
	public void addMallStyleMethod(String mallId,String styleId) throws ServiceException;
	/**
	 * 
	 * @Title: deleteMallStyleMethod
	 * @Description: 删除商城样式
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return 
	 * @throws ServiceException
	 */
	public void deleteMallStyleMethod (String mallId,String styleId)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMallStyleMethod
	 * @Description: 查询商城样式
	 * @param mallId 商城ID
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return Map<String,Object> returnList商城样式列表 totalNum 总条数
	 * @throws ServiceException
	 */
	public Map<String,Object> queryMallStyleMethod (String mallId,int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: queryMallStyleByStyleIdAndMallIdMethod
	 * @Description: 判断商城样式是否存在
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return Map<String,Object> mallStyleMap商城样式列表
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMallStyleByStyleIdAndMallIdMethod(String mallId,String styleId) throws ServiceException;
}
