package bros.provider.parent.bankmanage.legal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.legal.service.ILegalEntityService;

/**
 * 
 * @ClassName: LegalEntityServiceImpl 
 * @Description: 法人实体服务接口实现
 * @author 何鹏
 * @date 2016年7月26日 下午2:07:37 
 * @version 1.0
 */
@Repository(value = "legalEntityService")
public class LegalEntityServiceImpl implements ILegalEntityService{

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryLegalInfo 
	 * @Description: 根据法人编号和法人状态查询法人信息（查询单个法人信息）
	 * @param legalCode	法人编号
	 * @param stt	法人状态 	0：正常   1：清算	2：撤销
	 * @return
	 */
	public Map<String,Object> queryLegalInfo(String legalCode,String stt) throws ServiceException{
		Map<String, Object> result = null;
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
	
			parmIn.put("cllCode", legalCode);
			parmIn.put("cllStatus", stt);
			
			result = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.cmalegal.queryLegal", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询法人信息操作失败", e);
		}
		return result;
	}
	/**
	 * 
	 * @Title: queryLegalInfoById 
	 * @Description: 根据法人id和法人状态查询法人信息
	 * @param legalId	法人id
	 * @param stt	法人状态 	0：正常   1：清算	2：撤销
	 * @return
	 */
	@Override
	public Map<String, Object> queryLegalInfoById(String legalId, String stt)throws ServiceException {
		Map<String, Object> result = null;
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
	
			parmIn.put("cllId", legalId);
			parmIn.put("cllStatus", stt);
			
			result = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.cmalegal.queryLegal", parmIn);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询法人信息操作失败", e);
		}
		return result;
	}
}
