package bros.provider.parent.bankmanage.bannerinfo.service.impl;

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
import bros.common.core.util.DateUtil;
import bros.provider.parent.bankmanage.bannerinfo.service.IBannerinfoService;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;

/** 
 * @ClassName:IPBannerinfoServiceFacade  
 * @Description:广告栏服务接口
 * @author  wuchenglong
 * @date 2016年9月6日 上午14:50:28 
 * @version V1.0  
 */
@Repository(value = "bannerinfoService")
public class BannerinfoServiceImpl implements IBannerinfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(BannerinfoServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addBannerinfo
	 * @Description: 增加广告栏
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> addBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> bannerinfoMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String bannerId = BaseUtil.createUUID();//菜单uuid
			String bannerLegal = (String) bodyMap.get("bannerLegal");//法人id
			String bannerCode = (String) (bodyMap.get("bannerCode")==null?"":bodyMap.get("bannerCode"));//广告栏编码
			String bannerName = (String) (bodyMap.get("bannerName")==null?"":bodyMap.get("bannerName"));//广告栏名称
			String bannerUrl = (String) (bodyMap.get("bannerUrl")==null?"":bodyMap.get("bannerUrl"));//广告栏跳转的URL
			
			String bannerStus = (String) (bodyMap.get("bannerStus")==null?"":bodyMap.get("bannerStus"));//广告栏状态 0-已停止适用1-正在适用
			String bannerDesc = (String) (bodyMap.get("bannerDesc")==null?"":bodyMap.get("bannerDesc"));//广告栏描述
			String loginFlag = (String) (bodyMap.get("loginFlag")==null?"":bodyMap.get("loginFlag"));//是否需要登陆
			String bannerParentid = (String) (bodyMap.get("bannerParentid")==null?"":bodyMap.get("bannerParentid"));//父菜单ID
			String bannerChannel = (String) (bodyMap.get("bannerChannel")==null?"":bodyMap.get("bannerChannel"));//渠道号
			String bannerUpdate = DateUtil.getServerTime("yyyyMMddHHmmss");//更新时间
			int bannerSeqno = Integer.parseInt((bodyMap.get("bannerSeqno")==null?"":(String)bodyMap.get("bannerSeqno")));//菜单编码
			
			parmIn.put("bannerId", bannerId);
			parmIn.put("bannerLegal", bannerLegal);
			parmIn.put("bannerCode", bannerCode);
			parmIn.put("bannerName", bannerName);
			parmIn.put("bannerUrl", bannerUrl);
			parmIn.put("bannerStus", bannerStus);
			parmIn.put("bannerDesc", bannerDesc);
			parmIn.put("loginFlag", loginFlag);
			parmIn.put("bannerParentid", bannerParentid);
			parmIn.put("bannerChannel", bannerChannel);
			parmIn.put("bannerUpdate", bannerUpdate);
			parmIn.put("bannerSeqno", bannerSeqno);
			//新增菜单
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bannerinfo.insertBannerinfo",parmIn);
		} catch (ServiceException se) {
			logger.error("新增广告栏信息失败   " + this.getClass() + ".addBannerinfo");
			throw se;
		} catch (Exception e) {
			logger.error("新增广告栏信息失败   " + this.getClass() + ".addBannerinfo");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"新增广告栏信息操作失败", e);
		}
		return bannerinfoMap;
	}
	  /**
		 * 
		 * @Title: queryBannerinfo
		 * @Description: 广告栏查询
		 * @param headMap  头信息
		 * @param bodyMap  上送报文
		 * @return
		 * @throws ServiceException
		 */
	public Map<String, Object> queryBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String bannerLegal = (String) bodyMap.get("bannerLegal");//法人id
			String bannerCode = (String) (bodyMap.get("bannerCode")==null?"":bodyMap.get("bannerCode"));//菜单编号
			String bannerChannel = (String) (bodyMap.get("bannerChannel")==null?"":bodyMap.get("bannerChannel"));//系统标识
			int pageSize = Integer.parseInt((bodyMap.get("pageSize")==null?10000:bodyMap.get("pageSize")).toString());//每页显示条数
			int pageNo =  Integer.parseInt((bodyMap.get("pageNo")==null?1:bodyMap.get("pageNo")).toString());//第几页
			
			parmIn.put("bannerLegal", bannerLegal);
			parmIn.put("bannerCode", bannerCode);
			parmIn.put("bannerChannel", bannerChannel);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> branchList2  =  new ArrayList<Map<String, Object>>();	
			//查询菜单信息列表
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bannerinfo.queryBannerinfo", parmIn, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bannerinfo.queryBannerinfoNum", parmIn);
			
