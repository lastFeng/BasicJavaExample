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

import java.util.Comparator;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/18 16:33
 */
public class ComparatorSearchResult implements Comparator<ComparableSearchResult> {
	@Override
	public int compare(ComparableSearchResult o1, ComparableSearchResult o2) {
		// 相关度是第一排准则，更高者排前
		if (o1.relativeRatio !=  o2.relativeRatio){
			return o1.relativeRatio > o2.relativeRatio ? 1 : -1;
		}

		// 如果相关度一样，则最近订单数多者排前
		if (o1.recentOrders != o2.recentOrders){
			return o1.recentOrders > o2.recentOrders ? 1 : -1;
		}

		// 如果相关度和最近订单数都一样，则浏览多者排前
		if (o1.count != o2.count){
			return o1.count > o2.count ? 1 : -1;
		}
		return 0;
	}
}