package com.clicagency.lastfmapp.view.base;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.google.android.material.snackbar.Snackbar;


public abstract class BaseFragment<V extends ViewModel, D extends ViewDataBinding> extends Fragment {


    protected V viewModel;

    protected D dataBinding;

    protected BaseActivity parent;
    protected Snackbar snackbar;


    protected abstract Class<V> getViewModel();

    protected abstract ViewModelProvider.Factory getViewModelFactory();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(getViewModel());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    public void init(Bundle savedInstanceState){
        init_fragment(savedInstanceState);
        init_events();
    }

    public abstract void init_events();

    public abstract void init_fragment(Bundle savedInstanceState);

    public abstract boolean on_back_pressed();

    public void cancel() {
        removeSnackbar();
    };

    public void removeSnackbar() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }

}
