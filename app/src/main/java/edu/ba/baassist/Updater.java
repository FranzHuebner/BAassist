package edu.ba.baassist;

import android.app.Activity;

import java.util.Date;

/**
 * Created by Franz on 13.10.2017.
 */

public class Updater {

    public static Object updateInterval;
    public static Object lastCheck;

    //global Getter
    public Object getUpdateInterval(){
        return updateInterval;
    }
    //global Setter
    public void setUpdateInterval(Object newUpdateInterval){
        updateInterval = newUpdateInterval;
    }

    //Getter
    public Object getLastCheck(){
        return lastCheck;
    }

    //set the lastCheck jn
    public void setLastCheck(){

        //get actual campus dual time
        long actTime = System.currentTimeMillis()/1000;
        lastCheck = actTime;

        //refreshlogic
    }



}