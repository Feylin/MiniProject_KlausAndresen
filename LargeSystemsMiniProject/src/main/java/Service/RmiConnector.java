package Service;

import Shared.RegistryConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Administrator on 23-05-2015.
 */
public enum RmiConnector {
    INSTANCE;

    private RmiServer rmiServer;

//    RmiConnector() {
//        rmiServer = connectToRmi();
//    }

    public boolean connectToRmi() {
//        RmiServer rmiServer = null;
        try {
            // fire to localhost port 1099
            Registry myRegistry = LocateRegistry.getRegistry(RegistryConfig.REGISTRY_PORT);

            // search for serverImpl service
            rmiServer = (RmiServer) myRegistry.lookup(RegistryConfig.INSTANCE_NAME);
            return true;
        } catch (Exception e) {
//            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    public RmiServer getRmiServer() {
        return rmiServer;
    }
}
