package spms.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.dao.VisitorBoardDao;
import spms.util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
	//Connection conn;
	DBConnectionPool connPool; 
	@Override
	public void contextInitialized(ServletContextEvent event){
		try{
			ServletContext sc = event.getServletContext();
			
		/*	Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
		*/	
			
			connPool = new DBConnectionPool(
					sc.getInitParameter("driver"),
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			
			VisitorBoardDao visitorBoardDao = new VisitorBoardDao();
		//	visitorBoardDao.setConnection(conn);
			visitorBoardDao.setDbConnectionPool(connPool);
			
			sc.setAttribute("visitorBoardDao", visitorBoardDao);
			
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event){
		//try{
			//conn.close();
		//}catch(Exception e){}
		connPool.closeAll();
	}
	
}