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

    private final View view;

    RxObservable(View view) {
        this.view = view;
    }

    @Override protected void subscribeActual(Observer<? super Object> observer) {
        if (!RxPreconditions.Companion.checkMainThread(observer)) {
            return;
        }
        RxObservable.Listener listener = new RxObservable.Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final View view;
        private final Observer<? super Object> observer;

        Listener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override public void onClick(View v) {
            if (!isDisposed()) {
                observer.onNext(Notification.INSTANCE);
            }
        }

        @Override protected void onDispose() {
            view.setOnClickListener(null);
        }
    }
}