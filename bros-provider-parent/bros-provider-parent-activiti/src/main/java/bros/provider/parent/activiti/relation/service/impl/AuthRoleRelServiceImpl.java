package bros.provider.parent.activiti.relation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.IAuthRoleRelService;

/** 
 * @ClassName: AuthRoleRelServiceImpl 
 * @Description: 授权用关联角色服务实现
 * @author weiyancheng
 * @date 2016年7月21日 下午2:08:06 
 * @version 1.0 
 */
@Component("authRoleRelService")
public class AuthRoleRelServiceImpl implements IAuthRoleRelService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthRoleRelServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * @Title: qryAuthRoleByRelId 
	 * @Description: 根据角色关联id查询角色id列表
	 * @param relId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> qryAuthRoleByRelId(String relId)
			throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("relId", relId);
			List<Map<String, Object>> resultList = myBatisDaoSysDao
					.selectList("mybatis.mapper.activiti.inside.table.workflowrolerel.queryRelRole",param);
			return resultList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryAuthRoleByRelId");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryAuthRoleByRelId");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0000,"查询关联角色失败", e);
		}
	}

}
