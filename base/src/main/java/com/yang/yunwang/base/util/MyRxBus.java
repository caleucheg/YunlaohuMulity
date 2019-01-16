package com.yang.yunwang.base.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MyRxBus {

    //    // 有背压处理的 MyRxBus
    //    private final FlowableProcessor<Object> bus;

    private static volatile MyRxBus defaultRxBus;
    //非背压处理
    private final Subject<Object> bus;

    private MyRxBus() {
        //非背压处理
        bus = PublishSubject.create().toSerialized();

        //        // 有背压处理的 MyRxBus
        //        bus = PublishProcessor.create().toSerialized();

    }

    public static MyRxBus getInstance() {
        if (null == defaultRxBus) {
            synchronized (MyRxBus.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = new MyRxBus();
                }
            }
        }
        return defaultRxBus;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public boolean hasObservable() {
        return bus.hasObservers();
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Observable<T> toObservable(Class<T> type) {
        return bus.ofType(type);
    }
}