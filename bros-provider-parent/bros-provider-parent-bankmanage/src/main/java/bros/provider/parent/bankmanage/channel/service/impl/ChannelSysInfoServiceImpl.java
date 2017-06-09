package bros.provider.parent.bankmanage.channel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.channel.service.IChannelSysInfoService;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: ChannelSysInfoServiceImpl 
 * @Description: 渠道系统信息实现类
 * @author 高永静
 * @date 2016年6月28日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "channelSysInfoService")
public class ChannelSysInfoServiceImpl implements IChannelSysInfoService {
	/**
	 * 渠道系统信息Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(ChannelSysInfoServiceImpl.class);
	
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	
	/**
	 * 
	 * @Title: addChannelSysInfoMethod
	 * @Description: 新增渠道系统信息
	 * @param paramMap  渠道系统信息，包括以下
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void addChannelSysInfoMethod(String chlCode,String legalPersonId,String chlName,String chlDesc,
			String chlSysCode,String chlFlag)throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String chlId = BaseUtil.createUUID();  //  渠道系统唯一标识
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlName",chlName);
			param.put("chlDesc",chlDesc);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			
			// 添加渠道系统
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdchlsys.insertChlInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s addChannelSysInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s addChannelSysInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "新增渠道系统信息失败", e);
		}
	}

	/**
	 * 
	 * @Title: updateChannelSysInfoMethod
	 * @Description: 修改渠道系统信息
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlName 系统名称
	 * @param chlDesc 系统描述
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateChannelSysInfoMethod(String chlId,String chlCode,String legalPersonId,String chlName,String chlDesc,
			String chlSysCode,String chlFlag)throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlName",chlName);
			param.put("chlDesc",chlDesc);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			
			// 修改渠道系统信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.prdchlsys.updateChlInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateChannelSysInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateChannelSysInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改渠道系统信息失败", e);
		}

	}
	/**
	 * 
	 * @Title: updateChannelSysStatusMethod
	 * @Description: 修改渠道系统状态
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param chlFlag 启停状态
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateChannelSysStatusMethod(String chlId,String chlCode,String legalPersonId,
			String chlSysCode,String chlFlag)throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			
			// 修改渠道系统信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.prdchlsys.updateChlStatus", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s updateChannelSysStatusMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s updateChannelSysStatusMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "修改渠道系统状态失败", e);
		}

	}
	/**
	 * 
	 * @Title: deleteChannelSysInfoMethod
	 * @Description: 删除渠道系统信息
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteChannelSysInfoMethod(String chlId,String chlCode,String legalPersonId,String chlSysCode)
			throws ServiceException {
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			
			// 删除渠道系统
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdchlsys.deleteChlInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s deleteChannelSysInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s deleteChannelSysInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "删除渠道系统信息失败", e);
		}
	}

	/**
	 * 
	 * @Title: queryAllChannelSysInfoMethod
	 * @Description: 根据条件查询渠道系统信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryChannelSysInfoMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> chlList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //  渠道系统唯一标识
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
			String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
			String chlFlag = (String) (paramMap.get("chlFlag")==null?"":paramMap.get("chlFlag"));  //
			
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			 
			//为空时根据条件查询全部
			chlList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdchlsys.queryChlInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryAllChannelSysInfoMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryAllChannelSysInfoMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询渠道系统信息失败", e);
		}
		return chlList;
	}
	/**
	 * 
	 * @Title: queryChannelSysInfoPageMethod
	 * @Description: 根据条件分页查询渠道系统信息
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryChannelSysInfoPageMethod(Map<String, Object> paramMap) throws ServiceException {
		List<Map<String,Object>> chlList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //  渠道系统唯一标识
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
			String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
			String chlFlag = (String) (paramMap.get("chlFlag")==null?"":paramMap.get("chlFlag"));  //
			
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			int pageNo = (Integer)paramMap.get("pageNo");  //  页码
			int pageSize = (Integer)paramMap.get("pageSize");  //  每页条数
			chlList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdchlsys.queryChlInfo", param, pageNo, pageSize);
			
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoPageMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoPageMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据条件分页查询渠道系统信息失败", e);
		}
		return chlList;
	}
	/**
	 * 
	 * @Title: queryChannelSysTotalNumMethod
	 * @Description: 根据条件查询渠道系统总条数
	 * @param paramMap 渠道信息包含以下参数
	 * @param chlId 渠道系统唯一标识
	 * @param chlCode 渠道系统编号
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 系统分组编号
	 * @return List<Map<String,Object>> 渠道系统信息
	 * @throws ServiceException
	 */
	@Override
	public int queryChannelSysTotalNumMethod(Map<String, Object> paramMap) throws ServiceException {
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  //  渠道系统唯一标识
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
			String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
			String chlFlag = (String) (paramMap.get("chlFlag")==null?"":paramMap.get("chlFlag"));  //
			
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			param.put("chlFlag",chlFlag);
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdchlsys.queryChlInfoTotalNum", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysTotalNumMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysTotalNumMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据条件查询渠道系统总条数失败", e);
		}
		return totalNum;
	}
	/**
	 * 
	 * @Title: queryChannelSysInfoByObjectIdMethod
	 * @Description: 查询渠道系统信息
	 * @param paramMap 
	 * @return Map<String, Object> 渠道系统信息
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryChannelSysInfoByObjectIdMethod(Map<String, Object> paramMap)
			throws ServiceException {
		Map<String, Object> parmOut = new HashMap<String, Object>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			String chlId = (String) (paramMap.get("chlId")==null?"":paramMap.get("chlId"));  // 渠道系统唯一标识
			String chlCode = (String) (paramMap.get("chlCode")==null?"":paramMap.get("chlCode"));  // 渠道系统编号
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));  // 机构法人ID
			String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));  //  系统分组编号
			param.put("chlId",chlId);
			param.put("chlCode",chlCode);
			param.put("legalPersonId",legalPersonId);
			param.put("chlSysCode",chlSysCode);
			 
			parmOut = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdchlsys.queryChlInfo", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoByObjectIdMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoByObjectIdMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "查询渠道系统信息失败", e);
		}
		return parmOut;
	}

}
