package com.zhongjh.mvidemo.phone.template;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.zhongjh.mvidemo.phone.privacypolicy.PrivacyPolicyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;


/**
 * @author zhongjh
 * @date 2022/3/24
 */
public class kotlinTest extends AppCompatActivity {

    private static final String TAG = kotlinTest.class.getSimpleName();

    private void test() {
        Intent intent = new Intent(kotlinTest.this, PrivacyPolicyActivity.class);
    }

}
