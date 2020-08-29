package com.v3.cookbook.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.v3.cookbook.R;
import com.v3.cookbook.dbmanagement.DishSqlite;
import com.v3.cookbook.moduls.Disher;
import com.v3.cookbook.moduls.OnOneOffClickListener;
import com.v3.cookbook.view.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DisherAdapter extends RecyclerView.Adapter<DisherAdapter.RecyclerViewHolder> implements Filterable {
    private static final String tab00c0 = "AAAAAAACEEEEIIIIDNOOOOO×ØUUUUYIßaaaaaaaceeeeiiiiðnooooo÷øuuuuyþyAaAaAaCcCcCcCcDdDdEeEeEeEeEeGgGgGgGgHhHhIiIiIiIiIiJjJjKkkLlLlLlLlLlNnNnNnnNnOoOoOoOoRrRrRrSsSsSsSsTtTtTtUuUuUuUuUuUuWwYyYZzZzZzF";
    private Disher disher;
    public List<Disher> filterData;
    public List<Disher> lstDisher;
    private Context mContext;
    private CustomRecyclerFilter mFilter = new CustomRecyclerFilter();
    public DishSqlite mSqlite;
    public OnClickDetailDisher onClickDetailDisher;
    public class CustomRecyclerFilter extends Filter {
        public CustomRecyclerFilter() {
        }
        public FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            List<Disher> list = filterData;
            int count = list.size();
            ArrayList<Disher> nlist = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Disher mDisher = (Disher) list.get(i);
                if (Pattern.matches(".*[éèàù].*", filterString)) {
                    if (mDisher.getNameDisher().toLowerCase().contains(filterString)) {
                        nlist.add(mDisher);
                    }
                } else if (DisherAdapter.removeDiacritic(mDisher.getNameDisher()).toLowerCase().contains(DisherAdapter.removeDiacritic(filterString))) {
                    nlist.add(mDisher);
                }
            }
            results.count = nlist.size();
            results.values = nlist;
            return results;
        }

        public void publishResults(CharSequence recyclerChar, FilterResults results) {
            DisherAdapter.this.lstDisher = (ArrayList) results.values;
            DisherAdapter.this.notifyDataSetChanged();
        }
    }

    public List<Disher> updateList(){
            return lstDisher;
    }

    public interface OnClickDetailDisher {
        void onClickDetail(int i);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDisher)
        RoundedImageView imgDisher;
        @BindView(R.id.rlClick)
        RelativeLayout rlClickItem;
        @BindView(R.id.txtNameDish)
        TextView txtNameDisher;
        @BindView(R.id.imvProgress)
        ImageView imvProgress;
        public RecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, this.itemView);
        }
    }


    public DisherAdapter(Context mContext2, List<Disher> lstDisher2, OnClickDetailDisher onClickDetailDisher2) {
        this.lstDisher = lstDisher2;
        this.filterData = lstDisher2;
        this.mContext = mContext2;
        this.onClickDetailDisher = onClickDetailDisher2;
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_disher, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerViewHolder viewHolder, final int position) {
        this.disher = (Disher) this.lstDisher.get(position);
        this.mSqlite = new DishSqlite(this.mContext);
        viewHolder.txtNameDisher.setText(this.disher.getNameDisher());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);

        new GlideImageLoader(mContext,viewHolder.imgDisher,
                viewHolder.imvProgress).load(disher.getUrlImageDisher(),options);

        viewHolder.rlClickItem.setOnClickListener(new OnOneOffClickListener() {
            public void onSingleClick(View v) {
                DisherAdapter.this.onClickDetailDisher.onClickDetail(position);
            }
        });
    }

    public int getItemCount() {
        return this.lstDisher.size();
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public List<Disher> getFilteredData() {
        return this.lstDisher;
    }

    public static String removeDiacritic(String source) {
        char[] vysl = new char[source.length()];
        for (int i = 0; i < source.length(); i++) {
            char one = source.charAt(i);
            if (one >= 192 && one <= 383) {
                one = tab00c0.charAt(one - 192);
            }
            vysl[i] = one;
        }
        return new String(vysl);
    }
}
