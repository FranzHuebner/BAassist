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

import edu.ba.baassist.ConnAdapter;
import edu.ba.baassist.R;
import edu.ba.baassist.CacheAdapter;


/**
 * Fragment to change personal data and settings.
 */

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        buttonOnClick(rootView);

        return rootView;
    }

    //Define OnClickListener to see which button is pressed by the user.
    public void buttonOnClick(View v) {

        switch (v.getId()) {
            case R.id.clear_cache_button:
                deleteCache();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                break;

            case R.id.group_button:
                //Will be implemented soon.
                break;

            case R.id.refresh_status_button:

                new RefreshTask();
                try {
                    new CacheAdapter().checkDiff(ConnAdapter.getUserCal(),"userCal");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new CacheAdapter().checkDiff(ConnAdapter.getUserCredits(),"userCredits");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new CacheAdapter().checkDiff(ConnAdapter.getUserFs(),"userFs");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    new CacheAdapter().checkDiff(ConnAdapter.getUserExams(),"userExams");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //Function to wipe the whole cache.
    private static boolean deleteCache() {
        boolean exist1 = new CacheAdapter().getFileExistence("userGlobal");
        boolean exist2 = new CacheAdapter().getFileExistence("HashGlobal");
        boolean exist3 = new CacheAdapter().getFileExistence("userExams");
        boolean exist4 = new CacheAdapter().getFileExistence("userCredits");
        boolean exist5 = new CacheAdapter().getFileExistence("userFs");
        boolean exist6 = new CacheAdapter().getFileExistence("userCal");

        if (exist1 && exist2 && exist3 && exist4 && exist5 && exist6) {
            new CacheAdapter().deleteEntry("userGlobal");
            new CacheAdapter().deleteEntry("HashGlobal");
            new CacheAdapter().deleteEntry("userExams");
            new CacheAdapter().deleteEntry("userCredits");
            new CacheAdapter().deleteEntry("userFs");
            new CacheAdapter().deleteEntry("userCal");
            return true;
        } else {
            return false;
        }
    }

    //Async task to refresh the values.
    private class RefreshTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            boolean reachable = false;
            try {
                reachable = ConnAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (reachable) {
                ConnAdapter.refreshValues();
            }
            return null;
        }
    }
}
