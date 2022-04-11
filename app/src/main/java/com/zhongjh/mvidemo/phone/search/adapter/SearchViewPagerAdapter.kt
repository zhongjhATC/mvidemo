package com.zhongjh.mvidemo.phone.search.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingFragment;

/**
 * SearchActivity的ViewPager的适配器
 * @author zhongjh
 * @date 2022/4/07
 */
public class SearchViewPagerAdapter extends FragmentStateAdapter {

    public SearchViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ShopPingFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
