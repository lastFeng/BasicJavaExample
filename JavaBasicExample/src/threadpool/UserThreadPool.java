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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 9:52
 */
public class UserThreadPool {

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

	public static void main(String[] args) {
		// 缓存队列设置固定长度为2，为了快速触发rejectHandler
		BlockingQueue queue = new LinkedBlockingQueue(2);

		// 假设外部任务线程的来源由机房1和机房2的混合调用
		UserThreadFactory factory1 = new UserThreadFactory("第1机房");
		UserThreadFactory factory2 = new UserThreadFactory("第2机房");

		UserRejectHandler handler = new UserRejectHandler();

		// 核心线程为1， 最大线程为2，为了快速触发rejectHandler
		ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(1, 2,
			60, TimeUnit.SECONDS, queue, factory1, handler);
		// 核心线程为1， 最大线程为2，为了快速触发rejectHandler
		ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 2,
			60, TimeUnit.SECONDS, queue, factory2, handler);

		// 创建400个线程任务
		Runnable task = new Task();
		for (int i = 0; i < 200; i++){
			threadPoolExecutor1.execute(task);
			threadPoolExecutor2.execute(task);
		}
	}
}