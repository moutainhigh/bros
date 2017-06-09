package bros.provider.parent.branchmanage.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IBranchManageService 
 * @Description: 机构管理服务定义
 * @author liwei 
 * @date 2016年6月27日 下午4:22:22 
 * @version 1.0 
 */

public interface IBranchManageService {
	
	/**
	 * 
	 * @Title: addBranch 
	 * @Description: 添加机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException
	 */
	public void addBranch (Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: resultMap 
	 * @Description: 判断机构是否存在
	 * @param headMap
	 * @param contextMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAllBranchByObjectId(Map<String, Object> contextMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateBranch 
	 * @Description: 修改机构
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException
	 */
	public void updateBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBranch 
	 * @Description: 删除机构（注销）
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException
	 */
	public void deleteBranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAllBaranch 
	 * @Description: 查询机构列表信息
	 * @param headMap
	 * @param contextMap
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAllBaranch(Map<String, Object> headMap, Map<String, Object> contextMap) throws ServiceException;
}
