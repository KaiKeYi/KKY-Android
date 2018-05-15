package xyz.yikai.kky.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xlg.library.utils.CommonUtil;

import xyz.yikai.kky.R;
import xyz.yikai.kky.module.test.TestActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.skipActivity(TestActivity.class, R.anim.translate_top200, 0);
            }
        });
    }
}
