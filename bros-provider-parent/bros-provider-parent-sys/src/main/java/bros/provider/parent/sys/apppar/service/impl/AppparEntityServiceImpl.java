package bros.provider.parent.sys.apppar.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import bros.common.core.exception.ServiceException;
import bros.common.core.redis.util.GetCacheDataUtil;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.sys.apppar.service.IAppparEntityService;
import bros.provider.parent.sys.constants.SysErrorCodeConstants;

/**
 * 
 * @ClassName: AppparEntityServiceImpl 
 * @Description: 查询引用参数表服务实现
 * @author 何鹏
 * @date 2016年9月1日 下午2:29:11 
 * @version 1.0
 */
@Repository(value="appparEntityService")
public class AppparEntityServiceImpl implements IAppparEntityService {
	/**
	 * 
	 * @Title: queryAppparByTypeCode 
	 * @Description: 根据参数分类查询参数数据
	 * @param typeCodeList  参数分类list
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAppparByTypeCode(List<Map<String,Object>> typeCodeList) throws ServiceException{
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			for(Map<String,Object> map : typeCodeList){
				String typeCodeTemp = (String) map.get("typeCode");
				String valueTemp = (String) (map.get("value")==null?"":map.get("value"));//参数分类值
				String languageTemp = (String) (map.get("language")==null?"":map.get("language"));//参数分类语言
				
				List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> codeList = GetCacheDataUtil.getAppParByCache(typeCodeTemp);//从缓存中获取数据
				
				if(codeList != null && codeList.size()>0){//进行数据处理过滤
					for(Map<String,Object> tempTemp : codeList){
						boolean bool = false;
						String typeCode = (String) (tempTemp.get("typeCode")==null?"":tempTemp.get("typeCode"));//参数分类码
						String value = (String) (tempTemp.get("value")==null?"":tempTemp.get("value"));//参数分类值
						String language = (String) (tempTemp.get("language")==null?"":tempTemp.get("language"));//参数分类语言
						
						String oldCompareStr = typeCodeTemp;
						String compareStr = typeCode;
						
						if(!ValidateUtil.isEmpty(valueTemp)){
							oldCompareStr = oldCompareStr+"|"+valueTemp;
							compareStr = compareStr+"|"+value;
						}
						if(!ValidateUtil.isEmpty(languageTemp)){
							oldCompareStr = oldCompareStr+"|"+languageTemp;
							compareStr = compareStr+"|"+language;
						}
						if(oldCompareStr.equals(compareStr)){//存在数据需要进行处理数据
							bool = true;
						}
						
						if(bool){//存在的数据，需要进行处理
							tempList.add(tempTemp);
						}
					}		
				}
				
				result.put(typeCodeTemp, tempList);
			}
			
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(SysErrorCodeConstants.PPSS0001,"查询参数信息失败",e);
		}
		return result;
	}
}
