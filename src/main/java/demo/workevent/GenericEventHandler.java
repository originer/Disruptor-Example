package demo.workevent;

import com.lmax.disruptor.EventHandler;


public class GenericEventHandler<T> implements EventHandler<GenericEvent<T>> {

    protected String name;

    public GenericEventHandler() {
    }

    public GenericEventHandler(String name) {
        this.name = name;
    }

    public void onEvent(GenericEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者workHandler(" + name + ")" + ":" + event.get()+ ":" + Thread.currentThread().getName() + ":" + this.hashCode());
    }

}
