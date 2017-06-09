package bros.provider.parent.cache.pmsbanknoinfo;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPubPmsBankNoCacheInfo 
 * @Description: 人行联行表操作接口
 * @author mazhilei 
 * @date 2016年5月30日 上午10:38:32 
 * @version 1.0
 */
public interface IPubPmsBankNoCacheInfo {
	/**
	 * 
	 * @Title: queryAllPubPmsBankNo 
	 * @Description: 查询全部人行联行号信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllPubPmsBankNo() throws ServiceException;
	/**
	 * 
	 * @Title: queryPubPmsBankNoByName 
	 * @Description: 根据模糊名称，城市代码，行别代码查询人行联行号信息
	 * @param name 模糊行名
	 * @param bankType 行别代码
	 * @param cityCode 城市代码
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubPmsBankNoByName(String name,String bankType,String cityCode) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubBankType 
	 * @Description: 查询全部行别信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubBankType() throws ServiceException;

}
