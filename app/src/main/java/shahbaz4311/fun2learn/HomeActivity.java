package shahbaz4311.fun2learn;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import shahbaz4311.fun2learn.models.User;

public class HomeActivity extends AppCompatActivity {

    TextView username_label;

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

        //set username
        username_label.setText(user.getUsername());


    }
}
