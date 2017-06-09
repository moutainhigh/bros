package bros.common.core.comm.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.comm.netty.core.TcpTaskWorker;
import bros.common.core.comm.netty.util.BlockPoolExecutorUtil;
import bros.common.core.comm.netty.util.MetricThread;

public class FixedLengthServerHandler extends ChannelHandlerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(FixedLengthServerHandler.class);
	/**
	 * 业务逻辑线程池(业务逻辑最好跟netty io线程分开处理，线程切换虽会带来一定的性能损耗，但可以防止业务逻辑阻塞io线程)
	 */
    private final static ExecutorService workerThreadService = BlockPoolExecutorUtil.newBlockingExecutorsUseCallerRun(Runtime.getRuntime().availableProcessors() * 2);
    /**
     * 性能监听工具
     */
    private static MetricThread metricThread = new MetricThread("fixed thread");
    
	private final ApplicationContext context;//spring容器
	private IBusinessProcessService businessProcessService;//处理器
	private ISeverFormatService formatService;//报文编码解码器
	private AppConfig config;
	
	public FixedLengthServerHandler(final ApplicationContext context,IBusinessProcessService businessProcessService,ISeverFormatService formatService,AppConfig config) {
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

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		logger.info("netty recieve fixedlengh format:{}", msg);
        metricThread.increment();
        if (msg instanceof String) {
            workerThreadService.submit(new TcpTaskWorker(context,businessProcessService,ctx, formatService,msg,config));
        }
	}

}
