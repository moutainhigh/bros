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
import bros.provider.parent.manage.corporate.cstmanage.limit.service.IBmaCstLandLimitEntityService;
/**
 * 
 * @ClassName: BmaCstLandLimitEntityServiceImpl 
 * @Description: 企业落地限额服务
 * @author zsg 
 * @date 2017年1月12日 下午3:05:45 
 * @version 1.0
 */
@Repository(value = "bmaCstLandLimitEntityService")
public class BmaCstLandLimitEntityServiceImpl implements
	IBmaCstLandLimitEntityService {
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addBmaCstLandLimit 
	 * @Description: 添加企业客户落地限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @param singleMax
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int addBmaCstLandLimit(String cstNo, String channel, String legalId, String singleMax)
			throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("legalId",legalId);
			parmIN.put("singleMax",singleMax); 
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmacstlandlimit.insertBmaCstLandlimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0069, "添加企业客户落地限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0069, "添加企业客户落地限额失败", ex);
		}
	}
	/**
	 * 
	 * @Title: queryBmaCstLandLimitList 
	 * @Description: 根据客户号+法人id+渠道查询客户限额信息
	 * @param cstNo
	 * @param legalId
	 * @param channel
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBmaCstLandLimitList(String cstNo,
			String legalId,String channel) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("cstNo", cstNo);
			parmIN.put("legalId", legalId);
			parmIN.put("channel", channel);
			
			return myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmacstlandlimit.queryBmaCstLandLimit", parmIN);
		} catch(ServiceException e){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0072, "根据客户号+法人id+渠道+业务类型查询客户限额信息失败", e);
		}catch(Exception ex) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0072, "根据客户号+法人id+渠道+业务类型查询客户限额信息失败", ex);
		}
	}
	/**
	 * 
	 * @Title: updateBmaCstLandLimit 
	 * @Description: 更新企业客户落地限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @param singleMax
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int updateBmaCstLandLimit(String cstNo, String channel,
			String legalId, String singleMax)
			throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("legalId",legalId);
			parmIN.put("singleMax",singleMax); 
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmacstlandlimit.updateBmaCstLandlimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0070, "更新企业客户落地限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0070, "更新企业客户落地限额失败", ex);
		}
	}
	/**
	 * 
	 * @Title: deleteBmaCstLandLimit 
	 * @Description: 删除企业客户落地限额
	 * @param cstNo
	 * @param channel
	 * @param legalId
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ServiceException.class})
	public int deleteBmaCstLandLimit(String cstNo, String channel, String legalId) throws ServiceException {
		try{			
			Map<String,Object> parmIN=new HashMap<String,Object>();
			parmIN.put("cstNo",cstNo);
			parmIN.put("channel",channel);
			parmIN.put("legalId",legalId);
			
			return myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmacstlandlimit.deleteBmaCstLandLimit", parmIN);
		} catch (ServiceException e) {
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0071, "删除企业客户落地限额失败", e);
		}catch (Exception ex) {
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0071, "删除企业客户落地限额失败", ex);
		}
	}
}
