package fr.julienheissat.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.julienheissat.modelController.Project;
import fr.julienheissat.taskmanager.R;

/**
 * Created by juju on 24/09/2014.
 */
public class ProjectListAdapter extends ArrayAdapter<Project>
{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<Project> projectList;

    public ProjectListAdapter(Context context, int textViewResourceId,  ArrayList<Project> projectList) {
        super(context, textViewResourceId,projectList);
        this.context=context;
        this.projectList=projectList;

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
            convertView = mInflater.inflate(R.layout.item_project_list, null);
        }


        TextView label=(TextView)convertView.findViewById(R.id.title_project_list);
        label.setText(projectList.get(position).getProjectName());


        View rectangle= convertView.findViewById(R.id.rectangle);
        rectangle.setBackgroundColor(Integer.parseInt(projectList.get(position).getProjectColor(), 16)+0xFF000000);

        //
        // 0x8adcb300
        return convertView;
    }
}