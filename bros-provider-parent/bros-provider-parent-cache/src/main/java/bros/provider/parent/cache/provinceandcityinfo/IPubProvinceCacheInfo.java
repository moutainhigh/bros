package bros.provider.parent.cache.provinceandcityinfo;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPubProvinceCacheInfo 
 * @Description: 省份信息操作接口。
 * @author mazhilei 
 * @date 2016年5月30日 下午2:40:27 
 * @version 1.0
 */
public interface IPubProvinceCacheInfo {

	/**
	 * 
	 * @Title: queryAllPubProvinceCacheInfo 
	 * @Description: 查询全部省份信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllPubProvinceCacheInfo() throws ServiceException;
	/**
	 * 
	 * @Title: queryPubProvinceCacheInfoByName 
	 * @Description: 根据名称模糊查询省份信息
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubProvinceCacheInfoByName(String name) throws ServiceException;
}
