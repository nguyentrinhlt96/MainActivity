package com.v3.cookbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.v3.cookbook.R;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.moduls.Categories;
import com.v3.cookbook.moduls.Disher;
import com.v3.cookbook.moduls.OnOneOffClickListener;
import com.v3.cookbook.view.GlideImageLoader;

import java.util.List;

public class CategoryAdapter extends Adapter<CategoryAdapter.RecyclerViewHolder> {
    private Categories categories;
    public List<Categories> listCategory;
    public OnItemClickListener listener;
    private List<Disher> lstDisher;
    private Context mContext;
    private DishSqlite mSqlite;

    public interface OnItemClickListener {
        void onClick(int i, String str);
    }

    public class RecyclerViewHolder extends ViewHolder {
        @BindView(R.id.iv_category)
        RoundedImageView ivCategory;
        @BindView(R.id.imvProgress)
        ImageView imvProgress;
        @BindView(R.id.rl_category)
        RelativeLayout rlCategory;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_size_dish)
        TextView tvSize;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind( this, itemView);
        }
    }


    public CategoryAdapter(List<Categories> listCategory2, Context mContext2) {
        this.listCategory = listCategory2;
        this.mContext = mContext2;
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false));
    }


    public void onBindViewHolder(RecyclerViewHolder viewHolder, final int position) {
        this.categories = (Categories) this.listCategory.get(position);
        this.mSqlite = new DishSqlite(this.mContext);
        this.lstDisher = this.mSqlite.getListDisher(String.valueOf(position + 1));
        if (this.categories != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH);

            new GlideImageLoader(mContext,viewHolder.ivCategory,
                    viewHolder.imvProgress).load(categories.getNameUrlImageReview(),options);
            viewHolder.tvCategory.setText(this.categories.getNameDisher());
            TextView textView = viewHolder.tvSize;
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(this.lstDisher.size());
            sb.append(")");
            textView.setText(sb.toString());
        }
        viewHolder.rlCategory.setOnClickListener(new OnOneOffClickListener() {
            public void onSingleClick(View v) {
                CategoryAdapter.this.listener.onClick(position, ((Categories) CategoryAdapter.this.listCategory.get(position)).getNameDisher());
            }
        });
    }

    public int getItemCount() {
        return this.listCategory.size();
    }

    public void setListener(OnItemClickListener listener2) {
        this.listener = listener2;
    }
}
