package com.v3.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.v3.cookbook.adapters.SwitchFragmentAdapter;
import com.v3.cookbook.home.HomeFragment;
import com.v3.cookbook.moduls.IteamTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NMainActivity extends AppCompatActivity {
    @BindView(R.id.tab_switch_sreen)
    TabLayout tabNavigation;

    @BindView(R.id.view_screen)
    ViewPager viewScreen;

    private ArrayList<Pair<String, Fragment>> listFragment = new ArrayList<>();
    private SwitchFragmentAdapter switchFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_main);
        ButterKnife.bind(this);

        addFragment();
        addviewFragment();
    }

    private void addviewFragment(){
        switchFragmentAdapter = new SwitchFragmentAdapter(getSupportFragmentManager());
        viewScreen.setAdapter(switchFragmentAdapter);
        switchFragmentAdapter.setFragments(listFragment);
        tabNavigation.setupWithViewPager(viewScreen);
        initUITablayout();
        TextView tvTitle = tabNavigation.getTabAt(0).
                getCustomView().findViewById(R.id.tv_title);
        View view = tabNavigation.getTabAt(0).
                getCustomView().findViewById(R.id.imv_line);
        tvTitle.setTextColor(getResources().getColor(R.color.colorBlack));
        view.setBackgroundResource(R.drawable.select_tab);
        tabNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabSelect = tab.getCustomView();
                TextView tvTitle = tabSelect.findViewById(R.id.tv_title);
                View view =tabSelect.findViewById(R.id.imv_line);
                tvTitle.setTextColor(getResources().getColor(R.color.colorBlack));
                view.setBackgroundResource(R.drawable.select_tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabSelect = tab.getCustomView();
                TextView tvTitle = tabSelect.findViewById(R.id.tv_title);
                View view =tabSelect.findViewById(R.id.imv_line);
                tvTitle.setTextColor(getResources().getColor(R.color.colorlight));
                view.setBackgroundResource(R.drawable.un_select_tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initUITablayout(){
        String title;
        for (int i = 0; i < listFragment.size(); i++) {
            title = listFragment.get(i).first;
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_tab_layout, null);
            TextView tvTitle = linearLayout.findViewById(R.id.tv_title);
            tvTitle.setText(title);
            tabNavigation.getTabAt(i).setCustomView(linearLayout);
        }
    }

    private void addFragment(){
        listFragment.add(new Pair<>(getString(R.string.menu_home)
                , new HomeFragment()));
        listFragment.add(new Pair<>(getString(R.string.category)
                , new HomeFragment()));
        listFragment.add(new Pair<>(getString(R.string.menu_info)
                , new HomeFragment()));
    }
}