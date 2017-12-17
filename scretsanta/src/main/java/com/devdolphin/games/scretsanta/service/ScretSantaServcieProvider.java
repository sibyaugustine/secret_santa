package com.devdolphin.games.scretsanta.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devdolphin.games.scretsanta.provider.FriendFinder;

public class ScretSantaServcieProvider extends HttpServlet{
	private static final long serialVersionUID = -2243970479453169952L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        	System.out.println("Working Directory = " + System.getProperty("user.dir"));
    		String ipAddress = request.getRemoteAddr(); 
    		System.out.println(ipAddress);
    		String workingdir = System.getProperty("user.dir");
    		HttpSession session = request.getSession();
    		String team_id = (String) session.getAttribute("requested_team");
    		FriendFinder ffinder = new FriendFinder(workingdir,team_id,ipAddress);
    		response.setContentType("application/json");
    		try {
				String myfriend = ffinder.findMyFriend();
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().println("{ \"MyFriend\": \""+myfriend+"\"}");
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().println("{ \"errorcode\": \""+e.getMessage()+"\"}");
			}
	}
}
