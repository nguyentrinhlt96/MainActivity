package com.v3.cookbook.baseview;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import com.v3.cookbook.R;
import com.v3.cookbook.baseview.utils.ActivityUtils;

import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment {
    private static final boolean DEFAULT_START_ON_ANIMATION_ENDED = false;
    protected int mAnimIn = R.anim.slide_right_in;
    protected int mAnimOut = R.anim.slide_none;
    protected boolean mIsStarted = false;
    protected View mRootView;
    protected boolean mStartOnAnimationEnded = false;
    public abstract int getLayoutId();

    public abstract boolean needTranslationAnimation();

    public abstract void startPresent();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind((Object) this, this.mRootView);
        this.mRootView.setClickable(true);
        return this.mRootView;
    }

    public void addChildFragment(Fragment fragment, int frameId, boolean addToBackStack, String tag) {
        ActivityUtils.addChildFragment(getChildFragmentManager(), fragment, frameId, addToBackStack, tag);
    }

    public BaseFragment setAnimOut(int animOut) {
        this.mAnimOut = animOut;
        return this;
    }

    public BaseFragment setAnimIn(int animIn) {
        this.mAnimIn = animIn;
        return this;
    }

    public BaseFragment setStartOnAnimationEnded(boolean startOnAnimationEnded) {
        this.mStartOnAnimationEnded = startOnAnimationEnded;
        return this;
    }

    private static long getNextAnimationDuration(Fragment fragment, long defValue) {
        try {
            Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);
            Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimField.getInt(fragment));
            return nextAnim == null ? defValue : nextAnim.getDuration();
        } catch (NotFoundException | IllegalAccessException | NoSuchFieldException e) {
            return defValue;
        }
    }

    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim;
        if (!needTranslationAnimation()) {
            return null;
        }
        if (enter) {
            anim = AnimationUtils.loadAnimation(getActivity(), this.mAnimIn);
        } else {
            anim = AnimationUtils.loadAnimation(getActivity(), this.mAnimOut);
        }
        anim.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (BaseFragment.this.mRootView != null) {
                    BaseFragment.this.mRootView.setLayerType(0, null);
                }
                if (BaseFragment.this.mStartOnAnimationEnded && !BaseFragment.this.mIsStarted) {
                    BaseFragment.this.startPresent();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        Fragment parent = getParentFragment();
        if (enter || parent == null || !parent.isRemoving()) {
            return anim;
        }
        Animation doNothingAnim = new AlphaAnimation(1.0f, 1.0f);
        doNothingAnim.setDuration(500);
        return doNothingAnim;
    }

    public void onDisplay() {
    }

}
