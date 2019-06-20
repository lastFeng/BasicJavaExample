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

import java.util.concurrent.TimeUnit;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 10:58
 * 在操作不可变对象，使用ThreadLocal进行操作时，每个结果都是相同的
 * 但是，在操作可变对象，使用ThreadLocal进行时，结果就变得不可控，
 * 因此，使用某个引用来操作共享对象时，依然要进行线程同步
 */
public class InitValueInThreadLocal {
	private static final StringBuilder INIT_VALUE = new StringBuilder("init");

	private static final ThreadLocal<StringBuilder> builder = ThreadLocal.withInitial(() -> INIT_VALUE);

	private static class AppendStringThread extends Thread{
		@Override
		public void run(){
			StringBuilder inThread = builder.get();
			for (int i = 0; i < 10; i++){
				inThread.append("-" + i);
			}

			System.out.println(inThread.toString());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++){
			new AppendStringThread().start();
		}

		TimeUnit.SECONDS.sleep(10);
	}
}