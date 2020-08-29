package com.v3.cookbook.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.v3.cookbook.MainActivity;
import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.baseview.BaseFragment;
import com.v3.cookbook.favaurite.FauvariteActivity;
import com.v3.cookbook.moduls.OnBack;
import com.v3.cookbook.moduls.OnClickNavigation;
import com.v3.cookbook.moduls.OnSetBool;

import butterknife.OnClick;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LeftMenuFragment extends BaseFragment {
    private boolean isView = true;

    public int getLayoutId() {
        return R.layout.layout_left_menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
    @OnClick({R.id.rl_home, R.id.rl_fauvarite, R.id.rl_rate, R.id.rl_share})
    public void onClickNavigation(View view) {
        EventBus.getDefault().post(new OnClickNavigation());
        switch (view.getId()) {
            case R.id.rl_home:
                if(!isView) {
                    isView = true;
                    Utlis.currentState = Utlis.HOME;
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    StartAppAd.showAd(getContext());
                }
                break;

            case R.id.rl_fauvarite:
                if(isView) {
                    Utlis.currentState = Utlis.FAVAURITE;
                    Intent intent = new Intent(getContext(), FauvariteActivity.class);
                    startActivity(intent);
                    StartAppAd.showAd(getContext());
                }
                break;

            case R.id.rl_rate:
                rate();
                return;

            case R.id.rl_share:
                share();
                return;

            default:
                return;
        }
    }

    private void rate() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://play.google.com/store/apps/details?id=");
        sb.append(getActivity().getPackageName());
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
    }

    private void share() {
        Intent intentShare = new Intent("android.intent.action.SEND");
        intentShare.setType("text/plain");
        intentShare.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.title_dowload));
        sb.append("https://play.google.com/store/apps/details?id=");
        sb.append(getActivity().getPackageName());
        intentShare.putExtra("android.intent.extra.TEXT", sb.toString());
        startActivity(Intent.createChooser(intentShare, getString(R.string.share_with)));
    }

    public void startPresent() {
    }

    public boolean needTranslationAnimation() {
        return false;
    }

    @Subscribe
    public void onEventBack(OnBack onBack) {
        isView = true;
    }

    @Subscribe
    public void onEventBool(OnSetBool onSetBool) {
        isView = onSetBool.isView();
    }


}
