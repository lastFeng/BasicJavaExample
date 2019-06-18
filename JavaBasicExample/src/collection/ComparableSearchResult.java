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

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/18 15:54
 * Comparable: --- compareTo --- 自己跟自己比
 * Comparator: --- compare   --- 第三方比较器
 */
public class ComparableSearchResult implements Comparable<ComparableSearchResult>{
	/**
	 * 先根据相关度排序，然后根据访问数排序
	 * */
	int relativeRatio;
	long count;
	int recentOrders;

	private ComparableSearchResult(int relativeRatio, long count){
		this.relativeRatio = relativeRatio;
		this.count = count;
	}

	@Override
	public int compareTo(ComparableSearchResult o) {
		// 先比较相关度
		if (this.relativeRatio != o.relativeRatio){
			return this.relativeRatio > o.relativeRatio ? 1 : -1;
		}

		// 相关度相等比较浏览数
		if (this.count != o.count){
			return this.count > o.count ? 1 : -1;
		}
		return 0;
	}

	public void setRecentOrders(int recentOrders){
		this.recentOrders = recentOrders;
	}
}