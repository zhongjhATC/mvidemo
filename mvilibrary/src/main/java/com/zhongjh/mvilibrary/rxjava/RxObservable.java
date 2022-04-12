package com.zhongjh.mvilibrary.rxjava;

import android.view.View;

import com.jakewharton.rxbinding2.internal.Notification;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * @author zhongjh
 * @date 2022/4/11
 */
final class RxObservable extends Observable<Object> {

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        if (!RxPreconditions.Companion.checkMainThread(observer)) {
            return;
        }
        RxObservable.Listener listener = new RxObservable.Listener(observer);
        observer.onSubscribe(listener);
    }

    static final class Listener extends MainThreadDisposable {
        private final Observer<? super Object> observer;

        Listener(Observer<? super Object> observer) {
            this.observer = observer;
        }

        public void onNext() {
            if (!isDisposed()) {
                observer.onNext(Notification.INSTANCE);
            }
        }

        @Override
        protected void onDispose() {
        }
    }
}