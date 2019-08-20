package init;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class Start implements ServletContextListener{
	private InitialProcessController controller = new InitialProcessController();
	
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Thread t = new Thread(controller);
		t.start();
	}

}
