package com.clicagency.lastfmapp.data.remote.models;

public class NetWorkResponse<T> {

   private int statusCode ;
   private T data ;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
