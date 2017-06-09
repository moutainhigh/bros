package bros.common.core.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.redis.IRedisDao;
import bros.common.core.util.TransferCacheObjectUtil;
import bros.common.core.validation.bean.ValidationEntity;
import bros.common.core.validation.factory.ValidationCacheFactory;
/**
 * 
 * @ClassName: ValidationModelRedisTest 
 * @Description: 从缓存中获取校验模板单元测试
 * @author 何鹏
 * @date 2016年8月15日 上午10:34:42 
 * @version 1.0
 */
public class ValidationModelRedisTest{
	
	private ApplicationContext context;
	private IRedisDao redisDao;
	private Map<String,Object> exitModelMap;//存在模板的校验模板
	
	@Before
	public void setUp() throws Exception{
		context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		redisDao = (IRedisDao) context.getBean("redisDao");
		
		
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100001.xml";
		File modelFile = new File(modelPath);
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		
		exitModelMap = ValidationCacheFactory.initFormat(fileArray);
	}
	
	/**
	 * 
	 * @Title: setValidationModelToRedis 
	 * @Description: 将校验模板数据放到缓存中
	 * @throws Exception
	 */
	@Test
	public void setValidationModelToRedis() throws Exception{
		int i=0;
		for(Iterator<String> it = exitModelMap.keySet().iterator();it.hasNext();){
			String key = it.next();
			Object obj = exitModelMap.get(key);
			//将文件加载到缓存中
			redisDao.set(key, obj);
			i++;
		}
		assertTrue(i>0);
	}
	
	/**
	 * 
	 * @Title: getExitValidationFromRedis 
	 * @Description: 从缓存中获取存在的校验模板
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getExitValidationFromRedis() throws Exception{
		String fileName = "p0100001";
		Map<String,Object> putMap = null;
		Map<String,ValidationEntity> entity = null;
		try{
				for(Iterator<String> it = exitModelMap.keySet().iterator();it.hasNext();){
					String key = it.next();
					Object obj = exitModelMap.get(key);
					if(fileName.equals(key)){
						//将文件加载到缓存中
						putMap = (Map<String, Object>) obj;
						redisDao.set(key, obj);
						break;
					}
				}
				
				Object object = redisDao.get(fileName);
				entity = TransferCacheObjectUtil.getValidationMapFromObject(object);
		}catch(Exception e){
			
		}
		assertEquals(entity.toString(), putMap.toString());
	}
	
	@Test
	public void getNotExitValidationFromRedis() throws Exception{
		String fileName = "ssssssssss";
		Map<String,ValidationEntity> entity = null;
		try{
				
				Object object = redisDao.get(fileName);
				entity = TransferCacheObjectUtil.getValidationMapFromObject(object);
		}catch(Exception e){
			
		}
		assertNull(entity);
	}
}
