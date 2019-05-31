package com.iamninad.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class MyFlux<T> implements Publisher<T> {


    public static MyFlux<Integer> range(int start, int end) {
        return new MyRangeFlux(start, end);
    }

    @Override
    public void subscribe(Subscriber<? super T> s) {

    }

    public Disposable subscribe(Consumer<? super T> consumer) {
        MyLambdaSubscriber<T> t = new MyLambdaSubscriber<>(consumer);
        this.subscribe(t);
        return t;
    }

    static final class MyLambdaSubscriber<T> implements Disposable, Subscriber<T> {

        final Consumer<? super T> consumer;
        Subscription s;

        MyLambdaSubscriber(Consumer<? super T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.s = s;
            s.request(Integer.MAX_VALUE);
        }

        @Override
        public void onNext(T t) {
            consumer.accept(t);
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {
            System.out.println("onComplete");
        }

        @Override
        public void dispose() {
            s.cancel();
        }
    }
}
