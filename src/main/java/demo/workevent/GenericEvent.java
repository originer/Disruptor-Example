package demo.workevent;

import java.util.concurrent.CountDownLatch;

public class GenericEvent<T> {

    private T value;

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private CountDownLatch countDownLatch;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
