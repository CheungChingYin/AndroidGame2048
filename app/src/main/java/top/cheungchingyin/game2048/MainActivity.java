package top.cheungchingyin.game2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private Button btn_reset;
    private static MainActivity mainActivity = null;
    private int score = 0;

    public MainActivity(){
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = findViewById(R.id.tvScore);
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void  addScore(int s){
        score += s;
        showScore();
    }

    public void showScore(){
        tvScore.setText(score+"");
    }
}
