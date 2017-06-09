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
import bros.provider.parent.bankmanage.product.service.IProductCateService;
/**
 * 
 * @ClassName: ProductCateServiceImpl 
 * @Description: 产品目录基础服务实现类
 * @author huangdazhou
 * @date 2016年6月30日 下午2:56:11 
 * @version 1.0
 */
@Repository(value="productCateService")
public class ProductCateServiceImpl implements IProductCateService {
	private static final  Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addProductCateMethod 
	 * @Description: 新增产品目录方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int addProductCateMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		try {
			//产品目录添加
			resultInt=myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdproductcate.insertprdProductCate", bodyMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "addProductCateMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加产品目录信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: updateProductCateMethod 
	 * @Description: 修改产品目录方法
	 * @param bodyMap 报文体map
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int updateProductCateMethod(Map<String, Object> bodyMap) throws ServiceException {
		int resultInt=0;
		try {
			//修改产品目录信息
			resultInt=myBatisDaoSysDao.update("mybatis.mapper.single.table.prdproductcate.updateprdProductCate", bodyMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "updateProductCateMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改产品目录信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: deleteProductCateMethod 
	 * @Description: 删除产品目录方法
	 * @param cateCode 产品目录编码
	 * @return int 返回结果信息
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public int deleteProductCateMethod(String cateCode)
			throws ServiceException {
		int resultInt=0;
		try {
			Map<String, Object> tmpPrdCateMap=new HashMap<String, Object>();//产品分类map-查询库表
			tmpPrdCateMap.put("cateCode", cateCode);
			resultInt=myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdproductcate.deleteprdProductCateBycateCode", tmpPrdCateMap);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "deleteProductServiceMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除产品目录信息失败", e);
		}
		return resultInt;
	}

	/**
	 * 
	 * @Title: queryProductCateMethod 
	 * @Description: 查询产品目录方法
	 * @param bodyMap 报文体map
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductCateMethod(
			Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();//实例属性
		try {
			if((bodyMap.get("pageNo") != null && !bodyMap.get("pageNo").equals("")) &&
					(bodyMap.get("pageSize") != null && !bodyMap.get("pageSize").equals(""))){
				returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdproductcate.queryprdProductCateByParameter", bodyMap, 
						Integer.parseInt(String.valueOf(bodyMap.get("pageNo"))), 
						Integer.parseInt(String.valueOf(bodyMap.get("pageSize"))));
			}else{
				returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdproductcate.queryprdProductCateByParameter", bodyMap);
			}
			
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", e);
		}
		return returnList;
	}

	/**
	 * @Title: queryProductCateMethod 
	 * @Description: 查询产品目录目录方法
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @return List<Map<String, Object>> 返回结果信息
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryProductCateMethod(String cateCode,
			String cateName, String parentCate) throws ServiceException {
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("cateCode", cateCode);
		parmIN.put("cateName", cateName);
		parmIN.put("parentCate", parentCate);
		try {
			returnList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdproductcate.queryprdProductCateByParameter", parmIN);
		} catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductCateMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", e);
		}
		return returnList;
	}

	/**
	 * @Title: queryProductCatePageMethod 
	 * @Description: 分页查询产品目录方法
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductCatePageMethod(String cateCode,
			String cateName, String parentCate, int pageNo, int pageSize)
			throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("cateCode", cateCode);
		parmIN.put("cateName", cateName);
		parmIN.put("parentCate", parentCate);
		try {
			returnList=myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdproductcate.queryprdProductCateByParameter", parmIN, pageNo, pageSize);
			totalNum=queryProductCateNumMethod(cateCode, cateName, parentCate);
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", e);
		}
		return resultMap;
	}

	/**
	 * @Title: queryProductCateOneMethod 
	 * @Description: 单笔查询产品目录方法
	 * @param cateCode 产品目录编号
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public Map<String, Object> queryProductCateOneMethod(String cateCode)
			throws ServiceException {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("cateCode", cateCode);
		try {
			resultMap=myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdproductcate.queryprdProductCateByOne", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductCateOneMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", e);
		}
		return resultMap;
	}

	/**
	 * @Title: queryProductCateNumMethod 
	 * @Description: 查询产品目录笔数
	 * @param cateCode 产品目录编号
	 * @param cateName 产品目录名称
	 * @param parentCate 上级产品目录编号
	 * @return Map<String,Object> 返回结果信息
	 * @throws ServiceException
	 */
	public int queryProductCateNumMethod(String cateCode, String cateName,
			String parentCate) throws ServiceException {
		int totalNum=0;
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("cateCode", cateCode);
		parmIN.put("cateName", cateName);
		parmIN.put("parentCate", parentCate);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdproductcate.queryprdProductCateNum", parmIN);
		} catch (NumberFormatException exy) {
			logger.error("Exception from " + this.getClass().getName() + "queryProductCateOneMethod", exy);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", exy);
		}  catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "queryProductTypeMethod", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询产品目录信息失败", e);
		}
		return totalNum;
	}

	

}
