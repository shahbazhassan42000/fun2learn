package shahbaz4311.fun2learn;


import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inpField = findViewById(R.id.inpField);
        inpField.setText("HI");
        try {
            String quesStr=readFile("HtmlQuestions.txt");
            String [] temp= quesStr.split("\n");
            List<String> ques=Arrays.asList(temp);
            Collections.shuffle(ques);
            inpField.setText(ques.get(2));
        } catch (IOException e) {
            e.printStackTrace();
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