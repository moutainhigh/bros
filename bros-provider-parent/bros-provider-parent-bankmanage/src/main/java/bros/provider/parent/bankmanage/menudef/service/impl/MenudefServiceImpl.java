package bros.provider.parent.bankmanage.menudef.service.impl;

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
import bros.common.core.exception.BrosBaseException;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DateUtil;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.menudef.service.IMenudefService;
import bros.provider.parent.bankmanage.teller.service.impl.TellerServiceImpl;

/** 
 * @ClassName:MenudefServiceImpl  
 * @Description:菜单管理接口
 * @author  haojinhui
 * @date 2016年7月6日 上午10:57:36 
 * @version V1.0  
 */
@Repository(value = "menudefService")
public class MenudefServiceImpl implements IMenudefService{

	private static final Logger logger = LoggerFactory.getLogger(TellerServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryMenudef
	 * @Description: 查询菜单
	 * @param bodyMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryMenudef(Map<String,Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String bmfLegal = (String) bodyMap.get("legalPersonId");//法人id
			String bmfCode = (String) (bodyMap.get("bmfCode")==null?"":bodyMap.get("bmfCode"));//菜单编号
			String bmfChannel = (String) (bodyMap.get("bmfChannel")==null?"":bodyMap.get("bmfChannel"));//系统标识
			String bmfProperties = (String) (bodyMap.get("bmfProperties")==null?"":bodyMap.get("bmfProperties"));//菜单性质
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?10000:bodyMap.get("pageSize")));//每页显示条数
			int pageNo =  Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?1:bodyMap.get("pageNo")));//第几页
			
			parmIn.put("bmfLegal", bmfLegal);
			parmIn.put("bmfCode", bmfCode);
			parmIn.put("bmfChannel", bmfChannel);
			parmIn.put("bmfProperties", bmfProperties);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> branchList2  =  new ArrayList<Map<String, Object>>();	
			//查询菜单信息列表
			dataList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.bmamenudef.queryMenudef", parmIn, pageNo, pageSize);
		    //查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmamenudef.queryMenudefNum", parmIn);
			
			String bmfParentId = "";
			if(totalNum!=0 && !bmfCode.equals("")){
				bmfParentId = (String) dataList.get(0).get("bmfId");
			}
			if(bmfParentId != null && bmfParentId!=""){
				parmIn.put("bmfParentId", bmfParentId);
				//查询机构信息列表
				branchList2 = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmamenudef.queryMenudef",parmIn);				
				//查询总条数
				int totalNum2 = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);
				if(branchList2!=null){
					dataList.addAll(branchList2);
					totalNum = totalNum + totalNum2;
				}
			}
			//组装返回数据
			menudefMap.put("returnList", dataList);
			menudefMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询菜单信息失败   " + this.getClass() + ".queryMenudef");
			throw se;
		} catch (Exception e) {
			logger.error("查询菜单信息失败   " + this.getClass() + ".queryMenudef");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询菜单信息操作失败", e);
		}
		return menudefMap;
	}
	/**
	 * 
	 * @Title: addMenudef
	 * @Description: 新增菜单
	 * @param bodyMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> addMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String bmfId = BaseUtil.createUUID();//菜单uuid
			String bmfLegal = (String) bodyMap.get("legalPersonId");//法人id
			String bmfCode = (String) (bodyMap.get("bmfCode")==null?"":bodyMap.get("bmfCode"));//菜单编号
			String bmfName = (String) (bodyMap.get("bmfName")==null?"":bodyMap.get("bmfName"));//菜单名称
			String bmfUrl = (String) (bodyMap.get("bmfUrl")==null?"":bodyMap.get("bmfUrl"));//菜单链接
			String bmfLevel = (String) (bodyMap.get("bmfLevel")==null?"":bodyMap.get("bmfLevel"));//单层级 1:一级菜单，2:二级菜单，3:三级菜单
			String bmfParentid = (String) (bodyMap.get("bmfParentid")==null?"":bodyMap.get("bmfParentid"));//父菜单ID
			String bmfStt = (String) (bodyMap.get("bmfStt")==null?"":bodyMap.get("bmfStt"));//状态：0:正常，1:冻结
//			String bmfSeqNo = (String) (bodyMap.get("bmfSeqNo")==null?"":bodyMap.get("bmfSeqNo"));//菜单顺序号,表示在同一级别菜单中所显示的顺序
			int bmfSeqno = Integer.parseInt((bodyMap.get("bmfSeqno")==null?"1":(String)bodyMap.get("bmfSeqno")));//菜单顺序号,表示在同一级别菜单中所显示的顺序
			String bmfDesc = (String) (bodyMap.get("bmfDesc")==null?"":bodyMap.get("bmfDesc"));//菜单描述
			String bmfChannel = (String) (bodyMap.get("bmfChannel")==null?"":bodyMap.get("bmfChannel"));//系统标识：1001：柜面 1002：内管
			String bmfProperties = (String) (bodyMap.get("bmfProperties")==null?"":bodyMap.get("bmfProperties"));//菜单性质
			String bmfNote1 = (String) (bodyMap.get("bmfNote1")==null?"":bodyMap.get("bmfNote1"));//备用字段1
			String bmfNote2 = (String) (bodyMap.get("bmfNote2")==null?"":bodyMap.get("bmfNote2"));//备用字段2
			String bmfimageName = (String) (bodyMap.get("bmfimageName")==null?"":bodyMap.get("bmfimageName"));//备用字段2
			
			String bmfUrltype = (String) (bodyMap.get("bmfUrltype")==null?"":bodyMap.get("bmfUrltype"));//渠道类型
			String bmfLoginflag = (String) (bodyMap.get("bmfLoginflag")==null?"":bodyMap.get("bmfLoginflag"));//登陆状态
			String bmfTablayout = (String) (bodyMap.get("bmfTablayout")==null?"":bodyMap.get("bmfTablayout"));//标签布局
			String bmfUpdatetime = DateUtil.getServerTime("yyyyMMddHHmmss");//更新时间

			
			parmIn.put("bmfId", bmfId);
			parmIn.put("bmfLegal", bmfLegal);
			parmIn.put("bmfCode", bmfCode);
			parmIn.put("bmfName", bmfName);
			parmIn.put("bmfUrl", bmfUrl);
			parmIn.put("bmfLevel", bmfLevel);
			parmIn.put("bmfParentid", bmfParentid);
			parmIn.put("bmfStt", bmfStt);
			parmIn.put("bmfSeqno", bmfSeqno);
			parmIn.put("bmfDesc", bmfDesc);
			parmIn.put("bmfChannel", bmfChannel);
			parmIn.put("bmfProperties", bmfProperties);
			parmIn.put("bmfNote1", bmfNote1);
			parmIn.put("bmfNote2", bmfNote2);
			parmIn.put("bmfimageName", bmfimageName);
			
			parmIn.put("bmfUrltype", bmfUrltype);
			parmIn.put("bmfLoginflag", bmfLoginflag);
			parmIn.put("bmfTablayout", bmfTablayout);
			parmIn.put("bmfUpdatetime", bmfUpdatetime);

			//新增菜单
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.bmamenudef.insertMenudef",parmIn);
		} catch (ServiceException se) {
			logger.error("新增菜单信息失败   " + this.getClass() + ".addMenudef");
			throw se;
		} catch (Exception e) {
			logger.error("新增菜单信息失败   " + this.getClass() + ".addMenudef");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"新增菜单信息操作失败", e);
		}
		return menudefMap;
	}
	/**
	 * 
	 * @Title: updateMenudef
	 * @Description: 修改菜单信息
	 * @param bodyMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> updateMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String bmfId = (String) (bodyMap.get("bmfId")==null?"":bodyMap.get("bmfId"));//菜单id
			String bmfCode = (String) (bodyMap.get("bmfCode")==null?"":bodyMap.get("bmfCode"));//菜单编号
			String bmfName = (String) (bodyMap.get("bmfName")==null?"":bodyMap.get("bmfName"));//菜单名称
			String bmfUrl = (String) (bodyMap.get("bmfUrl")==null?"":bodyMap.get("bmfUrl"));//菜单链接
			String bmfLevel = (String) (bodyMap.get("bmfLevel")==null?"":bodyMap.get("bmfLevel"));//单层级 1:一级菜单，2:二级菜单，3:三级菜单
			String bmfParentid = (String) (bodyMap.get("bmfParentid")==null?"":bodyMap.get("bmfParentid"));//父菜单ID
			String bmfStt = (String) (bodyMap.get("bmfStt")==null?"":bodyMap.get("bmfStt"));//状态：0:正常，1:冻结
			int bmfSeqno = Integer.parseInt((bodyMap.get("bmfSeqno")==null?"1":(String)bodyMap.get("bmfSeqno")));//菜单顺序号,表示在同一级别菜单中所显示的顺序
			String bmfDesc = (String) (bodyMap.get("bmfDesc")==null?"":bodyMap.get("bmfDesc"));//菜单描述
			String bmfChannel = (String) (bodyMap.get("bmfChannel")==null?"":bodyMap.get("bmfChannel"));//系统标识：1001：柜面 1002：内管
			String bmfProperties = (String) (bodyMap.get("bmfProperties")==null?"":bodyMap.get("bmfProperties"));//菜单性质
			String bmfNote1 = (String) (bodyMap.get("bmfNote1")==null?"":bodyMap.get("bmfNote1"));//备用字段1
			String bmfNote2 = (String) (bodyMap.get("bmfNote2")==null?"":bodyMap.get("bmfNote2"));//备用字段2
			String bmfimageName = (String) (bodyMap.get("bmfimageName")==null?"":bodyMap.get("bmfimageName"));//备用字段2
			
			String bmfUrltype = (String) (bodyMap.get("bmfUrltype")==null?"":bodyMap.get("bmfUrltype"));//渠道类型
			String bmfLoginflag = (String) (bodyMap.get("bmfLoginflag")==null?"":bodyMap.get("bmfLoginflag"));//登陆状态
			String bmfTablayout = (String) (bodyMap.get("bmfTablayout")==null?"":bodyMap.get("bmfTablayout"));//标签布局
			String bmfUpdatetime = DateUtil.getServerTime("yyyyMMddHHmmss");//更新时间
			
			parmIn.put("bmfId", bmfId);
			parmIn.put("bmfCode", bmfCode);
			parmIn.put("bmfName", bmfName);
			parmIn.put("bmfUrl", bmfUrl);
			parmIn.put("bmfLevel", bmfLevel);
			parmIn.put("bmfParentid", bmfParentid);
			parmIn.put("bmfStt", bmfStt);
			parmIn.put("bmfSeqno", bmfSeqno);
			parmIn.put("bmfDesc", bmfDesc);
			parmIn.put("bmfChannel", bmfChannel);
			parmIn.put("bmfProperties", bmfProperties);
			parmIn.put("bmfNote1", bmfNote1);
			parmIn.put("bmfNote2", bmfNote2);
			parmIn.put("bmfimageName", bmfimageName);
			
			parmIn.put("bmfUrltype", bmfUrltype);
			parmIn.put("bmfLoginflag", bmfLoginflag);
			parmIn.put("bmfTablayout", bmfTablayout);
			parmIn.put("bmfUpdatetime", bmfUpdatetime);

			//修改菜单
			myBatisDaoSysDao.update("mybatis.mapper.single.table.bmamenudef.updateMenudef",parmIn);
		} catch (ServiceException se) {
			logger.error("菜单修改失败   " + this.getClass() + ".updateMenudef");
			throw se;
		} catch (Exception e) {
			logger.error("菜单修改失败   " + this.getClass() + ".updateMenudef");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"菜单修改操作失败", e);
		}
		return menudefMap;
	}
	/**
	 * 
	 * @Title: deleteMenudef
	 * @Description: 删除菜单
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> deleteMenudef(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menudefMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			Map<String, Object> parmInrel = new HashMap<String,Object>();
			
			String bmfId = (String) (bodyMap.get("bmfId")==null?"":bodyMap.get("bmfId"));//菜单id
			
			parmIn.put("bmfId", bmfId);
			parmInrel.put("bmrlMenuId", bmfId);
			//删除菜单信息
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmamenudef.deleteMenudef",parmIn);
			//删除菜单角色对应关系
			myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmamenurolerel.deleteMenuRoleRel",parmInrel);
		} catch (ServiceException se) {
			logger.error("菜单信息删除失败   " + this.getClass() + ".deleteMenudef");
			throw se;
		} catch (Exception e) {
			logger.error("菜单信息删除失败   " + this.getClass() + ".deleteMenudef");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"菜单信息删除操作失败", e);
		}
		return menudefMap;
	}
	/**
	 * 
	 * @Title: addMenuRole
	 * @Description: 设置菜单角色关联
	 * @param headMap  报文头
	 * @param bodyMap  报文体
	 * @return  Map<String, Object>
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> addMenuRole(Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> menuRoleRelMap = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> bmrlMenuList = new ArrayList<Map<String,Object>>();
			bmrlMenuList = (List<Map<String, Object>>) bodyMap.get("bodyList");
			for (int i = 0; i < bmrlMenuList.size(); i++) {
				String bmrlId = BaseUtil.createUUID();//菜单角色关联uuid
				bmrlMenuList.get(i).put("bmrlId", bmrlId);
				bmrlMenuList.get(i).put("bmrlNote1", "");
				bmrlMenuList.get(i).put("bmrlNote2", "");
			}
			
			//设置菜单角色关联
			myBatisDaoSysDao.insertBatchList("mybatis.mapper.single.table.bmamenurolerel.insertMenuRoleRel", bmrlMenuList);

		} catch (ServiceException se) {
			logger.error("设置菜单角色关联失败   " + this.getClass() + ".setMenuRole");
			throw se;
		} catch (Exception e) {
			logger.error("设置菜单角色关联失败   " + this.getClass() + ".setMenuRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"设置菜单角色关联操作失败", e);
		}
		return menuRoleRelMap;
	}
	/**
	 * 查询菜单角色关联
	 */
	public List<Map<String, Object>> queryMenuRole(String bmrlMenuId,
			String bmrlRoleId) throws ServiceException {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("bmrlMenuId", bmrlMenuId);
		parmIN.put("bmrlRoleId", bmrlRoleId);
		try {
			resultList=myBatisDaoSysDao.selectList("mybatis.mapper.single.table.bmamenurolerel.queryMenuRoleRel", parmIN);
		} catch (ServiceException se) {
			logger.error("查询菜单角色关联失败   " + this.getClass() + ".queryMenuRole");
			throw se;
		} catch (Exception e) {
			logger.error("查询菜单角色关联失败    " + this.getClass() + ".queryMenuRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询菜单角色关联失败 ", e);
		}
		return resultList;
	}
	/**
	 * 查询菜单角色关联笔数
	 */
	public int queryMenuRoleNum(String bmrlMenuId, String bmrlRoleId)
			throws ServiceException {
		int totalNum=0;
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("bmrlMenuId", bmrlMenuId);
		parmIN.put("bmrlRoleId", bmrlRoleId);
		try {
			totalNum=myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.bmamenurolerel.queryMenuRoleRelNum", parmIN);
		} catch (ServiceException se) {
			logger.error("查询菜单角色关联失败   " + this.getClass() + ".queryMenuRole");
			throw se;
		} catch (Exception e) {
			logger.error("查询菜单角色关联失败    " + this.getClass() + ".queryMenuRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询菜单角色关联失败 ", e);
		}
		return totalNum;
	}
	/**
	 * 删除菜单角色关联
	 */
	public int deleteMenuRole(String bmrlMenuId,
			String bmrlRoleId) throws ServiceException {
		int totalNum=0;
		Map<String, Object> parmIN=new HashMap<String, Object>();
		parmIN.put("bmrlMenuId", bmrlMenuId);
		parmIN.put("bmrlRoleId", bmrlRoleId);
		try {
			totalNum=myBatisDaoSysDao.delete("mybatis.mapper.single.table.bmamenurolerel.deleteMenuRoleRel", parmIN);
		} catch (ServiceException se) {
			logger.error("删除菜单角色关联失败   " + this.getClass() + ".deleteMenuRole");
			throw se;
		} catch (Exception e) {
			logger.error("删除菜单角色关联失败    " + this.getClass() + ".deleteMenuRole");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"删除菜单角色关联失败", e);
		}
		return totalNum;
	}
	
}
