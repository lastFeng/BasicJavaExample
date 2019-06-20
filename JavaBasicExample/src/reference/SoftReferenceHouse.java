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
package reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/20 10:27
 */
public class SoftReferenceHouse {
	public static void main(String[] args) {
		// 强引用
		List<House> houses = new ArrayList<>();

		// 弱引用
		List<SoftReference> houses1 = new ArrayList<>();

		// 剧情反转注释处
		int i = 0;
		while (true){
			houses.add(new House());

			// 剧情反转注释处
			SoftReference<House> buyer2 = new SoftReference<>(new House());
			houses1.add(buyer2);

			System.out.println("i=" + i++);
		}
	}
}

class House{
	private static final Integer DOOR_NUMBER = 2000;
	public Door[] doors = new Door[DOOR_NUMBER];

	class Door{}
}