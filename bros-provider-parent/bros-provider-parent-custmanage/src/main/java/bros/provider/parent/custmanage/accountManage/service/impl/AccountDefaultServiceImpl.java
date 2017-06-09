package bros.provider.parent.custmanage.accountManage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.accountManage.service.IAccountDefaultService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;

/**
 * 
 * @ClassName: AccountDefaultServiceImpl 
 * @Description: 默认账户维护
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "accountDefaultService")
public class AccountDefaultServiceImpl implements IAccountDefaultService {
	private static final  Logger logger = LoggerFactory.getLogger(AccountDefaultServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao; 
	/**
	 * 
	 * @Title: queryAccInfDefaultByAccNoandCstNoMethod
	 * @Description: 通过法人id、账号和 客户号   查询默认账户
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryAccInfDefaultByAccNoandCstNoMethod(
			String cstId, String accNo) throws ServiceException {
		Map<String,Object> accDefaultMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("cstId", cstId);
			param.put("cstNo", accNo);
			accDefaultMap = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tpraccinfdefault.queryAccInfDefaultByAccNoAndCstNo",param);
			
		}catch(ServiceException e){
			logger.error("查询默认账户失败 " + this.getClass() + ".queryAccInfDefaultByAccNoandCstNoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询默认账户失败 " + this.getClass() + ".queryAccInfDefaultByAccNoandCstNoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询默认账户失败", ex);
        }
		return accDefaultMap;
	}

}
