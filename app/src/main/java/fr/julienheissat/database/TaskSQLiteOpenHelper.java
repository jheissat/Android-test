package fr.julienheissat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.julienheissat.taskmanager.R;

/**
 * Created by juju on 16/09/2014.
 */
public class TaskSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TASK_DB_SQLITE = "task_db.sqlite";
    public static final int VERSION = 1;
    public static final String TASK_TABLE = "tasks";
    public static final String TASK_ID = "taskId";
    public static final String TASK_NAME = "name";
    public static final String TASK_COMPLETE = "complete";
    public static final String TASK_ADDRESS = "address";
    public static final String TASK_LATITUDE = "latitude";
    public static final String TASK_LONGITUDE = "longitude";
    public static final String TASK_PROJECT = "projectId";
    public static final String TASK_PRIORITY = "priorityId";
    public static final String TASK_DATE = "date";

    public static final String PRIORITY_TABLE = "priorities";
    public static final String PRIORITY_ID = "priorityId";
    public static final String PRIORITY_NAME = "name";
    public static final String PRIORITY_DESCRIPTION = "description";
    public static final String PRIORITY_RESSOURCE = "ressource";


    public static final String PROJECT_TABLE = "projects";
    public static final String PROJECT_ID = "projectId";
    public static final String PROJECT_NAME = "name";
    public static final String PROJECT_DESCRIPTION = "description";
    public static final String PROJECT_COLOR = "color";
    private Context context;


    public TaskSQLiteOpenHelper(Context context) {
        super(context, TASK_DB_SQLITE, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
//        db.execSQL("alter table "+ TASK_TABLE+" add column "+TASK_ADDRESS+" text");
//        db.execSQL("alter table "+ TASK_TABLE+" add column "+TASK_LATITUDE+" integer");
//        db.execSQL("alter table "+ TASK_TABLE+" add column "+TASK_LONGITUDE+" integer");
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TASK_TABLE + " (" +
                        TASK_ID + " integer primary key autoincrement not null, " +
                        TASK_NAME + " text, " +
                        TASK_COMPLETE + " text, " +
                        TASK_ADDRESS + " text, " +
                        TASK_LATITUDE + " integer, " +
                        TASK_LONGITUDE + " integer, " +
                        TASK_PROJECT + " text, " +
                        TASK_PRIORITY + " text, " +
                        TASK_DATE + " bigint " +
                        ");"
        );



        db.execSQL(
                "create table " + PROJECT_TABLE + " (" +
                        PROJECT_ID + " integer primary key autoincrement not null, " +
                        PROJECT_NAME + " text, " +
                        PROJECT_DESCRIPTION + " text, " +
                        PROJECT_COLOR + " text " +
                        ");"
        );



        StringBuilder sqlAddProjects = new StringBuilder();
        sqlAddProjects.append("INSERT INTO " + PROJECT_TABLE + " (" + PROJECT_ID + ", " + PROJECT_NAME + ", " + PROJECT_DESCRIPTION + ", " + PROJECT_COLOR + ") VALUES ");
        sqlAddProjects.append(" (NULL, 'Personal','description personal','DB334E'), ");
        sqlAddProjects.append(" (NULL, 'Professional','description professional','FED156'), ");
        sqlAddProjects.append(" (NULL, 'Other','description other','2980B9') ;");
        db.execSQL(sqlAddProjects.toString());



        db.execSQL(
                "create table " + PRIORITY_TABLE + " (" +
                        PRIORITY_ID + " integer primary key autoincrement not null, " +
                        PRIORITY_NAME + " text, " +
                        PRIORITY_DESCRIPTION + " text, " +
                        PRIORITY_RESSOURCE + " text " +
                        ");"
        );



        String[] names = context.getResources().getStringArray(R.array.priority_title_list);
        String[] descriptions = context.getResources().getStringArray(R.array.priority_description_list);
        String[] ressources = context.getResources().getStringArray(R.array.priority_ressource_list);

        StringBuilder sqlAddPriorities = new StringBuilder();
        sqlAddPriorities.append("INSERT INTO " + PRIORITY_TABLE + " ( " + PRIORITY_ID + ", " + PRIORITY_NAME + ", " + PRIORITY_DESCRIPTION + ", " + PRIORITY_RESSOURCE + ") VALUES ");

        for (int i = 0; i < names.length; i++) {
            sqlAddPriorities.append(" (NULL, '" + names[i] + "', '" + descriptions[i] + "', '" + ressources[i] + "')");
            if (i == (names.length - 1)) {
                sqlAddPriorities.append("; ");
            } else {
                sqlAddPriorities.append(", ");
            }
        }

        db.execSQL(sqlAddPriorities.toString());
    }

}
