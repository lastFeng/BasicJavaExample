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
package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/18 17:24
 */
public class ArrayListFailFast {
	public static void main(String[] args) {
		// 通过foreach来遍历元素，可以会出现错误
		List<String> list = new ArrayList<>();
		list.add("one");
		list.add("two");
		list.add("three");
		for (String s: list){
			if ("two".equals(s)){
				list.remove(s);
			}
		}
		System.out.println(list);

		// 最好利用迭代机制
		// 在多线程并发，需要在遍历时加上锁
		// 或者使用并发容器CopyOnWriteArrayList代替ArrayList，该容器会对Iterator进行加锁操作。
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()){
			synchronized (ArrayListFailFast.class){
				String item = iterator.next();
				if ("two".equals(item)){
					iterator.remove();
				}
			}
		}

		// 对COW进行add是灾难性的，其适用于读多写极少的场景
		// 其实fail-safe机制的。fail-safe：在安全的副本（或者没有修改操作的正本）上进行遍历，集合修改与副本的遍历没有任何关系
		// 缺点--读不到最新数据
		List<Long> copy = new CopyOnWriteArrayList<>();
		long start = System.nanoTime();
		for (int i = 0; i < 20 * 10000; i++){
			copy.add(System.nanoTime());
		}
		long end = System.nanoTime();
		System.out.println((end - start) + "ns");
	}
}