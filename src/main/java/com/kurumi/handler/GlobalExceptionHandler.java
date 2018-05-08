package com.kurumi.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
        
		ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.getModelMap().addAttribute("ex", e.getMessage());
        mav.setViewName("page_error");
        return mav;
    }
	
	@ExceptionHandler(value = RuntimeException.class)
    public ModelAndView runtimeErrorHandler(HttpServletRequest request, RuntimeException ex,HttpServletResponse response){
        if ((request.getHeader("accept").indexOf("application/json") > -1 ||
            (request.getHeader("X-Requested-With") != null && 
            request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))){

             try {
          	   if(ex instanceof UnauthorizedException) {
          		   response.setStatus(403);
          	   } else {
          		   response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          	   }
          	   
                 response.setContentType("text/html;charset=UTF-8");
                 response.setCharacterEncoding("UTF-8");
                 PrintWriter writer = response.getWriter();
                 writer.write(ex.getMessage());
                 writer.flush();
                 writer.close();
             } catch (Exception e) {

             }
             return null;
        }
        else{
        	ModelAndView mav = new ModelAndView();
        	mav.getModelMap().addAttribute("ex", ex.getMessage());
            mav.setViewName("page_error");
            return mav;
        }
    }
	
	@ExceptionHandler(value = UnauthorizedException.class)
	public ModelAndView unauthorizedErrorHandler(HttpServletRequest request, UnauthorizedException ex,HttpServletResponse response){
        
    	ModelAndView mav = new ModelAndView();
    	mav.getModelMap().addAttribute("ex", ex.getMessage());
        mav.setViewName("error/un_authorized");
        return mav;
    }
	
}
