package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by prep on 20-02-2015.
 */
public interface RmiServer extends Remote {
    double exchangeRate(String sourceCurrency, String targetCurrency, Double amount) throws RemoteException;

    double exchangeRate(String sourceCurrency, String targetCurrency) throws RemoteException;
}