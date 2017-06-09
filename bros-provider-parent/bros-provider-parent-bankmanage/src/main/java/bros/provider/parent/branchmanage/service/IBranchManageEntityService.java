package bros.provider.parent.branchmanage.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBranchManageEntityService 
 * @Description: 机构实体接口
 * @author 何鹏
 * @date 2016年7月19日 下午3:48:11 
 * @version 1.0
 */
public interface IBranchManageEntityService {
	/**
	 * 
	 * @Title: queryBranchInfo 
	 * @Description: 根据法人id和机构编号查询机构信息
	 * @param legalId	法人id
	 * @param branchCode	机构编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryBranchInfo(String legalId,String branchCode) throws ServiceException;
}
