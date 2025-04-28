package filter;

import model.bean.AdminBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/views/admin/secure/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        AdminBean user = null;
        if (session != null) {
            user = (AdminBean) session.getAttribute("loggedAdmin");
        }

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
