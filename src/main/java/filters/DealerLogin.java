package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DealerLogin implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		Object loginDealer = session.getAttribute("dealerVObyAccount");
		
		
			if (loginDealer == null) {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
				
			} else {
				chain.doFilter(request, response);
			}

		
		
	}
}