package bros.manage.auth.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bros.common.core.comm.route.service.IClientRouteTransService;
import bros.common.core.exception.ServiceException;
import bros.common.core.ftp.service.IFtpService;
import bros.common.core.util.JsonUtil;
import bros.common.core.util.ValidateUtil;
import bros.manage.auth.constants.AuthErrorCodeConstants;
import bros.pre.common.web.controller.CommonNoSessionController;

/**
 * 
 * @ClassName: InsideAuthController 
 * @Description: 内管授权
 * @author huangdazhou 
 * @date 2016年9月7日 下午2:29:48 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/auth")
public class InsideAuthNoSessionController extends CommonNoSessionController{
	private static final  Logger logger = LoggerFactory.getLogger(InsideAuthNoSessionController.class);
	/**
	 * 通讯服务
	 */
	@Autowired
	private IClientRouteTransService clientRouteTransService;
	/**
	 * ftp服务
	 */
	@Autowired
	private IFtpService ftpClientServer;
	
	
	/**
	 * 
	 * @Title: getAuthProcessPhoto 
	 * @Description: 根据任务id获取流程图片
	 * @param requstMap
	 * @param request
	 * @param response
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getInsideAuthProcessPhoto",method=RequestMethod.GET)
	public void getInsideAuthProcessPhoto(HttpServletRequest request,HttpServletResponse response) throws ServiceException {
		File file = null;
	    try {
	    	Object obj = request.getParameter("condition");
	    	JSONObject jsonObject = JSONObject.fromObject(obj);
	    	Map<String,Object> requstMap = JsonUtil.jsonObjecttoMap(jsonObject);
	    	Map<String, Object> resultMap = new HashMap<String, Object>();
	    	
	    	Map<String, Object> headMap=this.handleReqHeadData(requstMap);//报文头
			Map<String, Object> bodyMap=this.handleReqBodyData(requstMap);//报文体
			resultMap = clientRouteTransService.route(headMap,bodyMap);
			Map<String,Object> responseBody = this.handleRspBodyData(resultMap);
			if(responseBody == null || responseBody.size()<=0){
				throw new ServiceException(AuthErrorCodeConstants.MAUH0001,"返回信息不存在");
			}
			String ftpAbsolutePath = String.valueOf(responseBody.get("ftpAbsolutePath"));//ftp文件路径
			if(ValidateUtil.isEmpty(ftpAbsolutePath)){
				throw new ServiceException(AuthErrorCodeConstants.MAUH0002,"ftp路径不存在");
			}
			String directory = ftpAbsolutePath.substring(0,ftpAbsolutePath.lastIndexOf("/"));
			String downloadFile = ftpAbsolutePath.substring(ftpAbsolutePath.lastIndexOf("/")+1);
			Object object = ftpClientServer.getConnect();
			
			String saveFile = InsideAuthNoSessionController.class.getResource("/").getPath();
			logger.info("文件保存路径："+saveFile);
			ftpClientServer.download(directory, downloadFile, saveFile, object);
			
			file = new File(saveFile,downloadFile);
			InputStream in = new FileInputStream(file.getAbsolutePath());   
	        
	        response.setContentType("image/png");
	        OutputStream os = response.getOutputStream();
	        byte[] b = new byte[1024];  
			while( in.read(b)!= -1){ 
				os.write(b);     
			}
			in.close(); 
			os.flush();
			os.close();
	    } catch(ServiceException se){
	    	String message = "获取流程图失败：错误码："+se.getErrorCode()+"，错误信息："+se.getErrorMsg();
	    	logger.error(message, se);
	    }catch (Exception e) {
	    	String message = "获取流程图失败："+e.getMessage();
	    	logger.error(message, e);
	    }finally{
	    	if(file != null){
	    		file.delete();
	    	}
	    }
	}
}
