

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MailService;
import com.dealer.model.DealerService;
import com.tenant.model.TenantService;


@WebServlet("/forget")
public class forget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String who = req.getParameter("who");
		String mail = req.getParameter("Mail");
		MailService ms = new MailService();
		
		String pw = null;
		if("tenant".equals(who)) {
			TenantService tenantService = new TenantService();
			System.out.println(mail);
			pw = tenantService.getTenantPassword(mail);
			
			System.out.println(pw);
			
			
		}
		
		if("dealer".equals(who)) {
			DealerService dealerService = new DealerService();
			pw = dealerService.getDealerPassword(mail);
		}
		
		if (!(pw == null)) {
			String message = "你好，你在eChoice的 密碼為:" + pw ; 
			System.out.println("send");
			ms.sendMail(mail, "eChoice 密碼", message);
		}
		
		res.sendRedirect(req.getContextPath()+"/HomePage.jsp");
		
	}

}
