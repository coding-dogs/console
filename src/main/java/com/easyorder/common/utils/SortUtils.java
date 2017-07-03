package com.easyorder.common.utils;

import java.util.List;

import com.easyorder.modules.product.entity.ProductCategory;

public class SortUtils {
	/**
	 * 商品分类排序
	 */
	public static void productCategorySort(List<ProductCategory> list, List<ProductCategory> sourceList, String parentId, boolean cascade) {
		for (int i = 0; i < sourceList.size(); i++) {
			ProductCategory e = sourceList.get(i);
			if (e.getPid() != null && e.getPid().equals(parentId)) {
				list.add(e);
				if (cascade) {
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j = 0; j < sourceList.size(); j++) {
						ProductCategory child = sourceList.get(j);
						if (child.getPid() != null && child.getPid().equals(e.getId())) {
							productCategorySort(list, sourceList, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}
}
