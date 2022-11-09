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
    List<String> ques=getShuffledList("HtmlQuestions.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inpField = findViewById(R.id.inpField);
        inpField.setText("HI");



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