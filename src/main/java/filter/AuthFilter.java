package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if(!req.getServletPath().startsWith("/login")){
            if(req.getSession().getAttribute("username") != null){
                filterChain.doFilter(req,resp);
            }else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
