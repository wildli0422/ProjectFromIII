package com.hostel;

import java.util.Map;
import java.util.Set;

public class CompositeQuery_Hostel {

	public static String getQuery(String columnName, String value) {

		String aCondition = null;

		if ("hostelVerification".equals(columnName) || "hostelState".equals(columnName))
			aCondition = columnName + " like '" + value +"'";
		else if ("dealerName".equals(columnName)|| "hostelName".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";

		return aCondition + " ";
	}

	public static String getWhere(Map<String, String[]> map) {
		System.out.print("23");
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
