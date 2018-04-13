package com.example.sony.tiktactoehdt;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    public static  ChessBoard chessBoard;
    private Bitmap board;

    private EditText ed;
    private Button btnOff;
    private Button btnOnl;
    TCPClient client;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         imageView= (ImageView) findViewById(R.id.imv);
         tv= (TextView) findViewById(R.id.tv);


 chessBoard= new ChessBoard(MainActivity.this,8,8,300,300);
        chessBoard.init(0);
        board = chessBoard.drawBoard();
        imageView.setImageBitmap(board);

        btnOff= (Button) findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        return chessBoard.onTouch(view,motionEvent);
                    }
                });
            }

        });
        btnOnl= (Button) findViewById(R.id.btnOnl);
        btnOnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new connectTask().execute();

            }
        });



    }

    public class connectTask extends AsyncTask<Void, String, Void> {
        String vt="aaa";


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String text;
                client = new TCPClient("192.168.1.105", 1234);
                client.setPlayer();
                client.sendStep(imageView, chessBoard);

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            int play= Integer.parseInt(values[0]);
            chessBoard.init(play);
            Toast.makeText(MainActivity.this,play+"",Toast.LENGTH_LONG);
        }
    }
}


