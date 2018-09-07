package demo.event;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

    private String handlerName;

    public LongEventHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        event.getCountDownLatch().countDown();
        System.out.println("消费者Event(" + handlerName + "):" + Thread.currentThread().getName() + " " +
                event.hashCode() + ":" + event.getCountDownLatch().getCount());
        Thread.sleep(2);
    }
}