package bros.provider.parent.custmanage.transferManage.service.impl;

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
import bros.provider.parent.custmanage.constants.CustmanageConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.transferManage.service.IEletoSingleService;
/**
 * 
 * @ClassName: EletoSingleServiceImpl 
 * @Description: 电子回单
 * @author 高永静
 * @date 2016年11月3日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "eletoSingleService")
public class EletoSingleServiceImpl implements IEletoSingleService {
	private static final  Logger logger = LoggerFactory.getLogger(EletoSingleServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;
	/**
	 * 
	 * @Title: addEletoSingleMethod
	 * @Description: 添加电子回单
	 * @param eletoReceiptInfo 电子回单信息
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void addEletoSingleMethod(Map<String, Object> tranFlowNoMap)
			throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String, Object>();
			String flowNo = (String)tranFlowNoMap.get("gblflowSeq");        
			String cstNo = (String)tranFlowNoMap.get("cstNo");          
			String channel = (String)tranFlowNoMap.get("channel");        
			String legalId = (String)tranFlowNoMap.get("legalCode");        
			String payAccNo = (String)tranFlowNoMap.get("payAccNo");      
			String payAccName = (String)tranFlowNoMap.get("payAccName");     
			String payBranchNo = (String)tranFlowNoMap.get("payBranchNo");    
			String resiveAccNo = (String)tranFlowNoMap.get("resiveAccNo");   
			String resiveAccName = (String)tranFlowNoMap.get("resiveAccName");  
			String resiveBankNo = (String)tranFlowNoMap.get("resiveBankNo");   
			String resiveBankName = (String)tranFlowNoMap.get("resiveBankName"); 
			String transType = (String)tranFlowNoMap.get("note1");     
			String currency = (String)tranFlowNoMap.get("currency");       
			String transAmt = tranFlowNoMap.get("transAmt") + "";       
			String feeAmt = tranFlowNoMap.get("feeAmt") + "";         
			String security = (String)tranFlowNoMap.get("security");       
			String transTime = (String)tranFlowNoMap.get("transStartTime");      
			String tranStt = (String)tranFlowNoMap.get("transtt");        
			String purpose = (String)tranFlowNoMap.get("purpose");        
			String note1 = (String)tranFlowNoMap.get("note3");          
			String note2 = (String)tranFlowNoMap.get("note2"); 
			String id = BaseUtil.createUUID();  // 
			String randomAuthCode=BaseUtil.createUUID();
			param.put("id", id);
			param.put("flowNo", flowNo);
			param.put("cstNo", cstNo);
			param.put("channel", channel);
			param.put("legalId", legalId);
			param.put("payAccNo", payAccNo);
			param.put("payAccName", payAccName);
			param.put("payBranchNo", payBranchNo);
			param.put("resiveAccNo", resiveAccNo);
			param.put("resiveAccName", resiveAccName);
			param.put("resiveBankNo", resiveBankNo);
			param.put("resiveBankName", resiveBankName);
			param.put("transType", transType);
			param.put("currency", currency);
			param.put("transAmt", transAmt);
			param.put("feeAmt", feeAmt);
			param.put("security", security);
			param.put("transTime", transTime);
			param.put("tranStt", tranStt);
			param.put("purpose", purpose);
			param.put("authCode", randomAuthCode);//电子回单验证码
			param.put("printNum", CustmanageConstants.DEFAULT_PRINTNUM);//打印次数
			param.put("note1", note1);
			param.put("note2", note2);
			myBatisDaoSysDao.insertOne("mybatis.mapper.single.table.tpreletosingle.insertEletoSingle",param);
		}catch(ServiceException e){
			logger.error("添加电子回单失败 " + this.getClass() + ".addEletoSingleMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("添加电子回单失败 " + this.getClass() + ".addEletoSingleMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"添加电子回单失败", ex);
        }
	}
	/**
	 * 
	 * @Title: queryEleToSingleinfoMethod
	 * @Description: 查询电子回单信息
	 * @param legalId 法人id
	 * @param cstNo 客户号
	 * @param eleReceiptNo 回单号
	 * @param authCode 验证码
	 * @param transTimestamp 交易时间
	 * @param payAccNo 付款人账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> queryEleToSingleinfoMethod(String legalId,
			String cstNo, String eleReceiptNo, String authCode,
			String transTimestamp, String payAccNo) throws ServiceException {
		List<Map<String,Object>> eletoSingleInfoList = new ArrayList<Map<String,Object>>();
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("legalId", legalId);
			param.put("cstNo", cstNo);
			param.put("eleReceiptNo", eleReceiptNo);
			param.put("authCode", authCode);
			param.put("transTimestamp", transTimestamp);
			param.put("payAccNo", payAccNo);
			eletoSingleInfoList = myBatisDaoSysDao.selectList("mybatis.mapper.single.table.tpreletosingle.queryEleToSingleinfo",param);
			
		}catch(ServiceException e){
			logger.error("查询电子回单信息失败 " + this.getClass() + ".queryEleToSingleinfoMethod");
			throw e;
        } catch(Exception ex){
        	logger.error("查询电子回单信息失败 " + this.getClass() + ".queryEleToSingleinfoMethod");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"查询电子回单信息失败", ex);
        }
		return eletoSingleInfoList;
	}
	/**
	 * 
	 * @Title: updateEletoSinglePrintNumByObjectId
	 * @Description: 更新打印次数
	 * @param objectId  记录唯一id
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateEletoSinglePrintNumByObjectId(String objectId)
			throws ServiceException {
		try{
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("objectId", objectId);
			myBatisDaoSysDao.update("mybatis.mapper.single.table.tpreletosingle.updateEletoSinglePrintNumByObjectId",param);
		}catch(ServiceException e){
			logger.error("更新打印次数失败 " + this.getClass() + ".updateEletoSinglePrintNumByObjectId");
			throw e;
        } catch(Exception ex){
        	logger.error("更新打印次数失败 " + this.getClass() + ".updateEletoSinglePrintNumByObjectId");
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000,"更新打印次数失败", ex);
        }
	}
}
