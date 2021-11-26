package com.example.godgong;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NoticeActivity extends AppCompatActivity {
    Button btn_dialog;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        btn_dialog = (Button)findViewById(R.id.btn_dialog);
        tv_result = (TextView)findViewById(R.id.tv_result);

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(NoticeActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("제목");
                ad.setMessage("공지사항을 추가하시겠습니까?");

                final EditText et = new EditText(NoticeActivity.this);
                ad.setView(et);

                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = et.getText().toString();
                        tv_result.setText(result);
                        dialog.dismiss();


                    }
                });
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }

        });
    }
}