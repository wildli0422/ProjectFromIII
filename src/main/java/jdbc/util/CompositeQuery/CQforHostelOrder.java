package jdbc.util.CompositeQuery;

import java.util.Map;
import java.util.Set;

public class CQforHostelOrder {

	public static String get_aCondition_For_Oracle(String columnName,
			String value) {

		String aCondition = null;

		if ("hostelOrderNo".equals(columnName) || "hostelNo".equals(columnName)
				|| "tenantNo".equals(columnName)
				|| "tenantScore".equals(columnName)
				|| "hostelScore".equals(columnName)
				|| "customerQuantity".equals(columnName)) {// number
			aCondition = columnName + "=" + value;

		} else if ("paymentWay".equals(columnName)	// varchar
				|| "paymentState".equals(columnName)
				|| "orderState".equals(columnName)
				|| "hostelComment".equals(columnName)) {		
			aCondition = columnName + " like '%" + value + "%'";
		}else if ("hostelOrderDate".equals(columnName))     // 用於Oracle的date                     // 用於Oracle的date
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0
					&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1) {
					whereCondition.append(" where " + aCondition);
				} else {
					whereCondition.append(" and " + aCondition);
				}
				System.out.println("送出查詢的欄位數count = " + count);
			}// if
		}// for each
		return whereCondition.toString();
	}
}
