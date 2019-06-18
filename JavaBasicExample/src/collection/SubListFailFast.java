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
import java.util.List;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/18 17:18
 */
public class SubListFailFast {
	public static void main(String[] args) {
		List materList = new ArrayList();
		materList.add("one");
		materList.add("two");
		materList.add("three");
		materList.add("four");
		materList.add("five");

		List branchList = materList.subList(0, 3);

		// 下面三行要是不注释，会导致branchList操作出现异常 -- ConcurrentModificationException
		//materList.remove(0);
		//materList.add("ten");
		//materList.clear();

		// 下方四行全部能够正确执行
		branchList.clear();
		branchList.add("six");
		branchList.add("seven");
		branchList.remove(0);

		// 正常遍历结束，只有一个元素：seven
		for (Object t : branchList){
			System.out.println(t);
		}

		// 子列表修改导致主列表也被改动，输出：[seven, four, five]
		System.out.println(materList);
	}
}