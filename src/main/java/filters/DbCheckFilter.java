package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DbCheckFilter implements Filter {

	private FilterConfig config;
	private boolean DBHasRecovery=false;

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
		
			if (!DBHasRecovery) {
				DBHasRecovery=true;
				RequestDispatcher toInit=request.getRequestDispatcher("/DBinit");
				toInit.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}

		
		
	}
}