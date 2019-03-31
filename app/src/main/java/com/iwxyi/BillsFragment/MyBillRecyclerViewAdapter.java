package com.iwxyi.BillsFragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwxyi.BillsFragment.BillsFragment.OnListFragmentInteractionListener;
import com.iwxyi.BillsFragment.DummyContent.DummyItem;
import com.iwxyi.R;
import com.iwxyi.Utils.DateTimeUtil;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBillRecyclerViewAdapter extends RecyclerView.Adapter<MyBillRecyclerViewAdapter.ViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * 设置数值和监听器
     * @param items
     * @param listener
     */
    public MyBillRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * 创建Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bill, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定 holder，设置数据和显示的内容
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if ("".equals(holder.mItem.source) || holder.mItem.source.equals(holder.mItem.kind)) {
            holder.mTvSource.setText(holder.mItem.kind);
        } else {
            if ("".equals(holder.mItem.kind)) {
                holder.mTvSource.setText(holder.mItem.source);
            } else {
                holder.mTvSource.setText(holder.mItem.kind + " · " + holder.mItem.source);
            }
        }
        if (holder.mItem.mode == 0) {
            holder.mTvAmount.setTextColor(Color.rgb(255, 99, 71));
            if (holder.mItem.amount >= 0)
                holder.mTvAmount.setText("-" + holder.mItem.amount);
            else
                holder.mTvAmount.setText("" + holder.mItem.amount);
        } else if (holder.mItem.mode == 1) {
            holder.mTvAmount.setTextColor(Color.rgb(34, 139, 34));
            holder.mTvAmount.setText("+" + holder.mItem.amount);
        } else {
            holder.mTvAmount.setTextColor(Color.rgb(0, 51, 153));
            holder.mTvAmount.setText(" " + holder.mItem.amount);
        }

        if ("".equals(holder.mItem.note)) {
            holder.mTvNote.setVisibility(View.GONE);
        } else {
            holder.mTvNote.setVisibility(View.VISIBLE);
            holder.mTvNote.setText(holder.mItem.note);
        }
        if (holder.mItem.timestamp != 0) { // 如果为空，就不显示了
            try {
                holder.mTvTime.setText(DateTimeUtil.longToString(holder.mItem.timestamp, "MM-dd HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else { // 如果时间戳是空的，那么就改成添加的时间
            try {
                holder.mTvTime.setText(DateTimeUtil.longToString(holder.mItem.addTime, "MM-dd HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * 列表的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * 删除某个元素
     * @param position
     */
    @Override
    public void onItemDelete(int position) {
        DummyContent.removeItem(position);
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 交换某个元素
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(mValues, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        DummyContent.swapItem(fromPosition, toPosition);
    }

    /**
     * ViewHolder 类，保存缓冲的内容
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTvSource;
        public final TextView mTvAmount;
        public final TextView mTvNote;
        public final TextView mTvTime;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTvSource = (TextView) view.findViewById(R.id.tv_source);
            mTvAmount = (TextView) view.findViewById(R.id.tv_amount);
            mTvNote = (TextView) view.findViewById(R.id.tv_note);
            mTvTime = (TextView) view.findViewById(R.id.tv_time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTvSource.getText() + "'";
        }
    }
}
