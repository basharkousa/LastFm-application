package com.clicagency.lastfmapp.view.listeners;

public interface IResponseListener<T> {

    void onSuccess(T data);
    void onFailure(String message,Throwable t);
//    void onFailure(String message);

}
