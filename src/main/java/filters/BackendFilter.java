package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BackendFilter implements Filter {

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

		Object loginEmp = session.getAttribute("loginEmp");
		
		
			if (loginEmp == null) {
				resp.sendRedirect(req.getContextPath() + "/backEnd/loginPage.jsp");
				
			} else {
				chain.doFilter(request, response);
			}

		
		
	}
}