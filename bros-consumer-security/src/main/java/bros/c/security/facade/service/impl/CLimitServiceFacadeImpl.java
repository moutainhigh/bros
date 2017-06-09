package bros.c.security.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.limit.facade.service.ICLimitServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.limit.facade.service.IPLimitServiceFacade;
/**
 * 
 * @ClassName: CLimitServiceFacadeImpl 
 * @Description: 限额暴露接口实现类
 * @author pengxiangnan 
 * @date 2016年5月30日 下午4:21:00 
 * @version 1.0
 */
@Component("climitServiceFacade")
public class CLimitServiceFacadeImpl implements ICLimitServiceFacade {
	
	/**
	 * 个人银行类机构级实现类
	 */
	@Autowired
	private IPLimitServiceFacade plimitServiceFacade;
	
	/**
	 * 
	 * @Title: queryBankingLimit 
	 * @Description: 查询银行类限额
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000001")
	@Override
	public ResponseEntity queryBankingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.queryBankingLimit(headMap, bodyMap);
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
	@Validation(value="c0000012")
	@Override
	public ResponseEntity queryCstingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.queryCstingLimit(headMap, bodyMap);
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
	@Validation(value="c0000013")
	@Override
	public ResponseEntity queryAccingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException{
		return plimitServiceFacade.queryAccingLimit(headMap, bodyMap);
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
	@Validation(value="c0000014")
	@Override
	public ResponseEntity queryLegalOrBranchLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		return plimitServiceFacade.queryLegalOrBranchLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000002")
	@Override
	public ResponseEntity queryCstLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.queryCstLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000003")
	@Override
	public ResponseEntity queryAccLandingLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		
		return plimitServiceFacade.queryAccLandingLimit(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: saveSingleBankingLimit 
	 * @Description: 设置银行限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000004")
	@Override
	public ResponseEntity saveSingleBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleBankingLimit(headMap, bodyMap);
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
	@Validation(value="c0000015")
	@Override
	public ResponseEntity saveSingleCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleCstingLimit(headMap, bodyMap);
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
	@Validation(value="c0000016")
	@Override
	public ResponseEntity saveSingleAccingLimit(Map<String, Object> headMap,	Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleAccingLimit(headMap, bodyMap);
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
	@Validation(value="c0000005")
	@Override
	public ResponseEntity saveSingleLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleLegalOrBranchLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000006")
	@Override
	public ResponseEntity saveSingleCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleCstLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000007")
	@Override
	public ResponseEntity saveSingleAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveSingleAccLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000017")
	@Override
	public ResponseEntity updateSingleBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleBankingLimit(headMap, bodyMap);
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
	@Validation(value="c0000018")
	@Override
	public ResponseEntity updateSingleCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleCstingLimit(headMap, bodyMap);
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
	@Validation(value="c0000019")
	@Override
	public ResponseEntity updateSingleAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleAccingLimit(headMap, bodyMap);
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
	@Validation(value="c0000020")
	@Override
	public ResponseEntity updateSingleLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleLegalOrBranchLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000021")
	@Override
	public ResponseEntity updateSingleCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleCstLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000022")
	@Override
	public ResponseEntity updateSingleAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateSingleAccLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000008")
	@Override
	public ResponseEntity saveBatchBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchBankingLimit(headMap, bodyMap);
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
	@Validation(value="c0000303")
	@Override
	public ResponseEntity saveBatchCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchCstingLimit(headMap, bodyMap);
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
	@Validation(value="c0000304")
	@Override
	public ResponseEntity saveBatchAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchAccingLimit(headMap, bodyMap);
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
	@Validation(value="c0000009")
	@Override
	public ResponseEntity saveBatchLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchLegalOrBranchLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000010")
	@Override
	public ResponseEntity saveBatchCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchCstLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000011")
	@Override
	public ResponseEntity saveBatchAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.saveBatchAccLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000305")
	@Override
	public ResponseEntity updateBatchBankingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateBatchBankingLimit(headMap, bodyMap);
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
	@Validation(value="c0000306")
	@Override
	public ResponseEntity updateBatchCstingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateBatchCstingLimit(headMap, bodyMap);
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
	@Validation(value="c0000307")
	@Override
	public ResponseEntity updateBatchAccingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateBatchAccingLimit(headMap, bodyMap);
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
	@Validation(value="c0000308")
	@Override
	public ResponseEntity updateBatchLegalOrBranchLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException {
		plimitServiceFacade.updateBatchLegalOrBranchLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000309")
	@Override
	public ResponseEntity updateBatchCstLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateBatchCstLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000310")
	@Override
	public ResponseEntity updateBatchAccLandingLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.updateBatchAccLandingLimit(headMap, bodyMap);
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
	@Validation(value="c0000351")
	@Override
	public ResponseEntity checkSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.checkSingleLimit(headMap, bodyMap);
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
	@Validation(value="c0000352")
	@Override
	public ResponseEntity checkLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.checkLimit(headMap, bodyMap);
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
	@Validation(value="c0000353")
	@Override
	public ResponseEntity checkLimitUpdate(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		plimitServiceFacade.checkLimitUpdate(headMap, bodyMap);
		ResponseEntity entity = new ResponseEntity();
		return entity;
	}
    
	/**
	 * 
	 * @Title: queryPbCstLimitCustomListPage 
	 * @Description:  查询客户自定义查询
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000354")
	@Override
	public ResponseEntity queryPbCstLimitCustomListPage( Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.queryPbCstLimitCustomListPage(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000357")
	@Override
	public ResponseEntity saveSinglePbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveSinglePbCstingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: saveBatchPbCstingLimitCustom 
	 * @Description: 批量设置个人客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000358")
	@Override
	public ResponseEntity saveBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveBatchPbCstingLimitCustom(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: updateBatchPbCstingLimitCustom 
	 * @Description: 批量设置个人客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000359")
	@Override
	public ResponseEntity updateBatchPbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateBatchPbCstingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000355")
	@Override
	public ResponseEntity updateSinglePbCstingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateSinglePbCstingCustomLimit(headMap, bodyMap);
	}
	
	/**
	 * 校验落地限额
	 */
	@Validation(value="c0000356")
	@Override
	public ResponseEntity checkLandSingleLimit(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.checkLandSingleLimit(headMap, bodyMap);
	}
	
	
	/**
	 * 
	 * @Title: queryCbCstLimitCustomListPage 
	 * @Description: 查询企业客户自定限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000360")
	@Override
	public ResponseEntity queryCbCstLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return plimitServiceFacade.queryCbCstLimitCustomListPage(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: saveSinglePbCstingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000361")
	@Override
	public ResponseEntity saveSingleCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveSingleCbCstingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: saveBatchPbCstingLimitCustom 
	 * @Description: 批量设置客户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000362")
	@Override
	public ResponseEntity saveBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveBatchCbCstingLimitCustom(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: updateBatchcbCstingLimitCustom 
	 * @Description: 批量设置企业客户自定义类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000363")
	@Override
	public ResponseEntity updateBatchCbCstingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateBatchCbCstingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000364")
	@Override
	public ResponseEntity updateSingleCbCstingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateSingleCbCstingCustomLimit(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryCbAccLimitCustomListPage 
	 * @Description: 查询企业账户自定限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000365")
	@Override
	public ResponseEntity queryCbAccLimitCustomListPage(Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return plimitServiceFacade.queryCbAccLimitCustomListPage(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: saveSinglePbAccingLimitCustom 
	 * @Description: 设置客户自定义限额
	 * @param headMap
	 * @param bodyMap
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000366")
	@Override
	public ResponseEntity saveSingleCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveSingleCbAccingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: saveBatchCbAccingLimitCustom 
	 * @Description: 批量设置账户类限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000369")
	@Override
	public ResponseEntity saveBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.saveBatchCbAccingLimitCustom(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: updateBatchCbAccingLimitCustom 
	 * @Description: 批量更新账户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000367")
	@Override
	public ResponseEntity updateBatchCbAccingLimitCustom(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateBatchCbAccingLimitCustom(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: updateSingleCstingCustomLimit 
	 * @Description: 更新客户自定义限额
	 * @param headMap
	 * @param parmINList
	 * @throws ServiceException    设定文件
	 */
	@Validation(value="c0000368")
	@Override
	public ResponseEntity updateSingleCbAccingCustomLimit(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return plimitServiceFacade.updateSingleCbAccingCustomLimit(headMap, bodyMap);
	}
}
