//package com.brikton.lachacra.security.examples;
//
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class SecurityFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("before");
//        chain.doFilter(request, response);
//        System.out.println("after");
//    }
//}
//
////actua igual que el otro filtro, pero se le puede insertar aprametros en runtime
//class SecurityFilterBean extends GenericFilterBean {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("before");
//        chain.doFilter(request, response);
//        System.out.println("after");
//    }
//}
//
////se ejecuta una unica vez - puede leer la request http
//class SecurityFilterOncePerRequest extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        System.out.println(request.toString());
//        System.out.println("before");
//        chain.doFilter(request, response);
//        System.out.println("after");
//    }
//}