//			String bannerParentid = "";
//			if(totalNum!=0 && !bannerCode.equals("")){
//				bannerParentid = (String) dataList.get(0).get("bannerId");
//			}
//			if(bannerParentid != null && bannerParentid!=""){
//				parmIn.put("bannerParentid", bannerParentid);
//				//查询机构信息列表
//				branchList2 = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bannerinfo.queryBannerinfo",parmIn);				
//				//查询总条数
//				int totalNum2 = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);
//				if(branchList2!=null){
//					dataList.addAll(branchList2);
//					totalNum = totalNum + totalNum2;
//				}
//			}
			//组装返回数据
			menudefMap.put("returnList", dataList);
			menudefMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询广告栏信息失败   " + this.getClass() + ".queryBannerinfo");
			throw se;
		} catch (Exception e) {
			logger.error("查询广告栏信息失败   " + this.getClass() + ".queryBannerinfo");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询广告栏信息操作失败", e);
		}
		return menudefMap;
	}
	 /**
		 * 
		 * @Title: updateBannerinfo 
		 * @Description: 修改广告栏
		 * @param headMap  头信息
		 * @param bodyMap  上送报文
		 * @return
		 * @throws ServiceException
		 */
	public Map<String, Object> updateBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String bannerId = (String) (bodyMap.get("bannerId")==null?"":bodyMap.get("bannerId"));//菜单id
			String bannerLegal = (String) bodyMap.get("bannerLegal");//法人id
			String bannerCode = (String) (bodyMap.get("bannerCode")==null?"":bodyMap.get("bannerCode"));//广告栏编码
			String bannerName = (String) (bodyMap.get("bannerName")==null?"":bodyMap.get("bannerName"));//广告栏名称
			String bannerUrl = (String) (bodyMap.get("bannerUrl")==null?"":bodyMap.get("bannerUrl"));//广告栏跳转的URL
			
			String bannerStus = (String) (bodyMap.get("bannerStus")==null?"":bodyMap.get("bannerStus"));//广告栏状态 0-已停止适用1-正在适用
			String bannerDesc = (String) (bodyMap.get("bannerDesc")==null?"":bodyMap.get("bannerDesc"));//广告栏描述
			String loginFlag = (String) (bodyMap.get("loginFlag")==null?"":bodyMap.get("loginFlag"));//是否需要登陆
			String bannerParentid = (String) (bodyMap.get("bannerParentid")==null?"":bodyMap.get("bannerParentid"));//父菜单ID
			String bannerChannel = (String) (bodyMap.get("bannerChannel")==null?"":bodyMap.get("bannerChannel"));//渠道号
			String bannerUpdate = DateUtil.getServerTime("yyyyMMddHHmmss");//更新时间
			int bannerSeqno = Integer.parseInt((bodyMap.get("bannerSeqno")==null?"":(String)bodyMap.get("bannerSeqno")));//菜单编码
			parmIn.put("bannerId", bannerId);
			parmIn.put("bannerLegal", bannerLegal);
			parmIn.put("bannerCode", bannerCode);
			parmIn.put("bannerName", bannerName);
			parmIn.put("bannerUrl", bannerUrl);
			parmIn.put("bannerStus", bannerStus);
			parmIn.put("bannerDesc", bannerDesc);
			parmIn.put("loginFlag", loginFlag);
			parmIn.put("bannerParentid", bannerParentid);
			parmIn.put("bannerChannel", bannerChannel);
			parmIn.put("bannerUpdate", bannerUpdate);
			parmIn.put("bannerSeqno", bannerSeqno);

			//修改广告栏
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bannerinfo.updateBannerinfo",parmIn);
		} catch (ServiceException se) {
			logger.error("广告栏修改失败   " + this.getClass() + ".updateBannerinfo");
			throw se;
		} catch (Exception e) {
			logger.error("广告栏修改失败   " + this.getClass() + ".updateBannerinfo");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"广告栏修改操作失败", e);
		}
		return menudefMap;
	}
	/**
	 * 
	 * @Title: deleteBannerinfo 
	 * @Description: 删除广告栏
	 * @param headMap  头信息
	 * @param bodyMap  上送报文
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> deleteBannerinfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
//			Map<String, Object> parmInrel = new HashMap<String,Object>();
			
			String bannerId = (String) (bodyMap.get("bannerId")==null?"":bodyMap.get("bannerId"));//菜单id
			
			parmIn.put("bannerId", bannerId);
//			parmInrel.put("bmrlMenuId", bmfId);
			//删除菜单信息
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bannerinfo.deleteBannerinfo",parmIn);
			//删除菜单角色对应关系
//			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmamenurolerel.deleteMenuRoleRel",parmInrel);
		} catch (ServiceException se) {
			logger.error("广告栏信息删除失败   " + this.getClass() + ".deleteMenudef");
			throw se;
		} catch (Exception e) {
			logger.error("广告栏信息删除失败   " + this.getClass() + ".deleteMenudef");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"广告栏信息删除操作失败", e);
		}
		return menudefMap;
	}

}