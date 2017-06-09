package bros.provider.parent.bankmanage.teller.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.teller.service.ITellerEntityService;

/**
 * 
 * @ClassName: TellerEntityServiceImpl 
 * @Description: 柜员实体接口实现
 * @author 何鹏
 * @date 2016年7月19日 下午2:09:39 
 * @version 1.0
 */
@Repository(value = "tellerEntityService")
public class TellerEntityServiceImpl implements ITellerEntityService {
	private static final Logger logger = LoggerFactory.getLogger(TellerEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryTellerInfo 
	 * @Description: 查询柜员信息
	 * @param legalId	法人id
	 * @param branchCode	机构编号
	 * @param tellerCode	柜员编号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryTellerInfo(String legalId,String branchCode,String tellerCode) throws ServiceException {
		Map<String,Object> result = null;
		try {
			Map<String, Object> paramIn = new HashMap<String, Object>();
			paramIn.put("btrLegal", legalId);//法人id
			paramIn.put("btrCode", tellerCode);//柜员编号
			paramIn.put("btrBrancode", branchCode);//机构编号
			
			//查询柜员信息列表
			result = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.bmateller.queryTellerById", paramIn);
		} catch (ServiceException se) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerById");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerById");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询柜员信息操作失败", e);
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: updateTellerStt 
	 * @Description: 根据法人编号、机构编码、柜员编号更新柜员状态
	 * @param legalId
	 * @param branchCode
	 * @param tellerCode
	 * @param stt
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void updateTellerStt(String legalId,String branchCode, String tellerCode, String stt)throws ServiceException {
		try{
			Map<String,Object> paramIn = new HashMap<String, Object>();
			paramIn.put("btrLegal", legalId);
			paramIn.put("btrBrancode", branchCode);
			paramIn.put("btrCode", tellerCode);
			paramIn.put("btrStt", stt);
			
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmateller.tellerLogin", paramIn);
			
		} catch (ServiceException se) {
			logger.error("柜员状态更新失败   " + this.getClass() + ".updateTellerStt");
			throw se;
		} catch (Exception e) {
			logger.error("柜员信息修改失败   " + this.getClass() + ".updateTellerStt");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0219,"柜员状态更新失败", e);
		}
	}
}
