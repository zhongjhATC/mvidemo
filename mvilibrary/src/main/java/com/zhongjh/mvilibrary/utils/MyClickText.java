package com.zhongjh.mvilibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import szhome.bbs.companybank.WebViewActivity;

/**
 * 隐私保护的文字点击
 *
 * @author zhongjh
 * @date 2021/7/22
 */
public class MyClickText extends ClickableSpan {

    private Activity activity;

    public MyClickText(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(Color.RED);
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", "https://dongdong.szhome.com/secretrule.html");
        activity.startActivity(intent);
    }

}