package shahbaz4311.fun2learn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;
import java.util.List;

import shahbaz4311.fun2learn.models.User;
import shahbaz4311.fun2learn.utils.DBMS;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView username_label;
    Button logout_btn,quiz_btn,history_btn;
    Intent intent;

    DBMS dbms;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.home_activity);
        //get user from intent
        user = (User) getIntent().getSerializableExtra("user");

        //initialize views
        username_label = findViewById(R.id.username_label);
        logout_btn = findViewById(R.id.logout_btn);
        quiz_btn = findViewById(R.id.quiz_btn);
        history_btn = findViewById(R.id.history_btn);

        //set username
        username_label.setText(user.getUsername());

        //set on click listeners
        logout_btn.setOnClickListener(this);
        quiz_btn.setOnClickListener(this);
        history_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.logout_btn:
                //logout
                finish();
                //load main activity
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.quiz_btn:
                //start quiz activity
                intent = new Intent(this, QuizActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.history_btn:
                //start history
                intent = new Intent(this, History.class);
                //get user's quiz history from database
                dbms=new DBMS(this,null,1);
                List<List<Object>> quizzes = dbms.get_user_quizzes(user);
                if(quizzes!=null){
                    intent.putExtra("quizzes", (Serializable) quizzes);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                break;
        }

    }

}
