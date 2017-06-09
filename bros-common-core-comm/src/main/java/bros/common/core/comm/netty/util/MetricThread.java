package bros.common.core.comm.netty.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @ClassName: MetricThread 
 * @Description: 性能监控工具类
 * @author 何鹏
 * @date 2016年6月30日 下午1:40:30 
 * @version 1.0
 */
public class MetricThread extends TimerTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MetricThread.class);

    private String name;

    private AtomicInteger ps = new AtomicInteger(0);

    public MetricThread(String name) {
        this.name = name;
        new Timer().scheduleAtFixedRate(this, 1000L, 1000L);
    }

    public void increment() {
        ps.incrementAndGet();
    }

    @Override
    public void run() {
    	//logger.info("[name=" + name + "], " + "[ps/s=" + ps.get() + "]");
        ps = new AtomicInteger(0);
    }
}