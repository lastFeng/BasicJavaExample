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

import java.util.concurrent.atomic.LongAdder;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/19 17:30
 */
public class VolatileNotAtomic {
	private static volatile long count = 0L;
	private static final int NUMBER = 10000;

	public static void main(String[] args) {
		Thread substractThread = new SubstractThread();

		substractThread.start();

		for (int i=0; i < NUMBER; i++){
			count++;
		}

		// 等待减法线程结束
		while (substractThread.isAlive()){}

		System.out.println("count最后的值为： " + count);

	}

	private static class SubstractThread extends Thread{
		@Override
		public void run(){
			for (int i=0; i < NUMBER; i++){
				count--;
			}
		}
	}
}