package com.zhongjh.mvidemo.phone.main.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingFragment;
import com.zhongjh.mvidemo.phone.template.fragment.TemplateFragment;

/**
 * main的ViewPager适配器
 *
 * @author zhongjh
 * @date 2022/3/23
 */
public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ShopPingFragment();
            case 1:
                return new TemplateFragment();
            case 2:
                return new TemplateFragment();
            case 3:
                return new TemplateFragment();
            case 4:
            default:
                return new TemplateFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
