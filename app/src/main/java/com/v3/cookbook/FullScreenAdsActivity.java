package com.v3.cookbook;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;



public class FullScreenAdsActivity extends AppCompatActivity {
    private static final String TAG = "FullAdsActivity";
//    private AdRequest adRequest;
//    public InterstitialAd interstitialAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_ads);
//        this.interstitialAd = new InterstitialAd(this);
//        this.interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_full));
//        this.interstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                Log.i(FullScreenAdsActivity.TAG, "onAdLoaded: ");
//                FullScreenAdsActivity.this.interstitialAd.show();
//            }
//
//            public void onAdOpened() {
//                super.onAdOpened();
//                Log.i(FullScreenAdsActivity.TAG, "onAdOpened: ");
//            }
//
//            public void onAdLeftApplication() {
//                super.onAdLeftApplication();
//                Log.i(FullScreenAdsActivity.TAG, "onAdLeftApplication: ");
//            }
//
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                finish();
//                Log.i(FullScreenAdsActivity.TAG, "onAdFailedToLoad: ");
//            }
//
//            public void onAdClosed() {
//                super.onAdClosed();
//                finish();
//                Log.i(FullScreenAdsActivity.TAG, "onAdClosed: ");
//            }
//        });
//        this.adRequest = new AdRequest.Builder().build();
//        this.interstitialAd.loadAd(this.adRequest);
    }
}
