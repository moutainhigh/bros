package bros.p.limit.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.limit.cb.service.ICbLimitAssembleService;
import bros.common.core.limit.constants.LimitErrorCodeConstants;
import bros.common.core.limit.land.service.ILandLimitAssembleService;
import bros.common.core.limit.pack.service.IPackLimitService;
import bros.common.core.limit.pb.service.IPbLimitAssembleService;
import bros.common.core.limit.service.ILimitService;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.limit.facade.service.IPLimitServiceFacade;
import bros.provider.security.constants.SecurityErrorCodeConstants;
/**
 * 
 * @ClassName: PLimitServiceFacadeImpl 
 * @Description: 限额暴露接口实现类
 * @author pengxiangnan 
 * @date 2016年5月30日 下午4:21:00 
 * @version 1.0
 */
@Component("plimitServiceFacade")
public class PLimitServiceFacadeImpl implements IPLimitServiceFacade {
	
	/**
	 * 个人银行类机构级实现类
	 */
	@Autowired
	private ILimitService pbBankLimitBranchService;
	/**
	 * 个人银行类法人级实现类
	 */
	@Autowired
	private ILimitService  pbBankLimitLegalService;
	/**
	 * 个人银行类系统级实现类
	 */
	@Autowired
	private ILimitService pbBankLimitSystemService;
	/**
	 * 企业银行类机构级实现类
	 */
	@Autowired
	private ILimitService cbBankLimitBranchService;
	/**
	 * 企业银行类法人级实现类
	 */
	@Autowired
	private ILimitService  cbBankLimitLegalService;
	/**
	 * 企业银行类系统级实现类
	 */
	@Autowired
	private ILimitService cbBankLimitSystemService;
	/**
	 * 个人客户类法人级实现类
	 */
	@Autowired
	private ILimitService  pbCstLimitLegalService;
	/**
	 * 个人客户类系统级实现类
	 */
	@Autowired
	private ILimitService pbCstLimitSystemService;
	/**
	 * 企业客户类法人级实现类
	 */
	@Autowired
	private ILimitService  cbCstLimitLegalService;
	/**
	 * 企业客户类系统级实现类
	 */
	@Autowired
	private ILimitService cbCstLimitSystemService;
	/**
	 * 账户类系统级实现类
	 */
	@Autowired
	private ILimitService  cbAccLimitLegalService;
	/**
	 * 账户类系统级实现类
	 */
	@Autowired
	private ILimitService cbAccLimitSystemService;
	/**
	 * 自定义账户落地限额类
	 */
	@Autowired
	private ILimitService landLimitAccService;
	/**
	 * 自定义客户落地限额类
	 */
	@Autowired
	private ILimitService landLimitCstService;
	/**
	 * 自定义机构落地限额类
	 */
	@Autowired
	private ILimitService landLimitBranchService;
	/**
	 * 自定义法人落地限额类
	 */
	@Autowired
    private ILimitService landLimitLegalService;
	
	/**
	 * 包装实现类对外
	 */
	@Autowired
	private IPackLimitService packLimitService;
	
	/**
	 * 个人自定义限额
	 */
	@Autowired
	private ILimitService pbCstLimitCustomService;
	
	/**
	 * 企业客户自定义限额
	 */
	@Autowired
	private ILimitService cbCstLimitCustomService;
	/**
	 * 企业账户自定义限额
	 */
	@Autowired
	private ILimitService cbAccLimitCustomService;
	
	/**
	 * 个人校验类
	 */
	@Autowired
	private IPbLimitAssembleService pbLimitAssembleService;
	
	/**
	 * 企业校验类
	 */
	@Autowired
	private ICbLimitAssembleService cbLimitAssembleService;
	
	/**
	 * 落地限额校验类
	 */
	@Autowired
	private ILandLimitAssembleService landLimitAssembleService;
	
