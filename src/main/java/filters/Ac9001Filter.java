package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Ac9001Filter implements Filter {

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

		Object ac9001 = session.getAttribute("ac9001");
		if (!req.getServletPath().equals("/backEnd/emp/empMyInfo.jsp")) {
			if (ac9001 == null) {
				resp.sendRedirect(req.getContextPath() + "/backEnd/error/access_er.jsp");
				return;
			} else {
				chain.doFilter(request, response);
			} 
		}else {
			chain.doFilter(request, response);
		} 

	}
}