package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICShelfInfoServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPShelfInfoServiceFacade;

/**
 * 
 * @ClassName: CShelfInfoServiceFacadeImpl 
 * @Description: 货架信息对外接口实现类
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
@Component("cshelfInfoServiceFacade")
public class CShelfInfoServiceFacadeImpl implements ICShelfInfoServiceFacade {
	/**
	 * 货架信息服务
	 */
	@Autowired
	private IPShelfInfoServiceFacade pshelfInfoServiceFacade;
	/**
	 * 
	 * @Title: addShelfInfoMethod
	 * @Description: 新增货架信息
	 * @param headMap  头信息
	 * @param paramMap  货架信息，包括以下
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000110")
	@Override
	public ResponseEntity addShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		
		return pshelfInfoServiceFacade.addShelfInfoMethod(headMap, paramMap);
	}

	/**
	 * 
	 * @Title: updateShelfInfoMethod
	 * @Description: 修改货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param shelfName 货架名称
	 * @param shelfDesc 货架描述
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000119")
	@Override
	public ResponseEntity updateShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		 
		return pshelfInfoServiceFacade.updateShelfInfoMethod(headMap, paramMap);
	}

	/**
	 * 
	 * @Title: deleteShelfInfoMethod
	 * @Description: 删除货架信息
	 * @param headMap  头信息
	 * @param shelfCode 货架编码
	 * @param mallId 商城ID
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000111")
	@Override
	public ResponseEntity deleteShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		
		return pshelfInfoServiceFacade.deleteShelfInfoMethod(headMap,paramMap);
	}

	/**
	 * 
	 * @Title: queryShelfInfoMethod
	 * @Description: 查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表 
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryShelfInfoMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		
		return pshelfInfoServiceFacade.queryShelfInfoMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: queryShelfInfoPageMethod
	 * @Description: 分页查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param parentShelf 上级货架编码
	 * @param mallId 商城ID
	 * @param shelfFlag 上下架标志 00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="c0000132")
	@Override
	public ResponseEntity queryShelfInfoPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException{
		return pshelfInfoServiceFacade.queryShelfInfoPageMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: addShelfGoodsInfoMethod
	 * @Description: 新增货架商品信息
	 * @param headMap 头信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param prdTypeCode 产品分类编号
	 * @param description 商品描述
	 * @param goodsType 商品类型
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000112")
	@Override
	public ResponseEntity addShelfGoodsInfoMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		
		return pshelfInfoServiceFacade.addShelfGoodsInfoMethod(headMap, bodyMap);
		
	}

	/**
	 * 
	 * @Title: updateShelfGoodsInfoMethod
	 * @Description: 修改商品信息
	 * @param headMap 头信息
	 * @param paramMap 货架商品信息
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param description 商品描述
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000113")
	@Override
	public ResponseEntity updateShelfGoodsInfoMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		 
		return pshelfInfoServiceFacade.updateShelfGoodsInfoMethod(headMap, paramMap);
	}

	/**
	 * 
	 * @Title: deleteShelfGoodsInfoMethod
	 * @Description: 删除货架商品信息
	 * @param headMap 头信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Validation(value="c0000114")
	@Override
	public ResponseEntity deleteShelfGoodsInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		
		return pshelfInfoServiceFacade.deleteShelfGoodsInfoMethod(headMap, bodyMap);
	}

	/**
	 * 
	 * @Title: queryShelfGoodsInfoMethod
	 * @Description: 查询货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @return ResponseEntity Map<String,Object> returnList商品信息列表 
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryShelfGoodsInfoMethod(
			Map<String, Object> headMap, Map<String, Object> paramMap)
			throws ServiceException {
		
		return pshelfInfoServiceFacade.queryShelfGoodsInfoMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: queryShelfGoodsInfoPageMethod
	 * @Description: 分页查询货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList商品信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Validation(value="c0000133")
	public ResponseEntity queryShelfGoodsInfoPageMethod(Map<String, Object> headMap, Map<String, Object> paramMap)throws ServiceException{
		return pshelfInfoServiceFacade.queryShelfGoodsInfoPageMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: queryShowShelfInfoMethod
	 * @Description: 根据渠道编码、法人id、机构号查询货架信息
	 * @param headMap  头信息
	 * @param paramMap 信息,包含以下参数
	 * @param chlCode 渠道编码
	 * @param legalPersonId 法人id
	 * @param branchNo 机构号
	 * @return ResponseEntity Map<String,Object> returnList货架信息列表
	 * @throws ServiceException
	 */
	@Validation(value="c0000115")
	@Override
	public ResponseEntity queryShowShelfInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException{
		return pshelfInfoServiceFacade.queryShowShelfInfoMethod(headMap, paramMap);
	}
	/**
	 * 
	 * @Title: queryShowShelfGoodsInfoMethod
	 * @Description:   根据货架编码查询其下级货架编码
	 *                 根据货架编码查询需要展示的货架商品信息
	 * @param headMap  头信息
	 * @param paramMap 货架信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @return ResponseEntity Map<String,Object> 
	 * @throws ServiceException
	 */
	@Validation(value="c0000116")
	@Override
	public ResponseEntity queryShelfAndShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException{
		return pshelfInfoServiceFacade.queryShelfAndShelfGoodsInfoMethod(headMap, paramMap);
	}
}
