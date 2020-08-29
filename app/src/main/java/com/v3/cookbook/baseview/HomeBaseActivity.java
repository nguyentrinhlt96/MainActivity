package com.v3.cookbook.baseview;

import android.content.Intent;
import android.os.Bundle;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.v3.cookbook.SubActivity;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.baseview.interfaces.IPresenter;

public abstract class HomeBaseActivity extends ContainerActivity {
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void showToast(int stringResourceId) {
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void showToast(String msg) {
    }

    public void startActivity(IPresenter<?, ?> presenter, Bundle bundle) {
        Intent intent = SubActivity.createIntent(getApplicationContext());
        intent.putExtra(SubActivity.EXTRA_FRAGMENT_ARGS, bundle);
        SubActivity.startActivityLeft(this, intent, presenter);
    }

    public void startActivityForResult(IPresenter<?, ?> iPresenter, int requestCode, Bundle bd) {
        Intent intent = SubActivity.createIntent(getApplicationContext());
        intent.putExtra(SubActivity.EXTRA_FRAGMENT_ARGS, bd);
        SubActivity.startActivityForResultLeft(this, intent, requestCode, iPresenter);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
