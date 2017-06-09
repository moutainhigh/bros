package bros.provider.parent.bankmanage.teller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.teller.service.ITellerService;

/** 
 * @ClassName:TellerServiceImpl  
 * @Description:柜员管理接口
 * @author  haojinhui
 * @date 2016年6月28日 上午11:06:21 
 * @version V1.0  
 */
@Repository(value = "tellerService")
public class TellerServiceImpl implements ITellerService {
	private static final Logger logger = LoggerFactory.getLogger(TellerServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryTellerById
	 * @Description: 查询柜员信息
	 * @param contextMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryTellerById(Map<String,Object> headMap,Map<String, Object> contextMap) throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String btrLegal = (String) contextMap.get("legalPersonId");//法人id
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrStt = (String) (contextMap.get("btrStt")==null?"":contextMap.get("btrStt"));//柜员状态
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构
			
			int pageSize = Integer.parseInt((contextMap.get("pageSize")==null?10000:contextMap.get("pageSize")).toString());//每页显示条数
			int pageNo =  Integer.parseInt((contextMap.get("pageNo")==null?1:contextMap.get("pageNo")).toString());//第几页
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrStt", btrStt);
			parmIn.put("btrBrancode", btrBrancode);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			//查询柜员信息列表
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmateller.queryTellerById", parmIn, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmateller.queryTellerNum", parmIn);
			//组装返回数据
			tellerMap.put("returnList", dataList);
			tellerMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerById");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerById");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询柜员信息操作失败", e);
		}
		return tellerMap;
	}
	
	/**
	 * 
	 * @Title: queryTellerBybtrLegal
	 * @Description: 查询柜员信息(不用柜员状态查询)
	 * @param contextMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryTellerBybtrLegal(Map<String,Object> headMap,Map<String, Object> contextMap)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String btrLegal = (String) contextMap.get("legalPersonId");//法人id
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构
			
			int pageSize = Integer.parseInt((contextMap.get("pageSize")==null?10000:contextMap.get("pageSize")).toString());//每页显示条数
			int pageNo =  Integer.parseInt((contextMap.get("pageNo")==null?1:contextMap.get("pageNo")).toString());//第几页
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrBrancode", btrBrancode);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			//查询柜员信息列表
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmateller.queryTellerBybtrLegal", parmIn, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmateller.queryTellerBybtrLegalNum", parmIn);
			//组装返回数据
			tellerMap.put("returnList", dataList);
			tellerMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerBybtrLegal");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".queryTellerBybtrLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询柜员信息操作失败", e);
		}
		return tellerMap;
	}
	/**
	 * 
	 * @Title: addTeller
	 * @Description: 新增柜员
	 * @param contextMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> addTeller(Map<String,Object> headMap,Map<String, Object> contextMap)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String btrId = BaseUtil.createUUID();//柜员uuid
			String btrLegal = (String) contextMap.get("legalPersonId");//法人id
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构id
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrName = (String) (contextMap.get("btrName")==null?"":contextMap.get("btrName"));//柜员名称
			String btrDesc = (String) (contextMap.get("btrDesc")==null?"":contextMap.get("btrDesc"));//柜员描述
			String btrAddress = (String) (contextMap.get("btrAddress")==null?"":contextMap.get("btrAddress"));//柜员地址
			String btrCerttype = (String) (contextMap.get("btrCerttype")==null?"":contextMap.get("btrCerttype"));//柜员证件类型
			String btrCertno = (String) (contextMap.get("btrCertno")==null?"":contextMap.get("btrCertno"));//柜员证件号码
			String btrMobile = (String) (contextMap.get("btrMobile")==null?"":contextMap.get("btrMobile"));//柜员手机号
			String btrPhone = (String) (contextMap.get("btrPhone")==null?"":contextMap.get("btrPhone"));//柜员电话号
			String btrEmail = (String) (contextMap.get("btrEmail")==null?"":contextMap.get("btrEmail"));//柜员邮箱
			String btrPwd = (String) (contextMap.get("btrPwd")==null?"":contextMap.get("btrPwd"));//柜员登录密码
			String btrPwdstt = (String) (contextMap.get("btrPwdstt")==null?"":contextMap.get("btrPwdstt"));//柜员登录密码状态
			String btrStt = "0";//柜员状态
			String btrLoginstt = (String) (contextMap.get("btrLoginstt")==null?"":contextMap.get("btrLoginstt"));//柜员登录状态
			String btrValidateType = (String) (contextMap.get("btrValidateType")==null?"":contextMap.get("btrValidateType"));//登录方式
			String note1 = (String) (contextMap.get("note1")==null?"":contextMap.get("note1"));//备注1
			String note2 = (String) (contextMap.get("note2")==null?"":contextMap.get("note2"));//备注2
			parmIn.put("btrId", btrId);
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrBrancode", btrBrancode);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrName", btrName);
			parmIn.put("btrDesc", btrDesc);
			parmIn.put("btrAddress", btrAddress);
			parmIn.put("btrCerttype", btrCerttype);
			parmIn.put("btrCertno", btrCertno);
			parmIn.put("btrMobile", btrMobile);
			parmIn.put("btrPhone", btrPhone);
			parmIn.put("btrEmail", btrEmail);
			parmIn.put("btrPwd", btrPwd);
			parmIn.put("btrPwdstt", btrPwdstt);
			parmIn.put("btrStt", btrStt);
			parmIn.put("btrLoginstt", btrLoginstt);
			parmIn.put("btrValidateType", btrValidateType);
			parmIn.put("note1", note1);
			parmIn.put("note2", note2);

			//新增柜员
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmateller.insertTeller",parmIn);
		} catch (ServiceException se) {
			logger.error("新增柜员信息失败   " + this.getClass() + ".addTeller");
			throw se;
		} catch (Exception e) {
			logger.error("新增柜员信息失败   " + this.getClass() + ".addTeller");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"新增柜员信息操作失败", e);
		}
		return tellerMap;
		
	}
	
	/**
	 * 
	 * @Title: updateTeller
	 * @Description: 修改柜员信息
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> updateTeller(Map<String,Object> headMap,Map<String, Object> contextMap)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String btrLegal = (String) contextMap.get("legalPersonId");//法人id
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构编号
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrName = (String) (contextMap.get("btrName")==null?"":contextMap.get("btrName"));//柜员名称
			String btrDesc = (String) (contextMap.get("btrDesc")==null?"":contextMap.get("btrDesc"));//柜员描述
			String btrAddress = (String) (contextMap.get("btrAddress")==null?"":contextMap.get("btrAddress"));//柜员地址
			String btrCerttype = (String) (contextMap.get("btrCerttype")==null?"":contextMap.get("btrCerttype"));//柜员证件类型
			String btrCertno = (String) (contextMap.get("btrCertno")==null?"":contextMap.get("btrCertno"));//柜员证件号码
			String btrMobile = (String) (contextMap.get("btrMobile")==null?"":contextMap.get("btrMobile"));//柜员手机号
			String btrPhone = (String) (contextMap.get("btrPhone")==null?"":contextMap.get("btrPhone"));//柜员电话号
			String btrEmail = (String) (contextMap.get("btrEmail")==null?"":contextMap.get("btrEmail"));//柜员邮箱		
			String btrValidateType = (String) (contextMap.get("btrValidateType")==null?"":contextMap.get("btrValidateType"));//登录方式
			
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrBrancode", btrBrancode);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrName", btrName);
			parmIn.put("btrDesc", btrDesc);
			parmIn.put("btrAddress", btrAddress);
			parmIn.put("btrCerttype", btrCerttype);
			parmIn.put("btrCertno", btrCertno);
			parmIn.put("btrMobile", btrMobile);
			parmIn.put("btrPhone", btrPhone);
			parmIn.put("btrEmail", btrEmail);
			parmIn.put("btrValidateType", btrValidateType);

			//修改柜员信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmateller.updateTeller",parmIn);
		} catch (ServiceException se) {
			logger.error("柜员信息修改失败   " + this.getClass() + ".updateTeller");
			throw se;
		} catch (Exception e) {
			logger.error("柜员信息修改失败   " + this.getClass() + ".updateTeller");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"柜员信息修改操作失败", e);
		}
		return tellerMap;
	}
	
	/**
	 * 
	 * @Title: deleteTeller
	 * @Description: 删除柜员信息
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> deleteTeller(Map<String,Object> headMap,Map<String, Object> contextMap,String btrId)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			Map<String, Object> parmInrel = new HashMap<String,Object>();
			
			String btrLegal = (String) contextMap.get("legalPersonId");//法人id
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构编号
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrBrancode", btrBrancode);
			parmIn.put("btrCode", btrCode);
			parmInrel.put("burlTrllerid", btrId);
			//删除柜员信息
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmateller.deleteTeller",parmIn);
			//删除柜员角色对应关系
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmatellerrolerel.deleteTellerRolerel",parmInrel);
		} catch (ServiceException se) {
			logger.error("柜员信息删除失败   " + this.getClass() + ".deleteTeller");
			throw se;
		} catch (Exception e) {
			logger.error("柜员信息删除失败   " + this.getClass() + ".deleteTeller");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"柜员信息删除操作失败", e);
		}
		return tellerMap;
	}
	
	/**
	 * 
	 * @Title: tellerLogin
	 * @Description: 柜员登录
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> tellerLogin(Map<String,Object> headMap,Map<String, Object> contextMap)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String btrLegal = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构编号
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrStt = "2";//柜员状态
			String btrLoginstt = "0";//柜员登录状态
			
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrBrancode", btrBrancode);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrStt", btrStt);
			parmIn.put("btrLoginstt", btrLoginstt);

			//修改柜员信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmateller.tellerLogin",parmIn);
		} catch (ServiceException se) {
			logger.error("柜员登录操作失败   " + this.getClass() + ".tellerLogin");
			throw se;
		} catch (Exception e) {
			logger.error("柜员登录操作失败   " + this.getClass() + ".tellerLogin");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"柜员登录操作失败", e);
		}
		return tellerMap;
	}
	/**
	 * 
	 * @Title: tellerLogout
	 * @Description: 柜员签退
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> tellerLogout(Map<String,Object> headMap,Map<String, Object> contextMap)
			throws ServiceException {
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String btrLegal = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
			String btrBrancode = (String) (contextMap.get("btrBrancode")==null?"":contextMap.get("btrBrancode"));//所属机构编号
			String btrCode = (String) (contextMap.get("btrCode")==null?"":contextMap.get("btrCode"));//柜员编号
			String btrStt = (String) (contextMap.get("btrStt")==null?"":contextMap.get("btrStt"));//柜员状态
			String btrLoginstt = "1";//柜员登录状态
			
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrBrancode", btrBrancode);
			parmIn.put("btrCode", btrCode);
			parmIn.put("btrStt", btrStt);
			parmIn.put("btrLoginstt", btrLoginstt);

			//修改柜员信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmateller.tellerLogin",parmIn);
		} catch (ServiceException se) {
			logger.error("柜员签退操作失败   " + this.getClass() + ".tellerLogout");
			throw se;
		} catch (Exception e) {
			logger.error("柜员签退操作失败   " + this.getClass() + ".tellerLogout");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"柜员签退操作失败", e);
		}
		return tellerMap;
	}
	/**
	 * 
	 * @Title: queryTellerRoleMenu
	 * @Description: 根据柜员查询菜单
	 * @param bodyMap
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryTellerRoleMenu(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		Map<String, Object> roleMenuMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String btrchannel = (String) (bodyMap.get("btrchannel")==null?"":bodyMap.get("btrchannel"));//系统标识
			String btrCode = (String) (bodyMap.get("btrCode")==null?"":bodyMap.get("btrCode"));//柜员编号
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			parmIn.put("btrchannel", btrchannel);
			parmIn.put("btrCode", btrCode);
			
			//根据柜员查询菜单
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmateller.queryTellerRoleMenu",parmIn);
			roleMenuMap.put("returnList", dataList);
		} catch (ServiceException se) {
			logger.error("根据柜员查询菜单失败   " + this.getClass() + ".queryTellerRoleMenu");
			throw se;
		} catch (Exception e) {
			logger.error("根据柜员查询菜单失败   " + this.getClass() + ".queryTellerRoleMenu");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"根据柜员查询菜单失败", e);
		}
		return roleMenuMap;
	}
	/**
	 * 
	 * @Title: queryMenudefPro
	 * @Description: 根据法人id，菜单性质，系统标识查询货架编码
	 * @param bodyMap
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMenudefPro(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		Map<String, Object> roleMenuProMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String btrLegal = (String) bodyMap.get("legalPersonId");//法人id
			String btrproperties = (String) (bodyMap.get("btrproperties")==null?"":bodyMap.get("btrproperties"));//菜单性质
			String btrchannel = (String) (bodyMap.get("btrchannel")==null?"":bodyMap.get("btrchannel"));//系统标识
			String bmprShelfcode = (String) (bodyMap.get("bmprShelfcode")==null?"":bodyMap.get("bmprShelfcode"));//货架编号
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			parmIn.put("btrLegal", btrLegal);
			parmIn.put("btrchannel", btrchannel);
			parmIn.put("btrproperties", btrproperties);
			parmIn.put("bmprShelfcode", bmprShelfcode);
			
			//根据柜员查询菜单
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmateller.queryTellerRoleMenuPro",parmIn);
			roleMenuProMap.put("returnList", dataList);
		} catch (ServiceException se) {
			logger.error("查询货架编码操作失败   " + this.getClass() + ".queryMenudefPro");
			throw se;
		} catch (Exception e) {
			logger.error("查询货架编码操作失败   " + this.getClass() + ".queryMenudefPro");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询货架编码操作失败", e);
		}
		return roleMenuProMap;
	}
}
