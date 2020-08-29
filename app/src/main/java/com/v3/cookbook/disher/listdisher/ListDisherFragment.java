package com.v3.cookbook.disher.listdisher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.v3.cookbook.FullScreenAdsActivity;
import com.startapp.android.publish.ads.banner.Banner;
import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.adapters.DisherAdapter;
import com.v3.cookbook.ads.CheckInternet;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.moduls.Disher;
import com.v3.cookbook.moduls.OnLoadFavaurite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ListDisherFragment extends ViewFragment<ViewListDisherContract.Presenter> implements ViewListDisherContract.View {
    public List<Disher> listDisher = new ArrayList();
    private DisherAdapter mDisherAdapter;
    private DishSqlite mSqlite;
    @BindView(R.id.rv_disher)
    RecyclerView rvDisher;
    @BindView(R.id.txtTitle)
    TextView tvTitle;
    @BindView(R.id.adView_container)
    Banner adView;
    public static ListDisherFragment getInstance() {
        return new ListDisherFragment();
    }

    public int getLayoutId() {

        return R.layout.fragment_list_disher;
    }

    public void initLayout() {
        this.mSqlite = new DishSqlite(getContext());
        EventBus.getDefault().register(this);
        getDisher();

        Utlis.currentState = 2;
//        triggerBanner();
    }

//    private void triggerBanner() {
//        if (!Utlis.isEnable || !CheckInternet.isNetworkAvailable(getViewContext())) {
//            this.adView.setVisibility(GONE);
//            return;
//        }
//        this.adView.setVisibility(View.VISIBLE);
//        initAdmob();
//    }

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

    private void getDisher() {
        if (this.mPresenter.getIdCategory() != 0) {
            this.mSqlite.checkExistsDatabase(getContext());
            DishSqlite dishSqlite = this.mSqlite;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mPresenter.getIdCategory());
            sb.append("");
            this.listDisher = dishSqlite.getListDisher(sb.toString());
            this.rvDisher.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            this.mDisherAdapter = new DisherAdapter(getContext(), this.listDisher, new DisherAdapter.OnClickDetailDisher() {
                public void onClickDetail(int position) {
                    ListDisherFragment.this.mPresenter.getDetail(listDisher.get(position).getId()
                            , listDisher.get(position).getUrlImageDisher()
                            , listDisher.get(position).getNameDisher()
                            , listDisher.get(position).getIngredients()
                            , listDisher.get(position).getInstruction()
                            , listDisher.get(position).getFavaurite()
                            ,2);
                }

            });
            this.rvDisher.setAdapter(this.mDisherAdapter);
        }
        if (!TextUtils.isEmpty(this.mPresenter.getTitle())) {
            this.tvTitle.setText(this.mPresenter.getTitle());
        }
    }

    @OnClick({R.id.imgBack, R.id.img_search})
    public void onClickView(View view) {
        int id = view.getId();
        if (id == R.id.imgBack) {
            this.mPresenter.back();
        } else if (id == R.id.img_search) {

            this.mPresenter.viewSeach(1);
        }
    }

    @Subscribe
    public void onEventFavaurite(OnLoadFavaurite onClick) {
        listDisher.clear();
        getDisher();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
