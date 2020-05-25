package dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class InitListener implements ServletContextListener {


    public void contextDestroyed(ServletContextEvent arg0)  { 
        // ConnectionManager.close();
    }


    public void contextInitialized(ServletContextEvent arg0)  { 
         ConnectionManager.open();
         System.out.println("Uspesno povezano sa bazom!");
    }
	
}
