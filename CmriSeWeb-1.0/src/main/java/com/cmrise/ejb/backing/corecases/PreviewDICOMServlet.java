package com.cmrise.ejb.backing.corecases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PreviewDICOMServlet
 */
@WebServlet("/PreviewDICOM.dcm")
public class PreviewDICOMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreviewDICOMServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dcmKey = request.getParameter("dcmkey");
		HttpSession session = request.getSession();
		String filePath = String.valueOf(session.getAttribute(dcmKey));
		String fileName = dcmKey + ".dcm";
		FileInputStream fileInputStream = null;
		PrintWriter out = null;
		
		try{
			  response.setContentType("APPLICATION/OCTET-STREAM");   
			  response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");   
			  fileInputStream=new java.io.FileInputStream(filePath);  
			  int i;  
			  out = response.getWriter();
			  while ((i=fileInputStream.read()) != -1) {  
			    out.write(i);   
			  }   		
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			if(fileInputStream!=null){
				try{
					fileInputStream.close();
				}catch(IOException | RuntimeException e){
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	}

}
