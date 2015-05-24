package Runnables;

import Service.RmiServerImpl;

import java.util.Calendar;

/**
 * Created by Administrator on 01-03-2015.
 */
public class CurrencyUpdater implements Runnable {
    int total = 1;

    @Override
    public void run() {
        System.out.println("Update #" + total + " started on " + Calendar.getInstance().getTime());
        RmiServerImpl.fillCurrencyCache();
        System.out.println("Update #" + total + " completed on " + Calendar.getInstance().getTime());
        total++;
    }
}