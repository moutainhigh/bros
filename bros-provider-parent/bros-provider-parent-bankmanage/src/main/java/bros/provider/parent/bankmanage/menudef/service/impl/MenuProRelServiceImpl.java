package bros.provider.parent.bankmanage.menudef.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.menudef.service.IMenuProRelService;
/**
 * 
 * @ClassName: IMenuProRelService 
 * @Description: 菜单与货架商品关联
 * @author gaoyongjing 
 * @date 2016年7月11日 下午15:35:28 
 * @version 1.0
 */
@Repository(value="menuProRelService")
public class MenuProRelServiceImpl implements IMenuProRelService {
	/**
	 * 货架商品信息Log
	 */
	private static final Logger logger = LoggerFactory.getLogger(MenuProRelServiceImpl.class);
	
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addMenuProRelMethod
	 * @Description: 新增菜单与货架商品关系
	 * @param headMap  头信息
	 * @param paramMap  上送信息，包括以下
	 * @param menuCode 菜单编码
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public void addMenuProRelMethod(Map<String, Object> paramMap) throws ServiceException {
		try {
				//把需要添加到关联表的数据保存到showShelfGoodsList
				Map<String,Object> param=new HashMap<String, Object>();
				String menuId = BaseUtil.createUUID(); //唯一ID
				String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));//产品分类
				String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));//货架编号
				String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode"));//商品编号
				String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode"));//菜单编码
				param.put("menuCode", menuCode);
				param.put("prdTypeCode", prdTypeCode);
				param.put("shelfCode", shelfCode);
				param.put("goodsCode", goodsCode);
				param.put("menuId", menuId);
				// 商品信息与菜单关联
				myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmamenuproserrel.insertOneMenuProSerRel", param);
			 
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addMenuProRelMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addMenuProRelMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "添加关联信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: updateMenuProRelMethod
	 * @Description: 修改菜单与货架商品关系
	 * @param headMap  头信息
	 * @param paramMap  上送信息，包括以下
	 * @param menuCode 菜单编码
	 * @param oldShelfCode 修改前货架编码
	 * @param shelfCode 货架编码
	 * @param oldGoodsCode 修改前商品编码
	 * @param goodsCode 商品编号
	 * @param oldPrdTypeCode 修改前产品分类编码
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public void updateMenuProRelMethod(Map<String, Object> paramMap) throws ServiceException {
	  try {
		String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));//货架编码
		String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode"));//商品编号
		String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode"));//菜单编码
		String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));//产品分类编码
		//修改前货架编码
		String oldShelfCode = (String) (paramMap.get("oldShelfCode")==null?"":paramMap.get("oldShelfCode"));
		//修改前商品编号
		String oldGoodsCode = (String) (paramMap.get("oldGoodsCode")==null?"":paramMap.get("oldGoodsCode"));
		//修改前产品分类编码
		String oldPrdTypeCode = (String) (paramMap.get("oldPrdTypeCode")==null?"":paramMap.get("oldPrdTypeCode"));
		
		Map<String,Object> goodsAndMenuInfoMap = new HashMap<String, Object>();
		 
		goodsAndMenuInfoMap.put("shelfCode", shelfCode);
		goodsAndMenuInfoMap.put("menuCode", menuCode);
		goodsAndMenuInfoMap.put("prdTypeCode", prdTypeCode);
		goodsAndMenuInfoMap.put("goodsCode", goodsCode);
		
		goodsAndMenuInfoMap.put("oldShelfCode", oldShelfCode);
		goodsAndMenuInfoMap.put("oldGoodsCode", oldGoodsCode);
		goodsAndMenuInfoMap.put("oldPrdTypeCode", oldPrdTypeCode);
		 //修改关联表
		 myBatisDaoSysDao.update("mybatis.mapper.single.table.bmamenuproserrel.updateMenuProSerRelInfo",goodsAndMenuInfoMap);
		
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateMenuProRelMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateMenuProRelMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改关联信息失败", e);
		}
	}
	/**
	 * 
	 * @Title: deleteMenuProRelMethod
	 * @Description: 删除菜单与货架商品关系
	 * @param headMap  头信息
	 * @param paramMap  上送信息，包括以下
	 * @param menuCode 菜单编码
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param prdTypeCode 产品分类编号
	 * @return ResponseEntity 返回对象信息
	 * @throws ServiceException
	 */
	@Override
	public void deleteMenuProRelMethod(Map<String, Object> paramMap)
			throws ServiceException {
		try {
			
			Map<String,Object> param=new HashMap<String, Object>();
			//货架编号
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode"));
			//商品编号
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode"));
			//菜单编码
			String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode"));
			//产品分类编码
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			
			
			param.put("menuCode", menuCode);
			param.put("shelfCode", shelfCode);
			param.put("goodsCode", goodsCode);
			param.put("prdTypeCode", prdTypeCode);
			
			//需要删除关联表
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmamenuproserrel.deleteMenuProSerRel", param);
			  
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMenuProRelMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteMenuProRelMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除关联信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: queryMenuProRelMethod
	 * @Description: 查询菜单与货架商品关系
	 * @param headMap  头信息
	 * @param paramMap  上送信息，包括以下
	 * @param menuCode 菜单编码
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param prdTypeCode 产品分类编号
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return ResponseEntity Map<String,Object> returnList菜单与货架、商品信息列表 totalNum 总条数
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMenuProRelMethod(
			Map<String, Object> paramMap) throws ServiceException {
		Map<String, Object> paramOut = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> menuRelList = new ArrayList<Map<String,Object>>();
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode")); 
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			 
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			param.put("prdTypeCode",prdTypeCode);//产品分类编号
			param.put("menuCode",menuCode);//商品类型
			
			//页码和每页条数不为空的时候，分页查询
			if((paramMap.get("pageNo") != null && !paramMap.get("pageNo").equals("")) &&
				(paramMap.get("pageSize") != null && !paramMap.get("pageSize").equals(""))	){
				int pageNo = Integer.parseInt(paramMap.get("pageNo").toString());  //  页码
				int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());  //  每页条数
				menuRelList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmamenuproserrel.queryMenuProSerRel", param, pageNo, pageSize);
			}else{
				//为空时根据条件查询全部
				menuRelList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmamenuproserrel.queryMenuProSerRel", param);
			}
			
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmamenuproserrel.queryMenuProSerRelTotalNum", param);
			
			paramOut.put("returnList", menuRelList);
			paramOut.put("totalNum", totalNum);
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuProRelMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuProRelMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询关联信息失败", e);
		}
		return paramOut;
	}
	/**
	 * 
	 * @Title: queryMenuProRelByObjectIdMethod
	 * @Description: 查询菜单与货架、商品关系，单笔
	 * @param headMap  头信息
	 * @param paramMap  上送信息，包括以下
	 * @param menuCode 菜单编码
	 * @param shelfCode 货架编码
	 * @param goodsCode 商品编号
	 * @param prdTypeCode 产品分类编号
	 * @return Map<String,Object>菜单与货架、商品信息列表  
	 * @throws ServiceException
	 */
	public Map<String,Object> queryMenuProRelByObjectIdMethod (Map<String, Object> paramMap)throws ServiceException{
		Map<String, Object> menuRelMap = new HashMap<String,Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String shelfCode = (String) (paramMap.get("shelfCode")==null?"":paramMap.get("shelfCode")); 
			String goodsCode = (String) (paramMap.get("goodsCode")==null?"":paramMap.get("goodsCode")); 
			String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode")); 
			String prdTypeCode = (String) (paramMap.get("prdTypeCode")==null?"":paramMap.get("prdTypeCode"));
			 
			param.put("shelfCode",shelfCode);//货架编码
			param.put("goodsCode",goodsCode);//商品编号
			param.put("prdTypeCode",prdTypeCode);//产品分类编号
			param.put("menuCode",menuCode);//商品类型
				 
			//查询
			menuRelMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.bmamenuproserrel.queryMenuProSerRel", param);
			 
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuProRelByObjectIdMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryMenuProRelByObjectIdMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询关联信息失败", e);
		}
		return menuRelMap;
	}
}
