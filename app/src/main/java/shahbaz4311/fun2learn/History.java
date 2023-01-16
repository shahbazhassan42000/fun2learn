package shahbaz4311.fun2learn;

import static java.text.DateFormat.getDateTimeInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shahbaz4311.fun2learn.models.User;
import shahbaz4311.fun2learn.utils.QuizAdapter;

public class History extends AppCompatActivity {

    ListView history_list;
    List<List<Object>> quizzes;
//    String[] quizzes_array;
    List<SpannableString> quizzes_array;
    User user;

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

//        //convert list to array
//        quizzes_array = new List<SpannableString>();
//        for (int i = 0; i < quizzes.size(); i++) {
//            quizzes_array.set(i, formattedHTMLStr(i));
//        }
//
//        //add these quizzes to the list view
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_white_text,R.id.list_content, quizzes_array);
//        history_list.setAdapter(adapter);






    }

//    private SpannableString formattedHTMLStr (int index){
//        String[] quiz = quizzes.get(index);
//        int score = Integer.parseInt(quiz[1]);
//        String text=String.format("%1$-6s %2$-30s %3$02d/10",String.valueOf(index)+".",quiz[0],score);
//        SpannableString spanText = new SpannableString(text);
//        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)), 0, 6, 0);
//        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.black)), 6, 36, 0);
//        spanText.setSpan(new ForegroundColorSpan(score>5?getColor(R.color.green):getColor(R.color.red)), 36, 40, 0);
//        return spanText;
//    }
}