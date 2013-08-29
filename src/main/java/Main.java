import java.rmi.RemoteException;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.rmi.RmiClientInterceptor;
import org.test.spring.remoting.services.DefaultRemoteListener;
import org.test.spring.remoting.services.api.ResultListener;
import org.test.spring.remoting.services.api.SchedulerService;
import org.test.spring.remoting.services.api.TaskType;

public class Main {
	public static void main(String[] args) throws RemoteException {
		SchedulerService schedulerService = (SchedulerService) getProxyFor("127.0.0.1");
		ResultListener listener = new DefaultRemoteListener();

		schedulerService.runTask(TaskType.SYNC, listener, "Run");
		System.out.println(schedulerService.getMaximumRunningThreads());
	}

	public static Object getProxyFor(String hostName) {
		RmiClientInterceptor rmiClientInterceptor = new RmiClientInterceptor();
		rmiClientInterceptor.setServiceUrl(String.format(
				"rmi://%s:1199/SchedulerService", hostName));
		rmiClientInterceptor.setServiceInterface(SchedulerService.class);
		rmiClientInterceptor.afterPropertiesSet();
		rmiClientInterceptor.setLookupStubOnStartup(false);
		rmiClientInterceptor.setRefreshStubOnConnectFailure(true);

		return new ProxyFactory(SchedulerService.class, rmiClientInterceptor)
				.getProxy();
	}
}
