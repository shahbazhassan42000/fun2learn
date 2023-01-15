package shahbaz4311.fun2learn;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;

import shahbaz4311.fun2learn.models.User;
import shahbaz4311.fun2learn.utils.DBMS;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    TextView username_input, password_input, msg_label;
    Button login_btn, signup_btn;
    ImageView show_password_btn;
    User user;
    DBMS dbms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        //TODO: remove after testing homepage
        //put user in intent
//        user=new User("shahbaz","Hum-2977");
//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("user", (Serializable) user);
//        startActivity(intent);
//        finish();







        dbms = new DBMS(this, null, 1);

        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        msg_label = findViewById(R.id.msg_label);
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        show_password_btn = findViewById(R.id.show_hide_password_btn);

        //adding listeners
        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        show_password_btn.setOnClickListener(this);
        username_input.addTextChangedListener(this);
        password_input.addTextChangedListener(this);


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

    //display error message for 5 seconds
    private  void display_message(String msg, int color,int duration){
        msg_label.setText(msg);
        msg_label.setTextColor(color);
        new android.os.Handler().postDelayed(() -> msg_label.setText(""), duration);
    }

    private void signup(){
        user = new User(username_input.getText().toString(), password_input.getText().toString());
        //check if username exist or not
        if (dbms.userExists(user.getUsername())) {
            //username exist
            display_message(getString(R.string.username_exist), getColor(R.color.red), 5000);
        } else {
            //username not exist
            //add user to database
            if (dbms.signup(user)) {
                display_message(getString(R.string.account_created_successfully), getColor(R.color.green), 5000);
                clear_fields();
                load_new_activity();
            } else {
                display_message(getString(R.string.account_created_failed), getColor(R.color.red), 5000);
            }
        }
    }

    private void load_new_activity(){
        //load new activity
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("user", (Serializable) user);
        startActivity(intent);
        finish();
    }


    private void login(){
        user = new User(username_input.getText().toString(), password_input.getText().toString());
        //check if login credentials are correct or not
        if (dbms.login(user)) {
            //login credentials are correct
            display_message(getString(R.string.login_success), getColor(R.color.green), 5000);
            clear_fields();
            load_new_activity();
        } else {
            //login credentials are incorrect
            display_message(getString(R.string.login_failed), getColor(R.color.red), 5000);
        }
    }

    private void clear_fields() {
        username_input.setText("");
        password_input.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_hide_password_btn:
                //check if description is show_password then show password
                show_hide_password_btn_toggle();
                break;
            case R.id.signup_btn:
                //signup user
                signup();
                break;
            case R.id.login_btn:
                //login user
                login();
                break;
            default:
                break;
        }
    }

    private void show_hide_password_btn_toggle() {
        if (show_password_btn.getContentDescription().equals(getString(R.string.show_password))) {
            show_password_btn.setImageResource(R.drawable.password_hide_icon);
            show_password_btn.setContentDescription(getString(R.string.hide_password));
            password_input.setTransformationMethod(null);
        } else {
            show_password_btn.setImageResource(R.drawable.password_show_icon);
            show_password_btn.setContentDescription(getString(R.string.show_password));
            password_input.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

        //disable login and signup button if any field is empty
        if (!username_input.getText().toString().trim().equals("") && password_input.getText().toString().trim().length() >= 8) {
            login_btn.setEnabled(true);
            signup_btn.setEnabled(true);
        } else {
            login_btn.setEnabled(false);
            signup_btn.setEnabled(false);
        }

        //change border color to red if password is less than 8 characters
        password_input.setBackgroundTintList(password_input.getText().toString().trim().length() < 8 ? ColorStateList.valueOf(getColor(R.color.red)) : ColorStateList.valueOf(getColor(R.color.secondary)));
        username_input.setBackgroundTintList(username_input.getText().toString().trim().equals("") ? ColorStateList.valueOf(getColor(R.color.red)) : ColorStateList.valueOf(getColor(R.color.secondary)));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}