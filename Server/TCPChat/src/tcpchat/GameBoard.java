/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

/**
 *
 * @author SONY
 */
public class GameBoard {
    private int[][] board;
   
  

   private int colQty;
   private int rowQty;
   // private int bitmapWidth;
   // private int bitmapHeight;

    public GameBoard(int rowQty, int colQty) {
        this.colQty=colQty;
        this.rowQty=rowQty;
        board=new int[colQty][colQty];
        for (int i=0; i<rowQty;i++){
            for (int j=0; j<colQty;j++)
            this.board[i][j] = -1;
    }
    }
   
   
    public String check(String string,int  player){
        int index=Integer.parseInt(string);
        int rowIndex=(int) index/10;
        int colIndex= (int) index%10;
       if (board[rowIndex][colIndex]!=-1) return "false";
        board[rowIndex][colIndex] = player;
        String kqString= win(rowIndex,colIndex, player);
      if (kqString!="false") return "Win: "+player+ " "+kqString;
     
        return "true";
        
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
        return "false";
    }
    
}
