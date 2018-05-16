package edu.ba.baassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter to format the ListView(timetable).
 */

public class TimetableAdapter extends BaseAdapter {
    private final ArrayList<Object> timetable;
    private static final int TIMETABLE_ITEM = 0;
    private static final int HEADER = 1;
    private final LayoutInflater inflater;

    public TimetableAdapter(Context context, ArrayList<Object> timetable){
        this.timetable = timetable;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //Decides which type the list element is.
    @Override
    public int getItemViewType(int position) {
        if(timetable.get(position) instanceof TimetableItem){
            return TIMETABLE_ITEM;
        }
        else{
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //Get the size of the timetable.
    @Override
    public int getCount() {
        return timetable.size();
    }

    //Get the actual item of the timetable.
    @Override
    public Object getItem(int i) {
        return timetable.get(i);
    }

    //Part of the timetable.
    @Override
    public long getItemId(int i) {
        return i;
    }

    //Get the specific layout of the element and create a view for each element.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            switch(getItemViewType(i)){
                case TIMETABLE_ITEM:
                    view = inflater.inflate(R.layout.item_list_view,null);
                    break;
                case HEADER:
                   view = inflater.inflate(R.layout.item_listview_header,null);
                    break;
            }
        }
        switch(getItemViewType(i)){
            case TIMETABLE_ITEM:
                assert view != null;
                TextView subject = (TextView) view.findViewById(R.id.itemListViewTxtSubject);
                TextView teacher = (TextView) view.findViewById(R.id.itemListViewTxtTeacher);
                TextView time_room = (TextView) view.findViewById(R.id.itemListViewTxtTime);

                subject.setText(((TimetableItem)timetable.get(i)).getSubject());
                teacher.setText(((TimetableItem)timetable.get(i)).getTeacher());
                time_room.setText(((TimetableItem)timetable.get(i)).getTime_room());
                break;
            case HEADER:
                assert view != null;
                TextView title = (TextView) view.findViewById(R.id.itemListViewHeader);
                title.setText(((String)timetable.get(i)));
                break;
        }
        return view;
    }
}
