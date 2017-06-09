package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICMenuProRelServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPMenuProRelServiceFacade;
/**
 * 
 * @ClassName: CMenuProRelServiceFacadeImpl 
 * @Description: 菜单与货架商品关联实现类
 * @author gaoyongjing 
 * @date 2016年7月11日 下午15:35:28 
 * @version 1.0
 */
@Component("cmenuProRelServiceFacade")
public class CMenuProRelServiceFacadeImpl implements ICMenuProRelServiceFacade {
	@Autowired
	private IPMenuProRelServiceFacade pmenuProRelServiceFacade;
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
	@Validation(value="c0000023")
	public ResponseEntity addMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		 
		return pmenuProRelServiceFacade.addMenuProRelMethod(headMap, paramMap);
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
	@Validation(value="c0000024")
	public ResponseEntity updateMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
		return pmenuProRelServiceFacade.updateMenuProRelMethod(headMap, paramMap);
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
	@Validation(value="c0000025")
	public ResponseEntity deleteMenuProRelMethod(Map<String, Object> headMap,
			Map<String, Object> paramMap) throws ServiceException {
	 
		return pmenuProRelServiceFacade.deleteMenuProRelMethod(headMap, paramMap);
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
		 
		return pmenuProRelServiceFacade.queryMenuProRelMethod(headMap, paramMap);
	}

}
