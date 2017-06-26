package edu.ba.baassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richard on 21.06.2017.
 */

public class TimetableAdapter extends BaseAdapter {
    ArrayList<Object> timetable;
    private static final int TIMETABLE_ITEM = 0;
    private static final int HEADER = 1;
    private LayoutInflater inflater;

    public TimetableAdapter(Context context, ArrayList<Object> timetable){
        this.timetable = timetable;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

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

    @Override
    public int getCount() {

        return timetable.size();
    }

    @Override
    public Object getItem(int i) {

        return timetable.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

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
                TextView subject = (TextView) view.findViewById(R.id.itemListViewTxtSubject);
                TextView teacher = (TextView) view.findViewById(R.id.itemListViewTxtTeacher);
                TextView time = (TextView) view.findViewById(R.id.itemListViewTxtTime);

                subject.setText(((TimetableItem)timetable.get(i)).getSubject());
                teacher.setText(((TimetableItem)timetable.get(i)).getTeacher());
                time.setText(((TimetableItem)timetable.get(i)).getTime());
                break;
            case HEADER:
                TextView title = (TextView) view.findViewById(R.id.itemListViewHeader);
                title.setText(((String)timetable.get(i)));
                break;

        }
        return view;
    }
}
