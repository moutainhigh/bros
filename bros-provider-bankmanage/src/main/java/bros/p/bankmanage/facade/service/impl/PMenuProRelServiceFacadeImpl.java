package bros.p.bankmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPMenuProRelServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.menudef.service.IMenuProRelService;
/**
 * 
 * @ClassName: PMenuProRelServiceFacadeImpl 
 * @Description: 菜单与货架商品关联实现类
 * @author gaoyongjing 
 * @date 2016年7月11日 下午15:35:28 
 * @version 1.0
 */
@Component("pmenuProRelServiceFacade")
public class PMenuProRelServiceFacadeImpl implements IPMenuProRelServiceFacade {
	/**
	 * 菜单实现类
	 */
	@Autowired
	private IMenuProRelService menuProRelService;
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
	@Validation(value="p0000023")
	public ResponseEntity addMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		//temp1 校验菜单与货架、商品关系是否存在
		Map<String,Object> menuProSerRelMap = new HashMap<String, Object>();
		menuProSerRelMap = menuProRelService.queryMenuProRelByObjectIdMethod(paramMap);
		if(menuProSerRelMap != null && menuProSerRelMap.size() > 0){
			//关系存在，不允许重复添加 
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0126);
		}
			
		//temp2 添加关联关系
		menuProRelService.addMenuProRelMethod(paramMap);
		return entity;
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
	@Validation(value="p0000024")
	public ResponseEntity updateMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		//temp1 校验菜单与货架、商品关系是否存在
		Map<String,Object> menuProSerRelMap = new HashMap<String, Object>();
		//菜单编码
		String menuCode = (String) (paramMap.get("menuCode")==null?"":paramMap.get("menuCode"));
		//修改前货架编码
		String oldShelfCode = (String) (paramMap.get("oldShelfCode")==null?"":paramMap.get("oldShelfCode"));
		//修改前商品编号
		String oldGoodsCode = (String) (paramMap.get("oldGoodsCode")==null?"":paramMap.get("oldGoodsCode"));
		//修改前产品分类编码
		String oldPrdTypeCode = (String) (paramMap.get("oldPrdTypeCode")==null?"":paramMap.get("oldPrdTypeCode"));
		
		Map<String,Object> goodsAndMenuInfoMap = new HashMap<String, Object>();
		 
		goodsAndMenuInfoMap.put("shelfCode", oldShelfCode);
		goodsAndMenuInfoMap.put("menuCode", menuCode);
		goodsAndMenuInfoMap.put("prdTypeCode", oldPrdTypeCode);
		goodsAndMenuInfoMap.put("goodsCode", oldGoodsCode);
		
		menuProSerRelMap = menuProRelService.queryMenuProRelByObjectIdMethod(goodsAndMenuInfoMap);
		if(menuProSerRelMap == null || menuProSerRelMap.size() <= 0){
			//关系不存在，不允许修改 
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0127);
		}
			
		//temp2 修改关联关系
		menuProRelService.updateMenuProRelMethod(paramMap);
		return entity;
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
	@Validation(value="p0000025")
	public ResponseEntity deleteMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		ResponseEntity entity = new ResponseEntity();
		
		//temp1 校验菜单与货架、商品关系是否存在
		Map<String,Object> menuProSerRelMap = new HashMap<String, Object>();
		menuProSerRelMap = menuProRelService.queryMenuProRelByObjectIdMethod(paramMap);
		if(menuProSerRelMap == null || menuProSerRelMap.size() <= 0){
			//关系不存在，不允许删除 TODO
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0128);
		}
		
		//temp2 删除
		menuProRelService.deleteMenuProRelMethod(paramMap);
		return entity;
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
	public ResponseEntity queryMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		Map<String,Object> menuProSerRelMap = new HashMap<String, Object>();
		
		menuProSerRelMap = menuProRelService.queryMenuProRelMethod(paramMap);
		
		ResponseEntity entity = new ResponseEntity(menuProSerRelMap);
		
		return entity;
	}

}
