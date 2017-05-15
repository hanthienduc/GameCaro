package example.android.gamecaro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import example.android.gamecaro.R;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOnePlayer, btnTwoPlayer, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);

        initComponents();

    }

    private void initComponents() {
        btnOnePlayer = (Button) findViewById(R.id.oneplayer);
        btnTwoPlayer = (Button) findViewById(R.id.twoplayer);
        btnExit = (Button) findViewById(R.id.exit);
        btnOnePlayer.setOnClickListener(this);
        btnTwoPlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.oneplayer:
                intent = new Intent(MainMenuActivity.this, MainActivity.class);
                intent.putExtra("gameType", true);
                startActivityForResult(intent, 0);

                break;
            case R.id.twoplayer:
                intent = new Intent(MainMenuActivity.this, MainActivity.class);
                intent.putExtra("gameType", false);
                startActivityForResult(intent, 1);
                break;
            case R.id.exit:
                MainMenuActivity.this.finish();
                break;
        }
    }
}
