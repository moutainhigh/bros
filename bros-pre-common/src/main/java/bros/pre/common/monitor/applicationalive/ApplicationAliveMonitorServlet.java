package bros.pre.common.monitor.applicationalive;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.db.IMyBatisDaoSysDao;

/**
 * 
 * @ClassName: ApplicationAliveMonitorServlet
 * @Description: 验证当前系统是否存活
 * @author hepeng
 * @date 2016-12-13 03:51:13
 * @version 1.0
 */
public class ApplicationAliveMonitorServlet extends HttpServlet {

	private static final long serialVersionUID = -3434644762364422805L;

	private String errorCode = "00000000";
	private String errorMsg = "系统正常";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter print = null;
		errorCode = BaseParamsConstants.TRADE_SUCCESS_FLAG;
		errorMsg = "系统正常";
		try {
		    response.setContentType("text/html;charset=utf-8");  
			Map<String, Object> returnMap = new HashMap<String, Object>();
			try {
				 ServletContext servletContext = this.getServletContext();  
			     WebApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(servletContext);
			     IMyBatisDaoSysDao myBatisDaoSysDao = (IMyBatisDaoSysDao) context.getBean("myBatisDaoSysDao");
				 myBatisDaoSysDao.selectTotalNum("mybatis.mapper.single.table.puberrcode.selectCountMonitorApplicationAlive");
			} catch (Exception ex) {
				errorCode = "0001";
				errorMsg += ",数据库不正常";
			}
			returnMap.put("errorCode", errorCode);
			returnMap.put("errorMsg", errorMsg);
			print = response.getWriter();
			print.println(returnMap.toString());
		} finally {
			if (print != null) {
				print.close();
			}
		}
	}
	
	

}
