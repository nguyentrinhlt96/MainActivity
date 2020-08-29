package com.v3.cookbook.favaurite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.v3.cookbook.R;
import com.v3.cookbook.baseview.HomeBaseActivity;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.disher.detaildisher.DetailDisherPresenter;
import com.v3.cookbook.favaurite.favaurite2.FavauritePresenter;
import com.v3.cookbook.home.HomePresenter;
import com.v3.cookbook.moduls.OnBack;
import com.v3.cookbook.search.SearchPresenter;

import org.greenrobot.eventbus.EventBus;

public class FauvariteActivity extends HomeBaseActivity {
    private StartAppAd startAppAd = new StartAppAd(this);
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment) new FavauritePresenter(this).getFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StartAppAd.disableAutoInterstitial();
        EventBus.getDefault().post(new OnBack());
    }


}
