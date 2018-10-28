package com.kurumi.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kurumi.util.Captcha;

/**
 * 验证码控制器
 * @author lyh
 * @version 1.1
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

	/**
	 * 获取验证码图片
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("/verification_code")
	public void verificationCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Expires", "-1");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setHeader("Pragma", "-1");  
        Captcha captcha = Captcha.Instance();  
        //获取验证码字符串
        String code = captcha.getString(); 
        // 将验证码码字符串放置到session中，用来验证  
        request.getSession().setAttribute("verificationCode", code);  
        // 输出到web页面（jpg格式）  
        ImageIO.write(captcha.getImage(), "jpg", response.getOutputStream());  
	}
}
