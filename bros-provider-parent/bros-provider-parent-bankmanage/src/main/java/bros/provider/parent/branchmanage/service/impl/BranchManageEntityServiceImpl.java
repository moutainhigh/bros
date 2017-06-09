/**   
 * @Title: BranchManageServiceImpl.java 
 * @Package bros.provider.parent.branchmanage.service.impl 
 * @Description: 用一句话描述该文件做什么 
 * @author MacPro   
 * @date 2016年6月27日 下午4:22:47 
 * @version 1.0   
 */

package bros.provider.parent.branchmanage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.bankmanage.constants.BankmanageErrorCodeConstants;
import bros.provider.parent.branchmanage.service.IBranchManageEntityService;

/**
 * 
 * @ClassName: BranchManageEntityServiceImpl 
 * @Description: 机构实体接口实现
 * @author 何鹏
 * @date 2016年7月19日 下午3:51:25 
 * @version 1.0
 */
@Repository(value="branchManageEntityService")
public class BranchManageEntityServiceImpl implements IBranchManageEntityService{
	
	/**
	 * 机构管理Log
	 */
	private static final  Logger logger = LoggerFactory.getLogger(BranchManageEntityServiceImpl.class);
    
    /**
	  * 数据库操作
	  */
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/**
	 * 
	 * @Title: queryBranchInfo 
	 * @Description: 根据法人id和机构编号查询机构信息
	 * @param legalId	法人id
	 * @param branchCode	机构编号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryBranchInfo(String legalId, String branchCode) throws ServiceException {
		Map<String,Object> result = null;
		try {
			Map<String,Object> parmIn = new HashMap<String, Object>();
			parmIn.put("branchLegal", legalId);
			parmIn.put("branchCode", branchCode);
			
			result = myBatisDaoSysDao.selectOne("mybatis.mapper.single.table.branchmanage.queryAllBranchByObjectId", parmIn);
			
		} catch (ServiceException se) {
			logger.error("查询机构信息失败   " + this.getClass() + ".queryBranchInfo");
			throw se;
		} catch (Exception e) {
			logger.error("查询机构信息失败   " + this.getClass() + ".queryBranchInfo");
			throw new ServiceException(BankmanageErrorCodeConstants.PPBM0001,"查询机构信息操作失败", e);
		}
		return result;
	}
	
}
