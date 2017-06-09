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
import bros.p.leavewodtran.facade.service.IPLeaveWordTranServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.customer.service.ICstLeaveWordTranService;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;

/**
 * 
 * @ClassName: PLeaveWordTranServiceFacadeImpl 
 * @Description: 客户之声接口实现
 * @author lichen 
 * @date 2016年10月9日 上午10:53:55 
 * @version 1.0
 */
@Component("pleaveWordTranServiceFacade")
public class PLeaveWordTranServiceFacadeImpl implements IPLeaveWordTranServiceFacade{
	
	@Autowired
	private ICstLeaveWordTranService cstLeaveWordTranService;
	/**
	 * 客户服务
	 */
	@Autowired
	private ICustomervoiceService customervoiceService;
	
	
	/**
	 * 
	 * @Title: queryTranCstleaveWords 
	 * @Description: 客户之声查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryTranCstleaveWords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		Map<String,Object> respMap = null;
		try{
			
			Integer pageNo = Integer.valueOf(bodyMap.get("pageNo").toString());
			Integer pageSize = Integer.valueOf(bodyMap.get("pageSize").toString());
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
			
			Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
			if(cstInfoMap == null || cstInfoMap.size()<=0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0177,"客户信息不存在");
			}
			String cstId = String.valueOf(cstInfoMap.get("cstId"));
			
			bodyMap.put("cstId", cstId);
			
			// 查询客户之声总记录数
			int totalNo = cstLeaveWordTranService.selectTotalNum(pageNo, pageSize, bodyMap);
			
			if(totalNo == 0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0149,"没有符合条件的信息,查询无记录");
			}
			
			// 查询客户之声信息
			List<Map<String,Object>> customerVoiceList = cstLeaveWordTranService.queryTranCstleaveWords(pageNo, pageSize, bodyMap);
			
			respMap = new HashMap<String, Object>();
			respMap.put("totalNo", totalNo);
			respMap.put("customerVoiceList", customerVoiceList);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0139, "客户之声查询失败", ex);
		}
	}
	/**
	 * 
	 * @Title: addTranCstleaveWords 
	 * @Description: 客户之声留言添加
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity addTranCstleaveWords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		
		try {
			
			Map<String, Object> pranIn = new HashMap<String, Object>();
			
			String uuid = BaseUtil.createUUID();                                    //UUID
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));                        //法人ID
			String functionType = (String)bodyMap.get("functionType");              //功能类型
			String functionName = (String)bodyMap.get("functionName");  			//功能名称
			String customerVoiceInfo = (String)bodyMap.get("customerVoiceInfo");    //客户留言
			String linkMobileNo = (String)bodyMap.get("linkMobileNo");            	//手机号
			String cstNo = (String)bodyMap.get("cstNo");                          	//客户号
			String customerVoiceState = (String)bodyMap.get("customerVoiceState");  //客户之声处理状态
			String timestamp = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);//服务器时间
			String processDate = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);// 处理日期	
			String operatechannel = (String)bodyMap.get("operatechannel");  			            //渠道
			
			Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
			if(cstInfoMap == null || cstInfoMap.size()<=0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0177,"客户信息不存在");
			}
			String cstId = String.valueOf(cstInfoMap.get("cstId"));
			
			pranIn.put("uuid", uuid);
			pranIn.put("functionType", functionType);
			pranIn.put("functionName", functionName);
			pranIn.put("customerVoiceInfo", customerVoiceInfo);
			pranIn.put("linkMobileNo", linkMobileNo);
			pranIn.put("customerVoiceState", customerVoiceState);
			pranIn.put("timestamp", timestamp);
			pranIn.put("processDate", processDate);
			pranIn.put("operatechannel", operatechannel);
			pranIn.put("cstId", cstId);
			
			
			
			cstLeaveWordTranService.addTranCstleaveWords(pranIn);
			
			ResponseEntity resultEntity = new ResponseEntity();
			
			return resultEntity;
			
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0150,"客户之声添加失败", e);
		}
	}
	/**
	 * 
	 * @Title: updateCstleavewords 
	 * @Description: 客户之声处理状态更新
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity updateCstleavewords(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try {
			Map<String, Object> pranIn = new HashMap<String, Object>();
			
			String uuid = (String)bodyMap.get("uuid");
			String customerVoiceState = (String)bodyMap.get("customerVoiceState");
			String processDate = DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT_DB);// 处理日期	
			
			pranIn.put("uuid", uuid);
			pranIn.put("customerVoiceState", customerVoiceState);
			pranIn.put("processDate", processDate);
			
			
			cstLeaveWordTranService.updateCstleavewords(pranIn);
			
			ResponseEntity resultEntity = new ResponseEntity();
			
			return resultEntity;
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0140,"客户之声处理状态更新失败", e);
		}
	}
}
