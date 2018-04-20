package com.example.sony.tiktactoehdt;

import android.graphics.Bitmap;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    public static ChessBoard chessBoard;
    private Bitmap board;

    private EditText ed;
    private Button btnOff;
    private Button btnOnl;
    TCPClient client;
    private TextView tv;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    public static String x;
    public int player;
    public static int n;
    public static boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imv);
        tv = (TextView) findViewById(R.id.tv);


        chessBoard = new ChessBoard(MainActivity.this, 8, 8, 300, 300);
        chessBoard.init(0);
        board = chessBoard.drawBoard();
        imageView.setImageBitmap(board);

        btnOff = (Button) findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        return chessBoard.onTouch(view, motionEvent);
                    }
                });
            }

        });

        btnOnl = (Button) findViewById(R.id.btnOnl);
       btnOnl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {

                           socket = new Socket("192.168.1.111", 1234);
                           in=socket.getInputStream();
                           out=socket.getOutputStream();
                           player=Integer.parseInt(received());
                           MainActivity.n=player;




                       } catch (UnknownHostException e) {
                           System.err.println("Don't know about host " );
                           return;
                       } catch (IOException e) {
                           System.err.println("Couldn't get I/O for the connection to " +"\n" + e.getMessage());
                           return;
                       }
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               if(player==0) {
                                   tv.setText("Đã kết nối thành công, mời bạn đi trước");
                               }
                               else tv.setText("Đã kết nối thành công, chạm vào bàn cờ để bắt đầu");

                           }
                       });


                            imageView.setOnTouchListener(new View.OnTouchListener() {

                                @Override
                                public boolean onTouch(final View v, MotionEvent event) {
                                    // if (!MainActivity.flag) return false;
                                    // MainActivity.flag=false;
                                    if (n!=0) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {


                                                MainActivity.x=received();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        chessBoard.print(MainActivity.x.substring(0, 2), 0, v);

                                                    }
                                                });

                                            }
                                        }).start();
                                        n=0;
                                        return false;

                                    }


                                        int cellWidth = v.getWidth() / 8;
                                        int cellHeight = v.getHeight() / 8;
                                        String text;
                                        final int colIndex;
                                        colIndex = (int) (event.getX() / cellWidth);
                                        final int rowIndex;
                                        rowIndex = (int) (event.getY() / cellHeight);


                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                send(rowIndex + "" + colIndex);
                                            }

                                        }).start();




                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String a;
                                            MainActivity.x = received();
                                            if (MainActivity.x.equals("false")) {
                                                return;
                                            }

                                            System.out.println("aaa " + MainActivity.x);

                                            //  chessBoard.print(mess,player,v);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (MainActivity.x.length() > 5) {
                                                        chessBoard.print(MainActivity.x.substring(0, 2), player, v);
                                                        Toast.makeText(MainActivity.this, "Ban da thang", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        chessBoard.print(MainActivity.x, player, v);
                                                    }

                                                }
                                            });


                                            MainActivity.x = received();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int play;
                                                    if (player == 0) play = 1;
                                                    else play = 0;

                                                    if (MainActivity.x.length() > 5) {
                                                        chessBoard.print(MainActivity.x.substring(0, 2), play, v);
                                                        Toast.makeText(MainActivity.this, "Ban da thua", Toast.LENGTH_LONG).show();

                                                        return;
                                                    } else {
                                                        chessBoard.print(MainActivity.x, play, v);
                                                    }

                                                }
                                            });

                                        }
                                    }).start();


                                    //}
                                    return false;

                                }


                            });
                        }

               }).start();



           }
       });

    }

    public void send(String mess){
        try {
            out.write(mess.getBytes());
        } catch (IOException e) {
            System.out.println("Can't send");
        }
    }

    public String received(){
        byte[] buff = new byte[2048];
        String mess;

        try {

            int receivedBytes = in.read(buff);
            mess = new String(buff,0,receivedBytes);
            System.out.println("Nhan duoc" +mess);

            //   Toast.makeText(MainActivity.this,mess+"",Toast.LENGTH_LONG).show();
            return mess;


        } catch (Exception e) {
            System.out.println("Can't read "+e);
        }

        return "";
    }


  /*  public class connectTask extends AsyncTask<Void, String, Void> {
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
    }*/

    }




