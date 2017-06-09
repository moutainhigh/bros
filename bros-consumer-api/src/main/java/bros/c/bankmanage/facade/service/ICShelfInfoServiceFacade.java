package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IShelfInfoServiceFacade 
 * @Description: 货架信息对外接口
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
public interface ICShelfInfoServiceFacade {
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
	public ResponseEntity addShelfInfoMethod(Map<String, Object> headMap ,Map<String, Object> paramMap) throws ServiceException;
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
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateShelfInfoMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
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
	public ResponseEntity deleteShelfInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
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
	public ResponseEntity queryShelfInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
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
	public ResponseEntity queryShelfInfoPageMethod(Map<String, Object> headMap,Map<String, Object> paramMap) throws ServiceException;
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
	 * @param menuCode 菜单编码
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity addShelfGoodsInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateShelfGoodsInfoMethod
	 * @Description: 修改商品信息
	 * @param headMap 头信息
	 * @param paramMap 货架商品信息
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param oldDoodStatus 修改前商品状态
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param description 商品描述
	 * @param oldMenuCode 修改前菜单编码
	 * @param menuCode 菜单编码
	 * @param prdTypeCode 产品分类
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity updateShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteShelfGoodsInfoMethod
	 * @Description: 删除货架商品信息
	 * @param headMap 头信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param menuCode 菜单编码
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	public ResponseEntity deleteShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
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
	public ResponseEntity queryShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
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
	public ResponseEntity queryShelfGoodsInfoPageMethod(Map<String, Object> headMap, Map<String, Object> paramMap)throws ServiceException;
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
	public ResponseEntity queryShowShelfInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
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
	public ResponseEntity queryShelfAndShelfGoodsInfoMethod (Map<String, Object> headMap,Map<String, Object> paramMap)throws ServiceException;
}
