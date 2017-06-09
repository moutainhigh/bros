package bros.p.cst.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.BaseUtil;
import bros.p.cst.facade.service.IPCstManageServiceFacade;
import bros.provider.bankmanage.constants.BankManageErrorCodeConstants;
import bros.provider.parent.bankmanage.constants.AppConstants;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.bankmanage.cst.service.ICstManageService;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;

/**
 * 
 * @ClassName: PCstManageServiceFacadeImpl 
 * @Description: 渠道客户信息管理对外暴露接口实现
 * @author MacPro 
 * @date 2016年9月12日 下午3:05:53 
 * @version 1.0
 */
@Component("pcstManageServiceFacade")
public class PCstManageServiceFacadeImpl implements IPCstManageServiceFacade{
	
	/**
	 * logger对象
	 */
	private static final  Logger logger = LoggerFactory.getLogger(PCstManageServiceFacadeImpl.class);
	

	/**
	 * 渠道客户信息服务
	 */
	@Autowired
	private ICstManageService cstManageService;
	/**
	 * 客户服务实现
	 */
	@Autowired
	private ICustomervoiceService customervoiceService;
	
	/**
	 * 
	 * @Title: queryCstInfo 
	 * @Description: 客户信息查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	public ResponseEntity queryCstInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		
		Map<String,Object> respMap = null;
		
		try{
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);		// 法人Id
			String certType = (String) bodyMap.get("certType");		//证件类型
			String certNo = (String) bodyMap.get("certNo");			//证件号码
			String nameCn = (String) bodyMap.get("nameCn");			//客户姓名
			String payPhoneNo = (String) bodyMap.get("payPhoneNo");	//手机号
			String channel = (String) bodyMap.get("channel");	    //渠道标识
			
			
			// 客户信息查询
			respMap = cstManageService.queryCstInfoByCstNo(legalId,certType, certNo, nameCn);
			
			if(respMap == null){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0055,"没有符合条件的客户信息,查询无记录");
			}
			
			String cstId = (String) respMap.get("cstId");			// 客户id
			
			// 渠道客户信息查询
			List<Map<String,Object>> cstInfoChannelList = cstManageService.queryCstInfoChannelInfo(cstId, payPhoneNo, channel);
			
			respMap.put("cstChannelList", cstInfoChannelList);
			
			ResponseEntity resultEntity = new ResponseEntity(respMap);
			
			return resultEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0056, "客户信息查询失败", ex);
		}
		
	}
	
	/**
	 * 
	 * @Title: saveCstInfo 
	 * @Description: 保存渠道客户签约信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,ServiceException.class})
	public ResponseEntity saveCstInfo(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		
		try{
			Map <String,Object>cstChannelMap = new HashMap<String, Object>();
			List<Map<String,Object>> safetoolList = (List<Map<String, Object>>) bodyMap.get("safetoolList");		// 安全工具
			List<Map<String,Object>> signChannelList = (List<Map<String, Object>>) bodyMap.get("signChannelList");  // 渠道标识列表
			
			String id = BaseUtil.createUUID();																		// UUID
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));				// 法人ID
			String cstNo = bodyMap.get("cstNo") == null ? "" : bodyMap.get("cstNo").toString();						// 客户号
			String cstType = bodyMap.get("cstType") == null ? "" : bodyMap.get("cstType").toString();				// 客户类型
			String alias = bodyMap.get("cstNo") == null ? "" : bodyMap.get("cstNo").toString();						// 别名（默认取客户号）
			String timeOut = bodyMap.get("timeOut") == null ? "" : bodyMap.get("timeOut").toString();				// 别名（默认取客户号）
			String passWord = bodyMap.get("passWord") == null ? "" : bodyMap.get("passWord").toString();			// 密码
			String pretentInfo = bodyMap.get("pretentInfo") == null ? "" : bodyMap.get("pretentInfo").toString();	// 预留信息
			String branchNo = bodyMap.get("branchNo") == null ? "" : bodyMap.get("branchNo").toString();			// 机构编号
			String stt = bodyMap.get("stt") == null ? "" : bodyMap.get("stt").toString();		    				// 状态 0：正常；1：注销；2：暂时冻结；3：用户停用；4：永久冻结
			String machineCode1 = bodyMap.get("mobileNo") == null ? "" : bodyMap.get("mobileNo").toString();		// 手机号1
			String machineCode2 = bodyMap.get("mobileNo") == null ? "" : bodyMap.get("mobileNo").toString();		// 手机号2
			String machineCode3 = bodyMap.get("mobileNo") == null ? "" : bodyMap.get("mobileNo").toString();		// 手机号3
			
			String certType = bodyMap.get("certType") == null ? "" : bodyMap.get("certType").toString();			// 证件类型
			String certNo = bodyMap.get("certNo") == null ? "" : bodyMap.get("certNo").toString();					// 证件号码
			String nameCn = bodyMap.get("custName") == null ? "" : bodyMap.get("custName").toString();			// 客户姓名
			
			
			//查询客户信息
			Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
			if(cstInfoMap == null || cstInfoMap.size()<=0){
				throw new ServiceException(BankManageErrorCodeConstants.PBAE0177,"客户信息不存在");
			}
			String cstId = String.valueOf(cstInfoMap.get("cstId"));
			
			//cstChannelMap.put("id",id);
			cstChannelMap.put("cstId",cstId);
			//cstChannelMap.put("legalId",legalId);
			//cstChannelMap.put("cstNo",cstNo);
			cstChannelMap.put("cstType",cstType);
			cstChannelMap.put("alias",alias);
			cstChannelMap.put("timeOut",timeOut);
			cstChannelMap.put("passWord",passWord);
			cstChannelMap.put("pretentInfo",pretentInfo);
			cstChannelMap.put("branchNo",branchNo);
			cstChannelMap.put("stt",stt);
			cstChannelMap.put("machineCode1",machineCode1);
			cstChannelMap.put("machineCode2",machineCode2);
			cstChannelMap.put("machineCode3",machineCode3);
			
			//获取内管需要开通的渠道 和 该渠道的登录密码
			for(int i = 0; i < signChannelList.size(); i++){
				Map<String, Object> map = signChannelList.get(i);
				String channel = (String) map.get("signChannel");
				
				// 验证昵称
				String checkExist = cstManageService.checkAlias(cstId, alias, channel);
				if(AppConstants.FAIL.equals(checkExist)){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0009,"客户别名已存在");
				}
				

				// 查询渠道签约信息
				List<Map<String, Object>> resultList = cstManageService.queryCstInfoChannelInfo(cstId, "", channel);
				if(resultList != null && resultList.size() == 2){
					throw new ServiceException(BankmanageErrorCodeConstants.PPBM0011,"该客户已开通全部渠道");
				}
				
				// 检查手机号是否可用
				List<Map<String,Object>> mobileList = cstManageService.queryCstInfByMachine(machineCode1, channel);
				if(mobileList != null){
					for(Map<String,Object> listMob:mobileList){
						if(certType.equals(listMob.get("certType")) && certNo.equals(listMob.get("certNo")) && machineCode1.equals(listMob.get("mobile1"))){
							throw new ServiceException(BankmanageErrorCodeConstants.PPBM0013,"输入的手机号码已经存在");
						}
					}
				}
				
				if("EB".equals(channel)){			// 网银
					// 登录密码
					cstChannelMap.put("passWord", map.get("logonPassword"));
					// 签约渠道
					cstChannelMap.put("channel", map.get("signChannel"));
					// 保存渠道客户签约信息
					
					cstManageService.saveCstInfoChannel(cstChannelMap);
				}else if("MB".equals(channel)){		// 手机银行
					// 登录密码
					cstChannelMap.put("logonPassword", map.get("logonPassword"));
					// 签约渠道
					cstChannelMap.put("channel", map.get("signChannel"));
					// 保存渠道客户签约信息
					cstManageService.saveCstInfoChannel(cstChannelMap);
				}
			}
			ResponseEntity resultEntity = new ResponseEntity();
			
			return resultEntity;
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(BankManageErrorCodeConstants.PBAE0057, "渠道客户签约失败", ex);
		}
	}
}
