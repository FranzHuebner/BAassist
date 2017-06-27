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

import edu.ba.baassist.ConnAdapter;
import edu.ba.baassist.R;
import edu.ba.baassist.TimetableAdapter;
import edu.ba.baassist.TimetableItem;

/**
 * Standard fragment which is the base of the Fragment-Manager.
 * This fragment contains the timetable.
 */

public class MainFragment extends Fragment{

    long actTime = System.currentTimeMillis()/1000;
    Date actDate = ConnAdapter.convertUnixtoNormalDate(1475307900);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        String output= ConnAdapter.getUserCal();                     // Get data as string.

        String[] timeTableData = output.split("title");             //Array which splits the output string in the different elements.
        timeTableData = clean(timeTableData);                          //Removing null elements from array.


        ArrayList<Object> list = new ArrayList<>();                 //List which will be displayed.

        for(int i=1; i<timeTableData.length; i++){

            String[] temp=timeTableData[i].split(",");      //Split data to work with the single elements.

            //Values for the single element.
            String subject = temp[0].substring(2).replaceAll("\"","");
            String teacher = temp[9].replaceAll(":","").replaceAll("\"","");
            String begin = temp[1];
            String room=temp[7].substring(temp[7].indexOf(":")+1).replaceAll("\"","");


            int beginOfLessonInt = convertTimeStringToInteger(begin);        //Integer of start time.
            if(beginOfLessonInt>= actTime -5500){                                //Start time of the displayed list.

                Date timetableDate = ConnAdapter.convertUnixtoNormalDate(beginOfLessonInt);

                if(ConnAdapter.setTimeToMidnight(timetableDate).after(ConnAdapter.setTimeToMidnight(actDate))){
                    actDate = timetableDate;
                    String headerDate = ConnAdapter.convertUnixtoNormalDateString(beginOfLessonInt);
                    list.add(headerDate);                                           //Add a new header when a new day starts.
                }

                list.add(new TimetableItem(subject, teacher.replace("instructor", ""), ConnAdapter.convertUnixtoNormalTimeString(convertTimeStringToInteger(begin)) + "\n" + room));   //Add new Element
            }
        }


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_timetable);

        listView.setAdapter(new TimetableAdapter(rootView.getContext(), list));                     //Custom adapter for the list.

        return rootView;
    }

    //Method to convert time string to int.
    public int convertTimeStringToInteger(String input){
        String begOfLesson = input.substring(input.indexOf(":")+1);

        return Integer.parseInt(begOfLesson);
    }

    //Function to remove null elements in the array
    public static String[] clean(final String[] v) {
        List<String> list = new ArrayList<>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

}
