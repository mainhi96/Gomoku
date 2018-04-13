package com.example.sony.tiktactoehdt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 3/15/2018.
 */

public class ChessBoard {

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


    public ChessBoard(Context context, int colQty, int rowQty, int bitmapWidth, int bitmapHeight) {
        this.context = context;
        this.colQty = colQty;
        this.rowQty = rowQty;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
    }

    public void init(int player) {
        listlines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.DKGRAY);
        board = new int[rowQty][colQty];  //dong/cot


        for (int i = 0; i < rowQty; i++) {
            for (int j = 0; j < colQty; j++)
                board[i][j] = -1;
        }
        // this.player=player;

        int cellWidth = bitmapWidth / colQty;
        int cellHeight = bitmapHeight / rowQty;


        for (int i = 0; i <= colQty; i++) {
            listlines.add(new Line(i * cellWidth, 0, i * cellWidth, bitmapHeight));
        }
        for (int j = 0; j <= rowQty; j++) {
            listlines.add(new Line(0, j * cellHeight, bitmapWidth, j * cellHeight));
        }

        bmCross = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        bmX = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross);
    }

    public Bitmap drawBoard() {
        Line temp;
        for (int i = 0; i < listlines.size(); i++) {
            temp = listlines.get(i);
            canvas.drawLine(temp.getStartX(), temp.getStarY(), temp.getStopX(), temp.getStopY(), paint);
        }

        return this.bitmap;

    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public boolean onTouch(View v, MotionEvent event) {

        //  Toast.makeText(context, v.getX() + "- " + event.getY(),Toast.LENGTH_LONG ).show();
        int cellWidth = v.getWidth() / colQty;
        int cellHeight = v.getHeight() / rowQty;

        int cellWidthBM = bitmapWidth / colQty;
        int cellHeightBM = bitmapHeight / rowQty;

        //     Toast.makeText(context, cellHeightBM+"-"+cellHeightBM,Toast.LENGTH_LONG ).show();


        int colIndex;
        colIndex = (int) (event.getX() / cellWidth);
        int rowIndex;
        rowIndex = (int) (event.getY() / cellHeight);


        //  Toast.makeText(context,rowIndex+"-"+colIndex,Toast.LENGTH_LONG).show();


        if (board[rowIndex][colIndex] != -1) return true;
        board[rowIndex][colIndex] = player;
        Toast.makeText(context, "THANG: " + player + "[" + rowIndex + "-" + colIndex + "]" + win(rowIndex, colIndex, player), Toast.LENGTH_LONG).show();


        if (player == 0) {
            canvas.drawBitmap(bmCross, new Rect(0, 0, bmCross.getWidth(), bmCross.getHeight()), new Rect(colIndex * cellWidthBM, rowIndex * cellHeightBM, (colIndex + 1) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
            player = 1;
        } else {

            canvas.drawBitmap(bmX, new Rect(0, 0, bmX.getWidth(), bmX.getHeight()), new Rect(colIndex * cellWidthBM, rowIndex * cellHeightBM, (colIndex + 1) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
            player = 0;
        }


        v.invalidate();

        //Toast.makeText(context,"-",Toast.LENGTH_LONG ).show();
        return true;
    }



    public String show() {
        String play1 = "Player O: ";
        String play2 = "Player X: ";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) play1 += "[" + i + "-" + j + "]";
                else if (board[i][j] == 1) play2 += "[" + i + "-" + j + "]";
            }
        }
        // TextView tv= (TextView) v.findViewById(R.id.tv);
        return play1 + "/n" + play2;


    }

    public String win(int x, int y, int name) {
        int k, j;
        int d = 0;
        // kt chieu doc
        for (k = -4; k <= 4; k++) {
            if (x + k >= 0 && x + k < 8) {
                if (board[x + k][y] == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return "THANG DOC";
        } else {
            d = 0;
        }
        //xet ngang
        for (k = -5; k <= 5; k++) {
            if (y + k >= 0 && y + k < 8) {
                if (board[x][y + k] == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return "THANG NGANG";
        } else {
            d = 0;
        }
        //cheo
        for (k = -4, j = 4; k <= 4 && j >= -4; k++, j--) {
            if (y + k >= 0 && y + k < 8 && x + j >= 0 && x + j < 8) {
                if (board[x + j][y + k] == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return "THANG CHEO";
        } else {
            d = 0;
        }
        for (k = -4; k <= 4; k++) {
            if (y + k >= 0 && y + k < 8 && x + k >= 0 && x + k < 8) {
                if (board[x + k][y + k] == name) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            return "THANG CHEO";
        }
        return "CHUA THANG";
    }

    public boolean print(String a, int play, View v) {
            int index= Integer.parseInt(a);
            int colIndex=index%10;
            int rowIndex=index/10;

            //  Toast.makeText(context, v.getX() + "- " + event.getY(),Toast.LENGTH_LONG ).show();
            int cellWidth = v.getWidth() / colQty;
            int cellHeight = v.getHeight() / rowQty;

            int cellWidthBM = bitmapWidth / colQty;
            int cellHeightBM = bitmapHeight / rowQty;

            //     Toast.makeText(context, cellHeightBM+"-"+cellHeightBM,Toast.LENGTH_LONG ).show();




            //  Toast.makeText(context,rowIndex+"-"+colIndex,Toast.LENGTH_LONG).show();





            if (play == 0) {
                canvas.drawBitmap(bmCross, new Rect(0, 0, bmCross.getWidth(), bmCross.getHeight()), new Rect(colIndex * cellWidthBM, rowIndex * cellHeightBM, (colIndex + 1) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);
                         }
                else {

                canvas.drawBitmap(bmX, new Rect(0, 0, bmX.getWidth(), bmX.getHeight()), new Rect(colIndex * cellWidthBM, rowIndex * cellHeightBM, (colIndex + 1) * cellWidthBM, (rowIndex + 1) * cellHeightBM), paint);

            }


            v.invalidate();

            //Toast.makeText(context,"-",Toast.LENGTH_LONG ).show();
            return true;
        }
    public String checkWin(int row, int col, int player) {
        int dem = 0;
        while (row - 1 >= 0 && board[row - 1][col] == player) {
            dem++;
            row--;
        }
        while (row + 1 <= 7 && board[row + 1][col] == player) {
            dem++;
            row++;

        }
        if (dem >= 4) return "THANG DOC";
        //check ngang;
          /*  for (int i = 1; i < 4; i++) {
                if (i+col>7) break;
                if (board[row][col + i] == player) count++;
                else {
                    break;
                }
            }
            for (int i = 1; i < 4; i++) {
                if(col-i<0) break;
                if (board[row][col - i] == player) count++;
                else {
                    break;
                }
            }
            if (count>=4) return "THANGNGANG";
          //check doc
            count =0;
        for (int i = 0; i < 4; i++) {
            if (row+i>7) break;
            if (board[row+i][col] == player) count++;
            else {
                break;
            }
        }
        for (int i = 0; i < 4; i++) {
            if(row-i<0) break;
            if (board[row-i][col] == player) count++;
            else {
                break;
            }
        }
        if (count>=4) return "THANGDOC";
        //check cheo phai
        count =0;
        for (int i = 0; i < 4; i++) {
            if ((row-i<0)||(col+i>7)) break;
            if (board[row-i][col + i] == player) count++;
            else {
                break;

            }
        }
        for (int i = 0; i < 4; i++) {
            if ((row+i>7)||(col-i<0)) break;
            if (board[row+i][col - i] == player) count++;
            else {
                break;
            }
        }
        if (count>=4) return "THANGCHEOPHAI";
        //check cheo trai
        count=0;
        for (int i = 0; i < 4; i++) {
            if ((row+i>7)||(col+i>7)) break;
            if (board[row+i][col + i] == player) count++;
            else {
                break;

            }
        }
        for (int i = 0; i < 4; i++) {
            if ((row-i<0)||(col-i<0)) break;
            if (board[row-i][col - i] == player) count++;
            else {
                break;
            }
        }
        if (count>=4) return "THANGCHEOTRAI";*/
        dem = 0;
        while (col - 1 >= 0 && board[row][col - 1] == player) {
            dem++;
            col--;
        }
        while (col + 1 <= 7 && board[row][col + 1] == player) {
            col++;
            dem++;
        }
        if (dem > 4) return "THANG NGANG";
        dem = 0;
        while (col - 1 >= 0 && row + 1 <= 7 && board[row + 1][col - 1] == player) {
            dem++;
            col--;
        }
        while (col + 1 <= 7 && row - 1 >= 0 && board[row - 1][col + 1] == player) {
            col++;
            dem++;
        }
        if (dem > 4) return "THANG CHEO PHAI";

        dem = 0;
        while (col - 1 >= 0 && row - 1 >= 0 && board[row - 1][col - 1] == player) {
            dem++;
            col--;
        }
        while (col + 1 <= 7 && row + 1 <= 7 && board[row + 1][col + 1] == player) {
            col++;
            dem++;
        }
        if (dem > 4) return "THANG CHEO PHAI";


        return "CHUA THANG";
    }
    }

