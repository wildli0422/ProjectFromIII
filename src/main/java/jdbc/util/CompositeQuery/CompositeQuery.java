package jdbc.util.CompositeQuery;

import java.util.*;

public class CompositeQuery {
	
	public static String get_aCondition_For_Oracle(String columnName,String value){
		
		String aCondition=null;
		
		if("roomTypeNo".equals(columnName) || "hostelNo".equals(columnName) 
			|| "facilityNo".equals(columnName) || "roomTypeContain".equals(columnName)
			|| "unBookNumber".equals(columnName)|| "maxUnBookNumber".equals(columnName)){//number
			aCondition = columnName+"="+value;
		
		}else if("roomTypeName".equals(columnName) || "roomTypeContent".equals(columnName)){
			//varchar
			aCondition =columnName+" like '%"+value+"%'";
		}else if("roomTypePrice".equals(columnName)){
			aCondition =columnName+" >= "+value;
		}
			
		return aCondition+" ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map){
		Set<String> keys=map.keySet();
		StringBuffer whereCondition =new StringBuffer();
		int count=0;
		for(String key:keys){
			String value=map.get(key)[0];
			if(value!=null && value.trim().length()!=0 && !"action".equals(key)){
				count++;
				String aCondition =get_aCondition_For_Oracle(key, value.trim());
				
				if(count==1){
					whereCondition.append(" where "+aCondition);
				}else{
					whereCondition.append(" and "+aCondition);
				}
				System.out.println("送出查詢的欄位數count = "+count);
			}//if
		}//for each
		return whereCondition.toString();
	}
	
	public static void main(String [] args){
		
		Map<String,String[]> map =new TreeMap<String, String[]>();
		map.put("roomTypeNo", new String[]{"3001"});
		map.put("roomTypeName",new String[]{"name"});
		map.put("hostelNo", new String[]{"2001"});
		map.put("facilityNo", new String[]{"4001"});
		map.put("roomTypeContain", new String[]{"1"});
		map.put("roomTypePrice", new String[]{"1"});
		map.put("unBookNumber", new String[]{"2"});
		map.put("maxUnBookNumber", new String[]{"5"});
		map.put("roomTypeContent", new String[]{"cotent"});
		
		String finalSQL="select * from roomType"
				+CompositeQuery.get_WhereCondition(map)
				+"order by roomTypeNo";
		System.out.println("●●finalSQL = " + finalSQL);
	}
}
