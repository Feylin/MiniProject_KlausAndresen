package Service;

import Shared.RegistryConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 23-05-2015.
 */
public enum RmiConnector {
    INSTANCE;

    public RmiServer rmiLookup() {
        RmiServer rmiServer = null;
        try {
            // fire to localhost port 1099
            Registry myRegistry = LocateRegistry.getRegistry(RegistryConfig.REGISTRY_PORT);

            // search for myMessage service
            rmiServer = (RmiServer) myRegistry.lookup(RegistryConfig.INSTANCE_NAME);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return rmiServer;
    }
}
