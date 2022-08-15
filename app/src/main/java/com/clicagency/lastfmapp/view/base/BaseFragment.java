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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public abstract class BaseFragment<V extends ViewModel, D extends ViewDataBinding> extends DaggerFragment {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected V viewModel;

    protected D dataBinding;

    protected BaseActivity parent;
    protected Snackbar snackbar;

//  protected NavController navController;


    protected abstract Class<V> getViewModel();

    // use it when your project doesn't use Dagger2
    //protected abstract ViewModelProvider.Factory getViewModelFactory();

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }


    public void init(Bundle savedInstanceState){
        initFragment(savedInstanceState);
        initEvents();
    }

    public abstract void initEvents();

    public abstract void initFragment(Bundle savedInstanceState);

    public abstract boolean onBackPressed();

    public void cancel() {
        removeSnackbar();
    };

    public void removeSnackbar() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }

}
