package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ba.baassist.LoginActivity;
import edu.ba.baassist.R;
import edu.ba.baassist.connAdapter;

/**
 * Created by richa on 16.06.2017.
 */

public class StatusFragment extends Fragment {

    String user= "3002715";
    String hash= "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        String actSemester = connAdapter.getsemester(user, hash);
        TextView semester = (TextView) rootView.findViewById(R.id.textViewActSemester);
        semester.setText("Aktuelles Semester:" + actSemester);

        return rootView;
    }

}
