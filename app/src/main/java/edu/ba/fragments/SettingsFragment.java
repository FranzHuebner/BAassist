package edu.ba.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ba.baassist.CacheAdapter;
import edu.ba.baassist.ConnAdapter;
import edu.ba.baassist.R;

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
    public void buttonOnClick( View v) {

        switch (v.getId()) {

            //Clear all ++ restart app
            case R.id.clear_cache_button:

                DialogInterface.OnClickListener dialogClickListenercache = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                deleteCache();
                                //ActivityCompat.finishAffinity((Activity) v.getContext());
                                System.exit(0);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder alertBuildercache = new AlertDialog.Builder(v.getContext());
                alertBuildercache.setMessage("Möchtest du alle Daten löschen?").setTitle("Warnung").setPositiveButton("JA", dialogClickListenercache)
                        .setNegativeButton("Nein", dialogClickListenercache).show();

                break;

            case R.id.group_button:

                LayoutInflater layoutInflat = LayoutInflater.from(v.getContext());
                View DiaView = layoutInflat.inflate(R.layout.alert_filter_group, null);
                 final EditText userInput = (EditText) DiaView
                        .findViewById(R.id.editText);
                String myFilter = CacheAdapter.getFilterfromMem();

                if(CacheAdapter.getFileExistence("userFilter")) {
                    userInput.setText(myFilter, TextView.BufferType.EDITABLE);
                }


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case DialogInterface.BUTTON_POSITIVE:
                                //Edit global vars
                                ConnAdapter.setFilterGlobal(userInput.getText().toString());

                                try
                                {
                                    CacheAdapter.saveFiltertoMem(userInput.getText().toString());
                                }

                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                alertBuilder.setTitle("Filtereinstellungen").setPositiveButton("Hinzufügen", dialogClickListener)
                    .setNegativeButton("Abbrechen", dialogClickListener).setView(DiaView).show();


                break;

            case R.id.refresh_status_button:

                //Download new data
                new RefreshTask().execute();
                String result;
                //Check difference between cache and vars
                try {
                    result = CacheAdapter.checkDiff(ConnAdapter.getUserCal(), "userCal");
                    result = CacheAdapter.checkDiff(ConnAdapter.getUserCredits(), "userCredits");
                    result = CacheAdapter.checkDiff(ConnAdapter.getUserFs(), "userFs");
                    result = CacheAdapter.checkDiff(ConnAdapter.getUserExams(), "userExams");

                    if (result.equals("Datei überschrieben.")) {
                        Toast.makeText(v.getContext(), "Daten aktualisiert.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), "Daten sind auf dem aktuellen Stand.", Toast.LENGTH_LONG).show();
                    }
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                }

                break;
        }
    }

    //Function to wipe the whole cache.
    private static void deleteCache() {

        boolean exist1 = CacheAdapter.getFileExistence("userGlobal");
        boolean exist2 = CacheAdapter.getFileExistence("HashGlobal");
        boolean exist3 = CacheAdapter.getFileExistence("userExams");
        boolean exist4 = CacheAdapter.getFileExistence("userCredits");
        boolean exist5 = CacheAdapter.getFileExistence("userFs");
        boolean exist6 = CacheAdapter.getFileExistence("userCal");
        boolean exist7 = CacheAdapter.getFileExistence("userFilter");

        //TODO rework structure
        if (exist1 && exist2 && exist3 && exist4 && exist5 && exist6 || exist7) {
            CacheAdapter.deleteEntry("userGlobal");
            CacheAdapter.deleteEntry("HashGlobal");
            CacheAdapter.deleteEntry("userExams");
            CacheAdapter.deleteEntry("userCredits");
            CacheAdapter.deleteEntry("userFs");
            CacheAdapter.deleteEntry("userCal");
            CacheAdapter.deleteEntry("userFilter");
        }
    }

    //Async task to refresh the values.
    private class RefreshTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (ConnAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#"))
                {
                    ConnAdapter.refreshValues();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
