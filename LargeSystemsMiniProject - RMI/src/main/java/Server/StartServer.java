package Server;

import Model.UpdaterObject;
import Runnables.CurrencyUpdater;
import Service.RmiServerImpl;
import Shared.RegistryConfig;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 23-05-2015.
 */
public class StartServer {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static CurrencyUpdater updater = new CurrencyUpdater();
    private static UpdaterObject updaterSettings = new UpdaterObject();

    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started.");

        try { //special exception handler for registry creation
            Registry registry = LocateRegistry.createRegistry(RegistryConfig.REGISTRY_PORT);
            registry.rebind(RegistryConfig.INSTANCE_NAME, new RmiServerImpl());
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        // Schedule updater every ninety minutes, starting now
//        setUpdaterSettings(0, 90, TimeUnit.MINUTES);

         // Fetch currencies from yahoo
        System.out.println("Fetching initial currencies...");
        long startTime = System.currentTimeMillis();
        RmiServerImpl.fillCurrencyCache();
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime);
        System.out.println("All currencies are up to date.");
    }

    private static void setUpdaterSettings(int delay, int period, TimeUnit timeUnit) {
        updaterSettings.setDelay(delay)
                .setPeriod(period)
                .setTimeUnit(timeUnit);

        scheduleUpdate(updaterSettings);
    }

    private static void scheduleUpdate(UpdaterObject updaterSettings) {
        System.out.println("Automatic update scheduled to run every " + updaterSettings.getPeriod() + " " + updaterSettings.getTimeUnit().toString().toLowerCase() +".");
        scheduler.scheduleAtFixedRate(updater, updaterSettings.getDelay(), updaterSettings.getPeriod(), updaterSettings.getTimeUnit());
    }
}
