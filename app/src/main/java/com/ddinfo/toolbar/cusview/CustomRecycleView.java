package com.ddinfo.toolbar.cusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ddinfo.toolbar.R;

import java.util.ArrayList;

/**
 * Created by weitf
 * Email:weitengfei0212@gmail.com
 * On 2017/3/27.
 * 描述：自定义RecyclerView
 */
public class CustomRecycleView extends LinearLayout {

    private Context context;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridManager;
    private RecyclerView.ItemAnimator animator;
    private CustomRecycleAdapter adapter;
    private ArrayList<String> recyclerDatas = new ArrayList<>();
    private RecyclerView recView;
    private int resLayoutId;
    private int layoutId, layoutManagerType, spanCount, backgroundColor = 0;

    public CustomRecycleView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public CustomRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public CustomRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomRecycleView, defStyle, 0);
        layoutId = a.getResourceId(R.styleable.CustomRecycleView_recAdapterLayout, 0);
        layoutManagerType = a.getInt(R.styleable.CustomRecycleView_recLayoutManagerType, 0);
        spanCount = a.getInt(R.styleable.CustomRecycleView_recSpanCount, 1);
        backgroundColor = a.getColor(R.styleable.CustomRecycleView_recBackgroundColor, 0);
        a.recycle();
        LayoutInflater.from(context).inflate(R.layout.custom_recycle_view, this);
        recView = (RecyclerView) this.findViewById(R.id.recycler_view);
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(context, recyclerDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        if (layoutManagerType == 0) {
            this.layoutManager = new LinearLayoutManager(context);
            recView.setLayoutManager(this.layoutManager);
        } else if (layoutManagerType == 1) {
            gridManager = new GridLayoutManager(context, spanCount);
            recView.setLayoutManager(gridManager);
        }
        adapter = new CustomRecycleAdapter(context);
        animator = new DefaultItemAnimator();
//        recView.addItemDecoration(new CustomDecoration(context, layoutManagerType == 0 ? CustomDecoration.VERTICAL_LIST : CustomDecoration.HORIZONTAL_LIST));
        recView.addItemDecoration(new CustomDecoration(context, CustomDecoration.HORIZONTAL_LIST));
        recView.setBackgroundColor(backgroundColor == 0 ? Color.WHITE : backgroundColor);
        recView.setHasFixedSize(true);
        recView.setItemAnimator(animator);
        setAdapter(layoutId);
    }

    /**
     * 设置adapter item layout
     *
     * @param resId
     */
    private void setAdapter(int resId) {
        this.resLayoutId = resId;
        recView.setAdapter(adapter);
    }

    /**
     * 刷新adapter 数据源
     *
     * @param datas
     */
    public void refreshAllItems(ArrayList<String> datas) {
        if (adapter != null) {
            this.recyclerDatas = datas;
            adapter.refreshItemNotify(datas);
        }
    }

    /**
     * 刷新单行item 数据源
     *
     * @param data
     */
    public void refreshItemChanged(int position, String data) {
        if (adapter != null) {
            adapter.notifyItemChanged(position, data);
        }
    }

    class CustomRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private LayoutInflater inflater;
        private OnItemClickListener mOnItemClickListener = null;
        private ArrayList<String> datas;

        public CustomRecycleAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        public void refreshItemNotify(ArrayList<String> datas) {
            this.datas = datas;
            notifyDataSetChanged();

        }

        private void notifyItem(int position, Object o) {
            notifyItemChanged(position, o);
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CustomViewHolder holder;
            final View view = inflater.inflate(resLayoutId, parent, false);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(view, (int) view.getTag());
                    }
                }
            });
            holder = new CustomViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CustomViewHolder mHolder = (CustomViewHolder) holder;
            String name = datas.get(position);
            mHolder.tvName.setText(name);
            mHolder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}
