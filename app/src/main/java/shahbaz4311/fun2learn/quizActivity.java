package shahbaz4311.fun2learn;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

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
        loadQuestion(quesNo);
        prevQuesBtn.setEnabled(false);

        nextQuesBtn.setOnClickListener(v -> {
            quesNo++;
            loadQuestion(quesNo);
            if (quesNo == 9) nextQuesBtn.setEnabled(false);
            if (quesNo == 1) prevQuesBtn.setEnabled(true);
        });

        prevQuesBtn.setOnClickListener(v -> {
            quesNo--;
            loadQuestion(quesNo);
            if (quesNo == 0) prevQuesBtn.setEnabled(false);
            if (quesNo == 8) nextQuesBtn.setEnabled(true);
        });

        //on radio button change
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            questions.get(quesNo).setUserAnswer(options[checkedId - 1].getText().toString());
            Log.d(TAG, checkedId + "\t" + options[checkedId - 1].getText().toString());
        });

        submitBtn.setOnClickListener(v -> {
            int marked = 0;
            for (Question q : questions) {
                if (q.getUserAnswer() != null && !q.getUserAnswer().equals("")) marked++;
            }
            if (marked != 10) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(this, resultActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("questions", (Serializable) questions);
                startActivity(intent);
            }
            });


            //Next Question
//        nextQuesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int selected = choices.getCheckedRadioButtonId();
//                if (selected == -1) {
//                    Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_SHORT).show();
//                } else {
//                    RadioButton selectedChoice = (RadioButton) findViewById(selected);
//                    if (isCorrectAns(selectedChoice.getText().toString())) {
//                        Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
//                        correctAns++;
//                        marks.setText(Integer.toString(correctAns) + "/10");
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
//                    }
//                    choices.clearCheck();
//                    if(count<10) loadQuestion(count);
//                    else{
//                            Intent keyIntent=new Intent(getBaseContext(),keyActivity.class);
//                            keyIntent.putExtra("key",(Serializable) key);
//                            keyIntent.putExtra("marks",Integer.toString(correctAns));
//                            keyIntent.putExtra("name",userName);
//                            startActivity(keyIntent);
//                        finish();
//                    }
//                }
//            }
//        });
        }

        private void loadQuestion ( int quesNo){
            if (quesNo != 9) {
                quesInp.setText(formattedHTMLStr("0" + Integer.toString(quesNo + 1) + ". " + questions.get(quesNo)).toString(), TextView.BufferType.SPANNABLE);
            } else {
                quesInp.setText(formattedHTMLStr(Integer.toString(quesNo + 1) + ". " + questions.get(quesNo)).toString(), TextView.BufferType.SPANNABLE);
            }
            List<String> choices = questions.get(quesNo).getOptions();
            for (int i = 0; i < choices.size(); i++) {
                options[i].setText(choices.get(i));
                if (questions.get(quesNo).getUserAnswer() != null && questions.get(quesNo).getUserAnswer().equals(choices.get(i))) {
                    options[i].setChecked(true);
                }
            }
        }

//    private boolean isCorrectAns(String ans) {
//        String correctAnswer=sols.get(quesInp.getText().toString().substring(4));
//        key.get(count-1).setUserAnswer(ans);
//        key.get(count-1).setCorrectAnswer(correctAnswer);
//        return ans.equals(correctAnswer);
//    }

        private SpannableString formattedHTMLStr (String text){
            SpannableString spanText = new SpannableString(text);
            spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)), 0, 3, 0);
            return spanText;
        }

        private List<Question> loadQuestions (String fName){
            try {
                String[] lines = readFile(fName).split("\n");
                for (String line : lines) {
                    String[] parts = line.split("<==>");
                    Log.d("TESTING", parts[0]);
                    ArrayList<String> options = new ArrayList<>(Arrays.asList(parts[1].split(";")));
                    Collections.shuffle(options);
                    questions.add(new Question(parts[0], options, parts[2]));
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
