package com.zhongjh.mvilibrary.rxjava.textview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * 在原版基础上加入了处理null的即时处理
 *
 * @author zhongjh
 * @date 2022/4/12
 */
public class TextViewTextChangeNullEventObservable
        extends InitialValueObservable<TextViewTextChangeEvent> {

    private final TextView view;
    private final ObservableListener observableListener;

    public interface ObservableListener {
        /**
         * 当文本为空时立即触发
         */
        void onTextChangedNull();
    }

    public TextViewTextChangeNullEventObservable(TextView view, ObservableListener observableListener) {
        this.view = view;
        this.observableListener = observableListener;
    }

    @Override
    protected void subscribeListener(Observer<? super TextViewTextChangeEvent> observer) {
        Listener listener = new Listener(view, observer, observableListener);
        observer.onSubscribe(listener);
        view.addTextChangedListener(listener);
    }

    @Override
    protected TextViewTextChangeEvent getInitialValue() {
        return TextViewTextChangeEvent.create(view, view.getText(), 0, 0, 0);
    }

    final static class Listener extends MainThreadDisposable implements TextWatcher {

        private final TextView view;
        private final Observer<? super TextViewTextChangeEvent> observer;
        private final ObservableListener observableListener;

        Listener(TextView view, Observer<? super TextViewTextChangeEvent> observer, ObservableListener observableListener) {
            this.view = view;
            this.observer = observer;
            this.observableListener = observableListener;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                observableListener.onTextChangedNull();
            }
            if (!isDisposed()) {
                observer.onNext(TextViewTextChangeEvent.create(view, s, start, before, count));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        protected void onDispose() {
            view.removeTextChangedListener(this);
        }
    }
}
