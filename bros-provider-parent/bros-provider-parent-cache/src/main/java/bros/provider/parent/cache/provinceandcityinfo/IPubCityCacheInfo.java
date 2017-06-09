package bros.provider.parent.cache.provinceandcityinfo;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPubCityCacheInfo 
 * @Description: 城市信息操作接口
 * @author mazhilei 
 * @date 2016年5月30日 下午2:44:00 
 * @version 1.0
 */
public interface IPubCityCacheInfo {
	
	/**
	 * 
	 * @Title: queryAllPubCityInfo 
	 * @Description: 查询所有城市信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllPubCityInfo() throws ServiceException;
	/**
	 * 
	 * @Title: queryPubCityInfoByName 
	 * @Description: 根据名称模糊查询城市信息
	 * @param Name
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubCityInfoByName(String Name) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubCityInfoByCode 
	 * @Description: 根据省份code查询城市信息服务
	 * @param provinceId 省份id
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubCityInfoByCode(String provinceId) throws ServiceException;

}
