package nz.ac.auckland.concert.client;

import nz.ac.auckland.concert.common.Concert;
import nz.ac.auckland.concert.common.ConcertService;
import nz.ac.auckland.concert.server.Config;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test client for the RMI whiteboard application.
 *
 */
public class Client {

	// Proxy object to represent the remote ConcertService service.
	private static ConcertService _proxy;

	/**
	 * One-time setup method to retrieve the ShapeFactory proxy from the RMI 
	 * Registry.
	 */
	@BeforeClass
	public static void getProxy() {
		try {
			// Instantiate a proxy object for the RMI Registry, expected to be
			// running on the local machine and on a specified port. 
			Registry lookupService = LocateRegistry.getRegistry("localhost", Config.REGISTRY_PORT);
			
			// Retrieve a proxy object representing the ShapeFactory.
			_proxy = (ConcertService)lookupService.lookup(Config.SERVICE_NAME);
		} catch (RemoteException e) {
			System.out.println("Unable to connect to the RMI Registry");
		} catch (NotBoundException e) {
			System.out.println("Unable to acquire a proxy for the Concert service");
		}
	}
	
	/**
	 * Test that, using the ShapeFactory proxy, we can invoke methods on the 
	 * remote ShapeFactory to create remotely accessible Shapes. This test also
	 * then invokes methods on the remote Shapes objects, via their acquired 
	 * proxies.
	 */
	@Test
	public void testCreate() throws RemoteException {
		
			// Use the ShapeFactory proxy to create a couple of remote Shape
			// instances. newShape() returns proxies for the new remote Shapes.
			Long a = (long) 3;
			Long b = (long) 4;
			Concert concertA = _proxy.createConcert(new Concert(a,"SE306",new DateTime(2005, 3, 26, 12, 0, 0, 0)));
			Concert concertB = _proxy.createConcert(new Concert(b,"SE254",new DateTime(2006,3,25,12,0,0,0)));

			// Query the new Concert object's ids. the getId() calls are remote
			// method invocations on the Shapes that have been created on the
			// the server.
			System.out.println("ConcertA Id is " + concertA.getId());
			System.out.println("ConcertB Id is " + concertB.getId());
		
			// Query the remote factory.
			List<Concert> remoteConcerts = _proxy.getAllConcerts();
			
			assertTrue(remoteConcerts.contains(concertA));
			assertTrue(remoteConcerts.contains(concertB));
			assertEquals(2, remoteConcerts.size());
			
			for(Concert c : remoteConcerts) {
				
				System.out.println(c.toString());
			}

		remoteConcerts.clear();
	}
}
