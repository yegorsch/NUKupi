import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/authenticate/*")
public class AuthenticationFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = ( HttpServletRequest ) req ;
        HttpSession session = request.getSession();
        String test = ( String ) session.getAttribute("email");
        HttpServletResponse response;
        if ( test == null ) {
            response = ( HttpServletResponse ) resp ;
            response.sendRedirect("/Nukupi/login.html");
        } else {
            chain.doFilter(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}