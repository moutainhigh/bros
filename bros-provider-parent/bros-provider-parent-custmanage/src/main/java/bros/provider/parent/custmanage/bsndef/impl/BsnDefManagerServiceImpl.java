package bros.provider.parent.custmanage.bsndef.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.db.impl.MyBatisDaoSysDaoImpl;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.bsndef.IBsnDefManagerService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
/**
 * 
 * @ClassName: BsnDefManagerServiceImpl 
 * @Description: 交易功能定义信息实现类 可根据后续需求扩充
 * @author mazhilei 
 * @date 2016年6月29日 下午2:09:54 
 * @version 1.0
 */
@Component("bsnDefManager")
public class BsnDefManagerServiceImpl implements IBsnDefManagerService {
	private static final  Logger logger = LoggerFactory.getLogger(BsnDefManagerServiceImpl.class);
	@Autowired
	private MyBatisDaoSysDaoImpl myBatisDaoSysDao;
	/**
	 * 
	 * @Title: querySysFuncId 
	 * @Description: 查询系统功能定义编码
	 * @throws ServiceException   
	 */
	@Override
	public List<Map<String,Object>> querySysFuncId(String roleAuth) throws ServiceException {
		List<Map<String, Object>> funcIdList;
		Map<String, Object> param1 = new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		
		if ("1".equals(roleAuth.substring(0, 1))) {
			list.add("1");
		}
		if ("1".equals(roleAuth.substring(1, 2))) {
			list.add("2");
		}
		if ("1".equals(roleAuth.substring(2, 3))) {
			list.add("3");
		}
		param1.put("authArray", list.toArray());
		try {
			funcIdList = myBatisDaoSysDao.selectList("mybatis.mapper.relational.table.bmaBsndefttpbsntypereltemplate.selectFuncIdByRoleType", param1);
			if (funcIdList==null) {
				funcIdList=new ArrayList<Map<String,Object>>();
			}
		} catch(ServiceException e){
			logger.error("Exception from " + this.getClass().getName() + "'s querySysFuncId method.", e);
			throw e;
        } catch(Exception ex){
        	logger.error("Exception from " + this.getClass().getName() + "'s querySysFuncId method.", ex);
        	throw new ServiceException(CustmanageErrorCodeConstants.PPCG0000);
        }
		return funcIdList;
	}

}
