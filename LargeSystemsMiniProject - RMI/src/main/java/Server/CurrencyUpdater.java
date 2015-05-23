package Server;

import Service.RmiServerImpl;

/**
 * Created by Administrator on 01-03-2015.
 */
public class CurrencyUpdater implements Runnable {
    @Override
    public void run() {
        RmiServerImpl.fillCurrencyCache();
    }
}