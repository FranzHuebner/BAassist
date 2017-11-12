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
                String myFilter;
                try{
                     myFilter = new CacheAdapter().getFilterfromMem();
                } catch (FileNotFoundException e){
                    myFilter= "";
                }

                if(new CacheAdapter().getFileExistence("userFilter")) {
                    userInput.setText(myFilter, TextView.BufferType.EDITABLE);
                }


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case DialogInterface.BUTTON_POSITIVE:
                                //Edit global vars
                                ConnAdapter.setFilterGlobal(userInput.getText().toString());
                                new CacheAdapter().saveFiltertoMem(userInput.getText().toString());

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
                String result="";
                //Check difference between cache and vars
                try {
                    result=new CacheAdapter().checkDiff(ConnAdapter.getUserCal(),"userCal");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    result=new CacheAdapter().checkDiff(ConnAdapter.getUserCredits(),"userCredits");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    result=new CacheAdapter().checkDiff(ConnAdapter.getUserFs(),"userFs");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    result = new CacheAdapter().checkDiff(ConnAdapter.getUserExams(),"userExams");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if(result.equals("Datei überschrieben.")){
                    Toast.makeText(v.getContext(),"Daten aktualisiert.", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(v.getContext(),"Daten sind auf dem aktuellen Stand.", Toast.LENGTH_LONG).show();
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
        boolean exist7 = new CacheAdapter().getFileExistence("userFilter");

        //TODO rework structure
        if (exist1 && exist2 && exist3 && exist4 && exist5 && exist6 || exist7) {
            new CacheAdapter().deleteEntry("userGlobal");
            new CacheAdapter().deleteEntry("HashGlobal");
            new CacheAdapter().deleteEntry("userExams");
            new CacheAdapter().deleteEntry("userCredits");
            new CacheAdapter().deleteEntry("userFs");
            new CacheAdapter().deleteEntry("userCal");
            new CacheAdapter().deleteEntry("userFilter");
            return true;
        } else {
            return false;
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
