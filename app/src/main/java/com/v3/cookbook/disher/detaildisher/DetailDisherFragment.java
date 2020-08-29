package com.v3.cookbook.disher.detaildisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.v3.cookbook.FullScreenAdsActivity;
import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.ads.CheckInternet;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.fragments.ViewFragmentDetails;
import com.v3.cookbook.moduls.IteamTab;
import com.v3.cookbook.moduls.LoadInfoDetailDisher;
import com.v3.cookbook.moduls.OnLoadFavaurite;
import com.v3.cookbook.view.GlideImageLoader;
import com.v3.cookbook.view.WrapContentHeightViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class DetailDisherFragment extends ViewFragment<ViewDetailDisherContract.Presenter> implements ViewDetailDisherContract.View {
    FragmentStatePagerAdapter adapterInterest;
    @BindView(R.id.imvDisher)
    RoundedImageView imageDisher;
    @BindView(R.id.imvProgress)
    ImageView imvProgress;
    ArrayList<ViewFragmentDetails> listFragment = new ArrayList<>();
    public List<IteamTab> lstTab = new ArrayList();
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.imvFavaurite)
    ImageView imvFauvarite;

    private DishSqlite mSqlite;
    private boolean isFavaurite;


    public static DetailDisherFragment getInstance() {
        return new DetailDisherFragment();
    }

    public int getLayoutId() {
        return R.layout.detail_disher_fragment;
    }

    public void initLayout() {
        super.initLayout();
        EventBus.getDefault().register(this);
        this.lstTab.add(new IteamTab("0", getResources().getString(R.string.title_material)));
        this.lstTab.add(new IteamTab("1", getResources().getString(R.string.title_step)));
        this.lstTab.add(new IteamTab("2", getResources().getString(R.string.title_similar_dishes)));
        setUpTabLayout();
        if (!TextUtils.isEmpty(this.mPresenter.getName())) {
            this.tvName.setText(this.mPresenter.getName());
        }
        if (!TextUtils.isEmpty(this.mPresenter.getUrlDish())) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH);

            new GlideImageLoader(getViewContext(), imageDisher,
                    imvProgress).load(Utlis.modifyDropboxUrl(this.mPresenter.getUrlDish()), options);
        }
        if (mPresenter.getFauvarite().equals("0")) {
            isFavaurite = true;
            imvFauvarite.setImageResource(R.drawable.ic_heart_2);
        } else {
            isFavaurite = false;
            imvFauvarite.setImageResource(R.drawable.ic_like);
        }
        Utlis.currentState = 3;
//        triggerBanner();
    }

    //   private void triggerBanner() {
