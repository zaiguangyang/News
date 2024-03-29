package com.zhuoxin.News.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.zhuoxin.News.R;
import com.zhuoxin.News.adapter.NewsPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class NewsPagerFragment extends Fragment {
    @InjectView(R.id.vp_news_pager)
    ViewPager vp_news_pager;
    @InjectView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    NewsPagerAdapter newsPagerAdapter;
    List<NewsFragment> newsFragmentList = new ArrayList<>();
    //将类型存成一个数组类型
    String types[] = new String[]{NewsFragment.TYPE_TOP, NewsFragment.TYPE_GUOJI, NewsFragment.TYPE_KEJI, NewsFragment.TYPE_SHEHUI};
    String typesCN[] = new String[]{"头条", "国际", "科技", "社会"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_pager, container, false);
        ButterKnife.inject(this, view);
        newsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        newsPagerAdapter.setNewsFragmentList(newsFragmentList);
        vp_news_pager.setAdapter(newsPagerAdapter);
        initIndicator();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < types.length; i++) {
            NewsFragment newsFragment = new NewsFragment(types[i]);
            Logger.d(types[i]);
            newsFragmentList.add(newsFragment);
        }
        newsPagerAdapter.setNewsFragmentList(newsFragmentList);
        newsPagerAdapter.notifyDataSetChanged();
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return types.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(typesCN[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_news_pager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vp_news_pager);
    }
}
