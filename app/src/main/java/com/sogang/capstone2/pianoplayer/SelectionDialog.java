package com.sogang.capstone2.pianoplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dialog);

        final String mchoices[] = new String[] {
                "Kiss The Rain"
        };

        final String tchoices[] = new String[] {
                "무박자"
        };

        final TextView selectMusic = findViewById(R.id.selectMusic);
        final TextView selectTempo = findViewById(R.id.selectTempo);
        TextView confirm = findViewById(R.id.confirm);

        /*selectMusic.setText(mchoices[0]);
        selectTempo.setText(tchoices[0]);*/

        selectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad = new AlertDialog.Builder(SelectionDialog.this)
                        .setTitle("곡 선택")
                        .setItems(mchoices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectMusic.setText(mchoices[i]);
                            }
                        })
                        .create();

                ad.show();
            }
        });

        selectTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad = new AlertDialog.Builder(SelectionDialog.this)
                        .setTitle("박자 선택")
                        .setItems(tchoices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectTempo.setText(tchoices[i]);
                            }
                        })
                        .create();

                ad.show();
            }
        });
        
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectMusic.getText().toString().equals(mchoices[0]) && selectTempo.getText().toString().equals(tchoices[0]))
                    finish();
                else
                    Toast.makeText(SelectionDialog.this, "곡과 박자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
