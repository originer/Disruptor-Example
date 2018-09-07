package demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import demo.workevent.GenericEvent;
import demo.workevent.GenericEventFactory;
import demo.workevent.GenericEventProducer;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public abstract class MainTemplate {


    /*生产事件数量*/
    private static final int COUNT = 1000;

    public void run() {
        GenericEventFactory<GenericEvent<String>> eventFactory = new GenericEventFactory<GenericEvent<String>>();
        int bufferSize = 1024;
        Disruptor<GenericEvent<String>> disruptor = new Disruptor(eventFactory, bufferSize,
                Executors.defaultThreadFactory());

        addHandler(disruptor);

        disruptor.start();

        RingBuffer<GenericEvent<String>> ringBuffer = disruptor.getRingBuffer();

        doAfterDisruptorStart(ringBuffer);

        GenericEventProducer<String> producer = new GenericEventProducer(ringBuffer);

        for (; ; ) {
            Scanner scan = new Scanner(System.in);
            String msg = scan.nextLine();
            if ("exit".equals(msg)) {
                System.exit(0);
            }
            producer.onData(msg);
        }

        //CountDownLatch count = new CountDownLatch(COUNT);
        //long s = System.currentTimeMillis();
        //for (int i = 0; i < COUNT; i++) {
        //    producer.onData(count);
        //}
        //try {
        //    count.await();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //long e = System.currentTimeMillis();
        //System.out.println("耗时：" + (e - s));

    }

    protected void doAfterDisruptorStart(final RingBuffer<GenericEvent<String>> ringBuffer) {

    }

    public abstract void addHandler(Disruptor<GenericEvent<String>> disruptor);
}