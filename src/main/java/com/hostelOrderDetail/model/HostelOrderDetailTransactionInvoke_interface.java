/*本程式是使用Transaction產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrderDetail.model;
import java.sql.*;
public interface HostelOrderDetailTransactionInvoke_interface {

public void doTransaction (Connection con,HostelOrderDetailVO hostelOrderDetailVO)throws Exception;

}