package Model;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by prep on 10-03-2015.
 */
public class UpdaterObject implements Serializable {
    static final long serialVersionUID = 1L;
    private int delay;
    private int period;
    private TimeUnit timeUnit;

    public UpdaterObject() {

    }

    public int getDelay() {
        return delay;
    }

    public UpdaterObject setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public UpdaterObject setPeriod(int period) {
        this.period = period;
        return this;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public UpdaterObject setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }
}
