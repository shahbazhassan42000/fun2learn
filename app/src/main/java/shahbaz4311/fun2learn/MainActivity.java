package shahbaz4311.fun2learn;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.counter.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView inpField;
    List<String> ques;
    List<String> ans;
    List<String> opts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inpField = findViewById(R.id.inpField);
        inpField.setMovementMethod(new ScrollingMovementMethod());
        inpField.setText("HI");


        //Get questions,answers, and options list
        ques=getShuffledList("HtmlQuestions.txt");
        ans=getShuffledList("HtmlAnswers.txt");
        opts=getShuffledList("HtmlOptions.txt");

        for (String opt:opts) {
            inpField.setText(inpField.getText()+"\n");
            for(String str:opt.split(";")){
                inpField.setText(inpField.getText()+"\n"+str);
            }
            inpField.setText(inpField.getText()+"\n\n");
        }





    }

    private List<String> getShuffledList(String fName){
        try {
            List<String> ques=Arrays.asList(readFile(fName).split("\n"));
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
        return new String(buffer);
    }

}