package bros.common.core.comm.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.comm.netty.core.HttpTaskWorker;
import bros.common.core.comm.netty.util.BlockPoolExecutorUtil;
import bros.common.core.comm.netty.util.MetricThread;
/**
 * 
 * @ClassName: HttpServerHandler 
 * @Description: http协议服务端报文处理器
 * @author 何鹏
 * @date 2016年7月16日 下午9:10:56 
 * @version 1.0
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

	private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);
	
	/**
	 * 业务逻辑线程池(业务逻辑最好跟netty io线程分开处理，线程切换虽会带来一定的性能损耗，但可以防止业务逻辑阻塞io线程)
	 */
    private final static ExecutorService workerThreadService = BlockPoolExecutorUtil.newBlockingExecutorsUseCallerRun(Runtime.getRuntime().availableProcessors() * 2);
    /**
     * 性能监听工具
     */
    private static MetricThread metricThread = new MetricThread("fixed thread");
	
	private final ApplicationContext context;// spring容器
	private IBusinessProcessService businessProcessService;// 处理器
	private ISeverFormatService formatService;//报文编码解码器
	private AppConfig config;

	public HttpServerHandler(final ApplicationContext context,
			IBusinessProcessService businessProcessService,ISeverFormatService formatService,AppConfig config) {
		this.context = context;
		this.businessProcessService = businessProcessService;
		this.formatService = formatService;
		this.config = config;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error("发生异常，关闭链路：", cause);
		ctx.close();
	}
	
	/**
	 * 
	 * @Title: messageReceived 
	 * @Description: 接收完报文之后进行数据处理
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof DefaultFullHttpRequest){
			DefaultFullHttpRequest defaultRequest = (DefaultFullHttpRequest) msg;			
			ByteBuf buff = defaultRequest.content();
			String recieveMsg = buff.toString(CharsetUtil.UTF_8);//收到的报文数据
			logger.info("http message recieve body format:{}", recieveMsg);
			
	        metricThread.increment();
	        workerThreadService.submit(new HttpTaskWorker(context,businessProcessService,ctx, formatService,recieveMsg,config));

		}
	}
	


	
}