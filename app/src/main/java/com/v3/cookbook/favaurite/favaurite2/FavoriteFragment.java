package com.v3.cookbook.favaurite.favaurite2;

import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.v3.cookbook.FullScreenAdsActivity;
import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.adapters.DisherAdapter;
import com.v3.cookbook.ads.CheckInternet;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.fragments.LeftMenuFragment;
import com.v3.cookbook.moduls.Disher;
import com.v3.cookbook.moduls.OnClickOpenFragment;
import com.v3.cookbook.moduls.OnSetBool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;


public class FavoriteFragment extends ViewFragment<ViewFavauriteContract.Presenter> implements ViewFavauriteContract.View {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.imgMenu)
    ImageView imgMenuNav;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.rv_fauvarite)
    RecyclerView rcFauvarite;
    private List<Disher> lstFauvarite = new ArrayList<>();
    DisherAdapter mDisherAdapter;
    DishSqlite mSqlite;
//    @BindView(R.id.adView_container)
//    AdView adView;
    @BindView(R.id.txtNotifi)
    TextView txtNotifi;
    private int preState = -1;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    public void initLayout() {
        mSqlite = new DishSqlite(getViewContext());
        OnSetBool onSetBool = new OnSetBool();
        onSetBool.setView(false);
        EventBus.getDefault().post(onSetBool);
        menuLeft();
        getFavaurite();
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
//        if (this.preState == Utlis.FAVAURITE) {
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

    private void getFavaurite() {
        this.lstFauvarite = mSqlite.getFauvarite("1");
        if(lstFauvarite.size()>0) {
            txtNotifi.setVisibility(GONE);
            rcFauvarite.setVisibility(View.VISIBLE);
            this.rcFauvarite.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            this.mDisherAdapter = new DisherAdapter(getContext(), this.lstFauvarite, new DisherAdapter.OnClickDetailDisher() {
                public void onClickDetail(int position) {
                    mPresenter.getDetail(lstFauvarite.get(position).getId()
                            , lstFauvarite.get(position).getUrlImageDisher()
                            , lstFauvarite.get(position).getNameDisher()
                            , lstFauvarite.get(position).getIngredients()
                            , lstFauvarite.get(position).getInstruction()
                            , lstFauvarite.get(position).getFavaurite()
                            , 3);
                }

            });
            this.rcFauvarite.setAdapter(this.mDisherAdapter);
        }else{
            txtNotifi.setVisibility(View.VISIBLE);
            rcFauvarite.setVisibility(GONE);
        }
    }

    private void menuLeft() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            public void onDrawerSlide(android.view.View view, float v) {
                boolean z = getFragmentManager().findFragmentByTag("LeftMenuFragment") instanceof LeftMenuFragment;
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
        this.mRootView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                getFragmentManager().popBackStack((String) null, 1);
                drawerLayout.closeDrawers();
                return true;
            }
        });
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
                mPresenter.viewSeach(0);
                return;

            default:
                return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
