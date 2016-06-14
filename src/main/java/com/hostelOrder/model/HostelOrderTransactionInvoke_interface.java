/*本程式是使用Transaction產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelOrder.model;
import java.sql.*;
import java.util.List;

import tool.cart.model.OrderCartVO;
public interface HostelOrderTransactionInvoke_interface {

public int doTransaction(Connection con,HostelOrderVO hostelOrderVO,List<OrderCartVO> cartList)throws Exception ;

}