package bros.provider.parent.bankmanage.shelf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.shelf.service.IShelfGoodsInfoService;
/**
 * 
 * @ClassName: ShelfGoodsInfoServiceImpl 
 * @Description: 货架商品信息实现类
 * @author gaoyongjing 
 * @date 2016年7月1日 下午15:35:28 
 * @version 1.0
 */
@Repository(value="shelfGoodsInfoService")
public class ShelfGoodsInfoServiceImpl implements IShelfGoodsInfoService {
	/**
	 * 货架商品信息Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(ShelfGoodsInfoServiceImpl.class);
	
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
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
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	@Override
	public void addShelfGoodsInfoMethod(List<Map<String, Object>> shelfGoodsInfoList) throws ServiceException {
		try {
			//添加商品信息
			myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.prdshelfgoods.insertShelfGoods", shelfGoodsInfoList);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addShelfGoodsInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addShelfGoodsInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加货架商品信息失败", e);
		}

	}
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
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	@Override
	public void updateShelfGoodsInfoMethod(String shelfCode,String goodsCode,String goodStatus,
			String goodsName,String description,String goodsFlag) throws ServiceException {
	  try {
		Map<String,Object> goodsInfoMap = new HashMap<String,Object>();
		
		goodsInfoMap.put("shelfCode", shelfCode);
		goodsInfoMap.put("goodsCode", goodsCode);
		goodsInfoMap.put("goodStatus", goodStatus);
		goodsInfoMap.put("goodsName", goodsName);
		goodsInfoMap.put("description", description);
		goodsInfoMap.put("goodsFlag", goodsFlag);
		 //修改商品表
		 myBatisDaoSysDao.update("mybatis.mapper.single.table.prdshelfgoods.updateShelfGoods",goodsInfoMap);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateShelfGoodsInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateShelfGoodsInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改货架商品信息失败", e);
		}
	}
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
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	@Override
	public void deleteShelfGoodsInfoMethod(List<Map<String, Object>> shelfGoodsInfoList)
			throws ServiceException {
		try {
			//删除商品信息
			myBatisDaoSysDao.deleteList("mybatis.mapper.single.table.prdshelfgoods.deleteShelfGoods", shelfGoodsInfoList);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteShelfGoodsInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteShelfGoodsInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除货架商品信息失败", e);
		}

	}
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
	 * @param goodsFlag 商品标志00-上架 01-下架
	 * @return List<Map<String,Object>> 商品信息列表 
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryShelfGoodsInfoMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			String goodStatus = (String) (paramMap.get("goodStatus")==null?"":paramMap.get("goodStatus")); 
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			String goodsType = (String) (paramMap.get("goodsType")==null?"":paramMap.get("goodsType"));
			String goodsFlag = (String) (paramMap.get("goodsFlag")==null?"":paramMap.get("goodsFlag"));
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			param.put("goodStatus",goodStatus);//商品状态
			param.put("prdTypeCode",prdTypeCode);//产品分类编号
			param.put("goodsType",goodsType);//商品类型
			param.put("goodsFlag",goodsFlag);//商品标志00-上架 01-下架
			
			//根据条件查询全部
			shelfGoodsList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdshelfgoods.queryShelfGoods", param);
			
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架商品信息失败", e);
		}
		return shelfGoodsList;
	}
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
	@Override
	public List<Map<String,Object>> queryShelfGoodsInfoPageMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> shelfGoodsList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			String goodStatus = (String) (paramMap.get("goodStatus")==null?"":paramMap.get("goodStatus")); 
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			String goodsType = (String) (paramMap.get("goodsType")==null?"":paramMap.get("goodsType"));
			String goodsFlag = (String) (paramMap.get("goodsFlag")==null?"":paramMap.get("goodsFlag"));
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			param.put("goodStatus",goodStatus);//商品状态
			param.put("prdTypeCode",prdTypeCode);//产品分类编号
			param.put("goodsType",goodsType);//商品类型
			param.put("goodsFlag",goodsFlag);//商品标志00-上架 01-下架
			
			// 分页查询
			int pageNo = Integer.parseInt(paramMap.get("pageNo")==null?"1":paramMap.get("pageNo").toString());  //  页码
			int pageSize = Integer.parseInt(paramMap.get("pageSize")==null?"1":paramMap.get("pageSize").toString());  //  每页条数
			shelfGoodsList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdshelfgoods.queryShelfGoods", param, pageNo, pageSize);
			
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoPageMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoPageMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "分页查询货架商品信息失败", e);
		}
		return shelfGoodsList;
	}
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
	@Override
	public int queryShelfGoodsTotalNumMethod(Map<String, Object> paramMap) throws ServiceException {
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			String goodStatus = (String) (paramMap.get("goodStatus")==null?"":paramMap.get("goodStatus")); 
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			String goodsType = (String) (paramMap.get("goodsType")==null?"":paramMap.get("goodsType"));
			String goodsFlag = (String) (paramMap.get("goodsFlag")==null?"":paramMap.get("goodsFlag"));
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			param.put("goodStatus",goodStatus);//商品状态
			param.put("prdTypeCode",prdTypeCode);//产品分类编号
			param.put("goodsType",goodsType);//商品类型
			param.put("goodsFlag",goodsFlag);//商品标志00-上架 01-下架
			
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdshelfgoods.queryShelfGoodsTotalNum", param);
			 
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsTotalNumMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsTotalNumMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架商品总条数失败", e);
		}
		return totalNum;
	}
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
	@Override
	public Map<String, Object> queryShelfGoodsInfoByObjectMethod(
			Map<String, Object> paramMap) throws ServiceException {
		Map<String, Object> shelfGoodsMap= new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			
			shelfGoodsMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdshelfgoods.queryShelfGoods", param);
			 
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoByObjectMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShelfGoodsInfoByObjectMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询货架商品信息失败", e);
		}
		return shelfGoodsMap;
	}
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
	@Override
	public List<Map<String, Object>> queryShowShelfGoodsInfoMethod(String chlCode,String legalPersonId,String branchNo,String mallCode,String goodStatus,String goodsType,String goodsFlag,String shelfCode)throws ServiceException {
		List<Map<String, Object>> shelfGoodsInfoList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("chlCode",chlCode);//渠道编码
			param.put("legalPersonId",legalPersonId);//法人id
			param.put("branchNo",branchNo);//机构号
			param.put("mallCode",mallCode);//商城编码
			param.put("goodStatus",goodStatus);//商品状态00-展示 01-不展示
			param.put("goodsType",goodsType);//商品类型00-产品分类；01-产品服务；02-产品
			param.put("goodsFlag",goodsFlag);//商品上下架标志 00-上架 01-下架
			param.put("shelfCode",shelfCode);//货架编码
			//根据条件查询货架商品信息
			shelfGoodsInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.prdchlsys-prdmallinfo-prdshelfdef.queryShowShelfGoodsInfo", param);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryShowShelfGoodsInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryShowShelfGoodsInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据渠道编码、法人id、机构号、商城编码等查询货架商品信息", e);
		}
		return shelfGoodsInfoList;
	}
}
