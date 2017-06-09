package bros.provider.account;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.exception.ServiceException;
import bros.common.core.format.factory.JsonFormatConfigFactory;
import bros.common.core.router.service.IHttpClientRouter;
import bros.common.core.util.XmlFileNameFilterUtil;
import bros.common.core.validation.factory.ValidationFactory;


public class Provider {

	static private Logger logger = LoggerFactory.getLogger(Provider.class);

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws Exception {
		
		//ApplicationContext context = new FileSystemXmlApplicationContext("D:\\workspace\\channelWorkspace\\bros-provider-account\\target\\classes\\spring\\spring-context.xml");
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		System.out.println("=======121212");
		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		/*
		Map<String,Object> headMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		headMap.put("tranSeqNo", "0001201609142222000000019250019003000001");
		headMap.put("tranTime", "134610");
		headMap.put("channelDate", "20160914");
		headMap.put("tranDate", "20160914");
		headMap.put("branched", "9999999999");
		headMap.put("sceneCode", "0000");
		headMap.put("consumerId", "9002");
		headMap.put("sourceSysId", "2222");
		headMap.put("tranCode", "calculateShoppingCartPrice");
		headMap.put("globalSeqNo", "000120160914222200000001925001");
		headMap.put("tranTellerNo", "99999999");
		headMap.put("channel", "CIP");
		
		bodyMap.put("productStoreId","MODEL-FP-OL-STORE");
		bodyMap.put("partyId","c10004");
		bodyMap.put("webSiteId","baseWebStore");
		bodyMap.put("currency","CNY");
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapTemp = new HashMap<String, Object>();
		mapTemp.put("productId", "CARD_0521_10000-03");
		mapTemp.put("quantity", "1");
		mapTemp.put("prodCatalogId", "MODEL-FPSC-DP");
		mapTemp.put("configOptionId", "CARD_0521_10017");
		list.add(mapTemp);
		bodyMap.put("productItemList",list);
		
		IHttpClientRouter httpClientRouter = (IHttpClientRouter) context.getBean("httpClientRouter");
		try{
			Map<String,Object> result = httpClientRouter.send(headMap, bodyMap, "calculateShoppingCartPrice");
			System.out.println("result====="+result);
		}catch(ServiceException se){
			System.out.println(se.getErrorCode()+"|"+se.getErrorMsg());
		}
		*/
	}
}