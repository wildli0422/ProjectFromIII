package com.dealer;

import java.util.Map;
import java.util.Set;

public class CompositeQuery_Dealer {

	public static String getQuery(String columnName, String value) {

		String aCondition = null;
		
		if ("dealerSex".equals(columnName) || "dealerState".equals(columnName))
			aCondition = columnName + " like '" + value +"'";
		else if ("dealerMail".equals(columnName)|| "dealerName".equals(columnName)
				|| "dealerAddress".equals(columnName)|| "dealerPhone".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";

		return aCondition + " ";
	}

	public static String getWhere(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer where = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = getQuery(key, value.trim());

				if (count == 1)
					where.append(" where " + aCondition);
				else
					where.append(" and " + aCondition);
			}
		}

		return where.toString();
	}

}
