package edu.ba.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import edu.ba.baassist.R;
import edu.ba.baassist.connAdapter;

import static android.R.attr.end;
import static android.R.attr.path;

/**
 * Created by richa on 16.06.2017.
 */

public class StatusFragment extends Fragment {

    String user= (String) connAdapter.UserGlobal;
    String hash= (String) connAdapter.HashGlobal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_status, container, false);



        String actSemester = connAdapter.getUserFs() ;          //Strings für die Ausgabe vorbereiten
        String actCredits = connAdapter.getUserCredits();

        String[] exams = connAdapter.getUserExams().split(",");

        TextView semester = (TextView) rootView.findViewById(R.id.textViewActSemester);
        semester.setText("\nAktuelles Semester: "+actSemester
                            +"\n\nCredits: "+actCredits+" von 180"
                            +"\n\nPrüfungen:"
                            +"\nGesamt"+exams[0].replaceAll("\"","").replaceAll("\\{EXAMS","")
                            +"\nErfolgreich"+exams[1].replaceAll("\"","").replaceAll("SUCCESS","")
                            +"\nnicht Erfolgreich"+exams[2].replaceAll("\"","").replaceAll("FAILURE",""));

        return rootView;
    }


 }


