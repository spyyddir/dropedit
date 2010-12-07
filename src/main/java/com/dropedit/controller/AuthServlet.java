package com.dropedit.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import com.dropbox.client.*;
import oauth.signpost.exception.*;
import org.json.simple.parser.ParseException;


public class AuthServlet extends HttpServlet {
    public static final String VIEW = "/WEB-INF/jsp/login.jsp";

// http://www.apl.jhu.edu/~hall/java/Servlet-Tutorial/Servlet-Tutorial-Session-Tracking.html


    @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.getRequestDispatcher(VIEW).forward(req, resp);
    }

		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		Map config = null;
		Authenticator auth = null;
		try {
			config = Authenticator.loadConfig("config/keys.json");
			auth = new Authenticator(config);
//			String url = auth.retrieveRequestToken(null);
//			resp.getWriter().write(url);
//
//			auth.retrieveAccessToken("");

//			String access_key = auth.getTokenKey();
//			String access_secret = auth.getTokenSecret();

		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		DropboxClient dropbox = new DropboxClient(config, auth);

		HttpSession session = req.getSession();
		session.setAttribute("client", dropbox);

        RootPath rootPath = new RootPath();
        rootPath.addRootPath("");
        session.setAttribute("parentPath", rootPath);

		resp.sendRedirect("/");

    }
}
