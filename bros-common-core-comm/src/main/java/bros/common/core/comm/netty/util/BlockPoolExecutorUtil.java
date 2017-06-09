package bros.common.core.comm.netty.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @ClassName: BlockPoolExecutorUtil 
 * @Description: netty阻塞式业务处理线程池工具类
 * @author 何鹏
 * @date 2016年7月16日 下午9:14:05 
 * @version 1.0
 */
public class BlockPoolExecutorUtil {

    /**
     * 阻塞的ExecutorService
     *
     * @param size
     * @return
     */
    public static ExecutorService newBlockingExecutorsUseCallerRun(int size) {
        return new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}
