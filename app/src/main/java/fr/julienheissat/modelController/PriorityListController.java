package fr.julienheissat.modelController;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static fr.julienheissat.database.TaskSQLiteOpenHelper.PRIORITY_TABLE;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PRIORITY_ID;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PRIORITY_NAME;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PRIORITY_DESCRIPTION;
import static fr.julienheissat.database.TaskSQLiteOpenHelper.PRIORITY_RESSOURCE;

/**
 * Created by julienheissat on 13/10/2014.
 */

public class PriorityListController {

    private ArrayList<Priority> priorityList;
    private SQLiteDatabase database;

    public PriorityListController(SQLiteDatabase database) {

        this.database = database;
        loadPriorities();
    }


    public void loadPriorities() {

        priorityList = new ArrayList<Priority>();
        Cursor priorityCursor = database.query(
                PRIORITY_TABLE,
                new String[]{PRIORITY_ID, PRIORITY_NAME, PRIORITY_DESCRIPTION, PRIORITY_RESSOURCE},
                null, null, null, null, String.format("%s", PRIORITY_NAME));

        priorityCursor.moveToFirst();
        Priority p;

        if (!priorityCursor.isAfterLast()) {
            do {

                p = new Priority();
                p.setPriorityId(priorityCursor.getLong(0));
                p.setPriorityName(priorityCursor.getString(1));
                p.setPriorityDescription(priorityCursor.getString(2));
                p.setPriorityRessource(priorityCursor.getString(3));

                priorityList.add(p);

            }
            while (priorityCursor.moveToNext());
        }

        priorityCursor.close();


    }


    public ArrayList<Priority> getPriorityList()
    {
        return this.priorityList;
    }

    public Priority getPriorityById(Long priorityId)
    {
        for (Priority p:priorityList)
        {
            if (p.getPriorityId()==priorityId)
                return p;
        }
            return null;
    }

    public Priority getPriorityByName(String priorityName)
    {
        for (Priority p:priorityList)
        {
            if (p.getPriorityName()==priorityName)
                return p;
        }
        return null;

    }



}
