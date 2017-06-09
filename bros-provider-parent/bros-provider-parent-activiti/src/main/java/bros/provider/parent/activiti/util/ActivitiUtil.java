package bros.provider.parent.activiti.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: ActivitiUtil 
 * @Description: 工作流用工具类
 * @author weiyancheng
 * @date 2016年7月22日 上午10:13:46 
 * @version 1.0 
 */
public class ActivitiUtil {
	
	public static List<String> switchUserIdList(List<Map<String, Object>> userIdList,String userId){
		List<String> returnList = new ArrayList<String>();
		if(userIdList!=null && userIdList.size()>0){
			for(Map<String, Object> map:userIdList){
				returnList.add((String)map.get(userId));
			}
		}
		return returnList;
	}
	
	public static List<String> switchRoleIdList(List<Map<String, Object>> roleIdList,String roleId){
		List<String> returnList = new ArrayList<String>();
		if(roleIdList!=null && roleIdList.size()>0){
			for(Map<String, Object> map:roleIdList){
				returnList.add((String)map.get(roleId));
			}
		}
		return returnList;
	}

}
