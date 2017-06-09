package bros.common.core.comm.netty.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.netty.config.AppConfig;
/**
 * 
 * @ClassName: BootServer 
 * @Description: 框架启动基类
 * @author 何鹏
 * @date 2016年6月30日 上午9:43:21 
 * @version 1.0
 */
public class BootServer implements Server {

    private static final Logger logger = LoggerFactory.getLogger(BootServer.class);
    ApplicationContext context;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap b;
    @SuppressWarnings("unused")
	private DefaultEventExecutorGroup executorGroup;
    /**
     * 配置信息
     */
    private AppConfig config;
    /**
     * server服务句柄解析器
     */
    @SuppressWarnings("rawtypes")
	private ChannelInitializer channelInitializer;
    /**
     * 线程池名称
     */
    private String poolName;
    
    

    /**
     * 
     * <p>Title: 服务构造方法</p> 
     * <p>Description: 用于配置不同的服务</p> 
     * @param config 服务配置信息
     * @param context spring容器
     * @param channelInitializer  服务解析句柄
     * @param poolName 线程池名称
     */
    @SuppressWarnings("rawtypes")
	public BootServer(AppConfig config, ApplicationContext context,ChannelInitializer channelInitializer,String poolName) {
        this.config = config;
        this.context = context;
        this.channelInitializer = channelInitializer;
        this.poolName = poolName;
        init();
    }

	public void init() {
    	//Runtime.getRuntime().availableProcessors()  处理器个数
        //executorGroup = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2, new DefaultThreadFactory("decode-worker-thread-pool"));
        executorGroup = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2, new DefaultThreadFactory(poolName));
        init(channelInitializer);
    }


    public void start() {
        try {
            ChannelFuture f = b.bind(config.getPort()).sync();
            logger.info("server start:{}", config.getPort());
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        	logger.error(e.getMessage(), e);
        } finally {
            stop();
        }
    }

    @SuppressWarnings("rawtypes")
	private void init(ChannelInitializer channelInitializer) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, config.isTcpNoDelay())
                .option(ChannelOption.SO_KEEPALIVE, config.isSoKeepAlive())
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(channelInitializer);
    }


    public void stop() {
        if (bossGroup != null)
            bossGroup.shutdownGracefully();
        if (workerGroup != null)
            workerGroup.shutdownGracefully();
    }

    public BootServer stopWithJVMShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }));
        return this;
    }

}
