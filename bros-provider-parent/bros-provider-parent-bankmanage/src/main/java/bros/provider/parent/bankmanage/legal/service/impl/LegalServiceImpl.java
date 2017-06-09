package bros.provider.parent.bankmanage.legal.service.impl;

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
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.legal.service.ILegalService;

/** 
 * @ClassName:LegalServiceImpl
 * @Description:法人管理接口
 * @author  haojinhui
 * @date 2016年7月7日 下午3:15:04 
 * @version V1.0  
 */
@Repository(value = "legalService")
public class LegalServiceImpl implements ILegalService{
	
	private static final Logger logger = LoggerFactory.getLogger(LegalServiceImpl.class);

	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: queryLegal
	 * @Description: 查询法人
	 * @param bodyMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryLegal(Map<String,Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> LegalMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String cllCode = (String) (bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode"));//法人编号
			String cllStatus = (String) (bodyMap.get("cllStatus")==null?"":bodyMap.get("cllStatus"));//法人状态
					
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllStatus", cllStatus);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			//查询法人信息列表
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.cmalegal.queryLegal", parmIn);
			//查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);

			//组装返回数据
			LegalMap.put("returnList", dataList);
			LegalMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw se;
		} catch (Exception e) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询法人信息操作失败", e);
		}
		return LegalMap;
	}
	
	/**
	 * 分页查询法人信息
	 */
	public Map<String, Object> queryLegalPage(String cllCode, String cllStatus,
			int pageNo, int pageSize) throws ServiceException {
		int totalNum=0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			List<Map<String, Object>> returnList  =  new ArrayList<Map<String, Object>>();
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllStatus", cllStatus);
			//查询法人信息列表
			returnList = myBatisDaoSysDao.selectListPage("mybatis.mapper.single.table.cmalegal.queryLegal", parmIn,pageNo,pageSize);
			//查询总条数
			totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);
			//组装返回数据
			resultMap.put("returnList", returnList);
			resultMap.put("totalNum", totalNum+"");
		} catch (ServiceException se) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw se;
		} catch (Exception e) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询法人信息操作失败", e);
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: querytreeLegal
	 * @Description: 查询法人树形数据
	 * @param cllCode  法人编号
	 * @param cllStatus  法人状态
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> querytreeLegal(String cllCode,String cllStatus)
			throws ServiceException {
		Map<String, Object> LegalMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
					
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllStatus", cllStatus);
			
			List<Map<String, Object>> dataList  =  new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> branchList2  =  new ArrayList<Map<String, Object>>();	
			//查询法人信息列表
			dataList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.cmalegal.queryLegal", parmIn);
			//查询总条数
			int totalNum = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);
			String cllParentid="";
			if(dataList!=null && !cllCode.equals("")){
				cllParentid = (String) dataList.get(0).get("cllId");
			}
			if(cllParentid != null && cllParentid!=""){
				parmIn.put("cllParentid", cllParentid);
				//查询机构信息列表
				branchList2 = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.cmalegal.queryLegal",parmIn);			
				//查询总条数
				int totalNum2 = myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.cmalegal.queryLegalNum", parmIn);
				if(branchList2!=null){
					if(null==dataList){
						dataList = new ArrayList<Map<String, Object>>();
					}
					dataList.addAll(branchList2);
					
					totalNum = totalNum + totalNum2;
				}
				
			}
			
			//组装返回数据
			LegalMap.put("returnList", dataList);
			LegalMap.put("totalNum", totalNum);
		} catch (ServiceException se) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw se;
		} catch (Exception e) {
			logger.error("查询法人信息失败   " + this.getClass() + ".queryLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询法人信息操作失败", e);
		}
		return LegalMap;
	}
	/**
	 * 
	 * @Title: addLegal
	 * @Description: 新增法人
	 * @param bodyMap  上送报文
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> addLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> LegalMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			String cllId = BaseUtil.createUUID();//法人uuid
			String cllCode = (String) (bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode"));//法人编号（唯一索引）
			String cllName = (String) (bodyMap.get("cllName")==null?"":bodyMap.get("cllName"));//法人名称
			String cllAddress = (String) (bodyMap.get("cllAddress")==null?"":bodyMap.get("cllAddress"));//法人所在地
			String cllAgentaddress = (String) (bodyMap.get("cllAgentaddress")==null?"":bodyMap.get("cllAgentaddress"));//代报机构所在地
			String cllEconomy = (String) (bodyMap.get("cllEconomy")==null?"":bodyMap.get("cllEconomy"));//出资人经济成份类别
			String cllIpostatus = (String) (bodyMap.get("cllIpostatus")==null?"":bodyMap.get("cllIpostatus"));//上市状况
			String cllStatus = "0";//法人状态
			String cllRepresentative = (String) (bodyMap.get("cllRepresentative")==null?"":bodyMap.get("cllRepresentative"));//法人代表
			String cllParentid = (String) (bodyMap.get("cllParentid")==null?"":bodyMap.get("cllParentid"));//直属上级ID
			parmIn.put("cllId", cllId);
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllName", cllName);
			parmIn.put("cllAddress", cllAddress);
			parmIn.put("cllAgentaddress", cllAgentaddress);
			parmIn.put("cllEconomy", cllEconomy);
			parmIn.put("cllIpostatus", cllIpostatus);
			parmIn.put("cllStatus", cllStatus);
			parmIn.put("cllRepresentative", cllRepresentative);
			parmIn.put("cllParentid", cllParentid);
			//新增法人
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.cmalegal.addLegal",parmIn);
		} catch (ServiceException se) {
			logger.error("新增法人信息失败   " + this.getClass() + ".addLegal");
			throw se;
		} catch (Exception e) {
			logger.error("新增法人信息失败   " + this.getClass() + ".addLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"新增法人信息操作失败", e);
		}
		return LegalMap;
	}
	/**
	 * 
	 * @Title: updateLegal
	 * @Description: 修改法人信息
	 * @param bodyMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> updateLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> LegalMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String cllCode = (String) (bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode"));//法人编号（唯一索引）
			String cllName = (String) (bodyMap.get("cllName")==null?"":bodyMap.get("cllName"));//法人名称
			String cllAddress = (String) (bodyMap.get("cllAddress")==null?"":bodyMap.get("cllAddress"));//法人所在地
			String cllAgentaddress = (String) (bodyMap.get("cllAgentaddress")==null?"":bodyMap.get("cllAgentaddress"));//代报机构所在地
			String cllEconomy = (String) (bodyMap.get("cllEconomy")==null?"":bodyMap.get("cllEconomy"));//出资人经济成份类别
			String cllIpostatus = (String) (bodyMap.get("cllIpostatus")==null?"":bodyMap.get("cllIpostatus"));//上市状况
			String cllStatus = (String) (bodyMap.get("cllStatus")==null?"":bodyMap.get("cllStatus"));//法人状态
			String cllRepresentative = (String) (bodyMap.get("cllRepresentative")==null?"":bodyMap.get("cllRepresentative"));//法人代表
			String cllParentid = (String) (bodyMap.get("cllParentid")==null?"":bodyMap.get("cllParentid"));//直属上级ID
			
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllName", cllName);
			parmIn.put("cllAddress", cllAddress);
			parmIn.put("cllAgentaddress", cllAgentaddress);
			parmIn.put("cllEconomy", cllEconomy);
			parmIn.put("cllIpostatus", cllIpostatus);
			parmIn.put("cllStatus", cllStatus);
			parmIn.put("cllRepresentative", cllRepresentative);
			parmIn.put("cllParentid", cllParentid);

			//修改法人
			myBatisDaoSysDao.update("mybatis.mapper.single.table.cmalegal.updateLegal",parmIn);
		} catch (ServiceException se) {
			logger.error("法人修改失败   " + this.getClass() + ".updateLegal");
			throw se;
		} catch (Exception e) {
			logger.error("法人修改失败   " + this.getClass() + ".updateLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"法人修改操作失败", e);
		}
		return LegalMap;
	}
	/**
	 * 
	 * @Title: deleteLegal
	 * @Description: 删除法人
	 * @param contextMap  上送报文
	 * @return 
	 * @return Map<String,Object>
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> deleteLegal(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> LegalMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			String cllCode = (String) (bodyMap.get("cllCode")==null?"":bodyMap.get("cllCode"));//法人编号（唯一索引）
			String cllStatus = "2";//法人状态（唯一索引）
			
			parmIn.put("cllCode", cllCode);
			parmIn.put("cllStatus", cllStatus);
			//删除法人信息
			myBatisDaoSysDao.update("mybatis.mapper.single.table.cmalegal.deleteLegal",parmIn);
		} catch (ServiceException se) {
			logger.error("法人信息删除失败   " + this.getClass() + ".deleteLegal");
			throw se;
		} catch (Exception e) {
			logger.error("法人信息删除失败   " + this.getClass() + ".deleteLegal");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"法人信息删除操作失败", e);
		}
		return LegalMap;
	}

}
