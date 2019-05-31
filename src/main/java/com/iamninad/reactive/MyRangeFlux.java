package com.iamninad.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MyRangeFlux extends MyFlux<Integer> {

    private final int start;
    private final int end;

    MyRangeFlux(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        s.onSubscribe(new MyRangeSubscription(s, start, end));
    }

    static final class MyRangeSubscription implements Subscription{

        final Subscriber<? super Integer> actual;
        final int start;
        final int end;
        volatile boolean cancelled;

        MyRangeSubscription(Subscriber<? super Integer> flux, int start, int end){
            this.actual = flux;
            this.start = start;
            this.end = end;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void request(long n) {
            final Subscriber<Integer> s = (Subscriber<Integer>) actual;

            for(long i = start; i <= end; i++){
                if(cancelled){
                    return;
                }
                s.onNext((int)i);
            }

            if(cancelled){
                return;
            }

            s.onComplete();
        }

        @Override
        public void cancel() {
            this.cancelled = true;
        }
    }
}
