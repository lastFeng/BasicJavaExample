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
package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/19 18:16
 */
public class CountDownLatchTest {
	/**
	 * CountDownLatch:一次性同步，等到所有人都到齐了才开始执行
	 * CyclicBarrier是基于同步到达某个点的信号量触发机制，可以循环使用
	 * */
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch count = new CountDownLatch(3);
		long start = System.currentTimeMillis();

		Thread thread = new TransLateThread("1st content", count);
		Thread thread1 = new TransLateThread("2nd contend", count);
		Thread thread2 = new TransLateThread("3rd content", count);

		thread.start();
		thread1.start();
		thread2.start();

		count.await(10, TimeUnit.SECONDS);

		long end = System.currentTimeMillis();
		System.out.println("所有线程执行完成");

		System.out.println("Time is " + (end - start) + "ms");
		// 给调用返回翻译结果

	}
}

class TransLateThread extends Thread{
	private String content;
	private final CountDownLatch count;

	public TransLateThread(String content, CountDownLatch count){
		this.content = content;
		this.count = count;
	}

	@Override
	public void run(){
		// 在某种情况下，执行翻译解析时，抛出异常
		if (Math.random() > 0.5){
			throw new RuntimeException("原文存在非法字符！");
		}

		System.out.println(content + "你的翻译已完成，译文是...");
		count.countDown();
	}
}