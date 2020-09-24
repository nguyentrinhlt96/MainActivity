package com.v3.cookbook.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.v3.cookbook.FullScreenAdsActivity;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.adapters.CategoryAdapter;
import com.v3.cookbook.ads.CheckInternet;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.fragments.LeftMenuFragment;
import com.v3.cookbook.moduls.Categories;
import com.v3.cookbook.moduls.OnClickNavigation;
import com.v3.cookbook.moduls.OnClickOpenFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.internal.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.view.View.GONE;


public class HomeFragment extends ViewFragment<ViewContract.Presenter> implements CategoryAdapter.OnItemClickListener, ViewContract.View {
    private int columnView = 2;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.imgMenu)
    ImageView imgMenuNav;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    private List<Categories> listCategory = new ArrayList();
    private CategoryAdapter mCategoryAdapter;
    private DishSqlite mSqlite;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategory;

    @BindView(R.id.adView_container)
    Banner adView;

    private static volatile HomeFragment sInstance;


    private int preState = -1;

    public int getLayoutId() {

        return R.layout.fragment_home;
    }

    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    public void initLayout() {
        sInstance = this;
        EventBus.getDefault().register(this);
        this.mSqlite = new DishSqlite(getContext());
        getCategory();
        menuLeft();
        adView.loadAd();
        preState = Utlis.currentState;
//        triggerBanner();

    }

//    private void triggerBanner() {
//        if (!Utlis.isEnable || !CheckInternet.isNetworkAvailable(getViewContext())) {
//            this.adView.setVisibility(GONE);
//            return;
//        }
//        this.adView.setVisibility(View.VISIBLE);
//        initAdmob();
//
//        if (this.preState == Utlis.HOME) {
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intentADSFull = new Intent(getViewContext(), FullScreenAdsActivity.class);
//                    startActivity(intentADSFull);
//                }
//            },3000);
//
//        }
//    }
//
//    private void initAdmob() {
//     MobileAds.initialize(getViewContext(),Utlis.AD_UNIT_BANNER);
//        this.adView.loadAd(new AdRequest.Builder().addTestDevice(Utlis.TEST_DEVICE).build());
//        this.adView.setAdListener(new AdListener() {
//            public void onAdFailedToLoad(int errorCode) {
//                adView.setVisibility(GONE);
//            }
//
//            public void onAdLoaded() {
//                adView.setVisibility(View.VISIBLE);
//                System.out.println("Banner Admob is load, but still INVISIBLE");
//            }
//        });
//    }


    private void menuLeft() {
        this.drawerLayout.addDrawerListener(new DrawerListener() {
            public void onDrawerSlide(android.view.View view, float v) {
                boolean z = HomeFragment.this.getFragmentManager().findFragmentByTag("LeftMenuFragment") instanceof LeftMenuFragment;
            }

            public void onDrawerOpened(android.view.View view) {
            }

            public void onDrawerClosed(android.view.View view) {
            }

            public void onDrawerStateChanged(int i) {
            }
        });
        this.mRootView.setFocusableInTouchMode(true);
        this.mRootView.requestFocus();
        this.mRootView.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                HomeFragment.this.getFragmentManager().popBackStack((String) null, 1);
                HomeFragment.this.drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void getCategory() {
        this.mSqlite.checkExistsDatabase(getContext());
        this.listCategory = this.mSqlite.getListCategories();
        this.rvCategory.setLayoutManager(new GridLayoutManager(getContext(), this.columnView));
        this.mCategoryAdapter = new CategoryAdapter(this.listCategory, getContext());
        this.mCategoryAdapter.setListener(this);
        this.rvCategory.setAdapter(this.mCategoryAdapter);
    }

    public void onClick(int idCategory, String title) {
        this.mPresenter.viewListDisher(idCategory, title);
    }

    @OnClick({R.id.imgMenu, R.id.imgSearch})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.imgMenu:
                OnClickOpenFragment onClickOpenFragment = new OnClickOpenFragment();
                onClickOpenFragment.setiClick(0);
                EventBus.getDefault().post(onClickOpenFragment);
                this.drawerLayout.openDrawer(Gravity.LEFT);
                return;

            case R.id.imgSearch:
                this.mPresenter.viewSeach(0);
                return;

            default:
                return;
        }
    }

    @Subscribe
    public void onEventOpenFramnet(OnClickNavigation onClick) {
        OnClickOpenFragment onClickOpenFragment = new OnClickOpenFragment();
        onClickOpenFragment.setiClick(1);
        EventBus.getDefault().post(onClickOpenFragment);
        if (this.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            this.drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
