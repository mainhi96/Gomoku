package com.example.sony.tiktactoehdt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by SONY on 4/10/2018.
 */

public class TCPClient {
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private int player;
    private int player2;
    private String mes;




    public TCPClient(String severAddress, int severPort) throws IOException {
        socket= new Socket(severAddress, severPort);
        in = socket.getInputStream();
        out = socket.getOutputStream();

    }

    public int getPlayer() {
        return player;
    }



    //gui tin nhan
    public void send(String mess) {

        try {
            out.write(mess.getBytes());
        } catch (IOException e) {
            System.out.println("Can't send");
        }
    }
    public void sendStep(ImageView img, final ChessBoard chessBoard){


            img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int cellWidth = v.getWidth() / 8;
                    int cellHeight = v.getHeight() / 8;
                    String text;
                    int colIndex;
                    colIndex = (int) (event.getX() / cellWidth);
                    int rowIndex;
                    rowIndex = (int) (event.getY() / cellHeight);

                    send(rowIndex + "" + colIndex);

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    text=nhan().substring(0,2);
                    chessBoard.print(text,player,v);

                    //}
                    return true;

                }



            });




        }






    //nhan tin nhan

    public void setPlayer() {


        byte[] buff = new byte[2048];
        String mess;

        try {

                int receivedBytes = in.read(buff);
                mess = new String(buff,0,receivedBytes);
              //  Toast.makeText(context,mess+"",Toast.LENGTH_LONG).show();
                this.player= Integer.parseInt(mess);
                if (this.player==0) this.player2=1;
                else this.player2=0;
                MainActivity.chessBoard.setPlayer(player);
                System.out.println(MainActivity.chessBoard.getPlayer());


        } catch (Exception e) {
            System.out.println("Can't read"+ e);
        }


    }

    public String nhan() {


        byte[] buff = new byte[2048];
        String mess;

        try {

            int receivedBytes = in.read(buff);
            mess = new String(buff,0,receivedBytes);
            //  Toast.makeText(context,mess+"",Toast.LENGTH_LONG).show();
            return mess;


        } catch (Exception e) {
            System.out.println("Can't read "+e);
        }

        return "";
    }

    }




