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
import bros.provider.parent.bankmanage.channel.service.IChannelSysGroupService;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/**
 * 
 * @ClassName: IChannelSysGroupService
 * @Description: 渠道系统分组接口
 * @author 高永静
 * @date 2016年6月27日 上午9:45:17
 * @version 1.0
 */
@Repository(value = "channelSysGroupService")
public class ChannelSysGroupServiceImpl implements IChannelSysGroupService {
	private static final Logger logger = LoggerFactory.getLogger(ChannelSysGroupServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: addChannelSysGroupMethod
	 * @Description: 新增渠道系统分组
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @param chlSysName 渠道系统分组名称
	 * @param chlSysDesc 渠道系统分组描述
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void addChannelSysGroupMethod(String legalPersonId, String chlSysCode,
			String chlSysName, String chlSysDesc) throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalPersonId", legalPersonId); //法人ID
			parmIN.put("chlSysCode", chlSysCode);//渠道系统分组编码
			parmIN.put("chlSysName", chlSysName);//渠道系统分组名称
			parmIN.put("chlSysDesc", chlSysDesc);//渠道系统分组描述
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.prdchlsystem.insertChlSysGroup",parmIN);
		} catch (ServiceException se) {
			logger.error("添加渠道系统分组失败   " + this.getClass() + ".addChannelSysGroupMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("添加渠道系统分组失败    " + this.getClass() + ".addChannelSysGroupMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0002,"新增渠道系统分组失败", e);
		}
	}

	/**
	 * 
	 * @Title: updateChannelSysGroupMethod
	 * @Description: 修改渠道系统分组信息
	 * @param paramMap 渠道分组信息包含以下参数
	 * @param legalPersonId 法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @param chlSysName 渠道系统分组名称
	 * @param chlSysDesc 渠道系统分组描述
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateChannelSysGroupMethod(Map<String, Object> paramMap)
			throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			String legalPersonId = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("legalPersonId"));//法人ID
			String chlSysCode = (String) (paramMap.get("chlSysCode")==null?"":paramMap.get("chlSysCode"));//渠道系统分组编号
			String chlSysName = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("chlSysName"));//渠道系统分组名称
			String chlSysDesc = (String) (paramMap.get("legalPersonId")==null?"":paramMap.get("chlSysDesc"));//渠道系统分组描述
			parmIN.put("legalPersonId",legalPersonId);
			parmIN.put("chlSysCode", chlSysCode);
			parmIN.put("chlSysName", chlSysName);
			parmIN.put("chlSysDesc", chlSysDesc);
			myBatisDaoSysDao.update("mybatis.mapper.single.table.prdchlsystem.updateChlSysGroup",parmIN);
		} catch (ServiceException se) {
			logger.error("更新渠道系统分组失败   " + this.getClass() + ".updateChannelSysGroupMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("更新渠道系统分组失败   " + this.getClass() + ".updateChannelSysGroupMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0003,"修改渠道系统分组失败", e);
		}

	}

	/**
	 * 
	 * @Title: deleteChannelSysGroupMethod
	 * @Description: 删除渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void deleteChannelSysGroupMethod(String legalPersonId, String chlSysCode)
			throws ServiceException {
		try {
			Map<String, Object> parmIN = new HashMap<String, Object>();
			parmIN.put("legalPersonId", legalPersonId);//法人ID
			parmIN.put("chlSysCode", chlSysCode);//渠道系统分组编号
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.prdchlsystem.deleteChlSysGroup",parmIN);
		} catch (ServiceException se) {
			logger.error("删除渠道系统分组失败   " + this.getClass() + ".deleteChannelSysGroupMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("删除渠道系统分组失败   " + this.getClass() + ".deleteChannelSysGroupMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0004,"删除渠道系统分组失败", e);
		}
	}

	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description: 查询渠道系统分组信息
	 * @param legalPersonId
	 *            法人ID
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String,Object>> queryChannelSysGroupMethod(String legalPersonId)
			throws ServiceException {
		List<Map<String,Object>> chlSysGroupList = new ArrayList<Map<String,Object>>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalPersonId", legalPersonId);//法人ID
			chlSysGroupList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.prdchlsystem.queryChlSysGroup",parmIn);
		} catch (ServiceException se) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0005,"查询渠道系统分组失败", e);
		}
		return chlSysGroupList;
	}
	/**
	 * 
	 * @Title: queryChannelSysGroupPageMethod
	 * @Description: 分页查询渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return List<Map<String,Object>>
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryChannelSysGroupPageMethod (String legalPersonId,int pageNo,int pageSize)throws ServiceException{
		List<Map<String,Object>> chlGroupList = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("legalPersonId",legalPersonId);// 法人ID
			chlGroupList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.prdchlsystem.queryChlSysGroup", param, pageNo, pageSize);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoPageMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysInfoPageMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据条件分页查询渠道系统分组信息失败", e);
		}
		return chlGroupList;
	}
	/**
	 * 
	 * @Title: queryChannelSysGroupTotalNumMethod
	 * @Description: 查询渠道系统分组总条数
	 * @param legalPersonId  法人ID
	 * @return int
	 * @throws ServiceException
	 */
	public int queryChannelSysGroupTotalNumMethod (String legalPersonId)throws ServiceException{
		int totalNum = 0;
		try {
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("legalPersonId",legalPersonId);// 法人ID
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.prdchlsystem.queryChlSysGroupTotalNum", param);
		} catch(ServiceException se){
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysGroupTotalNumMethod method.", se);
			throw se;
		}catch (Exception e) {
			logger.error("Exception from " + this.getClass().getName() + "'s queryChannelSysGroupTotalNumMethod method.", e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001, "根据条件查询渠道系统分组总条数失败", e);
		}
		return totalNum;
	}
	/**
	 * 
	 * @Title: queryChannelSysGroupMethod
	 * @Description:根据法人ID、渠道系统分组编码查询渠道系统分组信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  Map<String,Object> queryChannelSysGroupBySysCodeMethod (String legalPersonId,String chlSysCode)throws ServiceException{
		Map<String, Object> parmOut = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("legalPersonId", legalPersonId);//法人ID
			parmIn.put("chlSysCode", chlSysCode);//渠道系统分组编码
			parmOut = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.prdchlsystem.queryChlSysGroup",parmIn);
		} catch (ServiceException se) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBySysCodeMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBySysCodeMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0005,"查询渠道系统分组失败", e);
		}
		return parmOut;
	}
	
	/*****************pub_channel***********/
	/**
	 * 
	 * @Title: queryChannelSysGroupBaseInfoByCode
	 * @Description:查询pub_channel渠道系统信息(查询单个)
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  Map<String,Object> queryChannelSysGroupBaseInfoByCode (String channelCode)throws ServiceException{
		Map<String, Object> parmOut = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			parmIn.put("channelCode", channelCode);//渠道系统分组编码
			parmOut = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.pubchannel.queryPubChannelByCode",parmIn);
		} catch (ServiceException se) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBaseInfoByCode",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBaseInfoByCode",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0005,"查询渠道系统分组失败", e);
		}
		return parmOut;
	}
	
	
	/**
	 * 
	 * @Title: queryChannelSysGroupBaseInfoMethod
	 * @Description:查询pub_channel渠道系统信息(查询所有)
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  List<Map<String,Object>> queryChannelSysGroupBaseInfoMethod ()throws ServiceException{
		List<Map<String,Object>> chlSysGroupList = new ArrayList<Map<String,Object>>();
		try {
			
			chlSysGroupList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.pubchannel.queryPubChannel");
		} catch (ServiceException se) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBaseInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询渠道系统分组信息失败   " + this.getClass() + ".queryChannelSysGroupBaseInfoMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0005,"查询渠道系统分组失败", e);
		}
		return chlSysGroupList;
	}
	
	/**
	 * 
	 * @Title: deleteChannelSysGroupBaseInfoMethod
	 * @Description:删除pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public  void deleteChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException{
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.pubchannel.deleteChlInfoByCode",paramMap);
		} catch (ServiceException se) {
			logger.error("删除渠道系统分组信息失败   " + this.getClass() + ".deleteChannelSysGroupBaseInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("删除渠道系统分组信息失败   " + this.getClass() + ".deleteChannelSysGroupBaseInfoMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0004,"删除渠道系统分组失败", e);
		}
	
	}
	
	/**
	 * 
	 * @Title: updateChannelSysGroupBaseInfoMethod
	 * @Description:更新pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public void updateChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException{
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			myBatisDaoSysDao.update("mybatis.mapper.single.table.pubchannel.updatePubChannelByCode",paramMap);
		} catch (ServiceException se) {
			logger.error("更新渠道系统分组信息失败   " + this.getClass() + ".updateChannelSysGroupBaseInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("更新渠道系统分组信息失败   " + this.getClass() + ".updateChannelSysGroupBaseInfoMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0003,"更新渠道系统分组失败", e);
		}
		
	}
	
	/**
	 * 
	 * @Title: addChannelSysGroupBaseInfoMethod
	 * @Description:新增pub_channel渠道系统信息
	 * @param legalPersonId  法人ID
	 * @param chlSysCode 渠道系统分组编码
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	public void addChannelSysGroupBaseInfoMethod (Map<String, Object> paramMap)throws ServiceException{
		try {
			//Map<String, Object> parmIn = new HashMap<String,Object>();
			
			 myBatisDaoSysDao.delete("mybatis.mapper.single.table.pubchannel.insertChlInfo",paramMap);
		} catch (ServiceException se) {
			logger.error("新增渠道系统分组信息失败   " + this.getClass() + ".addChannelSysGroupBaseInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("新增渠道系统分组信息失败   " + this.getClass() + ".addChannelSysGroupBaseInfoMethod",e);
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0002,"新增渠道系统分组失败", e);
		}
	
	}
}
