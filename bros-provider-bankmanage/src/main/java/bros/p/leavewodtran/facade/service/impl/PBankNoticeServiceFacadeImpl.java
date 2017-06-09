package bros.p.leavewodtran.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DateUtil;
import bros.p.leavewodtran.facade.service.IPBankNoticeServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.customer.service.IBankNoticeService;

/**
 * 
 * @ClassName: PBankNoticeServiceFacadeImpl 
 * @Description: 银行资讯接口实现
 * @author lichen
 * @date 2016年10月18日 上午9:48:24 
 * @version 1.0
 */
@Component("pbankNoticeServiceFacade")
public class PBankNoticeServiceFacadeImpl implements IPBankNoticeServiceFacade {
	
	@Autowired
	private IBankNoticeService bankNoticeService;

	/**
	 * 
	 * @Title: queryBankNotice 
	 * @Description: 银行资讯查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> respMap = null;
		try{
			
			Integer pageNo = Integer.valueOf(bodyMap.get("pageNo").toString());
			Integer pageSize = Integer.valueOf(bodyMap.get("pageSize").toString());
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			bodyMap.put("legalId", legalId);
			
			// 查询银行资讯总记录数
			int totalNo = bankNoticeService.selectTotalNum(pageNo, pageSize, bodyMap);
			
			if(totalNo == 0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0145,"没有符合条件的信息,查询无记录");
			}
			
			// 查询银行资讯信息
			List<Map<String,Object>> bankManagementReturnList = bankNoticeService.queryBankNotice(pageNo, pageSize, bodyMap);
			
			respMap = new HashMap<String, Object>();
			respMap.put("totalNo", totalNo);
			respMap.put("bankManagementReturnList", bankManagementReturnList);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0146, "公告查询失败", ex);
		}
	}

	/**
	 * 
	 * @Title: addBankNotice 
	 * @Description: 银行资讯添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity addBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try {
			
			Map<String, Object> pranIn = new HashMap<String, Object>();
			
			String uuid = BaseUtil.createUUID();                                //UUID
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));                    //法人ID
			String operatechannel = (String)bodyMap.get("operatechannel");              		//渠道分类
			String title = (String)bodyMap.get("title");  						//标题
			String content = (String)bodyMap.get("content");    				//正文
			String type = (String)bodyMap.get("type");            				//公告类型
			String createtime = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);             //创建时间
			String releasetime = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);  			//发布时间
			String stt = (String)bodyMap.get("stt");							//公告状态	
			String tellerno = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO));;					//柜员ID	
						
			pranIn.put("uuid", uuid);
			pranIn.put("legalId", legalId);
			pranIn.put("operatechannel", operatechannel);
			pranIn.put("title", title);
			pranIn.put("content", content);
			pranIn.put("type", type);
			pranIn.put("createtime", createtime);
			pranIn.put("releasetime", releasetime);
			pranIn.put("stt", stt);
			pranIn.put("tellerno", tellerno);
			
			bankNoticeService.addBankNotice(pranIn);
			
			ResponseEntity resultEntity = new ResponseEntity();
			
			return resultEntity;
			
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0143,"银行资讯添加失败", e);
		}
	}

	/**
	 * 
	 * @Title: updateBankNotice 
	 * @Description: 银行资讯修改
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity updateBankNotice(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try {
			
			Map<String, Object> pranIn = new HashMap<String, Object>();
			
			String uuid = (String)bodyMap.get("uuid");                               //UUID
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));                    //法人ID
			String title = (String)bodyMap.get("title");  						//标题
			String content = (String)bodyMap.get("content");    				//正文
			String type = (String)bodyMap.get("type");            				//公告类型
			String stt = (String)bodyMap.get("stt");							//公告状态	
						
			pranIn.put("uuid", uuid);
			pranIn.put("legalId", legalId);
			pranIn.put("title", title);
			pranIn.put("content", content);
			pranIn.put("type", type);
			pranIn.put("stt", stt);
			
			bankNoticeService.updateBankNotice(pranIn);
			
			ResponseEntity resultEntity = new ResponseEntity();
			
			return resultEntity;
			
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0144,"银行资讯修改失败", e);
		}
	}

}
