package com.iamninad;

import com.iamninad.reactive.MyFlux;

public class NumberStream {
    public static void main(String[] args) {
        NumberStream s = new NumberStream();
        s.simple();
    }

    private void simple(){
        MyFlux.range(1, 30).subscribe(System.out::println).dispose();
    }

}
