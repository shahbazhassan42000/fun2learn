package shahbaz4311.fun2learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class keyActivity extends AppCompatActivity {
    List<Key> key;
    int correctAns, count;
    String userName;
    TextView inpName, dateTime, marks;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        //enable vertical scroll bar
        main = findViewById(R.id.keyMainLayout);

        Intent intent = getIntent();
        key = (List<Key>) intent.getSerializableExtra("key");
        correctAns = Integer.parseInt(intent.getStringExtra("marks"));
        userName = intent.getStringExtra("name");
        marks = findViewById(R.id.marks);
        inpName = findViewById(R.id.userName);
        dateTime = findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        marks.setText(Integer.toString(correctAns) + "/10");


        for (Key key : key) {
            createKey(key);
        }


    }

    private void createKey(Key key) {
        createQuestion(key.getQuestion());
        createOptions(key.getOptions(),key.getUserAnswer(),key.getCorrectAnswer());
        count++;
    }

    private void createOptions(List<String> options, String userAnswer, String correctAnswer) {
        RadioGroup radioGroup=new RadioGroup(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(params);
        int index=0,checkedID=0;
        for(String option:options){
            RadioButton radioBtn=new RadioButton(this);
            params.setMargins(0,5,0,0);
            radioBtn.setLayoutParams(params);
            radioBtn.setTextColor(getColor(R.color.white));
            radioBtn.setBackgroundColor(getColor(R.color.secondary));
            radioBtn.setText(option);
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

        main.addView(radioGroup);
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
        params.setMargins(0, 20, 0, 5);
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