package shahbaz4311.fun2learn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import java.util.Calendar;
import java.util.List;

public class resultActivity extends AppCompatActivity {

    int correctAns, count;
    List<Question> questions;
    String userName;
    LinearLayout main;
    TextView inpName, dateTime, marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        correctAns=count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //enable vertical scroll bar
        main = findViewById(R.id.resultMainLayout);

        Log.d("TESTING", "STARTED");
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Log.d("TESTING", userName);
        Log.d("TESTING", "BEFORE");
        questions = (List<Question>) intent.getSerializableExtra("questions");
        Log.d("TESTING", "AFTER");
        marks = findViewById(R.id.marks);
        inpName = findViewById(R.id.userName);
        dateTime = findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        for (Question question : questions) {
            createResult(question);
        }
    }
    @SuppressLint("SetTextI18n")
    private void createResult(Question question) {
        createQuestion(question.getQuestion());
        createOptions(question.getOptions(),question.getUserAnswer(),question.getCorrectAnswer());
        if(count==questions.size()-1) marks.setText(Integer.toString(correctAns) + "/10");
        count++;
    }

    private void createOptions(List<String> options, String userAnswer, String correctAnswer) {
        RadioGroup radioGroup=new RadioGroup(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(params);
        int index=0,checkedID=0;
        for(String option:options){
            RadioButton radioBtn=new RadioButton(this);
            params.setMargins(0,10,0,0);
            radioBtn.setLayoutParams(params);
            radioBtn.setTextColor(getColor(R.color.white));
            radioBtn.setBackgroundColor(getColor(R.color.secondary));
            radioBtn.setText(option);
            radioBtn.setClickable(false);
            radioBtn.setEnabled(false);
            if(option.equals(userAnswer)){
                checkedID=index;
                radioBtn.setSelected(true);
                if(!userAnswer.equals(correctAnswer)) radioBtn.setBackgroundColor(getColor(R.color.red));
            }
            if(option.equals(correctAnswer)) radioBtn.setBackgroundColor(getColor(R.color.green));
            CompoundButtonCompat.setButtonTintList(radioBtn, ColorStateList.valueOf(getColor(R.color.primary)));
            radioGroup.addView(radioBtn);
            index++;
        }
        radioGroup.check(radioGroup.getChildAt(checkedID).getId());
        radioGroup.setEnabled(false);
        main.addView(radioGroup);

        //check if answer is correct
        if(userAnswer.equals(correctAnswer)) correctAns++;
        Log.d("TESTING",userAnswer+" "+userAnswer.length()+"\t"+correctAnswer+" "+correctAnswer.length());
    }

    private void createQuestion(String question) {
        TextView ques = new TextView(this);
        if (count != 9) {
            ques.setText(formattedHTMLStr("0" + Integer.toString(count + 1) + ". " + question), TextView.BufferType.SPANNABLE);
        } else {
            ques.setText(formattedHTMLStr(Integer.toString(count + 1) + ". " + question), TextView.BufferType.SPANNABLE);
        }
        ques.setBackgroundColor(getColor(R.color.primary));
        ques.setTextColor(getColor(R.color.white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 60, 0, 10);
        TextView t=findViewById(R.id.t);
        ques.setPadding(t.getPaddingLeft(), t.getPaddingTop(), t.getPaddingRight(), t.getPaddingBottom());
        ques.setLayoutParams(params);
        ques.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Medium);
        main.addView(ques);
    }

    private SpannableString formattedHTMLStr(String text) {
        SpannableString spanText = new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.white)), 3, text.length(), 0);
        spanText.setSpan(new ForegroundColorSpan(getColor(R.color.yellow)), 0, 3, 0);
        return spanText;
    }
}
