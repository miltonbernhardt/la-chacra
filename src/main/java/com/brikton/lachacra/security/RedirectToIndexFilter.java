//package com.brikton.lachacra.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class RedirectToIndexFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        String requestURI = req.getRequestURI();
//
//        if (requestURI.startsWith("/api/v1")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        if (requestURI.startsWith("/static")) { //todo ver
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // all requests not api or static will be forwarded to index page.
//        request.getRequestDispatcher("/").forward(request, response);
//    }
//
//}
