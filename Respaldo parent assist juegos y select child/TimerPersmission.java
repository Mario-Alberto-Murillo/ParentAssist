package com.example.superpositionexample;

import android.os.CountDownTimer;

public class TimerPersmission extends CountDownTimer {

    boolean countFinish=false;


    public TimerPersmission(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        countFinish=false;
    }

    public boolean getCount()
    {
        return countFinish;
    }

    @Override
    public void onFinish() {
        countFinish=true;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }



}