//        if (!Utlis.isEnable || !CheckInternet.isNetworkAvailable(getViewContext())) {
//            this.adView.setVisibility(GONE);
//            return;
//        }
//        this.adView.setVisibility(View.VISIBLE);
//        initAdmob();
//
//    }
//
//
//    private void initAdmob() {
//        MobileAds.initialize(getViewContext(),Utlis.AD_UNIT_BANNER);
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

    private void setUpTabLayout() {
        if (this.lstTab.size() > 0) {
            for (int i = 0; i < this.lstTab.size(); i++) {
                ViewFragmentDetails fragment = new ViewFragmentDetails();
                fragment.setData(((IteamTab) this.lstTab.get(i)).getId()
                        ,this.mPresenter.getIngredients()
                        ,this.mPresenter.getStep());
                this.listFragment.add(fragment);
            }
            this.adapterInterest = new FragmentStatePagerAdapter(getChildFragmentManager()) {
                public Fragment getItem(int position) {
                    return (Fragment) DetailDisherFragment.this.listFragment.get(position);
                }

                public int getCount() {
                    return DetailDisherFragment.this.lstTab.size();
                }

                public CharSequence getPageTitle(int position) {
                    return ((IteamTab) DetailDisherFragment.this.lstTab.get(position)).getTitleTab();
                }
            };
            this.viewPager.setAdapter(this.adapterInterest);
            ArrayList<ViewFragmentDetails> arrayList = this.listFragment;
            if (arrayList != null) {
                this.viewPager.setOffscreenPageLimit(arrayList.size());
            }
            this.tabLayout.setupWithViewPager(this.viewPager);
        }
    }

    @OnClick({R.id.imgBack, R.id.imvHome,R.id.imvFavaurite})
    public void onClickView(android.view.View view) {
       switch (view.getId()){
           case R.id.imgBack:
               if(mPresenter.getBack() == 0){
                   FragmentManager fmManager = getFragmentManager();
                   if (fmManager.getBackStackEntryCount() > 0) {
                       fmManager.popBackStack(fmManager.getBackStackEntryAt(fmManager.getBackStackEntryCount()-2).getId(),
                               FragmentManager.POP_BACK_STACK_INCLUSIVE);
                   }
               }else if(mPresenter.getBack() == 1){
                   FragmentManager fmManager = getFragmentManager();
                   if (fmManager.getBackStackEntryCount() > 0) {
                       fmManager.popBackStack(fmManager.getBackStackEntryAt(fmManager.getBackStackEntryCount()-2).getId(),
                               FragmentManager.POP_BACK_STACK_INCLUSIVE);
                   }
               }else{
                   mPresenter.back();
               }
               break;

           case R.id.imvHome:
               if(mPresenter.getBack() == 0 || mPresenter.getBack() == 2){
                   Utlis.currentState = Utlis.HOME;
                   FragmentManager fmManager = getFragmentManager();
                   if (fmManager.getBackStackEntryCount() > 0) {
                       fmManager.popBackStack(fmManager.getBackStackEntryAt(fmManager.getBackStackEntryCount()-2).getId(),
                               FragmentManager.POP_BACK_STACK_INCLUSIVE);
                   }
               }else if(mPresenter.getBack() == 1 ){
                   Utlis.currentState = Utlis.HOME;
                   FragmentManager fmManager = getFragmentManager();
                   if (fmManager.getBackStackEntryCount() > 0) {
                       fmManager.popBackStack(fmManager.getBackStackEntryAt(fmManager.getBackStackEntryCount()-3).getId(),
                               FragmentManager.POP_BACK_STACK_INCLUSIVE);
                   }
               }else {
                   mPresenter.back();
               }
               StartAppAd.showAd(getContext());
               break;

           case R.id.imvFavaurite:
               this.mSqlite = new DishSqlite(getContext());
               if (isFavaurite) {
                   isFavaurite = false;
                   imvFauvarite.setImageResource(R.drawable.ic_like);
                   mSqlite.updateFavaurite(mPresenter.getId(), "1");
               }else{
                   isFavaurite = true;
                   imvFauvarite.setImageResource(R.drawable.ic_heart_2);
                   mSqlite.updateFavaurite(mPresenter.getId(), "0");
               }
               EventBus.getDefault().post(new OnLoadFavaurite());
               break;
       }
    }

    @Subscribe
    public void onEventFavaurite(LoadInfoDetailDisher info) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);

        new GlideImageLoader(getViewContext(),imageDisher,
                imvProgress).load(info.getUrl(),options);
        tvName.setText(info.getNameDish());
        if(info.getFavaurite().equals("0")){
            isFavaurite = true;
            imvFauvarite.setImageResource(R.drawable.ic_heart_2);
        }else{
            isFavaurite = false;
            imvFauvarite.setImageResource(R.drawable.ic_like);
        }
        if (this.lstTab.size() > 0) {
            listFragment.clear();
            for (int i = 0; i < this.lstTab.size(); i++) {
                ViewFragmentDetails fragment = new ViewFragmentDetails();
                fragment.setData(lstTab.get(i).getId()
                        ,info.getIngredients()
                        ,info.getStep());
                this.listFragment.add(fragment);
            }
            this.adapterInterest = new FragmentStatePagerAdapter(getChildFragmentManager()) {
                public Fragment getItem(int position) {
                    return listFragment.get(position);
                }

                public int getCount() {
                    return DetailDisherFragment.this.lstTab.size();
                }

                public CharSequence getPageTitle(int position) {
                    return lstTab.get(position).getTitleTab();
                }
            };
            this.viewPager.setAdapter(this.adapterInterest);
            ArrayList<ViewFragmentDetails> arrayList = this.listFragment;
            if (arrayList != null) {
                this.viewPager.setOffscreenPageLimit(arrayList.size());
            }
            this.tabLayout.setupWithViewPager(this.viewPager);
        }
    }

}
