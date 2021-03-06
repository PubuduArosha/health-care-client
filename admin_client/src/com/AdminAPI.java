package com;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminAPI
 */
@WebServlet("/AdminAPI")
public class AdminAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
AdminRepository adminRepository = new AdminRepository();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Admin admin = new  Admin();
		admin.setAdminID(Integer.valueOf(request.getParameter("adminid")));
		admin.setHospitalName(request.getParameter("hostname"));
		admin.setLocation(request.getParameter("hostlocation"));
		
		response.getWriter().write(adminRepository.add(admin));
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		System.out.println(paras);
		Admin admin = new  Admin();
		
		String decodedHospitalName = URLDecoder.decode(paras.get("hostname").toString(), "UTF-8"); // Email decoder
		String decodedLocation = URLDecoder.decode(paras.get("hostlocation").toString(), "UTF-8"); // Email decoder
		admin.setAdminID(Integer.valueOf(paras.get("adminid").toString()));
		admin.setHospitalName(decodedHospitalName);
		admin.setLocation(decodedLocation);
		admin.setHospitalID(Integer.valueOf(paras.get("hidItemIDSave").toString()));
		String	 output = adminRepository.update(admin);
		response.getWriter().write(output);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		System.out.println(paras);
		Admin admin = new  Admin();
		admin.setHospitalID(Integer.parseInt(paras.get("hid").toString()));
		String	 output = adminRepository.delete(admin);
		response.getWriter().write(output);
	}


	
	
	private static Map getParasMap(HttpServletRequest request) { 
		
		Map<String, String> map = new HashMap<String, String>(); 
		
		try  {   
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");   
			String queryString = scanner.hasNext() ?          
			scanner.useDelimiter("\\A").next() : "";  
			scanner.close(); 
			
			String[] params = queryString.split("&"); 
			
			for (String param : params) {
				String[] p = param.split("=");    
				map.put(p[0], p[1]); 
			}
		}
		catch (Exception e) {
			
			
		}
		
		return map; 
	}
}
