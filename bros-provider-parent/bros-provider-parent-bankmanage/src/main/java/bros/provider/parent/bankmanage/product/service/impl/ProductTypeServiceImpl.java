package bros.provider.parent.bankmanage.product.service.impl;

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
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.product.service.IProductTypeService;
/**
 * 
 * @ClassName: ProductTypeServiceImpl 
 * @Description: 产品分类基础服务实现类
 * @author huangdazhou
 * @date 2016年6月27日 下午2:32:57 
 * @version 1.0
 */
@Repository(value="productTypeService")
public class ProductTypeServiceImpl implements IProductTypeService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addProductTypeMethod 
	 * @Description: 添加产品分类信息
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addProductTypeMethod(Map<String, Object> parmIN)
			throws ServiceException {
		int resultInt=0;
		try {
			resultInt=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdproducttype.insertPrdProductType", parmIN);
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品分类失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品分类失败", ex);
		}
		return resultInt;
	}
	
	/**
	 * 
	 * @Title: addSpecificationMethod 
	 * @Description: 添加规格属性信息实体方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addSpecificationMethod(List<Map<String,Object>> parmIN) throws ServiceException{
		int resultInt=0;
		try {
			//添加规格属性表
			resultInt=myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.prdptparspec.insertPrdPtparsPec", parmIN);
		}catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品分类失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品分类失败", ex);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: addInstanceMethod 
	 * @Description: 添加实例属性信息实体方法
	 * @param parmIN 参数列表
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addInstanceMethod(List<Map<String,Object>> parmIN) throws ServiceException{
		int resultInt=0;
		try {
			//添加实例属性表
			resultInt=myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.prdptparinst.insertPrdPtparInst", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品分类失败", e);
		}
		return resultInt;
	}
	/**
	 * 
	 * @Title: updateProductTypeMethod 
	 * @Description: 修改产品分类信息
	 * @param parmIN 参数列表
	 * @return int 返回对象信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int updateProductTypeMethod(
			Map<String, Object> parmIN) throws ServiceException {
		int resultInt=0;
		try {
			//修改产品分类信息
			resultInt=myBatisDaoSysDao.update("mybatis.mapper.single.table.prdproducttype.updatePrdProductType", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "产品分类信息修改失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: deleteProductTypeMethod 
	 * @Description: 删除产品分类信息
	 * @param prdTypeCode 产品分类编号
	 * @return Map<String,Object> 返回对象信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteProductTypeMethod(String prdTypeCode)
			throws ServiceException {
		int resultInt=0;
		Map<String, Object> tmpCommMap=new HashMap<String, Object>();//公共临时map
		tmpCommMap.put("prdTypeCode", prdTypeCode);
		try {
			//删除产品分类信息
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdproducttype.deletePrdProductTypeByprdTypeCode", tmpCommMap);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品分类失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: deleteSpecificationMethod 
	 * @Description: 删除规格属性信息实体方法
	 * @param prdTypeCode 参数信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteSpecificationMethod(String prdTypeCode) throws ServiceException{
		int resultInt=0;
		Map<String, Object> tmpCommMap=new HashMap<String, Object>();//公共临时map
		tmpCommMap.put("prdTypeCode", prdTypeCode);
		try {
			//删除规格属性信息
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdptparspec.deletePrdPtparsPecByprdTypeCode", tmpCommMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品分类失败", e);
		}
		return resultInt;
	}
	
	/**
	 * 
	 * @Title: deleteInstanceMethod 
	 * @Description: 删除实例属性信息实体方法
	 * @param prdTypeCode 参数信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteInstanceMethod(String prdTypeCode) throws ServiceException{
		int resultInt=0;
		Map<String, Object> tmpCommMap=new HashMap<String, Object>();//公共临时map
		tmpCommMap.put("prdTypeCode", prdTypeCode);
		try {
			//删除规格属性信息
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdptparinst.deletePrdPtparInstByprdTypeCode", tmpCommMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品分类失败", e);
		}
		return resultInt;
	}
	/**
	 * 
	 * @Title: queryProductTypeMethod 
	 * @Description: 查询产品分类信息
	 * @param bodyMap 报文体信息map
	 * @return List<Map<String,Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductTypeMethod(
			Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			if((bodyMap.get("pageNo") != null && !bodyMap.get("pageNo").equals("")) &&
					(bodyMap.get("pageSize") != null && !bodyMap.get("pageSize").equals(""))	){
				returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByParameter", bodyMap, 
						Integer.parseInt(String.valueOf(bodyMap.get("pageNo"))), 
						Integer.parseInt(String.valueOf(bodyMap.get("pageSize"))));
			}else{
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByParameter", bodyMap);
			}
			
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", e);
		}
		return returnList;
	}

	/**
	 * 
	 * @Title: querySpecificationMethod 
	 * @Description: 查询规格属性信息
	 * @param prdTypeCode 产品分类编号
	 * @return List<Map<String,Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> querySpecificationMethod(
			String prdTypeCode,String pageNo,String pageSize) throws ServiceException {
		Map<String, Object> requestMap=new HashMap<String, Object>();//返回结果map
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			requestMap.put("prdTypeCode", prdTypeCode);
			if((pageNo != null && !pageNo.equals("")) &&
					(pageSize != null && !pageSize.equals(""))){
				returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecByprdTypeCode", requestMap, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}else{
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecByprdTypeCode", requestMap);
			}
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", e);
		}
		return returnList;
	}

	/**
	 * 
	 * @Title: queryInstanceMethod 
	 * @Description: 查询实例属性信息
	 * @param prdTypeCode 产品分类编号
	 * @return List<Map<String,Object>> 返回对象信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryInstanceMethod(
			String prdTypeCode,String pageNo,String pageSize) throws ServiceException {
		Map<String, Object> requestMap=new HashMap<String, Object>();//返回结果map
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			requestMap.put("prdTypeCode", prdTypeCode);
			if((pageNo != null && !pageNo.equals("")) &&
					(pageSize != null && !pageSize.equals(""))){
				returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdptparinst.queryPrdPtparInstByprdTypeCode", requestMap, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			}else{
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdptparinst.queryPrdPtparInstByprdTypeCode", requestMap);
			}
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", e);
		}
		return returnList;
	}

	/**
	 * 查询产品分类信息
	 */
	public List<Map<String, Object>> queryProductTypeMethod(String prdTypeCode,
			String parentCode, String prdTypeName) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("parentCode", parentCode);
		parmIN.put("prdTypeName", prdTypeName);
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByParameter", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", e);
		}
		return returnList;
	}

	/**
	 * 分页查询产品分类信息
	 */
	public Map<String, Object> queryProductTypePageMethod(String prdTypeCode,
			String parentCode, String prdTypeName, int pageNo, int pageSize)
			throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("parentCode", parentCode);
		parmIN.put("prdTypeName", prdTypeName);
		try {
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByParameter", parmIN, pageNo,pageSize);
			totalNum=queryProductTypeNumMethod(prdTypeCode, parentCode, prdTypeName);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum",totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", e);
		}
		return resultMap;
	}

	/**
	 * 单笔查询产品分类信息
	 */
	public Map<String, Object> queryProductTypeOneMethod(String prdTypeCode)
			throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdTypeCode", prdTypeCode);
		try {
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByOne", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询产品分类信息笔数
	 */
	public int queryProductTypeNumMethod(String prdTypeCode,
			String parentCode, String prdTypeName)
			throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("prdTypeCode", prdTypeCode);
		parmIN.put("parentCode", parentCode);
		parmIN.put("prdTypeName", prdTypeName);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdproducttype.queryPrdProductTypeByParameter", parmIN);
					queryProductTypeNumMethod(prdTypeCode, parentCode, prdTypeName);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum",totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品分类失败", e);
		}
		return totalNum;
	}

	/**
	 * 查询规格属性信息
	 */
	public List<Map<String, Object>> querySpecificationMethod(String prdTypeCode)
			throws ServiceException {
		Map<String, Object> parmIN=new HashMap<String, Object>();//返回结果map
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecByprdTypeCode", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", e);
		}
		return returnList;
	}

	/**
	 * 分页查询规格属性信息
	 */
	public Map<String, Object> querySpecificationPageMethod(String prdTypeCode,
			int pageNo, int pageSize) throws ServiceException {
		int totalNum=0;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecByprdTypeCode", parmIN, pageNo, pageSize);
			totalNum=querySpecificationNumMethod(prdTypeCode);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", e);
		}
		return resultMap;
	}

	/**
	 * 单笔查询规格属性信息
	 */
	public Map<String, Object> querySpecificationOneMethod(String prdTypeCode,
			String property) throws ServiceException {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			parmIN.put("property", property);
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecByOne", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询规格属性信息笔数
	 */
	public int querySpecificationNumMethod(String prdTypeCode)
			throws ServiceException {
		int totalNum=0;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdptparspec.queryPrdPtparsPecNum", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "querySpecificationAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "规格属性查询失败", e);
		}
		return totalNum;
	}

	/**
	 * 查询实例属性信息
	 */
	public List<Map<String, Object>> queryInstanceMethod(String prdTypeCode)
			throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object>  parmIN=new HashMap<String, Object>();
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdptparinst.queryPrdPtparInstByprdTypeCode",  parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", e);
		}
		return returnList;
	}

	/**
	 * 分页查询实例属性信息
	 */
	public Map<String, Object> queryInstancePageMethod(String prdTypeCode,
			int pageNo, int pageSize) throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		Map<String, Object>  parmIN=new HashMap<String, Object>();
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdptparinst.queryPrdPtparInstByprdTypeCode", parmIN, pageNo, pageSize);
			totalNum=queryInstanceNumMethod(prdTypeCode);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", e);
		}
		return resultMap;
	}

	/**
	 * 单笔查询实例属性信息
	 */
	public Map<String, Object> queryInstanceOneMethod(String prdTypeCode,
			String property) throws ServiceException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		Map<String, Object>  parmIN=new HashMap<String, Object>();
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			parmIN.put("property", property);
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdptparinst.queryPrdPtparInstByOne", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", e);
		}
		return resultMap;
	}

	/**
	 * 查询实例属性信息笔数
	 */
	public int queryInstanceNumMethod(String prdTypeCode) throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		Map<String, Object>  parmIN=new HashMap<String, Object>();
		try {
			parmIN.put("prdTypeCode", prdTypeCode);
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdptparinst.queryPrdPtparsPecNum", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryInstanceAttributesMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "实例属性查询失败", e);
		}
		return totalNum;
	}
	
}
