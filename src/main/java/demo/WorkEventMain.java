package demo;

import com.lmax.disruptor.dsl.Disruptor;
import demo.event.LongEventHandler;
import demo.workevent.GenericEvent;
import demo.workevent.GenericEventHandler;
import demo.workevent.GenericWorkHandler;

/**
 * 一条消息只处理一次的方式:使用handleEventsWithWorkerPool，或者批量创建handler，一次加入
 * 链式调用，在注册handler时声明关系，多次独立添加的话链与链之间没有关系
 */
public class WorkEventMain extends MainTemplate {

    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {

        //将事件分发到工作处理程序线程池之一。 每个事件仅由其中一个工作处理程序处理,随机取出一个闲置的handler
        disruptor.handleEventsWithWorkerPool(new GenericWorkHandler<String>("workHandler-1")
                , new GenericWorkHandler<String>("workHandler-2")
                , new GenericWorkHandler<String>("workHandler-3")
                , new GenericWorkHandler<String>("workHandler-4")
                , new GenericWorkHandler<String>("workHandler-5")
                , new GenericWorkHandler<String>("workHandler-6")
                , new GenericWorkHandler<String>("workHandler-7")
                , new GenericWorkHandler<String>("workHandler-8")
                , new GenericWorkHandler<String>("workHandler-9")
        );

        //必须像上面一样一次全部添加进去，要不然还是会被处理处理多次(向下面这样)
        //disruptor.handleEventsWithWorkerPool(new GenericWorkHandler<String>("workHandler-1"));
        //disruptor.handleEventsWithWorkerPool(new GenericWorkHandler<String>("workHandler-2"));
        //disruptor.handleEventsWithWorkerPool(new GenericWorkHandler<String>("workHandler-3"));

        //批量创建一批Handler一次注入到distributor,这样每次事件都会按顺序取一个handler来处理
        //int handlerCounts = 10;
        //LongEventHandler[] handlers = new LongEventHandler[handlerCounts];
        //for (int i = 0; i < handlerCounts; i++) {
        //    handlers[i] = new LongEventHandler(""+i);
        //}
        //disruptor.handleEventsWith(handlers);

        //链式调用，step1、step2并行执行，都执行完后才执行step3
        //disruptor.handleEventsWith(new GenericEventHandler<String>("step1"))
        //        .then(new GenericEventHandler<String>("step2"))
        //        .then(new GenericEventHandler<String>("step3"));
    }

    public static void main(String[] args) {
        new WorkEventMain().run();
    }
}