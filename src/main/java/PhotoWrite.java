
import java.sql.*;
import java.io.*;


class PhotoWrite {
	/*
	 * ���O!!�ϤӤj�L�k���J�idb��
	 * ....�n���]���O
	 * �o���D�ڥ�����f
	 * �ڤ����D���D�b��
	 * ����X�i�ϴN�i�H�F
	 * */

        static {
            try {
                 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }

        public static void main(String argv[]) {
              Connection con = null;
              PreparedStatement pstmt = null;
              String url = "jdbc:oracle:thin:@localhost:1521:xe";
              String userid = "ZA105G3";
              String passwd = "ZA105G3";
              
              File fileForHostel=new File("C:\\Users\\cuser\\Desktop\\Project\\tablePic\\hostelPic");
              File fileForRoomType=new File("C:\\Users\\cuser\\Desktop\\Project\\tablePic\\roomTypePic");
              File[] hostelList=fileForHostel.listFiles();
              File[] roomTypeList=fileForRoomType.listFiles();
              File hostelPic=null;
              File roomTypePic=null;
              int count=0;
              int n=0;
              FileInputStream fin=null;
              FileInputStream fin2=null;
              File verification=null;
              
              try {
            	  
            	  con = DriverManager.getConnection(url, userid, passwd);
            	  
            	  verification=new File("C:\\Users\\cuser\\Desktop\\Project\\tablePic","verification.png");
            	  
				for(int i=0;i<10;i++){
					
					if(i<10)
					hostelPic =new File(fileForHostel,"0"+i+".jpg");
					else if(i>=10){
						hostelPic =new File(fileForHostel,(i/10)*10+(i%10)+".jpg");
					}
					long flen=hostelPic.length();
					long flen2=verification.length();
					fin=new FileInputStream(hostelPic);
					fin2=new FileInputStream(verification);
					
//					pstmt=con.prepareStatement("update hostel set hostelPicture=?,dealerVerify=? where hostelNo='"+"20"+((i+1)/10)+((i+1)%10)+"'");
//					pstmt.setBinaryStream(1,fin,flen);
//					pstmt.setBinaryStream(2,fin2,flen2);
//					int rowsUpdated = pstmt.executeUpdate();
//                    count += rowsUpdated;
//					pstmt=con.prepareStatement("update hostelPic set hostelPhoto=? where hostelPicNo='"+"60"+((i+1)/10)+((i+1)%10)+"'");
//					pstmt.setBinaryStream(1, fin,flen);
//					int rowsUpdated = pstmt.executeUpdate();
//	                    count += rowsUpdated;
				}

				for(int i=0;i<10;i++){
					if(i<10)
					roomTypePic=new File(fileForRoomType,"0"+i+".jpg");
					else if(i>=10){
						roomTypePic =new File(fileForRoomType,(i/10)*10+(i%10)+".jpg");
					}
					long flen=roomTypePic.length();
					fin=new FileInputStream(roomTypePic);
					
//					pstmt=con.prepareStatement("update roomType set roomTypePicture=? where roomTypeNo='"+"30"+((i+1)/10)+((i+1)%10)+"'");
//					pstmt.setBinaryStream(1,fin,flen);
//					int rowsUpdated = pstmt.executeUpdate();
//	                    count += rowsUpdated;
					pstmt=con.prepareStatement("update roomTypePic set roomTypePhoto=? where roomTypePicNo='"+"70"+((i+1)/10)+((i+1)%10)+"'");
					pstmt.setBinaryStream(1,fin,flen);
					int rowsUpdated = pstmt.executeUpdate();
	                    count += rowsUpdated;
				}
				
				
				pstmt.close();
				fin.close();
				System.out.print("Changed " + count);
              } catch (Exception e) {
				e.printStackTrace();
              }finally {
                    try {
                      con.close();
                    }catch (SQLException e) {
                    	e.printStackTrace();
                    }
             }
              
              
      }
}
