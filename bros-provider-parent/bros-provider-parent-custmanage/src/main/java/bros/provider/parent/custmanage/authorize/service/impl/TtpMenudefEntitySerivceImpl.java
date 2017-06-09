package bros.provider.parent.custmanage.authorize.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.authorize.service.ITtpMenudefEntitySerivce;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: BmaMenudefEntitySerivceImpl 
 * @Description: 菜单综合接口实现
 * @author pengxiangnan 
 * @date 2016年7月27日 下午6:51:55 
 * @version 1.0
 */
@Component(value="ttpMenudefEntitySerivce")
public class TtpMenudefEntitySerivceImpl implements ITtpMenudefEntitySerivce {

	private static final Logger logger = LoggerFactory.getLogger(TtpMenudefEntitySerivceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
    
	/**
	 * 查询关联菜单信息
	 */
	@Override
	public List<Map<String, Object>> queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData(String channel, String legalId, String cstNo)
			throws ServiceException {
		try{
			
			Map<String, Object> parmIN  = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			parmIN.put("channel", channel);
			
			List<Map<String, Object>> resultList =  myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-ttpfuncauth.queryFunctionMenu", parmIN);
			return resultList;
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, "查询关联菜单信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, " 查询关联菜单信息失败", ex);
		}
	}
	/**
	 * 查询业务功能及菜单信息
	 */
	public List<Map<String, Object>> queryTTBsnDefAndMenuByAll(String channel,
			String legalId) throws ServiceException {
		try {

			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);

			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList(
							"mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-ttpfuncauth.queryBsnDefAndMenuDef",
							parmIN);
			return resultList;
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, "查询关联菜单信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, " 查询关联菜单信息失败", ex);
		}
	}
	/**
	* 查询模型分配信息
	*/
	public List<Map<String, Object>> queryTTBsbAuth(String cstNo, String legalId)
			throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			
			return  myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-ttpfuncauth.queryBsnAuthByAll", parmIN);
		}catch(BrosBaseException e){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, "查询关联菜单信息失败", e);
		}catch(Exception ex){
			logger.error("Exception from " + this.getClass().getName() + "'s queryBmaBsnDefAndTtpFuncAuthAndBmaMenuData method.", ex);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0033, " 查询关联菜单信息失败", ex);
		}
	}
}
