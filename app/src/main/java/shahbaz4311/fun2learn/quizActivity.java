package shahbaz4311.fun2learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class quizActivity extends AppCompatActivity {

    Intent intent;
    String userName;
    TextView inpName,dateTime,marks,quesInp;
    RadioButton choice1,choice2,choice3,choice4;
    RadioGroup choices;
    List<String> ques, ans;
    List<List<String>> opts;
    LinearLayout main;
    int correctAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        correctAns=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        intent = getIntent();
        userName = intent.getStringExtra("userName");
        main=findViewById(R.id.quizMainLayout);
        main.setVerticalScrollBarEnabled(true);


        //Get questions,answers, and options list
        ques = getShuffledList("HtmlQuestions.txt");
        ans = getShuffledList("HtmlAnswers.txt");
        opts = new ArrayList<>();
        for (String opt : getShuffledList("HtmlOptions.txt")) {
            List<String> list = Arrays.asList(opt.split(";"));
            Collections.shuffle(list);
            opts.add(list);
        }


        marks=findViewById(R.id.marks);
        inpName=findViewById(R.id.userName);
        dateTime=findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        quesInp=findViewById(R.id.quesInp);
        choice1=findViewById(R.id.choice1);
        choice2=findViewById(R.id.choice2);
        choice3=findViewById(R.id.choice3);
        choice4=findViewById(R.id.choice4);
        choices=findViewById(R.id.choices);

        //show first question




    }


    private SpannableString formattedHTMLStr(String text){
        SpannableString spanText=new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)),0,2,0);
        return spanText;
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