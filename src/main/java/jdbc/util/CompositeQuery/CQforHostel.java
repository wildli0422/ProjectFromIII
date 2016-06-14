package jdbc.util.CompositeQuery;

import java.util.Map;
import java.util.Set;

public class CQforHostel {
	
	public static String get_aCondition_For_Oracle(String columnName,String value){
		String aCondition=null;
		
		if("hostelNo".equals(columnName) || "dealerNo".equals(columnName)
				|| "hostelVerificatio".equals(columnName) 
				|| "hostelState".equals(columnName)){
			aCondition = columnName+"="+value;
			
		}else if("hostelName".equals(columnName) || "hostelPhone".equals(columnName)
				|| "hostelAddress".equals(columnName)
				|| "hostelWebPages".equals(columnName)){
			aCondition = columnName+"like '%"+value+"%'";
		}
		
		return aCondition+" ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map){
		Set<String> keys=map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count=0;
		for(String key:keys){
			String value =map.get(key)[0];
			if(value !=null && value.trim().length() !=0
					&& !"action".equals(key)){
				count++;
				String aCondition =get_aCondition_For_Oracle(key, value.trim());
				
				if(count==1){
					whereCondition.append(" where "+aCondition);
				}else{
					whereCondition.append(" and "+aCondition);
				}
				System.out.println("�e�X�d�ߪ�����count = " + count);
			}//if
		}//for each
		return whereCondition.toString();
	}
}
