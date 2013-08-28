import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.rmi.RmiClientInterceptor;
import org.test.spring.remoting.services.api.SchedulerService;

public class Main {
	public static void main(String[] args) {
		SchedulerService schedulerService = (SchedulerService) getProxyFor("127.0.0.1");
		System.out.println(schedulerService.isShutdown());
	}

	public static Object getProxyFor(String hostName) {
		RmiClientInterceptor rmiClientInterceptor = new RmiClientInterceptor();
		rmiClientInterceptor.setServiceUrl(String.format(
				"rmi://%s:1199/SchedulerService", hostName));
		rmiClientInterceptor.setServiceInterface(SchedulerService.class);
		rmiClientInterceptor.afterPropertiesSet();

		return new ProxyFactory(SchedulerService.class, rmiClientInterceptor)
				.getProxy();
	}
}
