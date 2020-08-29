package com.v3.cookbook.search;


import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;

import com.v3.cookbook.R;
import com.v3.cookbook.Utlis;
import com.v3.cookbook.adapters.DisherAdapter;
import com.v3.cookbook.ads.CheckInternet;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.moduls.Disher;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class SearchFragment extends ViewFragment<ViewSearchContract.Presenter> implements ViewSearchContract.View {
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.ll_view_no_data)
    LinearLayout llViewNoData;
    public DisherAdapter mAdapter;
    private DishSqlite mDatabase;
    public List<Disher> mList = new ArrayList();
    @BindView(R.id.rv_search_disher)
    RecyclerView rcResult;
    CountDownTimer searchCountDownTimer;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    public void initLayout() {
        this.mDatabase = new DishSqlite(getContext());
        setupUI(this.mRootView.findViewById(R.id.lnLayout));
        listSearch();
        search();
        Utlis.currentState = 4;
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

    private void search() {
        this.edtSearch.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                if (event.getAction() == 0 && keyCode == 66) {
                    Utlis.hideKeyboard(SearchFragment.this.edtSearch, SearchFragment.this.getViewContext());
                }
                return false;
            }
        });
        this.edtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (SearchFragment.this.searchCountDownTimer == null) {
                    SearchFragment searchFragment = SearchFragment.this;
                    CountDownTimer r1 = new CountDownTimer(50, 50) {
                        public void onTick(long millisUntilFinished) {
                            if (SearchFragment.this.edtSearch != null) {
                                String text = SearchFragment.this.edtSearch.getText().toString().trim();
                                if (text.equals("")) {
                                    SearchFragment.this.rcResult.setVisibility(View.GONE);
                                    SearchFragment.this.llViewNoData.setVisibility(View.VISIBLE);
                                    return;
                                }
                                SearchFragment.this.llViewNoData.setVisibility(View.GONE);
                                if (SearchFragment.this.mAdapter != null && SearchFragment.this.mList.size() > 0) {
                                    SearchFragment.this.mAdapter.getFilter().filter(text);
                                }
                                int size = 0;
                                if (SearchFragment.this.mAdapter != null) {
                                    size = SearchFragment.this.mAdapter.getFilteredData().size();
                                }
                                if (size <= 0) {
                                    SearchFragment.this.rcResult.setVisibility(View.GONE);
                                } else {
                                    SearchFragment.this.rcResult.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        public void onFinish() {
                            String text = SearchFragment.this.edtSearch.getText().toString().trim();
                            if (text.equals("")) {
                                SearchFragment.this.rcResult.setVisibility(View.GONE);
                                SearchFragment.this.llViewNoData.setVisibility(View.VISIBLE);
                                return;
                            }
                            SearchFragment.this.llViewNoData.setVisibility(View.GONE);
                            if (mAdapter != null && mList.size() > 0) {
                                mAdapter.getFilter().filter(text);
                            }
                            int size = 0;
                            if (SearchFragment.this.mAdapter != null) {
                                size = SearchFragment.this.mAdapter.getFilteredData().size();
                            }
                            if (size <= 0) {
                                SearchFragment.this.rcResult.setVisibility(View.GONE);
                            } else {
                                SearchFragment.this.rcResult.setVisibility(View.VISIBLE);
                            }
                        }
                    };
                    searchFragment.searchCountDownTimer = r1;
                    SearchFragment.this.searchCountDownTimer.start();
                    return;
                }
                SearchFragment.this.searchCountDownTimer.cancel();
                SearchFragment.this.searchCountDownTimer.start();
            }
        });
    }

    private void listSearch() {
        this.mList = this.mDatabase.searchListDisher();
        this.mAdapter = new DisherAdapter(getContext(), this.mList, new DisherAdapter.OnClickDetailDisher() {
            public void onClickDetail(int position) {
                mPresenter.getDetail(mAdapter.updateList().get(position).getId()
                        , mAdapter.updateList().get(position).getUrlImageDisher()
                        , mAdapter.updateList().get(position).getNameDisher()
                        , mAdapter.updateList().get(position).getIngredients()
                        , mAdapter.updateList().get(position).getInstruction()
                        , mAdapter.updateList().get(position).getFavaurite()
                        , mPresenter.getback());
            }
        });
        this.rcResult.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        this.rcResult.setAdapter(this.mAdapter);
    }

    private void clickSearch() {
        if (edtSearch.getVisibility() == View.GONE) {
            this.edtSearch.setVisibility(View.VISIBLE);
            this.txtTitle.setVisibility(View.GONE);
        }else {
            this.edtSearch.setVisibility(View.GONE);
            this.txtTitle.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.imgBack, R.id.img_search})
    public void onClickView(android.view.View view) {
        int id = view.getId();
        if (id == R.id.imgBack) {
            this.mPresenter.back();
        } else if (id == R.id.img_search) {
            clickSearch();
        }
    }

    public void setupUI(android.view.View view) {
        if (view.getId() != R.id.edtSearch) {
            if (view.getId() == R.id.edtSearch) {
                view.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(android.view.View v, MotionEvent event) {
                        return true;
                    }
                });
                return;
            }
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(android.view.View v, MotionEvent event) {
                    if (SearchFragment.this.edtSearch.isFocused()) {
                        Utlis.hideKeyboard(SearchFragment.this.edtSearch, SearchFragment.this.getActivity());
                    }
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setupUI(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
