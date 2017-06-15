package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ba.baassist.R;

/**
 * Created by richa on 14.06.2017.
 */

public class MainFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        String[] timeTableData = {
                "INGG",
                "Linux",
                "Java",
                "Elektronik-Analog",
                "Englisch",
                "WIARB",
                "Elektronik-Analog",
                "RTG",
                "Mathe",
                "Elektronik Praktikum"

        };

        List<String> timeTableList = new ArrayList<>(Arrays.asList(timeTableData));

        ArrayAdapter <String> timetableListeAdapter =
                new ArrayAdapter<>(
                        getActivity(), //aktuelle Umgebung (diese Activity)
                        R.layout.list_item_timetable, // ID der XML-Layout Datei
                        R.id.list_item_timetable_textview, // ID des TextViews
                        timeTableList); // Beispieldaten aus der ArrayList

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView timetableListView = (ListView) rootView.findViewById(R.id.listview_timetable);
        timetableListView.setAdapter(timetableListeAdapter);

        return rootView;
    }


}
