package bros.p.custmanage.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.PersonConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPRecvPersonInfoServiceFacade;
import bros.provider.custmanage.constants.CustManageErrorCodeConstants;
import bros.provider.parent.custmanage.accountManage.service.IAccountManageService;
import bros.provider.parent.custmanage.customervoice.service.ICustomervoiceService;
import bros.provider.parent.custmanage.recvPersonInfo.service.IRecvPersonInfoService;
/**
 * 
 * @ClassName: PRecvPersonInfoServiceFacadeImpl 
 * @Description: 收款人信息维护接口
 * @author gaoyongjing 
 * @date 2016年9月21日 下午2:44:40 
 * @version 1.0
 */
@Component("precvPersonInfoServiceFacade")
public class PRecvPersonInfoServiceFacadeImpl implements
		IPRecvPersonInfoServiceFacade {
	/**
	 * 收款人名册服务
	 */
	@Autowired
	private IRecvPersonInfoService recvPersonInfoService;
	/**
	 * 账户管理服务
	 */
	@Autowired
	private IAccountManageService accountManageService;
	/**
	 * 客户服务
	 */
	@Autowired
	private ICustomervoiceService customervoiceService;
	/**
	 * 
	 * @Title: addRecvPersonInfoMethod
	 * @Description: 新增收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public ResponseEntity addRecvPersonInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		
		String accountName = (String) (bodyMap.get("accountName")==null?"":bodyMap.get("accountName"));//账号名称
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//账号
		String payeeType = (String) (bodyMap.get("payeeType")==null?"":bodyMap.get("payeeType"));//收款人类型
		String mobileNo = (String) (bodyMap.get("mobileNo")==null?"":bodyMap.get("mobileNo"));//手机号
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String transType = (String) (bodyMap.get("transType")==null?"":bodyMap.get("transType"));//转账类型
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		
		//step1 根据账号 、渠道平台客户号查询收款人信息是否存在
		Map<String,Object> recvPersonInfoMap = new HashMap<String, Object>();
		
		if(PersonConstants.ICOP_EB.equals(channel)){//个人网银
			recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByCstNoAccNoMethod(cstId, payeeType, mobileNo, accountNo, "");
			if(recvPersonInfoMap != null && recvPersonInfoMap.size() > 0){
				//step2 收款人主表信息存在，查询收款人汇路信息是否存在
				String payBookId = (String)recvPersonInfoMap.get("payBookId");
				Map<String,Object> subResult = recvPersonInfoService.querySubRecvPersonInfoMethod(payBookId, transType);
				if(subResult != null && subResult.size() > 0){
					//收款人信息已存在，不允许添加
					throw new ServiceException(CustManageErrorCodeConstants.PCUE0020,"收款人信息已存在，不允许添加");
				}
			}
		}else if(PersonConstants.ICOP_MB.equals(channel)){//手机银行
			recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByCstNoAccNoMethod(cstId, payeeType, mobileNo, accountNo, accountName);
			if(recvPersonInfoMap != null && recvPersonInfoMap.size() > 0){
				//收款人信息已存在，不允许添加
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0020,"收款人信息已存在，不允许添加");
			}
		}
		 
		
		if(payeeType.equals("1")){//如果是行内收款人，验证行内和账号户名一致性
//			如果是折，一本通，判断长度，10位，15位
			int accLength = accountNo.length();
			if(accLength != 10 && accLength != 15){
				
				if(!accountManageService.checkInnerAccountByAccNoMethod(accountNo)){//验证是否行内
					//如果非本行账号，也允许添加账号，是本行的就需要进行验证
					//throw new ServiceException(PersonErrorCodeConstants.EBPB2597,"收款人非本行账户，请核对后重新输入!");
				}else{//验证账号户名一致性
					Map<String, Object> accInfoMap = new HashMap<String,Object>();
					Map<String, Object> accResultMap = new HashMap<String,Object>();
					accInfoMap.put("currency", "01");//币种 01-人民币
					accInfoMap.put("accountNo", accountNo);//账号
					accResultMap = accountManageService.queryAccountInfoMethod(headMap,accInfoMap);
					if(accResultMap != null && accResultMap.size() > 0){
						String resAccountName = (String)accResultMap.get("accountName");
						if(!resAccountName.equals(accountName)){
							throw new ServiceException(CustManageErrorCodeConstants.PCUE0021,"收款账号和户名不一致,请重新输入!");
						}
					}else{
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0023,"账号不存在,请重新输入!");
					}
				}
			}else if(PersonConstants.ICOP_EB.equals(channel)
					&&(accLength==10 || accLength==15)){//个人网银要校验折和一本通的账户户名一致
				if(accLength==10){//商行定期一本通不能添加为收款人， 只有定期一本通
					throw new ServiceException(CustManageErrorCodeConstants.PCUE0022,"收款人账号不能为定期一本通");
				}
				Map<String, Object> accInfoMap = new HashMap<String,Object>();
				Map<String, Object> accResultMap = new HashMap<String,Object>();
				accInfoMap.put("accountNo", accountNo);//账号
				accResultMap = accountManageService.checkAccountInfoToHostMethod(headMap, accInfoMap);
				if(accResultMap != null && accResultMap.size() > 0){
					String custName = (String)accResultMap.get("custName");
					if(!custName.equals(accountName)){
						throw new ServiceException(CustManageErrorCodeConstants.PCUE0021,"收款账号和户名不一致,请重新输入!");
					}
				}else{
					throw new ServiceException(CustManageErrorCodeConstants.PCUE0023,"账号不存在,请重新输入!");
				}
			}
		}else if(payeeType.equals("3")){
			//校验客户信息是否存在
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("nameCn", accountName);
			param.put("payPhoneNo", mobileNo);
//			IMyBatisDaoSysService service = (IMyBatisDaoSysService) Context.getBean("myBatisDaoSysService");
//			Map<String,Object> result=service.selectOne("mybatis.mapper.pbcstinf.queryPbCstInfByPayPhoneNo",param);
//			Map<String,Object> result = new HashMap<String, Object>();
//			if(result==null){
//				throw new ServiceException(CustManageErrorCodeConstants.PCUE0024,"客户信息不存在");
//			}
//			List<Map<String, Object>> subPayBookList = new ArrayList<Map<String,Object>>();
//			Map<String, Object> map = new HashMap<String, Object>();
			bodyMap.put("transType", "08");
			bodyMap.put("comitrNo", PersonConstants.IBPS_BANK_NO);
		}else if(payeeType.equals("2")){
			if(accountManageService.checkInnerAccountByAccNoMethod(accountNo)){//验证是否行内
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0025,"您输入的账号为本行账号，请核对后重新输入!");
			}
		}
		 
		if(recvPersonInfoMap != null && recvPersonInfoMap.size() > 0){
			String payBookId = (String)recvPersonInfoMap.get("payBookId");
			bodyMap.put("payBookId", (String)recvPersonInfoMap.get("payBookId"));
			//主表信息存在更新主表信息
			recvPersonInfoService.updateRecvPersonInfoMethod(bodyMap);
			if(!"".equals(transType)){//转账类型不为空则添加子表信息
				String comitrNo = (String) (bodyMap.get("comitrNo")==null?"":bodyMap.get("comitrNo"));//联行行号
				recvPersonInfoService.addSubRecvPersonInfoMethod(payBookId, transType, comitrNo);
			}
		}else{
			String headPicName = (String) (bodyMap.get("headPicName")==null?"":bodyMap.get("headPicName"));//头像
			if("".equals(headPicName)){
				bodyMap.put("headPicName", "m1.png");
			}
			//如果别名为空，别名默认值为户名
			String accountAlias = (String) (bodyMap.get("accountAlias")==null?"":bodyMap.get("accountAlias"));//别名
			if("".equals(accountAlias)){
				bodyMap.put("accountAlias", accountName);
			}
			/*
			bodyMap.put("cstNo", cstNo);
			bodyMap.put("legalId", legalId);
			*/
			bodyMap.put("cstId", cstId);
			//step2 添加收款人信息
			recvPersonInfoService.addRecvPersonInfoMethod(bodyMap);
		}
		return responseEntity;
	}
	 
	/**
	 * 
	 * @Title: updateRecvPersonInfoMethod
	 * @Description: 修改收款人信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public ResponseEntity updateRecvPersonInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		String payBookId = (String) (bodyMap.get("payBookId")==null?"":bodyMap.get("payBookId"));//收款人记录唯一标识  
		String accountName = (String) (bodyMap.get("accountName")==null?"":bodyMap.get("accountName"));//账号名称
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//账号
		String payeeType = (String) (bodyMap.get("payeeType")==null?"":bodyMap.get("payeeType"));//收款人类型
		String mobileNo = (String) (bodyMap.get("mobileNo")==null?"":bodyMap.get("mobileNo"));//手机号
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道号
		String transType = (String) (bodyMap.get("transType")==null?"":bodyMap.get("transType"));//转账类型
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		
		
//		step1 收款人类型为手机银行收款人时，校验客户信息是否存在
		if(payeeType.equals("3")){
			//校验客户信息是否存在
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("nameCn", accountName);
			param.put("payPhoneNo", mobileNo);
//			IMyBatisDaoSysService service = (IMyBatisDaoSysService) Context.getBean("myBatisDaoSysService");
//			Map<String,Object> result=service.selectOne("mybatis.mapper.pbcstinf.queryPbCstInfByPayPhoneNo",param);
//			Map<String,Object> result = new HashMap<String, Object>();
//			if(result==null){
//				throw new ServiceException(CustManageErrorCodeConstants.PCUE0024,"客户信息不存在");
//			}
//			List<Map<String, Object>> subPayBookList = new ArrayList<Map<String,Object>>();
//			Map<String, Object> map = new HashMap<String, Object>();
		}
		
		//step2 根据payBookId查询收款人信息是否存在
		Map<String,Object> recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByPayBookIdMethod(payBookId);
		if(recvPersonInfoMap == null || recvPersonInfoMap.size() <= 0){
			//收款人信息不存在，不允许修改
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0019,"收款人信息不存在，不允许修改");
		}
		
		//step3 根据账号 、渠道平台客户号查询收款人信息是否存在
		if(PersonConstants.ICOP_EB.equals(channel)){//个人网银
			recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByCstNoAccNoMethod(cstId, payeeType, mobileNo, accountNo, "");
		}else if(PersonConstants.ICOP_MB.equals(channel)){//手机银行
			recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByCstNoAccNoMethod(cstId, payeeType, mobileNo, accountNo, accountName);
		}
		if(recvPersonInfoMap != null && recvPersonInfoMap.size() > 0){
			String payBookId2 = (String)recvPersonInfoMap.get("payBookId");
			if(!payBookId.equals(payBookId2)){
				//如果ID不一样，说明有两条记录，这是说明前台把一个收款人修改成了一个跟现有收款人信息一模一样的了
				throw new ServiceException(CustManageErrorCodeConstants.PCUE0020,"收款人信息已存在");
			}
		}	
		//step4 根据payBookId和transType查询收款人子信息是否存在
		Map<String,Object> subResult = recvPersonInfoService.querySubRecvPersonInfoMethod(payBookId, transType);
		String comitrNo = (String) (bodyMap.get("comitrNo")==null?"":bodyMap.get("comitrNo"));//联行行号
		if(subResult != null && subResult.size() > 0){
			//step5 更新收款人信息
			recvPersonInfoService.updateRecvPersonInfoMethod(bodyMap);
			if(!"".equals(transType)){
				//step6 更新子表信息
				recvPersonInfoService.updateSubRecvPersonInfoMethod(payBookId, transType, comitrNo);	
			}
		}else{
			//step5 更新收款人信息
			recvPersonInfoService.updateRecvPersonInfoMethod(bodyMap);
			if(!"".equals(transType)){//转账类型不为空则添加子表信息
				//step6 新增子表信息
				recvPersonInfoService.addSubRecvPersonInfoMethod(payBookId, transType, comitrNo);
			}
		}
		return responseEntity;
	}
	/**
	 * 
	 * @Title: deleteRecvPersonInfoMethod
	 * @Description:     删除收款人信息
	 * @param headMap    公共报文头
	 * @param bodyMap    报文体
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public ResponseEntity deleteRecvPersonInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();	
		 
		List<String> payBookIdList = (List<String>) (bodyMap.get("payBookIdList")==null?"":bodyMap.get("payBookIdList"));//收款人记录唯一标识 List
		 
		if(payBookIdList != null && payBookIdList.size() > 0){
			for (int i = 0; i < payBookIdList.size(); i++) {
				 String payBookId = (String) payBookIdList.get(i);
				
				 //step1 查询收款人信息是否存在
				 Map<String,Object> recvPersonInfoMap = recvPersonInfoService.queryRecvPersonInfoByPayBookIdMethod(payBookId);
				 
				 if(recvPersonInfoMap == null || recvPersonInfoMap.size() <= 0){
					 //收款人信息不存在，不允许删除
					 throw new ServiceException(CustManageErrorCodeConstants.PCUE0018,"收款人信息不存在，不允许删除");
				 }
				 
				 //step2 查询收款人子表信息是否存在
				 List<Map<String,Object>> subList = recvPersonInfoService.queryAllSubRecvPersonInfoMethod(payBookId);
				 if(subList != null && subList.size() > 0){
					 //step3 删除子表信息
					 recvPersonInfoService.deleteSubRecvPersonInfoMethod(payBookId);
				 }
				 
				 //step4 删除收款人信息
				 recvPersonInfoService.deleteRecvPersonInfoMethod(payBookId);
			}
		}
		
		return responseEntity;

	}
	/**
	 * @Title: queryAllRecvPersonInfoMethod
	 * @Description:        查询该渠道客户的所有收款人名册列表
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllRecvPersonInfoMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String payeeType = (String) (bodyMap.get("payeeType")==null?"":bodyMap.get("payeeType"));//收款人类型
		String accountName = (String) (bodyMap.get("accountName")==null?"":bodyMap.get("accountName"));//账号名称
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//账号
		String payBookId = (String) (bodyMap.get("payBookId")==null?"":bodyMap.get("payBookId"));//账号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		
		recvPersonInfoList = recvPersonInfoService.queryAllRecvPersonInfoMethod(cstId,payBookId,payeeType, accountName, accountNo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("resultList", recvPersonInfoList);//收款人信息列表
		
		ResponseEntity  responseEntity =  new  ResponseEntity(resultMap);
		
		return responseEntity;

	}
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表去账号重复
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryDistinctRecvPersonInfoPageMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException{
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String payeeType = (String) (bodyMap.get("payeeType")==null?"":bodyMap.get("payeeType"));//收款人类型
		String accountName = (String) (bodyMap.get("accountName")==null?"":bodyMap.get("accountName"));//账号名称
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//账号  
		String payBookId = (String) (bodyMap.get("payBookId")==null?"":bodyMap.get("payBookId"));//账号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String haveTransInfoFlg = (String) (bodyMap.get("haveTransInfoFlg")==null?"":bodyMap.get("haveTransInfoFlg"));//转账联行号信息查询标记0-不查 1-查
		int pageNo = (Integer) bodyMap.get("pageNo");//页码  
		int pageSize = (Integer) bodyMap.get("pageSize");//每页条数
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		int totalNum = 0;
		if(haveTransInfoFlg != null && "1".equals(haveTransInfoFlg)){
			//查询收款人信息
			recvPersonInfoList = recvPersonInfoService.queryDistinctRecvPersonInfoPageWithSubPayBookMethod(cstId,payBookId, payeeType, accountName, accountNo, pageNo, pageSize);
			//查询总条数
			totalNum = recvPersonInfoService.queryDistinctRecvPersonTotalNumWithSubPayBookMethod(cstId,payBookId,payeeType, accountName, accountNo);
		}else{
			//查询收款人信息
			recvPersonInfoList = recvPersonInfoService.queryRecvPersonInfoPageMethod(cstId,payBookId,payeeType, accountName, accountNo, pageNo, pageSize);
			//查询总条数
			totalNum = recvPersonInfoService.queryRecvPersonTotalNumMethod(cstId,payBookId, payeeType, accountName, accountNo);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("resultList", recvPersonInfoList);//收款人信息列表
		resultMap.put("totalNum", totalNum);//总条数
		 
		ResponseEntity  responseEntity =  new  ResponseEntity(resultMap);
		
		return responseEntity;
	}
	
	/**
	 * @Title: queryRecvPersonInfoPageMethod
	 * @Description:        分页查询该渠道客户的所有收款人名册列表不去除重复账号
	 * @param headMap 		公共报文头
	 * @param bodyMap 		报文体
	 * @return              收款人名册列表
	 * @throws ServiceException
	 */
	public ResponseEntity queryRecvPersonInfoPageMethod(Map<String,Object> headMap,Map<String, Object> bodyMap)throws ServiceException{
		String cstNo = (String)headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);//客户号
		String payeeType = (String) (bodyMap.get("payeeType")==null?"":bodyMap.get("payeeType"));//收款人类型
		String accountName = (String) (bodyMap.get("accountName")==null?"":bodyMap.get("accountName"));//账号名称
		String accountNo = (String) (bodyMap.get("accountNo")==null?"":bodyMap.get("accountNo"));//账号  
		String payBookId = (String) (bodyMap.get("payBookId")==null?"":bodyMap.get("payBookId"));//账号
		String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人ID
		String haveTransInfoFlg = (String) (bodyMap.get("haveTransInfoFlg")==null?"":bodyMap.get("haveTransInfoFlg"));//转账联行号信息查询标记0-不查 1-查
		int pageNo = (Integer) bodyMap.get("pageNo");//页码  
		int pageSize = (Integer) bodyMap.get("pageSize");//每页条数
		
		Map<String,Object> cstInfoMap = customervoiceService.queryTprCstCstinf(legalId,cstNo);
		if(cstInfoMap == null || cstInfoMap.size()<=0){
			throw new ServiceException(CustManageErrorCodeConstants.PCUE0049,"客户信息不存在");
		}
		String cstId = String.valueOf(cstInfoMap.get("cstId"));
		
		List<Map<String,Object>> recvPersonInfoList = new ArrayList<Map<String,Object>>();
		int totalNum = 0;
		if(haveTransInfoFlg != null && "1".equals(haveTransInfoFlg)){
			//查询收款人信息
			recvPersonInfoList = recvPersonInfoService.queryRecvPersonInfoPageWithSubPayBookMethod(cstId,payBookId, payeeType, accountName, accountNo, pageNo, pageSize);
			//查询总条数
			totalNum = recvPersonInfoService.queryRecvPersonTotalNumWithSubPayBookMethod(cstId,payBookId, payeeType, accountName, accountNo);
		}else{
			//查询收款人信息
			recvPersonInfoList = recvPersonInfoService.queryRecvPersonInfoPageMethod(cstId,payBookId,payeeType, accountName, accountNo, pageNo, pageSize);
			//查询总条数
			totalNum = recvPersonInfoService.queryRecvPersonTotalNumMethod(cstId,payBookId,payeeType, accountName, accountNo);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("resultList", recvPersonInfoList);//收款人信息列表
		resultMap.put("totalNum", totalNum);//总条数
		 
		ResponseEntity  responseEntity =  new  ResponseEntity(resultMap);
		
		return responseEntity;
	}
	
}
