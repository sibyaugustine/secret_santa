package com.devdolphin.games.scretsanta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ScretSantaAccessService extends HttpServlet{
	private static final long serialVersionUID = -2243970479453169952L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("Setting up session for dynamic url");
		String requestedurl = request.getRequestURI();
		try {
		String team_id = requestedurl.split("/")[2];
		HttpSession session = request.getSession();
		session.setAttribute("requested_team",team_id) ;
		response.sendRedirect("../play.html");
		}catch(Exception e) {
			response.sendRedirect("invalid_url.html");
		}
	}
}
