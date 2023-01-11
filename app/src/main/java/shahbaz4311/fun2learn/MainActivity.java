package shahbaz4311.fun2learn;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity extends AppCompatActivity {

    TextView inpName,errorMsg;
    Button startBtn;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        inpName=findViewById(R.id.inpName);
        errorMsg=findViewById(R.id.errorMsg);
        startBtn=findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String name= String.valueOf(inpName.getText());
            if(!name.equals("")){
                userName=name;
                Intent quizIntent= new Intent(getBaseContext(), quizActivity.class);
                quizIntent.putExtra("userName",userName);
                startActivity(quizIntent);
            }else{
                errorMsg.setText("Enter your name first to start quiz!!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        errorMsg.setText("");
                    }
                },3000);
            }
        });
    }
}