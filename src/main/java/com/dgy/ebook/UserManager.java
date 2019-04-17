package com.dgy.ebook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@WebServlet(name = "userManager", urlPatterns = "/userManager")
public class UserManager extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(UserManager.class);

	public UserManager(){
		super();
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		
		response.getWriter().append(userDetails.getUsername());
    }

}
