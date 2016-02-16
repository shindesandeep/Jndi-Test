package com.pc.jndi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;

/**
 * Servlet implementation class JndiServlet
 * 
 * @param <K>
 * @param <V>
 */
@WebServlet("/JndiServlet")
public class JndiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * @Resource(name="OracleJndi") DataSource ds;
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		try {

			Hashtable ht = new Hashtable();
			ht.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.enterprise.naming.SerialInitContextFactory");
			ht.put(Context.PROVIDER_URL, "iiop://localhost:4848");
			InitialContext context = new InitialContext(ht);
			XADataSource xaDataSource = (XADataSource) context.lookup("OracleXAJndi");
			XAConnection xaConnection = xaDataSource.getXAConnection("system", "tiger");
			if(xaConnection!=null){
				pw.print("Connectin established "+xaConnection);
			}else{
				pw.println("Connectin Failed"+xaConnection);
			}
			
			XAResource xaResource = xaConnection.getXAResource();
			pw.print("Resource "+xaResource);
			
			
			
		/*	DataSource ds = (DataSource) context.lookup("OracleXAJndi");
			Connection con = ds.getConnection();
			
			Statement st = con.createStatement();

			boolean flag = st.execute("SELECT * FROM TAB");

			
			pw.print(flag);
			if (con != null) {
				pw.print("<br>connection : " + flag);
			} else {
				pw.print("failed " + flag);
			}*/
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
