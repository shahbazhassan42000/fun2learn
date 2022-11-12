package shahbaz4311.fun2learn;


import android.os.Bundle;
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

    TextView inpName;
    Button startBtn;
    List<String> ques, ans;
    List<List<String>> opts;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);


        //Get questions,answers, and options list
        ques = getShuffledList("HtmlQuestions.txt");
        ans = getShuffledList("HtmlAnswers.txt");
        opts = new ArrayList<>();
        for (String opt : getShuffledList("HtmlOptions.txt")) {
            List<String> list = Arrays.asList(opt.split(";"));
            Collections.shuffle(list);
            opts.add(list);
        }

        inpName=findViewById(R.id.inpName);
        startBtn=findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });








    }

    private List<String> getShuffledList(String fName) {
        try {
            List<String> ques = Arrays.asList(readFile(fName).split("\n"));
            Collections.shuffle(ques);
            return ques;
        } catch (IOException e) {
            return null;
        }
    }

    private String readFile(String fName) throws IOException {
        InputStream ir = getAssets().open(fName);
        int size = ir.available();
        byte[] buffer = new byte[size];
        ir.read(buffer);
        ir.close();
        return new String(buffer);
    }

}