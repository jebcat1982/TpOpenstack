package calculateur;

import java.net.InetAddress;

import org.apache.xmlrpc.common.TypeConverterFactoryImpl;
//  import org.apache.xmlrpc.demo.webserver.proxy.impls.AdderImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class Server {
	private static final int DEFAULT_PORT = 8080;
	private static int port = DEFAULT_PORT;

	public static void main(String[] args) throws Exception {

		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		}

		System.out.println("== Server launch on port " + port + " ==");

		WebServer webServer = new WebServer(port);

		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		/* Load handler definitions from a property file.
		 * The property file might look like:
		 *   Calculator=org.apache.xmlrpc.demo.Calculator
		 *   org.apache.xmlrpc.demo.proxy.Adder=org.apache.xmlrpc.demo.proxy.AdderImpl
		 */
		
		phm.addHandler("Calculateur", calculateur.Calculateur.class);
		/* You may also provide the handler classes directly,
		 * like this:
		 
		 * phm.addHandler(org.apache.xmlrpc.demo.proxy.Adder.class.getName(),
		 *     org.apache.xmlrpc.demo.proxy.AdderImpl.class);
		 */
		xmlRpcServer.setHandlerMapping(phm);

		XmlRpcServerConfigImpl serverConfig =
				(XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		serverConfig.setEnabledForExtensions(true);
		serverConfig.setContentLengthOptional(false);

		webServer.start();
	}
}