package com.clicagency.lastfmapp.view.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.clicagency.lastfmapp.data.local.entity.Album;

public abstract class BaseAdapter<T>
        extends RecyclerView.Adapter<BaseAdapter<T>.MyViewHolder>
//        extends ListAdapter<T, BaseAdapter<T>.MyViewHolder>
        {

//    BaseAdapter(OnItemClickListener<T> itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public BaseAdapter() {
//        super(new DiffUtil.ItemCallback<T>() {
//            @Override
//            public boolean areItemsTheSame(T oldItem, T newItem) {
////                    return oldItem.getMbid() == newItem.getMbid();
//                return oldItem.equals(newItem);
//            }
//
//            @SuppressLint("DiffUtilEquals")
//            @Override
//            public boolean areContentsTheSame(T oldItem, T newItem) {
//                return oldItem.equals(newItem);
//            }
//        });
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }
//    private final OnItemClickListener<T> itemClickListener;

    @NonNull
    public BaseAdapter<T>.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        BaseAdapter<T>.MyViewHolder viewHolder = new BaseAdapter<T>.MyViewHolder(binding);
        viewHolder.bind(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseAdapter<T>.MyViewHolder holder,
                                 int position) {
        final T item = getItemForPosition(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.onItemClick(item);
//            }
//        });
        holder.bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private final DiffUtil.ItemCallback<T> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<T>() {
                @Override
                public boolean areItemsTheSame(T oldItem, T newItem) {
//                    return oldItem.getMbid() == newItem.getMbid();
                    return oldItem.equals(newItem);
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(T oldItem, T newItem) {
                    return oldItem.equals(newItem);
                }
            };

    protected abstract T getItemForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T item) {
            binding.setVariable(BR.obj, item);
//            binding.setVariable(BR.viewModel,);
            binding.executePendingBindings();
        }

        public void bind(BaseAdapter<T> adapter) {
            binding.setVariable(BR.adapter, adapter);
            binding.executePendingBindings();
        }
    }
}