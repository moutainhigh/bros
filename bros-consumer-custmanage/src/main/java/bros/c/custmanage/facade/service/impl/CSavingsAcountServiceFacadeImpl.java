package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICSavingsAcountServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPSavingsAcountServiceFacade;
/**
 * 
 * @ClassName: CSavingsAcountServiceFacadeImpl 
 * @Description: 储蓄账户服务发布实现接口
 * @author huangdazhou 
 * @date 2016年10月19日 上午10:49:43 
 * @version 1.0
 */
@Component("csavingsAcountServiceFacade")
public class CSavingsAcountServiceFacadeImpl implements ICSavingsAcountServiceFacade {
	@Autowired
	IPSavingsAcountServiceFacade psavingsAcountServiceFacade;
	/**
	 * @Title: queryDepositType 
	 * @Description: 储种查询
	 */
	public ResponseEntity queryDepositType(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psavingsAcountServiceFacade.queryDepositType(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: tranCurrentToFix 
	 * @Description:活期转定期
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity tranCurrentToFix(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psavingsAcountServiceFacade.tranCurrentToFix(headMap, bodyMap);
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
	public ResponseEntity queryAccountList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psavingsAcountServiceFacade.queryAccountList(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: tranFixToCurrent 
	 * @Description: 定转活
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity tranFixToCurrent(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return psavingsAcountServiceFacade.tranFixToCurrent(headMap, bodyMap);
	}

}
