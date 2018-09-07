package demo.event;

import java.util.concurrent.CountDownLatch;

public class LongEvent {

    public LongEvent() {
        System.out.println("构建longevent...");
    }

    private long value;

    public CountDownLatch countDownLatch;

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {

        this.countDownLatch = countDownLatch;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}