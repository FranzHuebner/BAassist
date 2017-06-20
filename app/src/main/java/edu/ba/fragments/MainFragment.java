package edu.ba.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
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
import java.util.concurrent.ExecutionException;

import edu.ba.baassist.MainActivity;
import edu.ba.baassist.R;
import edu.ba.baassist.connAdapter;

/**
 * Created by richa on 14.06.2017.
 */

public class MainFragment extends Fragment{

    long ActTime = System.currentTimeMillis()/1000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        String output=connAdapter.getUserCalc();


        String[] timeTableData = output.split("title");         //Array der alle Daten enthält
        String[] timeTableDisplay = new String[timeTableData.length];       //Array der für die Ausgabe an akt. Datum verwendet wird

        int j = 0;      //Zählvariable für Display-Array
        for(int i=1; i<timeTableData.length; i++){

            String[] temp=timeTableData[i].split(",");      //Daten-String in verschiedene Atrrays zur Weiterverarbeitung aufteilen

            String subject = temp[0];
            String teacher = temp[9];
            String beginn = temp[1];
            String end=temp[2];
            String room=temp[7];


            timeTableData[i]=subject+"\n"+teacher+"\n"+room+"\n"+beginn+"-"+end;
            timeTableData[i]=timeTableData[i].replaceAll("[\"\\[\\{\\}\\]]","");        //Sonderzeichen entfernen
            String beginnOfLesson = beginn.substring(beginn.indexOf(":")+1);
            int beginnOfLessonInt = Integer.parseInt(beginnOfLesson.toString());        //Integer der Startzeit
            if(beginnOfLessonInt>=ActTime){
                timeTableDisplay[j]=timeTableData[i];
                j++;
            }
        }
        timeTableData[0]="Stundenplan";
        List<String> timeTableList = new ArrayList<>(Arrays.asList(timeTableDisplay));


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
