package edu.ba.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import edu.ba.baassist.R;
import edu.ba.baassist.cacheAdapter;
import edu.ba.baassist.connAdapter;


/**
 * Created by richa on 16.06.2017.
 */

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        return rootView;

    }

    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.refresh_status_button || id == EditorInfo.IME_NULL) {
            boolean  reachable=false;
            try {
              reachable = connAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(reachable){
            }
            return true;
        }
        return false;
    }

    public boolean onEditorAction2(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.clear_cache_button || id == EditorInfo.IME_NULL) {
            return deleteCache();
        }
        return false;
    }

    public boolean onEditorAction3(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.logout_button || id == EditorInfo.IME_NULL) {
            getActivity().finish();
            return true;
        }
        return false;
    }

    //Function to wipe the whole cache.
    private boolean deleteCache() {
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

}
