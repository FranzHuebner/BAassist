package edu.ba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.ba.baassist.R;
import edu.ba.baassist.TimetableAdapter;
import edu.ba.baassist.TimetableItem;
import edu.ba.baassist.connAdapter;

/**
 * Standard fragment which is the base of the Fragment-Manager.
 * This fragment contains the timetable.
 */

public class MainFragment extends Fragment{

    long ActTime = System.currentTimeMillis()/1000;
    Date ActDate = connAdapter.convertUnixtoNormalDate(1475307900);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        String output=connAdapter.getUserCal();                     // Get Data as String.

        String[] timeTableData = output.split("title");             //Array which splits the outpu string in the different Elements.
        timeTableData = clean(timeTableData);                          //Removing null Elements from Array.


        ArrayList<Object> list = new ArrayList<>();                 //List which will be displayed.

        for(int i=1; i<timeTableData.length; i++){

            String[] temp=timeTableData[i].split(",");      //Split Data to work with the single Elements.

            //Values for the single Element.
            String subject = temp[0].substring(2).replaceAll("\"","");                        //TODO put this into one regex
            String teacher = temp[9].replaceAll(":","").replaceAll("\"","");
            String beginn = temp[1];
            String end=temp[2];
            String room=temp[7].substring(temp[7].indexOf(":")+1).replaceAll("\"","");


            int beginnOfLessonInt = convertTimeStringToInteger(beginn);        //Integer of start Time.
            if(beginnOfLessonInt>=ActTime-5500){                                //Start Time of the displayed List.

                Date timetableDate = connAdapter.convertUnixtoNormalDate(beginnOfLessonInt);

                if(connAdapter.setTimeToMidnight(timetableDate).after(connAdapter.setTimeToMidnight(ActDate))){
                    ActDate = timetableDate;
                    String headerDate = connAdapter.convertUnixtoNormalDateString(beginnOfLessonInt);
                    list.add(headerDate);                                           //Add a new header when a new day starts.
                }

                list.add(new TimetableItem(subject, teacher.replace("instructor", ""), connAdapter.convertUnixtoNormalTimeString(convertTimeStringToInteger(beginn)) + "\n" + room));   //Add new Element
            }
        }


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_timetable);

        listView.setAdapter(new TimetableAdapter(rootView.getContext(), list));                     //Custom adapter for the list.

        return rootView;
    }

    //Method to convert Time String to Int.
    public int convertTimeStringToInteger(String input){
        String begOfLesson = input.substring(input.indexOf(":")+1);
        if(begOfLesson != null){

            int begOfLessonInt = Integer.parseInt(begOfLesson);        //Integer of start Time.
            return begOfLessonInt;
        }else {
            return 2;
        }
    }

    //Function to remove null elements in the array
    public static String[] clean(final String[] v) {
        List<String> list = new ArrayList<String>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

}
