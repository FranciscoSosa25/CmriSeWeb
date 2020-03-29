package com.cmrise.ejb.backing.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmrise.utils.Utilitarios;

public class UserRoleFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletRequest httpRequest = (HttpServletRequest) request;
		 String strxXRole = (String)httpRequest.getSession().getAttribute("xXRole");
		 System.out.println("strxXRole:"+strxXRole);
		 System.out.println("httpRequest.getContextPath():"+httpRequest.getContextPath());
		 if(Utilitarios.ROL_USUARIO.equals(strxXRole)
		   ||Utilitarios.ROL_MAESTRO.equals(strxXRole)
		   ) {
			 chain.doFilter(httpRequest, response);
		 }else {
			 HttpServletResponse r = (HttpServletResponse) response;
	          r.sendRedirect(httpRequest.getContextPath() + "/faces/index.xhtml");
	            return;
		 }
		 
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
