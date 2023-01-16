package shahbaz4311.fun2learn.utils;

import static java.text.DateFormat.getDateTimeInstance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shahbaz4311.fun2learn.models.Question;
import shahbaz4311.fun2learn.models.User;

public class DBMS extends SQLiteOpenHelper {
    private final static String DB_NAME = "fun2learn.db";
    //USER TABLE
    private final static String USER_TABLE = "user";
    private final static String USERNAME = "username";
    private final static String USER_PASSWORD = "password";

    //Quiz Table
    private final static String QUIZ_TABLE = "quiz";
    private final static String QUIZ_ID = "id";
    private final static String QUIZ_DATE = "date";
    private final static String QUESTION = "question";
    private final static String OPTION1 = "option1";
    private final static String OPTION2 = "option2";
    private final static String OPTION3 = "option3";
    private final static String OPTION4 = "option4";
    private final static String ANSWER = "answer";
    private final static String USER_ANSWER = "user_answer";

    public DBMS(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table if not exists
        String query = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
                USERNAME + " TEXT PRIMARY KEY, " +
                USER_PASSWORD + " TEXT " +
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

    //add user to database
    public boolean signup(User user) {
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        //encrypt password using SHA512
        values.put(USER_PASSWORD, PASSWORD.encrypt(user.getPassword()));

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.insert(USER_TABLE, null, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //check if user exists
    public boolean userExists(String username) {
        try (SQLiteDatabase db = getReadableDatabase()) {
            String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USERNAME + " = '" + username + "';";
            return db.rawQuery(query, null).moveToFirst();
        } catch (Exception e) {
            return false;
        }
    }

    //login user
    public boolean login(User user) {
        try (SQLiteDatabase db = getReadableDatabase()) {
            String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USERNAME + " = '" + user.getUsername() + "';";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String pass = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                boolean res = pass.equals(PASSWORD.encrypt(user.getPassword()));
                cursor.close();
                return res;
            }
            cursor.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    //store quiz result against given user
    public void add_result(List<Question> questions, User user) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            for (Question question : questions) {
                ContentValues values = new ContentValues();
                values.put(QUIZ_DATE, getDateTimeInstance().format(question.getDate()));
                values.put(QUESTION, question.getQuestion());
                values.put(OPTION1, question.getOptions().get(0));
                values.put(OPTION2, question.getOptions().get(1));
                values.put(OPTION3, question.getOptions().get(2));
                values.put(OPTION4, question.getOptions().get(3));
                values.put(ANSWER, question.getCorrectAnswer());
                values.put(USER_ANSWER, question.getUserAnswer());
                values.put(USERNAME, user.getUsername());
                db.insert(QUIZ_TABLE, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get date and count of correct marks from quiz table
    public List<List<Object>> get_user_quizzes(User user) {
        try (SQLiteDatabase db = getReadableDatabase()) {
            //select date,count(user_answer) from quiz where answer=user_answer group by date order by date;
            String query = "SELECT " + QUIZ_DATE + ", COUNT(" + USER_ANSWER + ") FROM " + QUIZ_TABLE + " WHERE " + USERNAME + " = '" + user.getUsername() + "' AND " + ANSWER + " = " + USER_ANSWER + " GROUP BY " + QUIZ_DATE + " ORDER BY " + QUIZ_DATE + ";";
            Cursor cursor = db.rawQuery(query, null);
            List<List<Object>> list = new ArrayList<>();
            int i=0;
            while (cursor.moveToNext()) {
                List<Object> row = new ArrayList<>();
                row.add(i);
                row.add(cursor.getString(0));
                row.add(cursor.getInt(1));
                list.add(row);
                i++;
            }
            cursor.close();
            return list;
        } catch (Exception e) {
            return null;
        }

    }

    @SuppressLint("Range")
    //get quiz result for given date and user
    public List<Question> get_result(String quiz_date, User user) {
        try (SQLiteDatabase db = getReadableDatabase()) {
            String query = "SELECT * FROM " + QUIZ_TABLE + " WHERE " + USERNAME + " = '" + user.getUsername() + "' AND " + QUIZ_DATE + " = '" + quiz_date + "';";
            Cursor cursor = db.rawQuery(query, null);
            List<Question> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String question=cursor.getString(cursor.getColumnIndex(QUESTION));
                List<String> options = new ArrayList<>();
                options.add(cursor.getString(cursor.getColumnIndex(OPTION1)));
                options.add(cursor.getString(cursor.getColumnIndex(OPTION2)));
                options.add(cursor.getString(cursor.getColumnIndex(OPTION3)));
                options.add(cursor.getString(cursor.getColumnIndex(OPTION4)));
                String answer=cursor.getString(cursor.getColumnIndex(ANSWER));
                String user_answer=cursor.getString(cursor.getColumnIndex(USER_ANSWER));
                Date date = getDateTimeInstance().parse(cursor.getString(cursor.getColumnIndex(QUIZ_DATE)));
                list.add(new Question(question,options,answer,user_answer,date));
            }
            cursor.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
