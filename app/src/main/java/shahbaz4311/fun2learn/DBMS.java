package shahbaz4311.fun2learn;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBMS extends SQLiteOpenHelper {
    private final static String DB_NAME = "fun2learn.db";
    //USER TABLE
    private final static String USER_TABLE="user";
    private final static String USERNAME="username";
    private final static String PASSWORD="password";

    //Quiz Table
    private final static String QUIZ_TABLE="quiz";
    private final static String QUIZ_ID="id";
    private final static String QUIZ_DATE="date";
    private final static String QUESTION="question";
    private final static String OPTION1="option1";
    private final static String OPTION2="option2";
    private final static String OPTION3="option3";
    private final static String OPTION4="option4";
    private final static String ANSWER="answer";
    private final static String USER_ANSWER="user_answer";

    public DBMS(@Nullable Context context,  @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table if not exists
        String query = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
                USERNAME + " TEXT PRIMARY KEY, " +
                PASSWORD + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(query);

        //create quiz table with username as foreign key
        query = "CREATE TABLE IF NOT EXISTS " + QUIZ_TABLE + "(" +
                QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUIZ_DATE + " TEXT, " +
                QUESTION + " TEXT, " +
                OPTION1 + " TEXT, " +
                OPTION2 + " TEXT, " +
                OPTION3 + " TEXT, " +
                OPTION4 + " TEXT, " +
                ANSWER + " TEXT, " +
                USER_ANSWER + " TEXT, " +
                USERNAME + " TEXT, " +
                "FOREIGN KEY (" + USERNAME + ") REFERENCES " + USER_TABLE + "(" + USERNAME + ")" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table if exists
        String query = "DROP TABLE IF EXISTS " + USER_TABLE + ";";
        sqLiteDatabase.execSQL(query);
        query = "DROP TABLE IF EXISTS " + QUIZ_TABLE + ";";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
