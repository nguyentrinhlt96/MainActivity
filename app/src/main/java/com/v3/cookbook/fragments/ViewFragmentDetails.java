package com.v3.cookbook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.v3.cookbook.R;
import com.v3.cookbook.adapters.DisherAdapter;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.disher.listdisher.ListDisherFragment;
import com.v3.cookbook.moduls.Disher;
import com.v3.cookbook.moduls.LoadInfoDetailDisher;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ViewFragmentDetails extends Fragment {
    private String info;
    private String select;
    private byte[] steps;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.rv_ramdom)
    RecyclerView rcRamdom;
    private DishSqlite mSqlite;
    private List<Disher> lstRandom = new ArrayList<>();
    private DisherAdapter mDisherAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item, container, false);
        ButterKnife.bind( this, view);
        initView();
        return view;
    }


    private void initView() {
        mSqlite = new DishSqlite(getContext());
        if("0".equals(select)) {
            rcRamdom.setVisibility(View.GONE);
            tvInfo.setVisibility(View.VISIBLE);
            String strMaterial = info.replaceAll(";","\n- ");
            tvInfo.setText("- " + strMaterial);
        }else if("1".equals(select)){
            rcRamdom.setVisibility(View.GONE);
            tvInfo.setVisibility(View.VISIBLE);
            String str = null;
            String str2 = null;
            try {
                str = new String(steps, "UTF-8");
                str2 = str.replaceAll("#","\n-");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            tvInfo.setText("-" + str2);
        }else {
            rcRamdom.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);
            lstRandom = mSqlite.ramdomDish();
            this.rcRamdom.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            this.mDisherAdapter = new DisherAdapter(getContext(), this.lstRandom, new DisherAdapter.OnClickDetailDisher() {
                public void onClickDetail(int position) {
                    LoadInfoDetailDisher loadInfoDetailDisher =  new LoadInfoDetailDisher();
                    loadInfoDetailDisher.setId(lstRandom.get(position).getId());
                    loadInfoDetailDisher.setUrl(lstRandom.get(position).getUrlImageDisher());
                    loadInfoDetailDisher.setNameDish(lstRandom.get(position).getNameDisher());
                    loadInfoDetailDisher.setIngredients(lstRandom.get(position).getIngredients());
                    loadInfoDetailDisher.setStep(lstRandom.get(position).getInstruction());
                    loadInfoDetailDisher.setFavaurite(lstRandom.get(position).getFavaurite());
                    EventBus.getDefault().post(loadInfoDetailDisher);


                }
            });
            this.rcRamdom.setAdapter(this.mDisherAdapter);
        }
    }

    public void setData(String select2, String info2, byte[] steps2) {
        this.select = select2;
        this.info = info2;
        this.steps = steps2;
    }
}
