package bros.p.limit.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPLimitServiceFacade 
 * @Description: 限额对外接口
 * @author pengxiangnan 
 * @date 2016年5月30日 下午15:35:28 
 * @version 1.0
 */
public interface IPLimitServiceFacade {
	
	/**
	 * 
	 * @Title: queryBankingLimit 
	 * @Description: 查询银行类限额
	 * @param headMap 公共报文头
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity  queryBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCstingLimit 
	 * @Description: 查询客户类限额
	 * @param headMap
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity  queryCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	/**
	 * 
	 * @Title: queryPbCstLimitCustomListPage 
	 * @Description: 查询客户类限额
	 * @param headMap
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity  queryPbCstLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: queryAccingLimit 
	 * @Description: 查询账户类限额
	 * @param headMap 报文头
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity  queryAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryLandingLimit 
	 * @Description: 查询机构、法人落地限额
	 * @param headMap 报文头
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity queryLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryLandingLimit 
	 * @Description: 查询自定义客户落地限额
	 * @param headMap 报文头
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
	 * @throws ServiceException
	 */
	public ResponseEntity  queryCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
    /**
     * 
     * @Title: queryAccLandingLimit 
     * @Description: 查询自定义账户落地限额
     * @param headMap 报文头
	 * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @param pageNo  页码
	 * @param pageSize  每页记录数
	 * @return ResponseEntity Map<String, Object> limitList限额列表  totalNum 总条数
     * @throws ServiceException
     */
	public ResponseEntity queryAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
    /**
     * 
     * @Title: saveSingleBankingLimit 
     * @Description: 设置银行类限额
     * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
     * @throws ServiceException 
     */
	public ResponseEntity saveSingleBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSingleCstingLimit 
	 * @Description: 设置客户类限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSingleAccingLimit 
	 * @Description: 设置账户类限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSingleLegalOrBranchLandingLimit 
	 * @Description: 设置机构、法人落地限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSingleCstLandingLimit 
	 * @Description: 设置客户落地限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSingleAccLandingLimit 
	 * @Description: 设置账户落地限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param accNo 账号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
     * 
     * @Title: updateSingleBankingLimit 
     * @Description: 更新银行类限额
     * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
     * @throws ServiceException 
     */
	public ResponseEntity updateSingleBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleCstingLimit 
	 * @Description: 更新客户类限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额 
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额 
	 * @throws ServiceException
	 */
	public ResponseEntity updateSinglePbCstingCustomLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleAccingLimit 
	 * @Description: 更新账户类限额
     * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleLegalOrBranchLandingLimit 
	 * @Description: 更新机构、法人落地限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @param singleMax 单笔限额
     * @param dayMax  日累计限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleCstLandingLimit 
	 * @Description: 更新客户落地限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateSingleAccLandingLimit 
	 * @Description: 更新账户落地限额
	 * @param headMap 公共报文头 
     * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param accNo 账号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
     * 
     * @Title: saveBatchBankingLimit 
     * @Description: 批量设置银行类限额
     * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
     * @throws ServiceException 
     */
	public ResponseEntity saveBatchBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchCstingLimit 
	 * @Description: 批量设置客户类限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchAccingLimit 
	 * @Description: 批量设置账户类限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchLegalOrBranchLandingLimit 
	 * @Description: 批量设置机构、法人落地限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchCstLandingLimit 
	 * @Description: 批量设置客户落地限额
	 * @param headMap 公共报文头 
	 * @param bodyMapList 包含Map<String, Object> bodyMap  
	 * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchAccLandingLimit 
	 * @Description: 批量设置账户落地限额
	 * @param headMap 公共报文头 
	 * @param bodyMapList 包含Map<String, Object> bodyMap  
	 * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param accNo 账号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
     * 
     * @Title: updateBatchBankingLimit 
     * @Description: 批量更新银行类限额
     * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
     * @throws ServiceException 
     */
	public ResponseEntity updateBatchBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchCstingLimit 
	 * @Description: 批量更新客户类限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchAccingLimit 
	 * @Description: 批量更新账户类限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateLegalOrBranchLandingLimit 
	 * @Description: 批量更新机构、法人落地限额
	 * @param headMap 报文头
     * @param bodyMapList 包含Map<String, Object> bodyMap  
     * @param bodyMap 包括以下参数
	 * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param branchNo 机构编号
     * @param custNo 客户号
     * @param accNo 账号
     * @param bizType 业务类型
     * @param safeTool 认证方式
     * @param cstLevel 客户等级 
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchCstLandingLimit 
	 * @Description: 批量更新客户落地限额
     * @param headMap 公共报文头 
	 * @param bodyMapList 包含Map<String, Object> bodyMap  
	 * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchAccLandingLimit 
	 * @Description: 批量更新账户落地限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @param bodyMap 包括以下参数
     * @param channelNo 渠道编号
     * @param legalNo 法人唯一标识
     * @param custNo 客户号
     * @param accNo 账号
     * @param singleMax 单笔限额
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap ) throws ServiceException;

	
	/**
	 * 
	 * @Title: checkPbSingleLimit 
	 * @Description: 检查单笔限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @param channel 渠道编号
	 * @param legalId 法人唯一标识
	 * @param branchId 机构编号
	 * @param cstNo 客户号
	 * @param accNo 账号
	 * @param bizType 业务类型
	 * @param safeTool 认证方式
	 * @param payAmount 交易金额
	 * @throws ServiceException
	 */
	public ResponseEntity checkSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;

	/**
	 * 
	 * @Title: checkLimit 
	 * @Description: 检查限额（单笔，日累计）发生额不入库
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @param channel 渠道编号
	 * @param legalId 法人唯一标识
	 * @param branchId 机构编号
	 * @param cstNo 客户号
	 * @param accNo 账号
	 * @param bizType 业务类型
	 * @param safeTool 认证方式
	 * @throws ServiceException
	 */
	public ResponseEntity checkLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkLimitUpdate 
	 * @Description: 查询日累计限额 累计
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @param channel 渠道编号
	 * @param legalId 法人唯一标识
	 * @param branchId 机构编号
	 * @param cstNo 客户号
	 * @param accNo 账号
	 * @param bizType 业务类型
	 * @param safeTool 认证方式
	 * @throws ServiceException
	 */
	public ResponseEntity checkLimitUpdate(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkLandSingleLimit 
	 * @Description: 校验落地限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity checkLandSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom 
	 * @Description: 个人客户自定义单笔保存
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveSinglePbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: saveBatchPbCstingLimitCustom 
	 * @Description: 个人批量设置客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBatchPbCstingLimitCustom 
	 * @Description: 批量更新个人客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryCbCstLimitCustomListPage 
	 * @Description: 查询企业客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity queryCbCstLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: saveSingleCbCstingLimitCustom 
	 * @Description: 设置单笔企业客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: saveBatchCbCstingLimitCustom 
	 * @Description: 批量保存客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateBatchCbCstingLimitCustom 
	 * @Description: 批量更新客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateSingleCbCstingCustomLimit 
	 * @Description: 更新单笔企业客户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleCbCstingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCbAccLimitCustomListPage 
	 * @Description: 查询企业账户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity queryCbAccLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: saveSingleCbAccingLimitCustom 
	 * @Description: 设置单笔企业账户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveSingleCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: saveBatchCbAccingLimitCustom 
	 * @Description: 批量设置账户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity saveBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateBatchCbAccingLimitCustom 
	 * @Description: 批量更新企业账户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity updateBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateSingleCbAccingCustomLimit 
	 * @Description: 更新单笔企业账户自定义限额
	 * @param headMap 公共报文头 
	 * @param bodyMap 包含Map<String, Object> bodyMap  
	 * @throws ServiceException
	 */
	public ResponseEntity updateSingleCbAccingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
			

}
