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

import java.util.concurrent.Semaphore;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/19 18:25
 */
public class SemaphoreExample {
	public static void main(String[] args) {
		// 设定3个信号量，即3个服务窗口
		Semaphore semaphore = new Semaphore(3);

		// 这个队列排了5个人
		for (int i = 1; i <= 5; i++){
			new SecurityCheckThread(i, semaphore).start();
		}
	}

	private static class SecurityCheckThread extends Thread{
		private int seq;
		private Semaphore semaphore;

		public SecurityCheckThread(int seq, Semaphore semaphore){
			this.semaphore = semaphore;
			this.seq = seq;
		}

		@Override
		public void run(){
			try {
				// 请求
				semaphore.acquire();
				System.out.println("No." + seq + "乘客，正在查验中...");

				// 假设号码是整除2的人是身份可疑的，需要花更长时间来安检
				if (seq % 2 == 0){
					Thread.sleep(1000);
					System.out.println("No." + seq + "乘客，身份可疑，不能出国！！");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
				System.out.println("No." + seq + "乘客已完成查验。");
			}
		}
	}
}