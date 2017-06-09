package bros.provider.parent.custmanage.recvPersonInfo.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IRecvPersonInfoService 
 * @Description: 收款人名册维护接口
 * @author 高永静
 * @date 2016年9月20日 上午9:45:17 
 * @version 1.0
 */
public interface IRecvPersonInfoService {
	/**
	 * 
	 * @Title: addRecvPersonInfoMethod
	 * @Description: 新增收款人信息
	 * @return
	 * @throws ServiceException
	 */
	public void addRecvPersonInfoMethod(Map<String, Object> payBookMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateRecvPersonInfoMethod
	 * @Description: 修改收款人信息
	 * @return
	 * @throws ServiceException
	 */
	public void updateRecvPersonInfoMethod(Map<String, Object> payBookMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:     删除收款人信息
	 * @param payBookId  收款人记录唯一标识
	 * @return
	 * @throws ServiceException
	 */
	public void deleteRecvPersonInfoMethod (String payBookId)throws ServiceException;
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllRecvPersonInfoMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException;
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryRecvPersonInfoPageMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: queryRecvPersonTotalNumMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表的总记录数
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryRecvPersonTotalNumMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException;
	/**
	 * 
	 * @Title: queryRecvPersonInfoByCstNoAccNoMethod
	 * @Description:    根据渠道客户号、账号、手机号、账号名称,查询收款人信息，如果是手机号收款人，根据手机号，否则根据账号查
	 * @param cstId   客户Id
	 * @param payeeType	转账类型
	 * @param mobileNo	手机号
	 * @param accountNo	账号
	 * @param accountName 账号名称
	 * @return recvPersonInfoMap收款人信息
	 * @throws ServiceException
	 */
	public Map<String,Object> queryRecvPersonInfoByCstNoAccNoMethod (String cstId,String payeeType,String mobileNo,String accountNo,String accountName)throws ServiceException;
	/**
	 * 
	 * @Title: queryRecvPersonInfoByPayBookIdMethod
	 * @Description:        	根据收款人名册ID查询
	 * @param payBookId     	收款人唯一id
	 * @return recvPersonInfoMap收款人信息
	 * @throws ServiceException
	 */
	public Map<String,Object> queryRecvPersonInfoByPayBookIdMethod (String payBookId)throws ServiceException;
	/**
	 * 
	 * @Title: addSubRecvPersonInfoMethod
	 * @Description: 新增收款人子表信息
	 * @param payBookId 收款人记录唯一标识
	 * @param transType 转账类型
	 * @param comitrNo  收款银行联行号或网银互联号（他行使用）
	 * @return
	 * @throws ServiceException
	 */
	public void addSubRecvPersonInfoMethod(String payBookId,String transType,String comitrNo)throws ServiceException;
	/**
	 * 
	 * @Title: updateSubRecvPersonInfoMethod
	 * @Description:    修改收款人子表信息
	 * @param payBookId 收款人记录唯一标识
	 * @param transType 转账类型
	 * @param comitrNo  收款银行联行号或网银互联号（他行使用）
	 * @return
	 * @throws ServiceException
	 */
	public void updateSubRecvPersonInfoMethod(String payBookId,String transType,String comitrNo) throws ServiceException;
	/**
	 * 
	 * @Title: deleteSubRecvPersonInfoMethod
	 * @Description:     删除收款人子表信息
	 * @param payBookId  收款人记录唯一标识
	 * @return
	 * @throws ServiceException
	 */
	public void deleteSubRecvPersonInfoMethod (String payBookId)throws ServiceException;
	/**
	 * @Title: queryAllSubRecvPersonInfoMethod
	 * @Description:        根据关联字段payBookId查询收款人名册子表中的所有记录
	 * @param payBookId  	收款人记录唯一标识
	 * @return 
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAllSubRecvPersonInfoMethod(String payBookId)throws ServiceException;
	/**
	 * @Title: querySubRecvPersonInfoMethod
	 * @Description:        根据payBookId和tranType查询收款人名册子表中的是否存在记录
	 * @param payBookId     收款人记录唯一标识
	 * @param transType 	转账类型
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> querySubRecvPersonInfoMethod(String payBookId,String transType)throws ServiceException;
	/**
	 * @Title: queryRecvPersonInfoPageWithSubPayBookMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表及子表信息
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param cstNo         客户号
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryRecvPersonInfoPageWithSubPayBookMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: queryRecvPersonTotalNumWithSubPayBookMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表及子表的总记录数
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryRecvPersonTotalNumWithSubPayBookMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException;
	/**
	 * @Title: queryDistinctRecvPersonInfoPageWithSubPayBookMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表及子表信息(去掉账号重复)
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return recvPersonInfoList 收款人名册列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryDistinctRecvPersonInfoPageWithSubPayBookMethod(String cstId,String payBookId,String payeeType,String accountName,String accountNo,int pageNo,int pageSize)throws ServiceException;
	/**
	 * 
	 * @Title: queryDistinctRecvPersonTotalNumWithSubPayBookMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表及子表的总记录数(去掉账号重复)
	 * @param cstId   	客户Id
	 * @param payBookId 	收款人唯一id
	 * @param payeeType 	收款人类型
	 * @param accountName	账号名称
	 * @param accountNo		账号
	 * @return totalNum 	总条数
	 * @throws ServiceException
	 */
	public int queryDistinctRecvPersonTotalNumWithSubPayBookMethod (String cstId,String payBookId,String payeeType,String accountName,String accountNo)throws ServiceException;
}
