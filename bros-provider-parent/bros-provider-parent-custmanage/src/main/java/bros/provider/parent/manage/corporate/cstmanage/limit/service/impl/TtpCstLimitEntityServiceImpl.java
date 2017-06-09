package bros.provider.parent.manage.corporate.cstmanage.limit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.manage.corporate.cstmanage.limit.service.ITtpCstLimitEntityService;
/**
 * 
 * @ClassName: TtpCstLimitEntityServiceImpl 
 * @Description: 企业客户限额服务
 * @author zsg 
 * @date 2017年1月12日 下午3:05:00 
 * @version 1.0
 */
@Repository(value = "ttpCstLimitEntityService")
public class TtpCstLimitEntityServiceImpl implements ITtpCstLimitEntityService {

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addTtpCstLimit 
	 * @Description: 添加企业客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @param dayMax
	 * @param singleMax
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int addTtpCstLimit(String cstNo, String channel, String bizType,
			String legalId, String dayMax, String singleMax)
			throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("bizType",bizType);
			parmIN.put("legalId",legalId);
			parmIN.put("dayMax",dayMax); 
			parmIN.put("singleMax",singleMax); 
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpcstlimit.insertTtpCstLimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0073, "添加企业客户限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0073, "添加企业客户限额失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryTtpCstLimitList 
	 * @Description: 根据客户号+法人id+渠道+业务类型查询客户限额信息
	 * @param cstNo
	 * @param legalId
	 * @param channel
	 * @param bizType
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpCstLimitList(String cstNo,
			String legalId,String channel,String bizType) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstNo", cstNo);
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			parmIN.put("bizType", bizType);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpcstlimit.queryTtpCstLimit", parmIN);
		} catch(ServiceException e){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0067, "根据客户号+法人id+渠道+业务类型查询客户限额信息失败", e);
		}catch(Exception ex) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0067, "根据客户号+法人id+渠道+业务类型查询客户限额信息失败", ex);
		}
	}
	/**
	 * 
	 * @Title: updateTtpCstLimit 
	 * @Description: 更新企业客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @param dayMax
	 * @param singleMax
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int updateTtpCstLimit(String cstNo, String channel, String bizType,
			String legalId, String dayMax, String singleMax)
			throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("bizType",bizType);
			parmIN.put("legalId",legalId);
			parmIN.put("dayMax",dayMax); 
			parmIN.put("singleMax",singleMax); 
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpcstlimit.updateTtpCstLimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0074, "更新企业客户限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0074, "更新企业客户限额失败", ex);
		}
	}
	/**
	 * 
	 * @Title: deleteTtpCstLimit 
	 * @Description: 删除企业客户限额
	 * @param cstNo
	 * @param channel
	 * @param bizType
	 * @param legalId
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteTtpCstLimit(String cstNo, String channel, String bizType,
			String legalId) throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("bizType",bizType);
			parmIN.put("legalId",legalId);
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.ttpcstlimit.deleteTtpCstLimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0066, "删除企业客户限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0066, "删除企业客户限额失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryTtpCstLimitList 
	 * @Description: 查询所有企业客户限额
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryTtpCstLimitList()
			throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.ttpcstlimit.queryAllTtpCstLimit", parmIN);
		} catch(ServiceException e){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0068, "查询所有企业客户限额失败", e);
		}catch(Exception ex) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0068, "查询所有企业客户限额失败", ex);
		}
	}
}
