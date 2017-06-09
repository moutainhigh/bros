import java.util.HashMap;
import java.util.Map;


public class test {

	public static void main(String[] args) {
		
		
		
		/*<dubbo:reference check="false" version="1.0.0" id="addRoleManageAction_1.0.0" interface="bros.c.custmanage.facade.service.ICRoleManageServiceFacade">
		<dubbo:method name="addRoleManageMethod" />
	    </dubbo:reference>*/
		/*String str="checkSingleLimitAction,checkLimitAction,checkLimitUpdateAction,checkLandSingleLimitAction,queryPbCstLimitCustomListPageAction,saveSinglePbCstingLimitCustomAction,updatePbCstingLimitCustomAction,saveBatchPbCstingLimitCustomAction,updateBatchPbCstingLimitCustomAction,queryCbCstLimitCustomListPageAction,saveSingleCbCstingLimitCustomAction,updateCbCstingLimitCustomAction,saveBatchCbCstingLimitCustomAction,updateBatchCbCstingLimitCustomAction,queryCbAccLimitCustomListPageAction,saveSingleCbAccingLimitCustomAction,updateCbAccingLimitCustomAction,saveBatchCbAccingLimitCustomAction,updateBatchCbAccingLimitCustomAction";
		String str1="检查单笔限额,检查单笔日累计不入库,检查单笔日累计入库,检查单笔落地限额,查询个人客户自定义限额,设置个人客户自定义限额,修改个人客户自定义限额,批量设置个人客户自定义限额,批量修改个人客户自定义限额,查询企业客户自定义限额,设置企业客户自定义限额,修改企业客户自定义限额,批量设置企业客户自定义限额,批量修改企业客户自定义限额,查询企业账户自定义限额,设置企业账户自定义限额,修改企业账户自定义限额,批量设置企业账户自定义限额,批量修改企业账户自定义限额";
		String test = str.split(",")[0];
		//System.out.println(test.substring(0, test.length()-6));
		String[] strs= str.split(",");
		String[] strs1= str1.split(",");
		
		for (int i = 0; i < strs.length; i++) {
			System.out.println("    <!-- "+strs1[i]+" -->");
			System.out.println("    <dubbo:reference check='false' version='1.0.0' id='"+strs[i]+"_1.0.0' interface='bros.c.limit.facade.service.ICLimitServiceFacade'>");
			System.out.println("         <dubbo:method name='"+strs[i].substring(0, strs[i].length()-6)+"' />");
			System.out.println("    </dubbo:reference>");
			System.out.println( ""           );
		}
		
		*/
		Map map1 = new HashMap();  
        for (int i = 0; i < 10; i++) {  
            map1.put(i, i);  
        }  
        System.out.println(map1);  
        Map map2 = new HashMap();  
        for (int i = 0; i < 19; i++) {  
            map2.put(i, i+1);  
        }  
          
        map1.putAll(map2);  
          
        System.out.println(map1);  
	}

}
