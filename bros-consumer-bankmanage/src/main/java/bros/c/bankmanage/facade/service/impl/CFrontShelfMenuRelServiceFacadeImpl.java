package bros.c.bankmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.bankmanage.facade.service.ICFrontShelfMenuRelServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.bankmanage.facade.service.IPFrontShelfMenuRelServiceFacade;

/** 
 * @ClassName: CFrontShelfMenuRelServiceFacadeImpl  
 * @Description:前台展示菜单查询接口实现类
 * @author  gaoyongjing
 * @date 2016年7月16日 上午9:45:58 
 * @version V1.0  
 */
@Component("cfrontShelfMenuRelServiceFacade")
public class CFrontShelfMenuRelServiceFacadeImpl implements ICFrontShelfMenuRelServiceFacade {
	 
	@Autowired
	private IPFrontShelfMenuRelServiceFacade pfrontShelfMenuRelServiceFacade;
	/**
	 * 
	 * @Title: queryPbankLoginMenuMethod
	 * @Description: 查询个人网银柜员登录时展示的菜单信息
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPbankLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return pfrontShelfMenuRelServiceFacade.queryPbankLoginMenuMethod(headMap, bodyMap);
	}
	 /**
   	 * 
   	 * @Title: queryCbankLoginMenuMethod
   	 * @Description: 查询企业网银柜员登录时展示的菜单信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Override
	public ResponseEntity queryCbankLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return pfrontShelfMenuRelServiceFacade.queryCbankLoginMenuMethod(headMap, bodyMap);
	}
	 /**
   	 * 
   	 * @Title: queryFrontLoginMenuMethod
   	 * @Description: 查询柜面柜员登录时展示的菜单信息
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Override
	public ResponseEntity queryFrontLoginMenuMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return pfrontShelfMenuRelServiceFacade.queryFrontLoginMenuMethod(headMap, bodyMap);
	}
	 /**
   	 * 
   	 * @Title: queryAllChlShelfGoodsByShelfCodeMethod
   	 * @Description: 根据货架编号查询货架商品菜单信息,适用于各个渠道
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Validation(value="c0000125")
	@Override
	public ResponseEntity queryAllChlShelfGoodsByShelfCodeMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pfrontShelfMenuRelServiceFacade.queryAllChlShelfGoodsByShelfCodeMethod(headMap, bodyMap);
	}

	/**
   	 * 
   	 * @Title: queryShelfAndShelfGoodsMenuByShelfCodeMethod
   	 * @Description: 根据货架编号查询子货架信息及货架信息上的商品信息并与菜单关联
   	 * @param headMap  头信息
   	 * @param bodyMap  上送报文
   	 * @return ResponseEntity
   	 * @throws ServiceException
   	 */
	@Validation(value="c0000126")
	public ResponseEntity queryShelfAndShelfGoodsMenuByShelfCodeMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		
		return pfrontShelfMenuRelServiceFacade.queryShelfAndShelfGoodsMenuByShelfCodeMethod(headMap, bodyMap);
	}
}
