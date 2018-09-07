package demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import demo.event.LongEvent;
import demo.event.LongEventFactory;
import demo.event.LongEventHandler;
import demo.event.LongEventProducer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 最基本的使用方法
 */
public class BasicMain {

    public static void main(String[] args) throws Exception {

        //生产event的工厂，在disruptor启动的时候用来在ring buffer环中占坑
        LongEventFactory factory = new LongEventFactory();

        //指定ring buffer环的大小
        int bufferSize = 8;

        //创建disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize,
                Executors.newFixedThreadPool(8));


        //注册事件处理器，用来消费事件
        disruptor.handleEventsWith(new LongEventHandler("Basic"));

        //启动disruptor
        disruptor.start();

        //构建生产者，用来生产事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        final LongEventProducer producer = new LongEventProducer(ringBuffer);

        ExecutorService es = Executors.newSingleThreadExecutor();

        final CountDownLatch count = new CountDownLatch(5000);

        long s = System.currentTimeMillis();
        es.submit(new Runnable() {
            public void run() {
                //ByteBuffer bb = ByteBuffer.allocate(8);
                for (long l = 0; l < 5000; l++) {
                    //bb.putLong(0, l);
                    producer.onData(count);
                }
            }
        });
        count.await();
        long e = System.currentTimeMillis();

        System.out.println("耗时："+(e-s));
    }
}