package Service;

import javafx.util.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by prep on 20-02-2015.
 */
public interface RmiServer extends Remote {
    double exchangeRate(String sourceCurrency, String targetCurrency, Double amount) throws RemoteException;

    double exchangeRate(String sourceCurrency, String targetCurrency) throws RemoteException;

    Pair<String, Double> exchangeRate(String sourceCurrency) throws RemoteException;
}