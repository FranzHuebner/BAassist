package edu.ba.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ba.baassist.R;
import edu.ba.baassist.cacheAdapter;
import edu.ba.baassist.connAdapter;


/**
 * Fragment to change personal data and settings.
 */

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButtonOnClick(rootView);

        return rootView;
    }

    //Define onclicklistener to see which button is pressed by the user.
    //Need dialogues to show success // fails.
    public void ButtonOnClick(View v) {

        switch (v.getId()) {
            case R.id.clear_cache_button:
                deleteCache();
                break;

            case R.id.logout_button:
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                break;

            case R.id.group_button:
                //Will be implemented soon.
                break;

            case R.id.refresh_status_button:

                new refreshtask();
                try {
                    new cacheAdapter().checkdiff(connAdapter.getUserCal(),"userCal");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new cacheAdapter().checkdiff(connAdapter.getUserCredits(),"userCredits");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new cacheAdapter().checkdiff(connAdapter.getUserFs(),"userFs");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new cacheAdapter().checkdiff(connAdapter.getUserExams(),"userExams");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    //Function to wipe the whole cache.
    private static boolean deleteCache() {
        boolean exist1 = new cacheAdapter().getFileExistence("userGlobal");
        boolean exist2 = new cacheAdapter().getFileExistence("HashGlobal");
        boolean exist3 = new cacheAdapter().getFileExistence("userExams");
        boolean exist4 = new cacheAdapter().getFileExistence("userCredits");
        boolean exist5 = new cacheAdapter().getFileExistence("userFs");
        boolean exist6 = new cacheAdapter().getFileExistence("userCal");

        if (exist1 && exist2 && exist3 && exist4 && exist5 && exist6) {
            new cacheAdapter().deleteEntry("userGlobal");
            new cacheAdapter().deleteEntry("HashGlobal");
            new cacheAdapter().deleteEntry("userExams");
            new cacheAdapter().deleteEntry("userCredits");
            new cacheAdapter().deleteEntry("userFs");
            new cacheAdapter().deleteEntry("userCal");
            return true;
        } else {
            return false;
        }
    }

    //Async task to refresh the values.
    private class refreshtask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            boolean reachable = false;
            try {
                reachable = connAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#");
            } catch (IOException e) {

                e.printStackTrace();
            }

            if (reachable) {
                connAdapter.refreshValues();
            }
            return null;
        }
    }


}
