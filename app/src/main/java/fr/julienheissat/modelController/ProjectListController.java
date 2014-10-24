package fr.julienheissat.modelController;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import static fr.julienheissat.database.TaskSQLiteOpenHelper.PROJECT_ID;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PROJECT_NAME;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PROJECT_DESCRIPTION;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PROJECT_COLOR;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PROJECT_TABLE;


/**
 * Created by juju on 24/09/2014.
 */
public class ProjectListController
{
    private ArrayList<Project> projectList;
    private SQLiteDatabase database;
    
    public ProjectListController(SQLiteDatabase database)
    {
        this.database=database;
        loadProjects();
    }


    public void loadProjects() {

        projectList = new ArrayList<Project>();
        Cursor projectCursor = database.query(
                PROJECT_TABLE,
                new String[]{PROJECT_ID, PROJECT_NAME, PROJECT_DESCRIPTION, PROJECT_COLOR},
                null, null, null, null, String.format("%s", PROJECT_ID));

        projectCursor.moveToFirst();
        Project p;

        if (!projectCursor.isAfterLast()) {
            do {


                p = new Project();
                p.setProjectID(projectCursor.getLong(0));
                p.setProjectName(projectCursor.getString(1));
                p.setProjectDescription(projectCursor.getString(2));
                p.setProjectColor(projectCursor.getString(3));

                projectList.add(p);

            }
            while (projectCursor.moveToNext());
        }

        projectCursor.close();


    }


    public ArrayList<Project> getProjectList()
    {
        return this.projectList;
    }

    public Project getProjectById(Long projectId)
    {
        for (Project p:projectList)
        {
            if (p.getProjectID()==projectId)
                return p;
        }
        return null;
    }

    public Project getProjectByName(String projectName)
    {
        for (Project p:projectList)
        {
            if (p.getProjectName()==projectName)
                return p;
        }
        return null;

    }

    public void addProject(Project p)
    {
        assert (null != p);

        ContentValues values = new ContentValues();
        values.put(PROJECT_NAME,p.getProjectName());
        values.put(PROJECT_DESCRIPTION,p.getProjectDescription());
        values.put(PROJECT_COLOR,p.getProjectColor());

        long id = database.insert(PROJECT_TABLE, null, values);
        p.setProjectID(id);

        projectList.add(p);


    }

    private void saveProject(Project p)

    {
        assert (null != p);

        ContentValues values = new ContentValues();
        values.put(PROJECT_NAME,p.getProjectName());
        values.put(PROJECT_DESCRIPTION,p.getProjectDescription());
        values.put(PROJECT_COLOR,p.getProjectColor());

        long id = p.getProjectID();
        String where = String.format("%s = ?",PROJECT_ID);
        database.update(PROJECT_TABLE,values,where,new String[]{id+""});
    }


    public void deleteProject(Long id)
    {


        String where = String.format("%s in (%s)",PROJECT_ID,id);
        database.delete(PROJECT_TABLE,where,null);
    }






}
