package bros.provider.parent.custmanage.savings.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ISavingsAcountService 
 * @Description: 储蓄账户服务实现接口
 * @author huangdazhou 
 * @date 2016年10月19日 上午10:41:06 
 * @version 1.0
 */
public interface ISavingsAcountService {
	/**
	 * 
	 * @Title: queryDepositTypeMethod 
	 * @Description: 储种查询
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return Map<String,Object> 返回对象
	 * @throws ServiceException
	 */
	public Map<String,Object> queryDepositTypeMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductList 
	 * @Description: 定转活账号列表查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAccountList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
