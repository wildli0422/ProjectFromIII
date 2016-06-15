import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class test {
	public static void main(String[] args){
		
//		Cale
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int dayofMonth=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year);
		System.out.println(month);
		System.out.println(dayofMonth);
	}
}
