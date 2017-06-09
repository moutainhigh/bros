package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICMallInfoServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPMallInfoServiceFacade;
/**
 * 
 * @ClassName: CMallInfoServiceFacadeImpl 
 * @Description: 商城信息维护对外接口实现类
 * @author gaoyongjing 
 * @date 2016年6月30日 下午15:35:28 
 * @version 1.0
 */
@Component("cmallInfoServiceFacade")
public class CMallInfoServiceFacadeImpl implements ICMallInfoServiceFacade {
	/**
	 * 商城信息服务
	 */
	@Autowired
	private IPMallInfoServiceFacade pmallInfoServiceFacade;

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
	@Validation(value="c0000106")
	@Override
	public ResponseEntity addMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pmallInfoServiceFacade.addMallInfoMethod(headMap, paramMap);
		
	}
	/**
	 * 
	 * @Title: updateMallInfoMethod
	 * @Description: 修改商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param mallDesc 商城描述
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @param pageName 登录首页页面名称
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000107")
	@Override
	public ResponseEntity updateMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pmallInfoServiceFacade.updateMallInfoMethod(headMap, paramMap);
	}

	/**
	 * 
	 * @Title: deleteMallInfoMethod
	 * @Description: 删除商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId 商城ID
	 * @param mallCode 商城编号
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pmallInfoServiceFacade.deleteMallInfoMethod(headMap, paramMap);
	}

	/**
	 * 
	 * @Title: queryMallInfoMethod
	 * @Description: 查询商城信息
	 * @param headMap  头信息
	 * @param paramMap 商城信息包含以下参数
	 * @param mallId  商城ID
	 * @param mallCode 商城编号
	 * @param mallName 商城名称
	 * @param chlId 渠道系统唯一标识
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList商城信息列表 
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryMallInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pmallInfoServiceFacade.queryMallInfoMethod(headMap, paramMap);
	}
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
	@Validation(value="c0000131")
	@Override
	public ResponseEntity queryMallInfoPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pmallInfoServiceFacade.queryMallInfoPageMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: addMallStyleMethod
	 * @Description: 新增商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000108")
	@Override
	public ResponseEntity addMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		 
		return pmallInfoServiceFacade.addMallStyleMethod(headMap,paramMap);
	}

	/**
	 * 
	 * @Title: deleteMallStyleMethod
	 * @Description: 删除商城样式
	 * @param headMap 头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param styleId 样式ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000108")
	@Override
	public ResponseEntity deleteMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		
		return pmallInfoServiceFacade.deleteMallStyleMethod(headMap,paramMap);
	}
	/**
	 * 
	 * @Title: queryMallStyleMethod
	 * @Description: 查询商城样式
	 * @param headMap  头信息
	 * @param paramMap 商城样式信息包含以下参数
	 * @param mallId 商城ID
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商城样式列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="c0000109")
	@Override
	public ResponseEntity queryMallStyleMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		
		return pmallInfoServiceFacade.queryMallStyleMethod(headMap,paramMap);
	}
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
	@Validation(value="c0000117")
	@Override
	public ResponseEntity queryMallInfoByChlCodeLegalIdBranchNoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pmallInfoServiceFacade.queryMallInfoByChlCodeLegalIdBranchNoMethod(headMap, paramMap);
	}
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
	@Validation(value="c0000127")
	public ResponseEntity queryChlSysGroupChlInfoAndMallInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap)
			throws ServiceException{
		return pmallInfoServiceFacade.queryChlSysGroupChlInfoAndMallInfoMethod(headMap, paramMap);
	}
}
