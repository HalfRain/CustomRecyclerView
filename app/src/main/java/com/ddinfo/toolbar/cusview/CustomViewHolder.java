package com.ddinfo.toolbar.cusview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ddinfo.toolbar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weitf
 * Email:weitengfei0212@gmail.com
 * On 2017/3/27.
 * 描述：
 */

public class CustomViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_name)
    public TextView tvName;

    public CustomViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
