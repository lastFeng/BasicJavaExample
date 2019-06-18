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

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/18 17:01
 */
public class EqualsObject {
	private int id;
	private String name;

	public EqualsObject(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object object){
		// 如果为null，或者非同类，则直接返回false
		if (object == null || this.getClass() != object.getClass()){
			return false;
		}

		// 如果引用指向同一个对象，则返回true
		if (this == object){
			return true;
		}

		// 需要强制转换来获取EqualsObject的方法
		EqualsObject temp = (EqualsObject) object;

		// 本示例判断标准是两个属性值相等，逻辑随业务场景不同而不同
		if (temp.getId() == this.getId() && temp.getName() == this.getName()){
			return true;
		}
		return false;
	}

	/**
	 * 要是没有实现hashCode，那么生成相同对象的引用不同
	 * */
	//@Override
	//public int hashCode() {
	//	return Objects.hash(id, name);
	//}
	public static void main(String[] args) {
		Set<EqualsObject> hasSet = new HashSet<>();
		EqualsObject a = new EqualsObject(1, "one");
		EqualsObject b = new EqualsObject(1, "one");
		EqualsObject c = new EqualsObject(1, "one");

		hasSet.add(a);
		hasSet.add(b);
		hasSet.add(c);

		// 输出3，存储了重复的元素
		System.out.println(hasSet.size());
	}
}