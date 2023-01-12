package shahbaz4311.fun2learn;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    TextView username_input, password_input, msg_label;
    Button login_btn, signup_btn;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        msg_label = findViewById(R.id.msg_label);
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);


//        inpName=findViewById(R.id.inpName);
//        errorMsg=findViewById(R.id.errorMsg);
//        startBtn=findViewById(R.id.startBtn);
//
//        startBtn.setOnClickListener(view -> {
//            String name= String.valueOf(inpName.getText());
//            if(!name.equals("")){
//                userName=name;
//                Intent quizIntent= new Intent(getBaseContext(), quizActivity.class);
//                quizIntent.putExtra("userName",userName);
//                startActivity(quizIntent);
//                finish();
//            }else{
//                errorMsg.setText("Enter your name first to start quiz!!!");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        errorMsg.setText("");
//                    }
//                },3000);
//            }
//        });
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.login_btn:
//                String username = username_input.getText().toString();
//                String password = password_input.getText().toString();
//                if(username.equals("") || password.equals("")){
//                    msg_label.setText("Please fill all the fields");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            msg_label.setText("");
//                        }
//                    },3000);
//                }else{
//                    user = new User(username,password);
//                    if(user.login()){
//                        Intent intent = new Intent(getBaseContext(), quizActivity.class);
//                        intent.putExtra("user",user);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        msg_label.setText("Invalid username or password");
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                msg_label.setText("");
//                            }
//                        },3000);
//                    }
//                }
//                break;
//            case R.id.signup_btn:
//                Intent intent = new Intent(getBaseContext(), signupActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

        //disable login and signup button if any field is empty
        if (!username_input.getText().toString().trim().equals("") && !password_input.getText().toString().trim().equals("")) {
            login_btn.setEnabled(true);
            signup_btn.setEnabled(true);
        } else {
            login_btn.setEnabled(false);
            signup_btn.setEnabled(false);
        }

        //change border color to red if password is less than 8 characters
        password_input.setBackgroundTintList(password_input.getText().toString().trim().length() < 8?ColorStateList.valueOf(getColor(R.color.red)):ColorStateList.valueOf(getColor(R.color.secondary)));

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}