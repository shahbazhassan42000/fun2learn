package shahbaz4311.fun2learn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class quizActivity extends AppCompatActivity {

    Intent intent;
    String userName;
    TextView inpName, dateTime, marks, quesInp;
    RadioButton[] options;
    RadioGroup optionsGroup;
    Button nextQuesBtn, prevQuesBtn, submitBtn;
    List<Question> questions;
    LinearLayout main;
    int quesNo;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        quesNo = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        intent = getIntent();
        userName = intent.getStringExtra("userName");
        questions=new ArrayList<>();

        //enable vertical scroll bar
        main = findViewById(R.id.quizMainLayout);
        main.setVerticalScrollBarEnabled(true);

        //load questions
        questions = loadQuestions("HTMLQuestions.txt");


        marks = findViewById(R.id.marks);
        inpName = findViewById(R.id.userName);
        dateTime = findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        quesInp = findViewById(R.id.quesInp);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextQuesBtn = findViewById(R.id.nextQuesBtn);
        prevQuesBtn = findViewById(R.id.prevQuesBtn);
        submitBtn = findViewById(R.id.submitBtn);
        options = new RadioButton[4];
        options[0] = findViewById(R.id.option1);
        options[1] = findViewById(R.id.option2);
        options[2] = findViewById(R.id.option3);
        options[3] = findViewById(R.id.option4);

        //set question
        creationQuestion();
        prevQuesBtn.setEnabled(false);

        nextQuesBtn.setOnClickListener(v -> {
            checkOptions();
            quesNo++;
            creationQuestion();
            if (quesNo == 9) nextQuesBtn.setEnabled(false);
            if (quesNo == 1) prevQuesBtn.setEnabled(true);
        });

        prevQuesBtn.setOnClickListener(v -> {
            checkOptions();
            quesNo--;
            creationQuestion();
            if (quesNo == 0) prevQuesBtn.setEnabled(false);
            if (quesNo == 8) nextQuesBtn.setEnabled(true);
        });


        submitBtn.setOnClickListener(v -> {
            checkOptions();
            int marked = 0;
            for (Question q : questions) {
                if (!q.getUserAnswer().equals("")) marked++;
            }
            if (marked != 10) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(this, resultActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("questions", (Serializable) questions);
                Log.d("TESTING", "STARTING RESULT ACTIVITY");
                startActivity(intent);
                finish();
            }
            });
        }

        private void checkOptions() {
            int selected = optionsGroup.getCheckedRadioButtonId();
            if (selected != -1 ){
                RadioButton selectedChoice = (RadioButton) findViewById(selected);
                questions.get(quesNo).setUserAnswer(selectedChoice.getText().toString());
            }
        }

        private void creationQuestion(){
            if (quesNo != 9) {
                quesInp.setText(formattedHTMLStr("0" + Integer.toString(quesNo + 1) + ". " + questions.get(quesNo).getQuestion()), TextView.BufferType.SPANNABLE);
            } else {
                quesInp.setText(formattedHTMLStr(Integer.toString(quesNo + 1) + ". " + questions.get(quesNo).getQuestion()), TextView.BufferType.SPANNABLE);
            }
            List<String> choices = questions.get(quesNo).getOptions();
            optionsGroup.clearCheck();
            for (int i = 0; i < choices.size(); i++) {
                options[i].setText(choices.get(i));
                if(questions.get(quesNo).getUserAnswer().equals(choices.get(i))) optionsGroup.check(options[i].getId());
            }
        }


        private SpannableString formattedHTMLStr (String text){
            SpannableString spanText = new SpannableString(text);
            spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)), 0, 3, 0);
            return spanText;
        }

        private List<Question> loadQuestions (String fName){
            try {
                String[] lines = readFile(fName).split("\r\n");
                for (String line : lines) {
                    String[] parts = line.split("<==>");
                    ArrayList<String> options = new ArrayList<>(Arrays.asList(parts[1].split(";")));
                    Collections.shuffle(options);
                    questions.add(new Question(parts[0].trim(), options, parts[2].trim()));
                }
                Collections.shuffle(questions);
                return questions;
            } catch (IOException e) {
                return null;
            }
        }


        private String readFile (String fName) throws IOException {
            InputStream ir = getAssets().open(fName);
            int size = ir.available();
            byte[] buffer = new byte[size];
            ir.read(buffer);
            ir.close();
            return new String(buffer);
        }
    }
