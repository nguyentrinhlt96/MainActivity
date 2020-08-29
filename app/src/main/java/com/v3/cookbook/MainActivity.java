package com.v3.cookbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerListener;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.v3.cookbook.baseview.HomeBaseActivity;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.home.HomePresenter;
import com.v3.cookbook.moduls.OnClickNavigation;
import com.v3.cookbook.moduls.OnClickOpenFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends HomeBaseActivity {
    private int iClick = 1;

    public void onCreate(Bundle savedInstanceState) {
        StartAppSDK.init(getViewContext(), Utlis.APP_ID,false);
        StartAppAd.disableSplash();
        StartAppAd.disableAutoInterstitial();
        super.onCreate(savedInstanceState);



    }

    public ViewFragment onCreateFirstFragment() {

        EventBus.getDefault().register(this);
        return (ViewFragment) new HomePresenter(this).getFragment();
    }

    @Subscribe
    public void onEventOpenFramnet(OnClickOpenFragment onClick) {
        this.iClick = onClick.getiClick();
    }

    public void onBackPressed() {
        if (this.iClick == 0) {
            EventBus.getDefault().post(new OnClickNavigation());
        } else {
            StartAppAd.disableAutoInterstitial();
            super.onBackPressed();
        }
    }
}
