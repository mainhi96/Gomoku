package com.example.sony.tiktactoehdt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 4/13/2018.
 */

public class ChessBoardTCP {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private int[][] board;
    private int player;
    private Context context;

    private int colQty;
    private int rowQty;
    private int bitmapWidth;
    private int bitmapHeight;

    private List<Line> listlines;

    private Bitmap bmCross;
    private Bitmap bmX;


    public ChessBoardTCP(Context context, int colQty, int rowQty, int bitmapWidth, int bitmapHeight) {
        this.context = context;
        this.colQty = colQty;
        this.rowQty = rowQty;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
    }

    public void init(int player){
        listlines= new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas= new Canvas(bitmap);
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.DKGRAY);
        board= new int [rowQty][colQty];  //dong/cot


        for (int i=0; i<rowQty;i++){
            for (int j=0; j<colQty;j++)
                board[i][j] = -1;
        }
        this.player=player;

        int cellWidth = bitmapWidth/colQty;
        int cellHeight = bitmapHeight/rowQty;


        for (int i=0;i<=colQty;i++){
            listlines.add(new Line(i*cellWidth,0,i*cellWidth,bitmapHeight));
        }
        for (int j=0;j<=rowQty;j++){
            listlines.add(new Line(0,j*cellHeight,bitmapWidth,j*cellHeight));
        }

        bmCross= BitmapFactory.decodeResource(context.getResources(),R.drawable.icon);
        bmX= BitmapFactory.decodeResource(context.getResources(),R.drawable.cross);
    }



    public boolean onTouch (View v, MotionEvent event){

        //  Toast.makeText(context, v.getX() + "- " + event.getY(),Toast.LENGTH_LONG ).show();
        int cellWidth= v.getWidth()/colQty;
        int cellHeight = v.getHeight()/rowQty;

        int cellWidthBM = bitmapWidth/colQty;
        int cellHeightBM = bitmapHeight/rowQty;

        //     Toast.makeText(context, cellHeightBM+"-"+cellHeightBM,Toast.LENGTH_LONG ).show();


        int colIndex;
        colIndex=(int) (event.getX()/cellWidth);
        int rowIndex;
        rowIndex= (int)  (event.getY()/cellHeight);



        //  Toast.makeText(context,rowIndex+"-"+colIndex,Toast.LENGTH_LONG).show();


        if (board[rowIndex][colIndex]!=-1) return true;
        board[rowIndex][colIndex] = player;
   //     Toast.makeText(context,"THANG: "+player +"["+ rowIndex +"-"+colIndex+"]"+ win(rowIndex,colIndex,player) ,Toast.LENGTH_LONG).show();


        if (player==0){
            canvas.drawBitmap(bmCross,new Rect(0,0,bmCross.getWidth(),bmCross.getHeight()),new Rect(colIndex*cellWidthBM,rowIndex*cellHeightBM,(colIndex+1)*cellWidthBM,(rowIndex+1)*cellHeightBM),paint);

        }
        else {

            canvas.drawBitmap(bmX,new Rect (0,0,bmX.getWidth(),bmX.getHeight()),new Rect(colIndex*cellWidthBM,rowIndex*cellHeightBM,(colIndex+1)*cellWidthBM,(rowIndex+1)*cellHeightBM),paint);

        }



        v.invalidate();

        //Toast.makeText(context,"-",Toast.LENGTH_LONG ).show();
        return true;
    }
}
