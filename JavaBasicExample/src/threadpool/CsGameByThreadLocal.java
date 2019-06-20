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

import java.util.concurrent.ThreadLocalRandom;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 10:36
 */
public class CsGameByThreadLocal {
	private static final Integer BULLET_NUMBER = 1500;
	private static final Integer KILLED_ENEMIES = 0;
	private static final Integer LIFE_VALUE = 10;
	private static final Integer TOTAL_PLAYERS = 10;

	// 随机数用来展示每个对象的不同的数据
	private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

	// 初始化子弹数
	private static final ThreadLocal<Integer> BULLET_NUMBER_THREADLOCAL = ThreadLocal.withInitial(() -> BULLET_NUMBER);

	// 初始化杀敌数
	private static final ThreadLocal<Integer> KILLED_ENEMIES_THREADLOCAL = ThreadLocal.withInitial(() -> KILLED_ENEMIES);

	// 初始化自己的命数
	private static final ThreadLocal<Integer> LIFE_VALUE_THREADLOCAL = ThreadLocal.withInitial(() -> LIFE_VALUE);

	// 定义每位成员
	private static class Play extends Thread{
		@Override
		public void run(){
			Integer bullets = BULLET_NUMBER_THREADLOCAL.get() - RANDOM.nextInt(BULLET_NUMBER);
			Integer killEnemies = KILLED_ENEMIES_THREADLOCAL.get() + RANDOM.nextInt(TOTAL_PLAYERS / 2);
			Integer lifeValue = LIFE_VALUE_THREADLOCAL.get() - RANDOM.nextInt(LIFE_VALUE);

			System.out.println(getName() + ", BULLET_NUMBER is " + bullets);
			System.out.println(getName() + ", KILLED_EMEMIES is " + killEnemies);
			System.out.println(getName() + ", LIFE_VALUE is " + lifeValue);

			BULLET_NUMBER_THREADLOCAL.remove();
			KILLED_ENEMIES_THREADLOCAL.remove();
			LIFE_VALUE_THREADLOCAL.remove();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < TOTAL_PLAYERS; i++){
			new Play().start();
		}
	}
}