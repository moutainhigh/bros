package bros.provider.parent.cache.unionbankno;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPubUnionBankNo 
 * @Description: 网银互联联行号表（总行表）操作接口
 * @author haojinhui
 * @date 2016年9月20日
 * @version 1.0
 */
public interface IPubUnionBankNo {
	/**
	 * 
	 * @Title: queryAllPubUnionBankNo 
	 * @Description: 查询全部网银互联联行号信息
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllPubUnionBankNo() throws ServiceException;
	/**
	 * 
	 * @Title: queryPubUnionBankNoByName 
	 * @Description: 根据名称模糊查询网银互联联行号信息
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryPubUnionBankNoByName(String name) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubUnionBankNoByBankNo 
	 * @Description: 根据行号查行别信息
	 * @param bankNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryPubUnionBankNoByBankNo(String bankNo) throws ServiceException;

}