	/**
	 * 
	 * @Title: queryBankingLimit 
	 * @Description: 查询银行类限额
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000001")
	@Override
	public ResponseEntity queryBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		String channel = (String) bodyMap.get("channelNo");
		String legalId = (String) bodyMap.get("legalNo");
		String branchId = (String) bodyMap.get("branchNo");
		String bizType = (String) bodyMap.get("bizType");
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel");
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		parmOut = packLimitService.queryBankLimitListPage(channel, legalId, branchId, "", "", bizType, safeTool, cstLevel, pageNo, pageSize);
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
    
	/**
	 * 
	 * @Title: queryCstingLimit 
	 * @Description: 查询客户类限额
	 * @param headMap 报文头	 
	 * @param bodyMap 掺入参数
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000012")
	@Override
	public ResponseEntity queryCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		String channel = (String) bodyMap.get("channelNo");
		String legalId = (String) bodyMap.get("legalNo");
		String branchId = (String) bodyMap.get("branchNo");
		String bizType = (String) bodyMap.get("bizType");
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel");
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		parmOut = packLimitService.queryCstLimitListPage(channel, legalId, branchId, "", "", bizType, safeTool, cstLevel, pageNo, pageSize);
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
    
	/**
	 * 
	 * @Title: queryAccingLimit 
	 * @Description: 查询账户类限额
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000013")
	@Override
	public ResponseEntity queryAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException{
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		String channel = (String) bodyMap.get("channelNo");
		String legalId = (String) bodyMap.get("legalNo");
		String branchId = (String) bodyMap.get("branchNo");
		String bizType = (String) bodyMap.get("bizType");
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel");
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		parmOut = packLimitService.queryAccLimitListPage(channel, legalId, branchId, "", "", bizType, safeTool, cstLevel, pageNo, pageSize);		
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
    
	/**
	 * 
	 * @Title: queryLegalOrBranchLandingLimit 
	 * @Description: 查找银行落地限额
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000014")
	@Override
	public ResponseEntity queryLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		String channel =  bodyMap.get("channelNo")==null?"":bodyMap.get("channelNo").toString();
		String legalId =  bodyMap.get("legalNo")==null?"":bodyMap.get("legalNo").toString();
		String branchId = bodyMap.get("branchNo")==null?"":bodyMap.get("branchNo").toString();
		String bizType =  bodyMap.get("bizType")==null?"":bodyMap.get("bizType").toString();
		String safeTool =  bodyMap.get("safeTool")==null?"":bodyMap.get("safeTool").toString();
		String cstLevel =  bodyMap.get("cstLevel")==null?"":bodyMap.get("cstLevel").toString();
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		parmOut = packLimitService.queryLandLimitListPage(channel, legalId, branchId, "", "", bizType, safeTool, cstLevel, pageNo, pageSize);
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
    
    /**
     * 
     * @Title: queryCstLandingLimit 
     * @Description: 查询客户落地限额
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException    设定文件
     */
	@Validation(value="p0000002")
	@Override
	public ResponseEntity queryCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		String cstNo = (String) bodyMap.get("cstNo");
		String channel = (String) bodyMap.get("channelNo");
		String legalId = (String) bodyMap.get("legalNo");
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		parmOut = packLimitService.queryCstLandLimitListPage(channel, legalId, cstNo, pageNo, pageSize);
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
    
	
	/**
	 * 
	 * @Title: queryAccLandingLimit 
	 * @Description: 查询账户落地限额
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000003")
	@Override
	public ResponseEntity queryAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String cstNo = (String) bodyMap.get("custNo");
		String channel = (String) bodyMap.get("channelNo");
		String legalId = (String) bodyMap.get("legalNo");
		String accNo = (String) bodyMap.get("accNo");
		int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
		int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
		
		Map<String, Object>  parmOut = new HashMap<String, Object>();
		parmOut = packLimitService.queryAccLandLimitListPage(channel, legalId, cstNo, accNo, pageNo, pageSize);
		ResponseEntity entity = new ResponseEntity(parmOut);
		return entity;
	}
	
	/**
	 * 
	 * @Title: saveSingleBankingLimit 
	 * @Description: 设置银行限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000004")
	@Override
	public ResponseEntity saveSingleBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String branchId = bodyMap.get("tradeBranchId")==null?"":bodyMap.get("tradeBranchId").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("branchId", branchId);
		bodyMap.put("legalId", legalId);
		String id = BaseUtil.createUUID();
		bodyMap.put("id", id);
		//查询总条数
		List<Map<String, Object>> totalNumList = cbBankLimitSystemService.queryLimitCommon("mybatis.mapper.single.table.bmabanklimit.queryBmaBankLimitByChannelAndBranchIdAndLegalId", bodyMap);
		if(totalNumList != null && totalNumList.size() > 0){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0011,"此银行类限额已存在，不允许添加");
		}
		
		cbBankLimitSystemService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}

	/**
	 * 
	 * @Title: saveSingleCstingLimit 
	 * @Description: 设置客户限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000015")
	@Override
	public ResponseEntity saveSingleCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String id = BaseUtil.createUUID();
		bodyMap.put("id", id);
		
		//查询总条数
		List<Map<String, Object>> totalNumList = cbCstLimitSystemService.queryLimitCommon("mybatis.mapper.single.table.bmacstlimit.queryBmaCstLimitTotalNum", bodyMap);
		if(totalNumList.size() > 0){
			throw new ServiceException(LimitErrorCodeConstants.CCLT0036,"此客户类限额已存在，不允许添加");
		}
		
		cbCstLimitSystemService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveSingleAccingLimit 
	 * @Description: 设置银行账户限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000016")
	@Override
	public ResponseEntity saveSingleAccingLimit(Map<String, Object> headMap,	Map<String, Object> bodyMap) throws ServiceException {
		cbAccLimitSystemService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveSingleLegalOrBranchLandingLimit 
	 * @Description: 设置银行落地限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000005")
	@Override
	public ResponseEntity saveSingleLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String branchId = bodyMap.get("tradeBranchId")==null?"":bodyMap.get("tradeBranchId").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("branchId", branchId);
		bodyMap.put("legalId", legalId);
		String id = BaseUtil.createUUID();
		bodyMap.put("id", id);
		//查询总条数
		List<Map<String, Object>> totalNumList = landLimitBranchService.queryLimitCommon("mybatis.mapper.single.table.bmalandlimit.queryBmaLimitByChannelAndBranchIdAndLegalId", bodyMap);
		if(totalNumList != null && totalNumList.size() > 0){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0012,"此银行落地限额已存在，不允许添加");
		}
		
		landLimitBranchService.saveSingleLimit(bodyMap);	
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveSingleCstLandingLimit 
	 * @Description: 设置客户落地限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000006")
	@Override
	public ResponseEntity saveSingleCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String cstNo = bodyMap.get("tradeCstNo")==null?"":bodyMap.get("tradeCstNo").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("cstNo", cstNo);
		bodyMap.put("legalId", legalId);
		List<Map<String, Object>>  parmOut = new ArrayList<Map<String,Object>>();
		parmOut = landLimitCstService.queryLimitCommon("mybatis.mapper.single.table.bmacstlandlimit.queryBmaCstLandLimit", bodyMap);
		if(parmOut != null && parmOut.size() > 0){
			throw new ServiceException(SecurityErrorCodeConstants.PSEY0013,"此银行客户落地限额已存在，不允许添加");
		}
		landLimitCstService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveSingleAccLandingLimit 
	 * @Description: 设置账户落地限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000007")
	@Override
	public ResponseEntity saveSingleAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		landLimitAccService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateSingleBankingLimit 
	 * @Description: 更新银行类限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000017")
	@Override
	public ResponseEntity updateSingleBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String branchId = bodyMap.get("tradeBranchId")==null?"":bodyMap.get("tradeBranchId").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("branchId", branchId);
		bodyMap.put("legalId", legalId);
		cbBankLimitSystemService.updateSingleLimit(bodyMap);
	   ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateSingleCstingLimit 
	 * @Description: 更新客户类限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000018")
	@Override
	public ResponseEntity updateSingleCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbCstLimitSystemService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateSingleAccingLimit 
	 * @Description:更新账户类限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000019")
	@Override
	public ResponseEntity updateSingleAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbAccLimitSystemService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateSingleLegalOrBranchLandingLimit 
	 * @Description: 更新银行类落地限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000020")
	@Override
	public ResponseEntity updateSingleLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String branchId = bodyMap.get("tradeBranchId")==null?"":bodyMap.get("tradeBranchId").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("branchId", branchId);
		bodyMap.put("legalId", legalId);
		landLimitBranchService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
    /**
     * 
     * @Title: updateSingleCstLandingLimit 
     * @Description: 更新自定义客户落地限额
     * @param headMap
     * @param bodyMap
     * @throws ServiceException    设定文件
     */
	@Validation(value="p0000021")
	@Override
	public ResponseEntity updateSingleCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String channel = bodyMap.get("tradeChannel")==null?"":bodyMap.get("tradeChannel").toString();
		String cstNo = bodyMap.get("tradeCstNo")==null?"":bodyMap.get("tradeCstNo").toString();
		String legalId = bodyMap.get("tradeLegalId")==null?"":bodyMap.get("tradeLegalId").toString();
		bodyMap.put("channel", channel);
		bodyMap.put("cstNo", cstNo);
		bodyMap.put("legalId", legalId);
		landLimitCstService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateSingleAccLandingLimit 
	 * @Description: 更新自定义账户落地限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000022")
	@Override
	public ResponseEntity updateSingleAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		landLimitAccService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchBankingLimit 
	 * @Description: 批量设置银行限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000008")
	@Override
	public ResponseEntity saveBatchBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
	    cbBankLimitSystemService.saveBatchLimit(parmINList);
	    ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchCstingLimit 
	 * @Description: 批量设置客户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000303")
	@Override
	public ResponseEntity saveBatchCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbCstLimitSystemService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchAccingLimit 
	 * @Description: 批量设置账户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000304")
	@Override
	public ResponseEntity saveBatchAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbAccLimitSystemService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchLegalOrBranchLandingLimit 
	 * @Description: 批量设置银行机构与法人落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000009")
	@Override
	public ResponseEntity saveBatchLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitLegalService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchCstLandingLimit 
	 * @Description: 批量设置自定义客户落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000010")
	@Override
	public ResponseEntity saveBatchCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitCstService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: saveBatchAccLandingLimit 
	 * @Description: 批量设置定义账户落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000011")
	@Override
	public ResponseEntity saveBatchAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitAccService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchBankingLimit 
	 * @Description: 批量更新银行限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000305")
	@Override
	public ResponseEntity updateBatchBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbBankLimitSystemService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchCstingLimit 
	 * @Description: 批量更新客户限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000306")
	@Override
	public ResponseEntity updateBatchCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbCstLimitSystemService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchAccingLimit 
	 * @Description: 批量更新账户限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000307")
	@Override
	public ResponseEntity updateBatchAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbAccLimitSystemService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchLegalOrBranchLandingLimit 
	 * @Description: 批量更新银行落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000308")
	@Override
	public ResponseEntity updateBatchLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitLegalService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchCstLandingLimit 
	 * @Description: 批量更新客户落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000309")
	@Override
	public ResponseEntity updateBatchCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitCstService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: updateBatchAccLandingLimit 
	 * @Description: 批量更新账户落地限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000310")
	@Override
	public ResponseEntity updateBatchAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		landLimitAccService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: checkSingleLimit 
	 * @Description: 检查单笔限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000351")
	@Override
	public ResponseEntity checkSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
		String branchId = (String) headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);
		String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
		String accNo = (String) bodyMap.get("accNo");
		String bizType  = (String) bodyMap.get("bizType"); 
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel"); 
		String payAmount = (String) bodyMap.get("payAmount");
		
		if(!ValidateUtil.isEmpty(channel)&&"1003".equals(channel)){
			cbLimitAssembleService.checkSingleLimit(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, payAmount);
		}else{
			pbLimitAssembleService.checkSingleLimit(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, payAmount);
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: checkLimit 
	 * @Description 检查限额（单笔，日累计）发生额不入库
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000352")
	@Override
	public ResponseEntity checkLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
			
		String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
		String branchId = (String) headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);
		String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
		String accNo = (String) bodyMap.get("accNo");
		String bizType  = (String) bodyMap.get("bizType"); 
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel"); 
		String payAmount = (String) bodyMap.get("payAmount");
		String dayMax = (String) bodyMap.get("dayMax");
		String daySum= (String) bodyMap.get("daySum"); 
		
		if(!ValidateUtil.isEmpty(channel)&&"1003".equals(channel)){
			cbLimitAssembleService.checkLimit(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, dayMax, daySum, payAmount);
		}else{
			pbLimitAssembleService.checkLimit(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, dayMax, daySum, payAmount);
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: checkLimitUpdate 
	 * @Description:  检查限额（单笔，日累计）发生额入库
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000353")
	@Override
	public ResponseEntity checkLimitUpdate(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		
		String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
		String branchId = (String) headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);
		String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
		String accNo = (String) bodyMap.get("accNo");
		String bizType  = (String) bodyMap.get("bizType"); 
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel"); 
		String payAmount = (String) bodyMap.get("payAmount");
		String dayMax = (String) bodyMap.get("dayMax");
		String daySum= (String) bodyMap.get("daySum"); 
		
		if(!ValidateUtil.isEmpty(channel)&&"1003".equals(channel)){
			cbLimitAssembleService.checkLimitUpdate(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, dayMax, daySum, payAmount);
		}else{
			pbLimitAssembleService.checkLimitUpdate(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, dayMax, daySum, payAmount);
		}
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryPbCstLimitCustomListPage 
	 * @Description: 查询客户自定限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000354")
	@Override
	public ResponseEntity queryPbCstLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
			Map<String, Object>  parmOut = new HashMap<String, Object>();
			String channel = (String) bodyMap.get("channelNo");
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			String cstNo = (String) bodyMap.get("cstNo");
			String bizType = (String) bodyMap.get("bizType");
		
			int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
			
			parmOut = packLimitService.queryPbCstLimitCustomListPage(channel, legalId, "", cstNo, "", bizType, "", "", pageNo, pageSize);
			ResponseEntity entity = new ResponseEntity(parmOut);
			return entity;
	}
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000357")
	@Override
	public ResponseEntity saveSinglePbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
		bodyMap.put("legalId", legalId);
		//查询总条数
		List<Map<String, Object>> totalNumList = pbCstLimitCustomService.queryLimitCommon("mybatis.mapper.single.table.tprcstlimit.queryTprCstLimit", bodyMap);
		if(totalNumList != null && totalNumList.size() > 0){
			throw new ServiceException(LimitErrorCodeConstants.CCLT0036,"此客户类限额已存在，不允许添加");
		}
		pbCstLimitCustomService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: saveBatchPbCstingLimitCustom 
	 * @Description: 批量设置个人客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000358")
	@Override
	public ResponseEntity saveBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		pbCstLimitCustomService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateBatchPbCstingLimitCustom 
	 * @Description: 批量设置个人客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000359")
	@Override
	public ResponseEntity updateBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		pbCstLimitCustomService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000355")
	@Override
	public ResponseEntity updateSinglePbCstingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
		bodyMap.put("legalId", legalId);
		pbCstLimitCustomService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 校验落地限额
	 */
	@Validation(value="p0000356")
	@Override
	public ResponseEntity checkLandSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
		String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
		String branchId = (String) headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);
		String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
		String accNo = (String) bodyMap.get("accNo");
		String bizType  = (String) bodyMap.get("bizType"); 
		String safeTool = (String) bodyMap.get("safeTool");
		String cstLevel = (String) bodyMap.get("cstLevel"); 
		String payAmount = (String) bodyMap.get("payAmount");
	
		landLimitAssembleService.checkSingleLimit(channel, legalId, branchId, cstNo, accNo, bizType, safeTool, cstLevel, payAmount);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	
	/**
	 * 
	 * @Title: queryCbCstLimitCustomListPage 
	 * @Description: 查询企业客户自定限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000360")
	@Override
	public ResponseEntity queryCbCstLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
			Map<String, Object>  parmOut = new HashMap<String, Object>();
			String channel = (String) bodyMap.get("channelNo");
			String legalId = (String) bodyMap.get("legalNo");
			String cstNo = (String) bodyMap.get("cstNo");
			String bizType = (String) bodyMap.get("bizType");
		
			int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
			
			parmOut = packLimitService.queryCbCstLimitCustomListPage(channel, legalId, "", cstNo, "", bizType, "", "", pageNo, pageSize);
			ResponseEntity entity = new ResponseEntity(parmOut);
			return entity;
	}
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000361")
	@Override
	public ResponseEntity saveSingleCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbCstLimitCustomService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: saveBatchPbCstingLimitCustom 
	 * @Description: 批量设置客户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000362")
	@Override
	public ResponseEntity saveBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbCstLimitCustomService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateBatchcbCstingLimitCustom 
	 * @Description: 批量设置企业客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000363")
	@Override
	public ResponseEntity updateBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbCstLimitCustomService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000364")
	@Override
	public ResponseEntity updateSingleCbCstingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbCstLimitCustomService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: queryCbAccLimitCustomListPage 
	 * @Description: 查询企业账户自定限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000365")
	@Override
	public ResponseEntity queryCbAccLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
			Map<String, Object>  parmOut = new HashMap<String, Object>();
			String channel = (String) bodyMap.get("channelNo");
			String legalId = (String) bodyMap.get("legalNo");
			String cstNo = (String) bodyMap.get("cstNo");
			String bizType = (String) bodyMap.get("bizType");
			String accNo = (String) bodyMap.get("accNo");
			
			
			int pageNo  = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")==null?"1":bodyMap.get("pageNo")));
			int pageSize = Integer.parseInt(String.valueOf(bodyMap.get("pageSize")==null?"10":bodyMap.get("pageSize")));
			
			parmOut = packLimitService.queryCbAccLimitCustomListPage(channel, legalId, "", cstNo, accNo, bizType, "", "", pageNo, pageSize);
			ResponseEntity entity = new ResponseEntity(parmOut);
			return entity;
	}
	/**
	 * 
	 * @Title: saveSinglePbAccingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000366")
	@Override
	public ResponseEntity saveSingleCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbAccLimitCustomService.saveSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: saveBatchCbAccingLimitCustom 
	 * @Description: 批量设置账户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000369")
	@Override
	public ResponseEntity saveBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbAccLimitCustomService.saveBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	
	/**
	 * 
	 * @Title: updateBatchCbAccingLimitCustom 
	 * @Description: 批量更新账户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000367")
	@Override
	public ResponseEntity updateBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		List<Map<String, Object>> parmINList = (List<Map<String, Object>>) bodyMap.get("parmINList");
		cbAccLimitCustomService.updateBatchLimit(parmINList);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="p0000368")
	@Override
	public ResponseEntity updateSingleCbAccingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		cbAccLimitCustomService.updateSingleLimit(bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
}
