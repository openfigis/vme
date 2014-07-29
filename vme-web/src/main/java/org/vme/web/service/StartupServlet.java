/**
 * 
 */
package org.vme.web.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;

/**
 * @author SIBENI
 * 
 */
public class StartupServlet extends HttpServlet {

	private static final long serialVersionUID = 8383171148542383897L;
	private static final String DBPATH = "dbaccess-path";

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		System.out.println("FAB: servlet init with parameter dbaccess-path: ["
				+ config.getInitParameter(DBPATH) + "]");
		FilesystemMsAccessConnectionProvider.dbLocation = config.getInitParameter(DBPATH);

	}

}
