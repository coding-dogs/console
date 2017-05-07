package com.easyorder.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @Project : framework-core
 * @Program Name : com.ljt.framework.core.utils.CollectionUtils.java
 * @Description : Collection 工具类
 * @Author : wangchao
 * @Creation Date : 2016年5月13日 下午3:46:08
 * @ModificationHistory Who When What ---------- ------------- -----------------------------------
 *                      wangchao 2016年5月13日 create
 */
public abstract class CollectionUtils extends org.springframework.util.CollectionUtils {

  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }
}
