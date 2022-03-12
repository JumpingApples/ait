package org.millburntalent;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MiotServlet")
public class MiotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MiotServlet() {
		super();

		System.out.println("Servlets found.");
	}
	
	protected boolean hasCookie(HttpServletRequest request, String query) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (query.equals(cookies[i].getName())
						&& !cookies[i].getValue().equals(""))
					return true;
			}
		}
		return false;
	}
	
	protected int readSemesterYear(HttpServletRequest request) {
		int semesterYear = 0;
		
		if (request.getParameter("SY") != null)
			semesterYear += Integer.parseInt(request.getParameter("SY")) * 10;
		else
			semesterYear += getConfigYear() * 10;
		
		if (request.getParameter("SEM") != null)
			semesterYear += Integer.parseInt(request.getParameter("SEM"));
		else
			semesterYear += getConfigSemester();
		
		return semesterYear;
	}
	
	protected String readSemesterName(HttpServletRequest request) {
		int sem;
		if (request.getParameter("SEM") != null)
			sem = Integer.parseInt(request.getParameter("SEM"));
		else
			sem = getConfigSemester();
		
		if (sem == 1)
			return "Summer";
		else
			return "";
	}
	
	private int getConfigSemester() {
		return 2;
	}
	
	private int getConfigYear() {
		return 2020;
	}
	
	protected String expandDay(String day) {
		if (day.equals("S"))
			return "Sun";
		if (day.equals("M"))
			return "Mon";
		if (day.equals("T"))
			return "Tue";
		if (day.equals("W"))
			return "Wed";
		if (day.equals("U"))
			return "Thu";
		if (day.equals("F"))
			return "Fri";
		if (day.equals("A"))
			return "Sat";
		return day;
	}
    
    protected String encode64(String source) {
    	return Base64.getEncoder().encodeToString(source.getBytes());
    }
    
    protected String decode64(String source) {
    	return new String(Base64.getDecoder().decode(source));
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Testing logging.");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Preferably unreachable code.
		doGet(request, response);
	}
	
	
	
	
	
	
	
	protected int hardCodedAdminFee() { return 25; }
	
	protected int hardCodedPDFee()    { return 0;  }

}
