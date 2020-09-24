package com.v3.cookbook.adapters;

import android.util.Pair;
import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SwitchFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Pair<String,Fragment>> listFragment = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<>();
    public SwitchFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i).second;
    }

    public void setFragments(ArrayList<Pair<String, Fragment>> fragments) {
        if(fragments!=null)
            this.listFragment = fragments;
        else
            this.listFragment = new ArrayList<>();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFragment.get(position).first;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}
