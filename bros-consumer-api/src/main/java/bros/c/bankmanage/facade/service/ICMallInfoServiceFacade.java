package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICMallInfoServiceFacade 
 * @Description: 商城信息维护对外接口
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
public interface ICMallInfoServiceFacade {
	/**
	 * 
	 * @Title: addMallInfoMethod
	 * @Description: 新增商城信息
	 * @param headMap  头信息
	 * @param paramMap  商城信息，包括以下
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addMallInfoMethod(Map<String, Object> headMap ,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateMallInfoMethod
	 * @Description: 修改商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param maillId 商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateMallInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteMallInfoMethod
	 * @Description: 删除商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param maillId 商城ID
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity deleteMallInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param maillId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList商城信息列表 
	 * @throws ServiceException
	 */
	public ResponseEntity queryMallInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoPageMethod
	 * @Description: 分页查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商城信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity queryMallInfoPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: addMallStyleMethod
	 * @Description: 新增商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param maillId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addMallStyleMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteMallStyleMethod
	 * @Description: 删除商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param maillId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity deleteMallStyleMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMallStyleMethod
	 * @Description: 查询商城样式
	 * @param headMap  头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param maillId 商城ID
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商城样式列表 totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity queryMallStyleMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryMallInfoByChlCodeLegalIdBranchNoMethod
	 * @Description: 根据法人id、渠道编码、机构号查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param legalPersonId  法人ID
	 * @param chlCode 渠道编码
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> mallInfoMap商城信息
	 * @throws ServiceException
	 */
	public ResponseEntity queryMallInfoByChlCodeLegalIdBranchNoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryChlSysGroupChlInfoAndMallInfoMethod
	 * @Description: 根据法人id查询 渠道分组、渠道信息及商城信息
	 * @param headMap  头信息
	 * @param paramMap 上送map
	 * @param legalPersonId 法人ID
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity queryChlSysGroupChlInfoAndMallInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
}
