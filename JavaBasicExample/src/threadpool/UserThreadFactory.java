/*
 * Copyright 2001-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 9:21
 *
 *
 * 线程池的作用：
 *  利用线程池管理并复用线程、控制最大并发数等
 *  实现任务线程队列缓存策略和拒绝机制
 *  实现某些与时间相关的功能，如定时执行、周期执行等
 *  隔离线程环境。比如：交易服务和搜索服务在同一台服务器上，分别开启两个线程池，交易线程的资源消耗明显要大；
 *              因此，通过配置独立的线程池，将较慢的交易服务于搜索服务隔离开，避免各服务线程相互影响
 */
public class UserThreadFactory implements ThreadFactory {

	private final String namePrefix;
	private final AtomicInteger nextId = new AtomicInteger(1);

	/**
	 * 定义线程组名称，在使用jstack来排查线程问题时，非常有帮助
	 * */
	public UserThreadFactory(String namePrefix){
		this.namePrefix = namePrefix;
	}

	@Override
	public Thread newThread(Runnable task) {
		String name = namePrefix + nextId.getAndIncrement();
		Thread thread = new Thread(task, name);
		System.out.println(thread.getName());
		return thread;
	}

	/**
	 * @param corePoolSize 常驻核心线程数。如果等于0，则任务执行完之后，没有任何请求进入时销毁线程池的线程；
	 *                     如果大于0，即使本地任务执行完毕，核心线程也不会被销毁。
	 *                     这个值设置过大会浪费资源，设置过小会导致线程频繁创建和销毁
	 * @param maximumPoolSize 表示线程池能够容纳同时执行的最大线程数。其值必须大于或等于1。
	 *                        如果待执行的线程数大于此值，需要使用workQueue队列来缓存
	 *                        如果corePoolSize和maximumPoolSize相等，即是固定大小线程池
	 * @param keepAliveTime 表示线程池中的线程空闲时间，当空闲时间达到keepAliveTime值时，
	 *                         线程会被销毁，直到只剩下corePoolSize个线程为止，避免浪费内存和句柄资源；
	 *                      在默认情况下，当线程池的线程数大于corePoolSize时，keepAliveTime才会起作用。
	 *                      但是，当ThreadPoolExecutor的allowCoreThreadTimeOut变量设置为true时，核心线程超市后也会被回收。
	 * @param unit 表示时间单位。通常有TimeUnit.SECONDS, TimeUnit.DAYS, TimeUnit.HOURS等等
	 * @param workQueue 表示缓存队列。
	 *                  当请求的线程数大于corePoolSize时，线程进入BlockingQueue阻塞队列。
	 * @param threadFactory 表示线程工厂。
	 *                      用来生产一组相同任务的线程。
	 *                      线程池的命名是通过这个factory增加组名前缀来实现的
	 * @param handler 表示拒绝策略的对象。
	 *                当workQueue的任务缓存区到达上限后，并且活动线程数大于maximumPoolSize时，线程池通过该策略处理请求。--限流保护
	 *                策略有三种：
	 *                  保存到数据库进行销峰填谷。在空闲时再提取出来执行
	 *                  转向某个提示页面
	 *                  打印日志
	 *                提供：
	 *                  AbortPolicy(默认)：丢弃任务并抛出RejectExecutionException异常
	 *                  DiscardPolicy：丢弃任务，但是不抛出异常
	 *                  DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加入队列中
	 *                  CallerRunsPolicy：调用任务的run()方法绕过线程池直接执行
	 * @return ThreadPoolExecutor Object
	 * */
	public ThreadPoolExecutor threadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
	                                             TimeUnit unit, BlockingQueue<Runnable> workQueue,
	                                             ThreadFactory threadFactory, RejectedExecutionHandler handler){
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}
}

/**
 * 任务执行体
 * */
class Task implements Runnable{
	private final AtomicLong count = new AtomicLong(0L);
	@Override
	public void run() {
		System.out.println("running_" + count.getAndIncrement());
	}
}