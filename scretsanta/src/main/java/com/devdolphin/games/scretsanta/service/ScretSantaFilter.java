package com.devdolphin.games.scretsanta.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ScretSantaFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException{
		System.out.println("Filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String team_id = (String) session.getAttribute("requested_team");
		if(team_id==null || team_id.equals("")) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			res.getWriter().println("{ \"errorcode\": \"Invalid Session\"}");
		}else {
			chain.doFilter(request, response);
		}
	}
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
