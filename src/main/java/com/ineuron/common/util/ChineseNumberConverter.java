package com.ineuron.common.util;

import java.util.HashMap;

/*
 * 用作汉字转换成数字的类
 */

public class ChineseNumberConverter {

	private static HashMap<String, Long> theMap = new HashMap<String, Long>() {

		private static final long serialVersionUID = 1L;
		{
			put("零", 0L);
			put("一", 1L);
			put("壹", 1L);
			put("二", 2L);
			put("两", 2L);
			put("贰", 2L);
			put("三", 3L);
			put("叁", 3L);
			put("四", 4L);
			put("肆", 4L);
			put("五", 5L);
			put("伍", 5L);
			put("六", 6L);
			put("陆", 6L);
			put("七", 7L);
			put("柒", 7L);
			put("八", 8L);
			put("捌", 8L);
			put("九", 9L);
			put("玖", 9L);
			put("十", 10L);
			put("拾", 10L);
			put("百", 100L);
			put("佰", 100L);
			put("千", 1000L);
			put("仟", 1000L);
			put("万", 10000L);
			put("亿", 100000000L);
		}
	};

	public static long convertToNum(String s) {
		// 中间及最终结果
		long result = 0;
		// 当前汉字代表的数值
		long num = 1;
		// 前一汉字的数值
		long flag = 1;
		// 当数值过亿时一亿以上的数值
		long k = 0;
		for (int i = 0; i < s.length(); i++) {
			String slice = s.substring(i, i + 1);
			if (theMap.get(slice) == null) {
				return 0;
			}
			if (theMap.get(slice) == 0) {
				continue;
			}
			if (theMap.get(slice) < 10) {
				num = theMap.get(slice);
				if (i == s.length() - 1) {
					result += num;
					return result + k;
				}
			} else if (theMap.get(slice) >= 10000) {
				// 当中间结果不为0并且前一数值是十的倍数时，可直接相乘
				if (result != 0 && flag % 10 == 0) {
					result *= theMap.get(slice);
				} else {
					result = (result + num) * theMap.get(slice);
				}
				if (theMap.get(slice) == 100000000) {
					k = result;
					result = 0;
				}
			} else {
				result += num * theMap.get(slice);
			}
			flag = theMap.get(slice);
		}
		return result + k;
	}
}