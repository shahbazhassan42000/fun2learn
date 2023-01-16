package shahbaz4311.fun2learn;

import static java.text.DateFormat.getDateTimeInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shahbaz4311.fun2learn.models.Question;
import shahbaz4311.fun2learn.models.User;
import shahbaz4311.fun2learn.utils.DBMS;
import shahbaz4311.fun2learn.utils.QuizAdapter;

public class History extends AppCompatActivity {

    ListView history_list;
    List<List<Object>> quizzes;
//    String[] quizzes_array;
    List<SpannableString> quizzes_array;
    User user;
    DBMS dbms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //get user and quizzes from intent
        user = (User) getIntent().getSerializableExtra("user");
        quizzes = (List<List<Object>>) getIntent().getSerializableExtra("quizzes");


        // Initialize the list view
        history_list = findViewById(R.id.history_list);


        //create quiz adapter
        QuizAdapter quizAdapter = new QuizAdapter(this, quizzes);

        //set adapter to list view
        history_list.setAdapter(quizAdapter);


        //set on item click listener
        history_list.setOnItemClickListener((parent, view, position, id) -> {
            //get quiz
            List<Object> quiz = quizzes.get(position);
            //get quiz date
            String quiz_date =quiz.get(1).toString();
            //get quiz result from db
            dbms = new DBMS(this, null, 1);
            List<Question> questions = dbms.get_result(quiz_date, user);
            if (questions != null) {
                //load result activity
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("questions", (Serializable) questions);
                intent.putExtra("user", user);
                startActivity(intent);
            }

        });
    }
}