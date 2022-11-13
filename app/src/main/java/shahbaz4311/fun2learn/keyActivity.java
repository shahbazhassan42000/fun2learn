package shahbaz4311.fun2learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class keyActivity extends AppCompatActivity {
    List<Key> key;
    int correctAns;
    String userName;
    TextView inpName, dateTime, marks;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        //enable vertical scroll bar
        main=findViewById(R.id.keyMainLayout);
        main.setVerticalScrollBarEnabled(true);

        Intent intent=getIntent();
        key=(List<Key>) intent.getSerializableExtra("key");
        correctAns=Integer.parseInt(intent.getStringExtra("marks"));
        userName=intent.getStringExtra("name");
        marks = findViewById(R.id.marks);
        inpName = findViewById(R.id.userName);
        dateTime = findViewById(R.id.dateTime);
        inpName.setText(userName);
        dateTime.setText(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

        for(Key key:key){
            createKey();
        }


    }

    private void createKey() {

    }
}