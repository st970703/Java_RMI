//package nz.ac.auckland.concert.client;
//
//import nz.ac.auckland.concert.common.Concert;
//import nz.ac.auckland.concert.common.ConcertService;
//import nz.ac.auckland.concert.server.ConcertServant;
//import nz.ac.auckland.concert.server.Config;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//
//import static org.testng.Assert.assertTrue;
//import static org.testng.Assert.fail;
//
///**
// * Created by st970 on 27/07/2017.
// */
//public class concertUnitTest {
//    private static ConcertService _proxy;
//
//    // Port number that the RMI registry will use.
//    private static final int REGISTRY_PORT = 8090;
//
//    // Name used to advertise/register the ShapeFactory service in the RMI
//    // Registry.
//    private static final String SERVICE_NAME = "concert-factory";
//
//
//
//    /**
//     * One-time setup method to retrieve the ShapeFactory proxy from the RMI
//     * Registry.
//     */
//    @org.junit.BeforeClass
//    public static void getProxy() {
//        try {
//            // Instantiate a proxy object for the RMI Registry, expected to be
//            // running on the local machine and on a specified port.
//            Registry lookupService = LocateRegistry.getRegistry("localhost", Config.REGISTRY_PORT);
//
//            // Retrieve a proxy object representing the ShapeFactory.
//            _proxy = (ConcertService)lookupService.lookup(Config.SERVICE_NAME);
//        } catch (RemoteException e) {
//            System.out.println("Unable to connect to the RMI Registry");
//        } catch (NotBoundException e) {
//            System.out.println("Unable to acquire a proxy for the Concert service");
//        }
//    }
//
//    /**
//     * Test that, using the ShapeFactory proxy, we can invoke methods on the
//     * remote ShapeFactory to create remotely accessible Shapes. This test also
//     * then invokes methods on the remote Shapes objects, via their acquired
//     * proxies.
//     */
//    @Test
//    public void testCreate() throws RemoteException {
//
//        org.joda.time.format.DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime dateTime = f.parseDateTime("2012-01-10 23:13:26");
//
//        Concert testConcert = new Concert((long) 2094070921, "new concert", dateTime);
//        Long testID = testConcert.getId();
//
//        Concert concertA = _proxy.createConcert(testConcert);
//
//        boolean sameConcert = testConcert.getId().equals(testID);
//
//        assertTrue(sameConcert);
//    }
//}
