package bros.provider.parent.custmanage.savings.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.provider.parent.custmanage.accountManage.service.impl.AccountManageServiceImpl;
import bros.provider.parent.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.savings.service.ISavingsAcountService;
/**
 * 
 * @ClassName: SavingsAcountServiceImpl 
 * @Description: 储蓄账户服务实现类
 * @author huangdazhou 
 * @date 2016年10月19日 下午1:21:24 
 * @version 1.0
 */
@Repository(value = "savingsAcountService")
public class SavingsAcountServiceImpl implements ISavingsAcountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountManageServiceImpl.class);
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	/**
	 * @Title: queryDepositTypeMethod
	 * @Description: 储种查询
	 */
	public Map<String, Object> queryDepositTypeMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000080);
		} catch (ServiceException se) {
			logger.error("储种查询失败   " + this.getClass() + ".queryDepositTypeMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("储种查询失败    " + this.getClass() + ".queryDepositTypeMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0060,"储种查询失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: queryProductList 
	 * @Description: 定转活账号列表查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public Map<String, Object> queryAccountList(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000081);
		} catch (ServiceException se) {
			logger.error("产品列表查询失败   " + this.getClass() + ".queryAccountList",se);
			throw se;
		} catch (Exception e) {
			logger.error("产品列表查询失败    " + this.getClass() + ".queryAccountList",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0062,"产品列表查询失败", e);
		}
		return resultMap;
	}

}
