package com.example.superpositionexample;

public class TimerPermissionV2 implements Runnable {
    private boolean isTimeFinish=false;
    private int timeLimit=5;
    private int count=0;

    public int getCount() {
        return count;
    }

    private boolean running=true;
    Thread t;

    public TimerPermissionV2()
    {

    }
    public boolean isTimeFinish() {
        return isTimeFinish;
    }

    public void setTimeFinish(boolean timeFinish) {
        isTimeFinish = timeFinish;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void start()
    {
        t = new Thread(this);
        t.start();
    }
    public void restart()
    {
        this.isTimeFinish=false;
        this.count=0;
    }
    @Override
    public void run() {
        while(running==true)
        {
            try {
                t.sleep(1000);
                if(this.count<this.timeLimit)
                {
                    this.count++;
                    if(this.count==this.timeLimit)
                    {
                        this.isTimeFinish=true;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
