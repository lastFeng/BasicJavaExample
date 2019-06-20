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
package threadlocalproblem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 11:09
 * 脏数据的生成
 */
public class DirtyDataInThreadLocal {
	public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
		// 使用固定大小为1的线程池，说明上一个的线程属性会被下一个线程属性复用
		ExecutorService pool = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 2; i++){
			Mythread thread = new Mythread();
			pool.execute(thread);
		}
	}

	private static class Mythread extends Thread{
		private static boolean flag = true;

		@Override
		public void run(){
			if (flag){
				// 第一个线程set后，并没有进行remove
				// 而第二个线程由于某种原因没有进行set操作
				threadLocal.set(this.getName() + ". session info.");
				flag = false;
			}
			System.out.println(this.getName() + " 线程是 " + threadLocal.get());
		}
	}
}