package bros.provider.parent.bankmanage.mall.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IMallInfoService 
 * @Description: 商城基本信息维护接口
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
public interface IMallInfoService {

	/**
	 * 
	 * @Title: addMallInfoMethod
	 * @Description: 新增商城信息
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return 
	 * @throws ServiceException
	 */
	public void addMallInfoMethod(String mallCode,String mallName,String mallDesc,
			String chlId,String branchNo,String pageName) throws ServiceException;
	/**
	 * 
	 * @Title: updateMallInfoMethod
	 * @Description: 修改商城信息
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return 
	 * @throws ServiceException
	 */
	public void updateMallInfoMethod(String mallId,String mallCode,String mallName,String mallDesc,
			String chlId,String branchNo,String pageName) throws ServiceException;
	/**
	 * 
	 * @Title: deleteMallInfoMethod
	 * @Description: 删除商城信息
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return 
	 * @throws ServiceException
	 */
	public void deleteMallInfoMethod (String mallId,String mallCode,String chlId,String branchNo)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return List<Map<String,Object>> 商城信息列表 
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryMallInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoPageMethod
	 * @Description: 分页查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return List<Map<String,Object>>商城信息列表
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryMallInfoPageMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return int  总条数
	 * @throws ServiceException
	 */
	public int queryMallTotalNumMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoByObjectIdMethod
	 * @Description: 查询商城信息是否存在
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param mallId 商城唯一标识
	 * @return Map<String,Object> 商城信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMallInfoByObjectIdMethod(String mallCode,String branchNo,String chlId,String mallId) throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoByBranchNoMethod
	 * @Description: 根据机构号渠道标识查询商城信息是否存在
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return Map<String,Object> 商城信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMallInfoByBranchNoMethod(String branchNo,String chlId) throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoByChlCodeLegalIdBranchNoMethod
	 * @Description: 根据法人id、渠道编码、机构号查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlCode 渠道编码
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList商城信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryMallInfoByChlCodeLegalIdBranchNoMethod(Map<String,Object> paramMap) throws ServiceException;
}
