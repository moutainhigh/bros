package bros.provider.parent.bankmanage.authorize.service.impl;

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
import bros.provider.parent.bankmanage.authorize.service.IBmaMenudefEntitySerivce;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: BmaMenudefEntitySerivceImpl
 * @Description: 菜单综合接口实现
 * @author pengxiangnan
 * @date 2016年7月27日 下午6:51:55
 * @version 1.0
 */
@Component(value = "bmaMenudefEntitySerivce")
public class BmaMenudefEntitySerivceImpl implements IBmaMenudefEntitySerivce {

	private static final Logger logger = LoggerFactory
			.getLogger(BmaMenudefEntitySerivceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 根据渠道标识和法人查找菜单
	 */
	@Override
	public List<Map<String, Object>> queryMenudefToTree(String channel,
			String legalId, List<String> menuIdList) throws ServiceException {

		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String[] menuIdArray = menuIdList.toArray(new String[menuIdList
					.size()]);
			parmIN.put("menuIdArray", menuIdArray);
			parmIN.put("bmfLegal", legalId);
			parmIN.put("bmfChannel", channel);

			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList(
							"mybatis.mapper.single.table.bmamenudef.queryMenudefByListId",
							parmIN);
			return resultList;
		} catch (BrosBaseException e) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s queryMenudefToTree method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					"获取菜单信息失败", e);
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s queryMenudefToTree method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					"获取菜单信息失败", ex);
		}
	}

	/**
	 * 查询关联菜单信息
	 */
	@Override
	public List<Map<String, Object>> queryBmaBsnDefAndBmaBsnAuthAndBmaMenuData(
			String channel, String legalId) throws ServiceException {
		try {

			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);

			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList(
							"mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-bmabsnauth.queryBmaFunctionMenu",
							parmIN);
			return resultList;
		} catch (BrosBaseException e) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					"查询关联菜单信息", e);
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					" 查询关联菜单信息", ex);
		}
	}

	/**
	 * 查询业务对应的菜单及菜单上级信息
	 */
	public List<Map<String, Object>> queryMenuDefByBsnDef(String channel,
			String legalId) throws ServiceException {
		try {

			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);

			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList("mybatis.mapper.single.table.bmamenudef.queryMenudefByMessage",parmIN);
			return resultList;
		} catch (BrosBaseException e) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					"查询关联菜单信息", e);
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					" 查询关联菜单信息", ex);
		}
	}

	/**
	 * 查询业务功能及菜单信息
	 */
	public List<Map<String, Object>> queryBsnDefAndMenuByAll(String channel,
			String legalId) throws ServiceException {
		try {

			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);

			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList(
							"mybatis.mapper.relational.table.bmamenudef-bmabsndef-bmamenubsndefrel-bmabsnauth.queryBsnDefAndMenuDefMessage",
							parmIN);
			return resultList;
		} catch (BrosBaseException e) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					"查询业务功能信息信息", e);
		} catch (Exception ex) {
			logger.error("Exception from " + this.getClass().getName()
					+ "'s saveBmaActRoleRel method.", ex);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0220,
					" 查询业务功能信息信息", ex);
		}
	}

}
