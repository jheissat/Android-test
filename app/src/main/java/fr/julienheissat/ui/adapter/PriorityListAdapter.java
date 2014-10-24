package fr.julienheissat.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.julienheissat.modelController.Priority;
import fr.julienheissat.modelController.Project;
import fr.julienheissat.taskmanager.R;

/**
 * Created by juju on 24/09/2014.
 */
public class PriorityListAdapter extends ArrayAdapter<Priority>
{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<Priority> priorityList;



    public PriorityListAdapter(Context context, int textViewResourceId,    ArrayList<Priority> priorityList) {
        super(context, textViewResourceId, priorityList);
        this.context=context;
        this.priorityList=priorityList;
    }


    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
             mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_priority_list, null);
        }


        TextView label=(TextView)convertView.findViewById(R.id.title_priority_list);
        label.setText(priorityList.get(position).getPriorityName());

        TextView sub=(TextView)convertView.findViewById(R.id.description_priority_list);
        sub.setText(priorityList.get(position).getPriorityDescription());

        ImageView icon=(ImageView)convertView.findViewById(R.id.image_priority_list);


        icon.setImageResource(context.getResources().getIdentifier
                (priorityList.get(position).getPriorityRessource(),
                  "drawable", context.getPackageName()));

        return convertView;
    }
}