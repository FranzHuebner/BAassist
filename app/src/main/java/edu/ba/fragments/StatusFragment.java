package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ba.baassist.R;
import edu.ba.baassist.connAdapter;

/**
 * Show details of the account in the StatusFragment.
 */

public class StatusFragment extends Fragment {

    //Initialise variables.


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Set the view.
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        //Get global variables from connAdapter.
        String actExams= connAdapter.UserExams.toString();
        String actSemester = connAdapter.UserFs.toString();
        String actCredits = connAdapter.UserCredits.toString();
        String[] exams = actExams.split(",");


        TextView semester = (TextView) rootView.findViewById(R.id.textViewActSemester);
        semester.setText("\nAktuelles Semester: "+actSemester
                            +"\n\nCredits: "+actCredits+" von 180"
                            +"\n\nPrüfungen:"
                            +"\nGesamt"+""+exams[0].replaceAll("\"","").replaceAll("\\{EXAMS","")
                            +"\nErfolgreich"+""+exams[1].replaceAll("\"","").replaceAll("SUCCESS","")
                            +"\nnicht Erfolgreich"+exams[2].replaceAll("\"","").replaceAll("FAILURE",""));

        return rootView;
    }


 }


