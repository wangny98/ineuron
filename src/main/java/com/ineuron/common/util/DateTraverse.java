package com.ineuron.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * traverse all the date in the specified period
 */

public class DateTraverse {

	public static List<Date> getDates(Date startDate, Date endDate){
	    if (!startDate.before(endDate))
	    	return null;
	    Long spi = endDate.getTime() - startDate.getTime();
	    Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

	    List<Date> dateList = new ArrayList<Date>();
	    dateList.add(startDate);
	    for (int i = 1; i <= step; i++) {
	        dateList.add(new Date(dateList.get(i - 1).getTime()
	                + (24 * 60 * 60 * 1000)));// 比上一天加一
	    }
	    return dateList;
	}
}