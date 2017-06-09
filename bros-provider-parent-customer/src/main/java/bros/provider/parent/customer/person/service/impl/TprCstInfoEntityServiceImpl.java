package bros.provider.parent.customer.person.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.customer.constants.CustomerErrorCodeConstants;
import bros.provider.parent.customer.person.service.ITprCstInfoEntityService;

/**
 * 
 * @ClassName: TprCstInfoEntityServiceImpl 
 * @Description: 个人客户基本信息实体服务
 * @author huangcanhui 
 * @date 2016年10月8日 下午3:56:01 
 * @version 1.0
 */
@Repository(value = "tprCstInfoEntityService")
public class TprCstInfoEntityServiceImpl implements ITprCstInfoEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(TprCstInfoEntityServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 根据法人ID+渠道编号+证件号码查询个人客户基本信息
	 */
	public Map<String, Object> queryTprCstInfoByCtfNo(String legalId, String channel, String ctfNo) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			parmIN.put("ctfNo", ctfNo);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstinfo.queryTprCstInfoByCtfNo", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户基本信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户基本信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0003, "查询个人客户基本信息失败", e);
		}
	}
	
	/**
	 * 根据客户标识查询个人客户基本信息
	 */
	public Map<String, Object> queryTprCstInfoByCstId(String cstId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstId", cstId);
			
			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstinfo.queryTprCstInfoByCstId", parmIN);
		}catch(BrosBaseException se) {
			logger.error("查询个人客户基本信息异常", se);
			throw new ServiceException(se);
		}catch (Exception e){
			logger.error("查询个人客户基本信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0003, "查询个人客户基本信息失败", e);
		}
	}
	
	/**
	 * 根据法人ID+客户编号+渠道编号查询个人客户基本信息
	 */
	public Map<String, Object> queryTprCstInfoByCstNo(String legalId, String cstNo, String channel) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalId", legalId);
			parmIN.put("cstNo", cstNo);
			parmIN.put("channel", channel);

			return myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.tprcstinfo.queryTprCstInfoByCstNo", parmIN);
		}catch(BrosBaseException se){
			logger.error("查询个人客户基本信息异常", se);
			throw new ServiceException(se);
		}catch(Exception e){
			logger.error("查询个人客户基本信息失败 ", e);
			throw new ServiceException(CustomerErrorCodeConstants.PPCR0003, "查询个人客户基本信息失败", e);
		}
	}
	
}
