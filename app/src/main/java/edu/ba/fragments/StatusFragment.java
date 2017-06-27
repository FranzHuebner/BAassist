package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ba.baassist.ConnAdapter;
import edu.ba.baassist.R;

/**
 * Show details of the account in the StatusFragment.
 */

public class StatusFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Set the view.
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        //Get global variables from ConnAdapter.
        String actExams= ConnAdapter.userExams.toString();
        String actSemester = ConnAdapter.userFs.toString();
        String actCredits = ConnAdapter.userCredits.toString();
        String[] exams = actExams.split(",");

        //Display the actual information.
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


