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
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.clicagency.lastfmapp.BR;
import com.clicagency.lastfmapp.view.fragments.albumsArtistFragment.AlbumsArtistViewModel;
import com.clicagency.lastfmapp.viewmodel.ViewModelFactory;
import com.clicagency.lastfmapp.viewmodel.ViewModelNewInstanceFactory;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import dagger.hilt.android.AndroidEntryPoint;

public abstract class BaseFragment<V extends ViewModel, D extends ViewDataBinding> extends Fragment
//        extends DaggerFragment
{

//    @Inject
//    protected ViewModelProvider.Factory viewModelFactory;

    protected V viewModel;

    protected D dataBinding;

    protected BaseActivity parent;
    protected Snackbar snackbar;

//  protected NavController navController;


    protected abstract Class<V> getViewModel();

    protected abstract ViewModelProvider.Factory getViewModelFactory();

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

//        if(getViewModelFactory()==null)
//        viewModel = new ViewModelProvider(this,viewModelFactory).get(getViewModel());
//        else
//        viewModel = new ViewModelProvider(this, getViewModelFactory()).get(getViewModel());

        viewModel = new ViewModelProvider(this).get(getViewModel());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        dataBinding.setVariable(BR.viewModel,viewModel);
        dataBinding.setLifecycleOwner(this);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        navController = Navigation.findNavController(view);
        init(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void init(Bundle savedInstanceState){
        initEvents();
        initFragment(savedInstanceState);
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
