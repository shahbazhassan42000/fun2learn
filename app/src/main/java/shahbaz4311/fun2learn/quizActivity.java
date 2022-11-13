package shahbaz4311.fun2learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class quizActivity extends AppCompatActivity {

    Intent intent;
    String userName;
    TextView inpName, dateTime, marks, quesInp;
    RadioButton choice1, choice2, choice3, choice4;
    RadioGroup choices;
    Button nextQuesBtn;
    List<String> ques;
    HashMap<String, String> sols;
    HashMap<String, List<String>> opts;

    LinearLayout main;
    int correctAns, count;

    TextView t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        correctAns = count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        intent = getIntent();
        userName = intent.getStringExtra("userName");
        main = findViewById(R.id.quizMainLayout);
        main.setVerticalScrollBarEnabled(true);


        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);


        //Get questions,answers, and options list
        ques = getShuffledList("HtmlQuestions.txt");
        sols = getSolutions("HtmlQuestions.txt", "HtmlAnswers.txt");
        opts = getOptions("HtmlQuestions.txt", "HtmlOptions.txt");


        marks = findViewById(R.id.marks);
        inpName = findViewById(R.id.userName);
        dateTime = findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        quesInp = findViewById(R.id.quesInp);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        choices = findViewById(R.id.choices);

        //load first Question
        loadQuestion(count);

        //Next Question
        nextQuesBtn = findViewById(R.id.nextQuesBtn);
        nextQuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = choices.getCheckedRadioButtonId();
                if (selected == -1) {
                    Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedChoice = (RadioButton) findViewById(selected);
                    if (isCorrectAns(selectedChoice.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        correctAns++;
                        marks.setText(Integer.toString(correctAns) + "/10");
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
                    }
                    choices.clearCheck();
                    if(count<10) loadQuestion(count);
                    else{
                        finish();
                    }
                }
            }
        });


    }

    private void loadQuestion(int quesNo){
        if(quesNo!=9){
            quesInp.setText(formattedHTMLStr("0"+Integer.toString(quesNo+1)+". " + ques.get(quesNo)), TextView.BufferType.SPANNABLE);
        }else{
            quesInp.setText(formattedHTMLStr(Integer.toString(quesNo+1)+". " + ques.get(quesNo)), TextView.BufferType.SPANNABLE);
        }
        List<String> choices = opts.get(quesInp.getText().toString().substring(4));
        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        choice3.setText(choices.get(2));
        choice4.setText(choices.get(3));
        count++;
    }

    private boolean isCorrectAns(String ans) {
//        t1.setText(quesInp.getText().toString().substring(4));
//        t2.setText(ans+String.valueOf(ans.length()));
//        t3.setText(sols.get(quesInp.getText().toString().substring(4))+String.valueOf(sols.get(quesInp.getText().toString().substring(4)).length()));
//        t4.setText(Boolean.toString(ans.equals(sols.get(quesInp.getText().toString().substring(4)))));
        return ans.equals(sols.get(quesInp.getText().toString().substring(4)));
    }

    private SpannableString formattedHTMLStr(String text) {
        SpannableString spanText = new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)), 0, 3, 0);
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

    private HashMap<String, String> getSolutions(String quesFName, String ansFName) {
        try {
            List<String> ques = Arrays.asList(readFile(quesFName).split("\n"));
            List<String> ans = Arrays.asList(readFile(ansFName).split("\n"));
            HashMap<String, String> sols = new HashMap<>();
            for (int i = 0; i < ques.size(); i++) {
                sols.put(ques.get(i), ans.get(i).trim());
            }
            return sols;
        } catch (IOException e) {
            return null;
        }
    }

    private HashMap<String, List<String>> getOptions(String quesFName, String optFName) {
        try {
            List<String> ques = Arrays.asList(readFile(quesFName).split("\n"));
            List<List<String>> opts = new ArrayList<>();
            HashMap<String, List<String>> options = new HashMap<>();
            for (String opt : Arrays.asList(readFile(optFName).split("\n"))) {
                List<String> list = Arrays.asList(opt.split(";"));
                Collections.shuffle(list);
                opts.add(list);
            }
            for (int i = 0; i < ques.size(); i++) {
                options.put(ques.get(i), opts.get(i));
            }
            return options;
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