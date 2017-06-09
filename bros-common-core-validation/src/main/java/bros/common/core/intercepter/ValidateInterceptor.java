package bros.common.core.intercepter;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import bros.common.core.annotation.Validation;
import bros.common.core.constants.BaseErrorCodeConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.ValidationCheck;

/**
 * 
 * @ClassName: ValidateInterceptor 
 * @Description: 字段校验拦截器
 * @author 何鹏
 * @date 2016年5月20日 下午4:14:51 
 * @version 1.0
 */
public class ValidateInterceptor implements MethodInterceptor {
	/**
	 * 
	 * @Title: invoke 
	 * @Description: 进行数据校验
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result = null;
		try{
			try{
				//数据校验
				validationParams(invocation);
			}catch(ServiceException exx){
				throw exx;
			}catch(Exception e){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0020,"数据校验失败",e);
			}
			
			//调用方法执行
			result = invocation.proceed();
		}catch(ServiceException ex){
			throw ex;
		}
		
		return result;
	}


	/**
	 * 
	 * @Title: validationParams 
	 * @Description: 进行数据校验
	 * @param invocation
	 * @throws ServiceException
	 */
	private void validationParams(MethodInvocation invocation) throws ServiceException{
		try{
			Method method = invocation.getMethod();//方法名
			Object[] object= invocation.getArguments();//参数
			if(object.length != 2){
				throw new ServiceException("","服务调用参数个数不为2个");
			}
			
			//没有validation标签，默认都校验报文头
			Object[] headObject = {object[0]};
			String[] headString = {"headMap"};
			//校验报文头
			ValidationCheck.validator(headObject, headString, "head");
			
			//校验报文体
			Validation validation = method.getAnnotation(Validation.class);
			if(validation != null){//Validation注解存在，需要进行数据校验
				String templeteName = validation.value();//注解值
				
				String[] paramName = validation.paramName();//关联校验模板中的参数与方法中的参数的对应关系，如果不需要校验，需要填写""
				if(ValidateUtil.isEmpty(templeteName)){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0030,"校验注解的value属性不能为空");
				}
				
				if(paramName.length != object.length){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0031,"方法参数与校验参数个数不匹配");
				}
				
				//调用验证方法进行验证
				//ValidationCheck.validator(object, paramName, templeteName);
				
				//区分校验
				Object[] bodyObject = {object[1]};
				String[] bodyString = {paramName[1]};
				ValidationCheck.validator(bodyObject, bodyString, templeteName);
				
			}
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0020,"数据校验失败",e);
		}
	}
}
