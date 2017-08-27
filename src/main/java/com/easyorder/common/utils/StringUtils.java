package com.easyorder.common.utils;

/**
 * @Project : spring-boot-sample
 * @Program Name : com.ljt.springboot.common.utils.StringUtils.java
 * @Description : 字符串工具类
 * @Author : wangchao
 * @Creation Date : 2016-3-8 上午11:38:41
 * @ModificationHistory Who When What ---------- ------------- -----------------------------------
 *                      wangchao 2016-3-8 create
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {

  /**
   * @Description : 去除首字符串
   * @return : String
   * @Creation Date : 2015-4-23 上午11:36:48
   * @Author : wangchao
   */
  public static String removeFristStr(String str, String substr) {
    if (!hasText(str))
      return "";
    return str.substring(substr.length());
  }

  /**
   * @Description : 去除末字符串
   * @return : String
   * @Creation Date : 2016年5月21日 下午10:29:16
   * @Author : chichangchao
   */
  public static String removeEndStr(final String str, final String remove) {
    if (!hasText(str) || !hasText(remove)) {
      return "";
    }
    if (str.endsWith(remove)) {
      return str.substring(0, str.length() - remove.length());
    }
    return str;
  }

  /**
   * @Description : 去除上级元素
   * @return : String
   * @Creation Date : 2015-5-5 下午2:29:37
   * @Author : wangchao
   */
  public static String trimParent(String generateJson, String parent) {
    if (!hasText(generateJson))
      return "";
    StringBuilder resutl = new StringBuilder(generateJson.trim());
    resutl = resutl.deleteCharAt(0);
    resutl = resutl.deleteCharAt(resutl.length() - 1);
    return delete(resutl.toString(), "\"" + parent + "\":");
  }

  public static String generteBatchNum(String source, int length) {
    String value = String.valueOf(source);
    while (value.length() < length) {
      value = "0" + value;
    }
    return value;
  }

  /**
   * @Description : 字符串快速连接方法
   * @return : String
   * @Creation Date : 2016年5月6日 上午11:30:04
   * @Author : wangchao
   */
  public static String append(String... values) {
    if (values == null || values.length == 0)
      return "";
    StringBuilder builder = new StringBuilder();
    for (String val : values) {
      builder.append(val);
    }
    return builder.toString();
  }

  /**
   * @Description : 返回目标对象的字符值
   * @return : String
   * @Creation Date : 2016年7月7日 下午6:05:12
   * @Author : wangchao
   */
  public static String toString(Object target) {
    if (target == null)
      return "";
    return BeanUtils.toString(target);
  }


  public static boolean hasText(String str) {
    if ("null".equalsIgnoreCase(str) || "undefined".equalsIgnoreCase(str)) {
      return false;
    }
    return hasText((CharSequence) str);
  }
  
  /**
   * @Description:校验字符串是否为空
   * @Title: isNotEmpty
   * @date:2016年12月21日下午3:48:10
   * @author:zhanbq/zhanbq@lianjintai.com
   * @version 
   * @param str true:不为空 ; false:空
   * @return
   */
  public static boolean isNotEmpty(String str) {
    return !(str == null || "".equals(str));
  }

  /**
   * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
   * 例如：HelloWorld->HELLO_WORLD
   * 
   * @param name 转换前的驼峰式命名的字符串
   * @return 转换后下划线大写方式命名的字符串
   */
  public static String trans2Underscore(String name) {
    StringBuilder result = new StringBuilder();
    if (name != null && name.length() > 0) {
      // 将第一个字符处理成大写
      result.append(name.substring(0, 1).toUpperCase());
      // 循环处理其余字符
      for (int i = 1; i < name.length(); i++) {
        String s = name.substring(i, i + 1);
        // 在大写字母前添加下划线
        if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0)) && !s.trim().isEmpty()) {
          result.append("_");
        }
        // 其他字符直接转成大写
        result.append(s.toUpperCase());
      }
    }
    return result.toString();
  }

  /**
   * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
   * 例如：HELLO_WORLD->HelloWorld
   * 
   * @param name 转换前的下划线大写方式命名的字符串
   * @return 转换后的驼峰式命名的字符串
   */
  public static String camelName(String name) {
    StringBuilder result = new StringBuilder();
    // 快速检查
    if (name == null || name.isEmpty()) {
      // 没必要转换
      return "";
    } else if (!name.contains("_")) {
      // 不含下划线，仅将首字母小写
      return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
    // 用下划线将原始字符串分割
    String camels[] = name.split("_");
    for (String camel : camels) {
      // 跳过原始字符串中开头、结尾的下换线或双重下划线
      if (camel.isEmpty()) {
        continue;
      }
      // 处理真正的驼峰片段
      if (result.length() == 0) {
        // 第一个驼峰片段，全部字母都小写
        result.append(camel.toLowerCase());
      } else {
        // 其他的驼峰片段，首字母大写
        result.append(camel.substring(0, 1).toUpperCase());
        result.append(camel.substring(1).toLowerCase());
      }
    }
    return result.toString();
  }

  /**
   * @Description : 将脚本名称转换为版本号scriptToVersion
   * @return : String
   * @Creation Date : 2016年12月23日 下午3:11:08
   * @Author : zhiqiang zhang
   */
  public static String scriptToVersion(String param) {
    return param.substring(1, param.indexOf("__")).replaceAll("_", ".");
  }
  
  /**
   *  @Description	: 判断指定字符串是否存在于数组中
   *  @param sourceArray
   *  @param str
   *  @return	
   *  @return         : boolean
   *  @Creation Date  : 2017年2月17日 上午9:58:38 
   *  @Author         : qiudequan
   */
  public static boolean arrayContainsStr(String[] sourceArray, String str) {
    if(sourceArray == null || sourceArray.length == 0 || isEmpty(str)) {
      return false;
    }
    
    for (int i = 0, length = sourceArray.length; i < length; i++) {
      if(str.equals(sourceArray[i])) {
        return true;
      }
    }
    return false;
  }
  
  /**
   *  @Description	: 获取字符串，为空则返回默认值
   *  @param str
   *  @param defaultValue
   *  @return	
   *  @return         : String
   *  @Creation Date  : 2017年2月20日 下午6:31:34 
   *  @Author         : qiudequan
   */
  public static String getOrDefault(String str, String defaultValue) {
    if(hasText(str)) {
      return str;
    }
    return defaultValue;
  }
  
  /**
   *  @Description 	  : JSP页面列表和详细信息中-字段没有单位时，列表和详细信息中字段为空显示“-”
   *  @param field    : 字段
   *  @return         : String
   *  @Creation Date  : 2017年3月14日 上午11:20:15
   *  @Author         : 邓聪
   */
  public static String getDefaultValue(String field) {
	  return getHasUnitValue(field,null);
  }
  
  /**
   *  @Description 	  : JSP页面列表和详细信息中-字段有单位时，为空仅显示“-”
   *  @param field    : 字段
   *  @param unit     : 字段单位
   *  @return         : String
   *  @Creation Date  : 2017年3月14日 上午11:20:15
   *  @Author         : 邓聪
   */
  public static String getHasUnitValue(String field, String unit ) {
    if(hasText(field)) {
      if(hasText(unit)){
          return field+unit;
      }else{
          return field;  
      }
    }
    return "-";
  }
  
  /**
   * 
   *  @Description  : 联系电话脱敏。规则：无论长度，始终替换第4至第7位为*号。若不满4位则不替换。不满7位则有多少替换多少。
   *  @Return         : String
   *  @Creation Date  : 2017年4月18日 下午4:04:11 
   *  @Author         : wangshouming
   */
  public static String phoneDesensitization(String phoneNum){
    String result = "";
    if( null == phoneNum || "".equals(phoneNum)){
      return result;
    }
    StringBuffer buffer = new StringBuffer(phoneNum);
    if(phoneNum.length()<=3){
      result = phoneNum;
    }else{
      int length = phoneNum.length() - 3;
      if(length >= 4){
        result = buffer.replace(3, 7, "****").toString();
      }else{
        result = buffer.replace(3, 3 + length, int2Star(length)).toString();
      }
    }
    return result;
  }
  
  public static String int2Star(int num){
    String starStr = "";
    for (int i = 0; i < num; i++) {
      starStr = starStr + "*";
    }
    return starStr;
  }
  
  /**
   * 用于多规格列表展示
   * @param json
   * @return
   */
//  public static String specificationShow(String json) {
//  	SpecificationItem specificationItem = GSONUtils.fromJson(json, SpecificationItem.class);
//  	if(specificationItem == null) {
//  		return Constants.EMPTY_STRING;
//  	}
//  	// 获取规格值
//  	List<SpecificationItem> children = specificationItem.getChildren();
//  	if(CollectionUtils.isEmpty(children)) {
//  		return Constants.EMPTY_STRING;
//  	}
//  	StringBuilder specBuilder = new StringBuilder();
//  	for (SpecificationItem si : children) {
//			specBuilder.append(si.getName()).append(Constants.BLANK_SPACE_STRING);
//		}
//  	if(specBuilder.length() != 0) { 
//  		specBuilder.deleteCharAt(specBuilder.length() - 1);
//  	}
//  	return specBuilder.toString();
//  }
  
}
