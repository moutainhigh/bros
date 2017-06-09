package bros.provider.parent.bankmanage.shelf.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IShelfGoodsInfoService 
 * @Description: 货架商品信息接口
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
public interface IShelfGoodsInfoService {
	/**
	 * 
	 * @Title: addShelfGoodsInfoMethod
	 * @Description: 新增货架商品信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param prdTypeCode 产品分类编号
	 * @param description 商品描述
	 * @param goodsType 商品类型
	 * @return 
	 * @throws ServiceException
	 */
	public void addShelfGoodsInfoMethod(List<Map<String,Object>> shelfGoodsInfoList) throws ServiceException;
	/**
	 * 
	 * @Title: updateShelfGoodsInfoMethod
	 * @Description: 修改商品信息
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param goodsName 商品名称
	 * @param description 商品描述
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @return 
	 * @throws ServiceException
	 */
	public void updateShelfGoodsInfoMethod (String shelfCode,String goodsCode,String goodStatus,
			String goodsName,String description,String goodsFlag)throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteShelfGoodsInfoMethod
	 * @Description: 删除货架商品信息
	 * @param shelfGoodsInfoList 货架商品信息包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @return 
	 * @throws ServiceException
	 */
	public void deleteShelfGoodsInfoMethod (List<Map<String,Object>> shelfGoodsInfoList)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryShelfGoodsInfoMethod
	 * @Description: 查询货架商品信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @return List<Map<String,Object>>商品信息列表 
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryShelfGoodsInfoMethod (Map<String, Object> paramMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfGoodsInfoPageMethod
	 * @Description: 分页查询货架商品信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return List<Map<String,Object>>商品信息列表 
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryShelfGoodsInfoPageMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfGoodsTotalNumMethod
	 * @Description: 查询货架商品总条数
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param goodStatus 商品状态
	 * @param prdTypeCode 产品分类编号
	 * @param goodsType 商品类型
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @return int总条数
	 * @throws ServiceException
	 */
	public int queryShelfGoodsTotalNumMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryShelfGoodsInfoByObjectMethod
	 * @Description: 查询货架商品详细信息
	 * @param paramMap 商品信息,包含以下参数
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @return Map<String,Object> 商品信息 
	 * @throws ServiceException
	 */
	public Map<String, Object> queryShelfGoodsInfoByObjectMethod(Map<String, Object> paramMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryShowShelfGoodsInfoMethod
	 * @Description: 根据渠道编码、法人id、机构号、商城编码等查询货架商品信息
	 * @param chlCode 渠道编码
	 * @param legalPersonId 法人id
	 * @param branchNo 机构号
	 * @param mallCode 商城编码
	 * @param goodStatus 商品状态00-展示 01-不展示
	 * @param goodsType 商品类型00-产品分类；01-产品服务；02-产品
	 * @param goodsFlag 商品上下架标志 00-上架 01-下架
	 * @param shelfCode 货架编码
	 * @return ResponseEntity Map<String,Object> returnList货架商品信息列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryShowShelfGoodsInfoMethod(String chlCode,String legalPersonId,String branchNo,String mallCode,String goodStatus,String goodsType,String goodsFlag,String shelfCode)throws ServiceException;
}
