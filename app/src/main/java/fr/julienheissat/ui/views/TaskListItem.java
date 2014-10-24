package fr.julienheissat.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import fr.julienheissat.modelController.PriorityListController;
import fr.julienheissat.modelController.ProjectListController;
import fr.julienheissat.modelController.Task;
import fr.julienheissat.taskmanager.R;

/**
 * Created by juju on 15/09/2014.
 */

public class TaskListItem extends RelativeLayout {


    private ImageView imagePriority;
    private CheckedTextView checkbox;
    private TextView projectView;
    private TextView dateView;
    private Context context;

    private Task task;
//    private TextView addressText;

    public TaskListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imagePriority = (ImageView) findViewById(R.id.list_image);
        checkbox = (CheckedTextView) findViewById(R.id.list_title);
        projectView = (TextView) findViewById(R.id.list_project);
        dateView = (TextView) findViewById(R.id.list_date);


    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        Drawable draw;
        String taskPriorityNumber;
        checkbox.setText(task.getName());
        checkbox.setChecked(task.isComplete());
        imagePriority.setImageResource(context.getResources().getIdentifier
                (task.getPriority().getPriorityRessource(),
                        "drawable", context.getPackageName()));


        projectView.setText(task.getProject().getProjectName());
        projectView.setBackgroundColor(Integer.parseInt(task.getProject().getProjectColor(), 16)+0xFF000000);
        dateView.setText(task.getDateString());

    }
}