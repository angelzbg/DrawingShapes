package angelzani.drawingshapes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import angelzani.drawingshapes.figures.Figure;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "drawingshapes.db";

    private SQLiteDatabase db;

    //Таблици имена
    private static final String TABLE_PROJECTS = "Projects";
    private static final String TABLE_FIGURES = "Figures";

    //Колони таблица FIGURES
    private static final String FIGURE_ID = "id";
    private static final String PROJECT_NAME = "projectname";
    private static final String FIGURE_TYPE = "type";
    private static final String FIGURE_TITLE = "title";
    private static final String FILL_COLOR = "fillcolor";
    private static final String STROKE_COLOR = "strokecolor";
    private static final String STROKE_WIDTH = "strokewidth";
    private static final String FIGURE_WIDTH = "width";
    private static final String FIGURE_HEIGHT = "height";
    private static final String FIGURE_X = "x";
    private static final String FIGURE_Y = "y";
    private static final String FIGURE_ROTATION = "rotation";

    //Колони таблица PROJECTS
    private static final String PROJECT_ID = "id";
    //private static final String PROJECT_NAME = "projectname";
    /*
     * Трябва id-то на проекта да бъде foreign key в таблицата с фигури, обаче не ми се занимава -> просто ще пишем името на проекта за всяка фигура в таблицата (няма какво да се обърка)
     */

    //Query за създаване на таблиците
    private static final String CREATE_TABLE_FIGURES =
            "CREATE TABLE " + TABLE_FIGURES +
                    " ( '" + FIGURE_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT , '" +
                    PROJECT_NAME + "' TEXT, '" +
                    FIGURE_TYPE + "' INTEGER, '" +
                    FIGURE_TITLE + "' INTEGER, '" +
                    FILL_COLOR + "' TEXT, '" +
                    STROKE_COLOR + "' TEXT, '" +
                    STROKE_WIDTH + "' INTEGER, '" +
                    FIGURE_WIDTH + "' INTEGER, '" +
                    FIGURE_HEIGHT + "' INTEGER, '" +
                    FIGURE_X + "' INTEGER, '" +
                    FIGURE_Y + "' INTEGER, '" +
                    FIGURE_ROTATION + "' INTEGER );";
    private static final String CREATE_TABLE_PROJECTS =
            "CREATE TABLE " + TABLE_PROJECTS + " ( '" + PROJECT_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT , '" + PROJECT_NAME + "' TEXT UNIQUE );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FIGURES);
        sqLiteDatabase.execSQL(CREATE_TABLE_PROJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_FIGURES);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PROJECTS);
        onCreate(sqLiteDatabase);
    }

    public void saveProject(String title, ArrayList<Figure> figures){
        // Слагаме името в таблицата с имена -> и да се повтаря и да има грешка няма никакво значение -> няма го - екстра, има го - пак екстра
        try {
            db = getWritableDatabase();
            ContentValues CV = new ContentValues();
            CV.put(PROJECT_NAME, title);
            db.insertOrThrow(TABLE_PROJECTS, null, CV); // unique -> няма да се запише ако вече съществува -> SQLException
        } catch (SQLException e) {
            //Log.e("SQLException", e.getMessage());
            //e.printStackTrace();
            // Shut the fuck up
            // Nobody even wants you here
        } finally {
            if (db != null) db.close();
        }

        // Добавяне на фигурите в таблица фигури
        try {
            db = getWritableDatabase();
            db.delete(TABLE_FIGURES, PROJECT_NAME + " LIKE ?", new String[] { title });

            for(Figure f : figures) {
                ContentValues cv = new ContentValues();
                cv.put(PROJECT_NAME, title);
                cv.put(FIGURE_TYPE, f.type);
                cv.put(FIGURE_TITLE, f.title);
                cv.put(FILL_COLOR, f.fillColor);
                cv.put(STROKE_COLOR, f.strokeColor);
                cv.put(STROKE_WIDTH, f.strokeWidth);
                cv.put(FIGURE_WIDTH, f.getLayoutParams().width);
                cv.put(FIGURE_HEIGHT, f.getLayoutParams().height);
                cv.put(FIGURE_X, (int)f.getX());
                cv.put(FIGURE_Y, (int)f.getY());
                cv.put(FIGURE_ROTATION, (int)f.getRotation());

                db.insertOrThrow(TABLE_FIGURES, null, cv);
            }

        } catch (SQLException e) {
            Log.e("SQLException", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    } // end of saveProject()

    public ArrayList<String> getAllProjectNames(){
        Cursor c = null;
        try {
            db = getReadableDatabase();
            String query = "SELECT " + PROJECT_NAME + " FROM " + TABLE_PROJECTS + ";";

            c = db.rawQuery(query, null);
            if (c.isBeforeFirst()) {
                ArrayList<String> projects = new ArrayList<String>();
                while (c.moveToNext()) {
                    projects.add(c.getString(c.getColumnIndex(PROJECT_NAME)));
                }
                return projects;
            }
        } catch (SQLException e) {
            Log.e("SQLException", e.getMessage());
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
        return null;
    } // end of getAllProjectNames()

    public ArrayList<FigureDB> getProject(String projectName) {
        Cursor c = null;
        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_FIGURES + " WHERE " + PROJECT_NAME + " LIKE '" + projectName + "';";

            c = db.rawQuery(query, null);
            if (c.isBeforeFirst()) {
                ArrayList<FigureDB> project = new ArrayList<FigureDB>();
                while (c.moveToNext()) {
                    FigureDB figure = new FigureDB();
                    figure.type = c.getInt(c.getColumnIndex(FIGURE_TYPE));
                    figure.title = c.getString(c.getColumnIndex(FIGURE_TITLE));
                    figure.fillColor = c.getString(c.getColumnIndex(FILL_COLOR));
                    figure.strokeColor = c.getString(c.getColumnIndex(STROKE_COLOR));
                    figure.strokeWidth = c.getInt(c.getColumnIndex(STROKE_WIDTH));
                    figure.width = c.getInt(c.getColumnIndex(FIGURE_WIDTH));
                    figure.height = c.getInt(c.getColumnIndex(FIGURE_HEIGHT));
                    figure.x = c.getInt(c.getColumnIndex(FIGURE_X));
                    figure.y = c.getInt(c.getColumnIndex(FIGURE_Y));
                    figure.rotation = c.getInt(c.getColumnIndex(FIGURE_ROTATION));
                    /*
                    int type = c.getInt(2);
                    String title = c.getString(3);
                    String fillColor = c.getString(4);
                    String strokeColor = c.getString(5);
                    int strokeWidth = c.getInt(6);
                    int width = c.getInt(7);
                    int height = c.getInt(8);
                    int x = c.getInt(9);
                    int y = c.getInt(10);
                    int rotation = c.getInt(11);
                    FigureDB figure = new FigureDB(type, title, fillColor, strokeColor, strokeWidth, width, height, x, y, rotation);
                    */
                    project.add(figure);
                }
                return project;
            }
        } catch (SQLException e) {
            Log.e("SQLException", e.getMessage());
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (db != null) db.close();
        }
        return null;
    } //  end of getProject()

} // end of DatabaseHelper{}