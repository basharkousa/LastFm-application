package com.clicagency.lastfmapp.data.remote.models;

public class State<T> {

    Status status;

    T data;

    String message;


    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public State() {
    }

    public State(Status status,T data){
        this.data = data;
        this.status = status;

    }
    public State(Status status,String msg){
        this.message = msg;
        this.status = status;

    }

    public enum Status{
        LOADING,
        SUCCESS,
        FAILED
    }

}

