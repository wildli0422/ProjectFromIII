package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Ac9005Filter implements Filter {

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

		Object ac9005 = session.getAttribute("ac9005");
		if (ac9005 == null) {
			resp.sendRedirect(req.getContextPath() + "/backEnd/error/access_er.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}

	}
}