package bros.common.core.comm.netty.core;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;

/**
 * 
 * @ClassName: TcpTaskWorker
 * @Description: 业务处理线程类
 * @author 何鹏
 * @date 2016年6月30日 下午1:43:28
 * @version 1.0
 */
public class TcpTaskWorker implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(TcpTaskWorker.class);
			
	private ChannelHandlerContext ctx;
	private Object object;
	private final ApplicationContext context;//spring容器
	private IBusinessProcessService businessProcessService;//处理器
	private ISeverFormatService formatService;//报文编码解码器
	private AppConfig config;//配置信息
	
	public TcpTaskWorker(final ApplicationContext context,IBusinessProcessService businessProcessService,ChannelHandlerContext ctx, ISeverFormatService formatService,Object object,AppConfig config) {
		this.context = context;
		this.businessProcessService = businessProcessService;
		this.ctx = ctx;
		this.formatService = formatService;
		this.object = object;
		this.config = config;
	}

	@Override
	public void run() {
		logger.info("tcpip recieve format:{}",object);
		String returnMessage = MessageProcess.process(context, businessProcessService, formatService, object,config);
		logger.info("tcpip send format:{}",returnMessage);
		ctx.writeAndFlush(returnMessage);
	}

}
