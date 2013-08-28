import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Main2 {
	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> enumeration = NetworkInterface
				.getNetworkInterfaces();

		while (enumeration.hasMoreElements()) {
			NetworkInterface networkInterface = enumeration.nextElement();
			Enumeration<InetAddress> enumeration2 = networkInterface
					.getInetAddresses();

			while (enumeration2.hasMoreElements()) {
				InetAddress inetAddress = enumeration2.nextElement();
				System.out.println(inetAddress.getHostName());
				System.out.println(inetAddress.getCanonicalHostName());
				System.out.println(inetAddress.getHostAddress());
			}
		}
	}
}
