package com.clicagency.lastfmapp.tools;

import android.os.Bundle;

import com.clicagency.lastfmapp.view.listeners.IReceivable;


public class DataMessage {

    private Bundle bundle;
    private IReceivable receiver;

    public DataMessage(IReceivable receiver){
        bundle = new Bundle();
        this.receiver = receiver;
    }

    public Bundle get_extra(){
        return  bundle;
    }

    public IReceivable get_receiver() {
        return receiver;
    }

}
