package com.example.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;
    private boolean gameOver = false;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    int rowLineDrawn = -1;
    int colLineDrawn = -1;
    Boolean diagLine1Drawn = false;
    Boolean diagLine2Drawn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (gameOver) {
            resetBoard();
            return;
        }

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount >= 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
// rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                drawRowLine(i);
                return true;
            }
        }
// columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                drawColLine(i);
                return true;
            }
        }
// diagonal top left to bottom right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            drawDiagLine1();
            return true;
        }
// diagonal top right to bottom left
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            drawDiagLine2();
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText( this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        //resetBoard();
        gameOver = true;
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        //resetBoard();
        gameOver = true;
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        //resetBoard();
        gameOver = true;
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        findViewById(R.id.row_0).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.row_1).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.row_2).setBackgroundColor(Color.TRANSPARENT);

        findViewById(R.id.button_00).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_01).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_02).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_10).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_11).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_12).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_20).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_21).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.button_22).setBackgroundColor(Color.TRANSPARENT);

        findViewById(R.id.board).setBackgroundColor(Color.TRANSPARENT);

        rowLineDrawn = -1;
        colLineDrawn = -1;
        diagLine1Drawn = false;
        diagLine2Drawn = false;

        roundCount = 0;
        player1Turn = true;
        gameOver = false;


    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    private void drawRowLine(int rowNumber) {
        rowLineDrawn = rowNumber;
        String rowID = "row_" + rowNumber;
        int resID = getResources().getIdentifier(rowID,"id", getPackageName());
        LinearLayout row = findViewById(resID);

        Drawable horizontalLine = ContextCompat.getDrawable(getApplicationContext(), R.drawable.horizontal_line);
        row.setBackground(horizontalLine);


    }

    private void drawColLine(int colNumber) {
        colLineDrawn = colNumber;
        String buttonID = "button_0" + colNumber;
        int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
        Button button1 = findViewById(resID);
        buttonID = "button_1"+ colNumber;
        resID = getResources().getIdentifier(buttonID,"id", getPackageName());
        Button button2 = findViewById(resID);
        buttonID = "button_2"+ colNumber;
        resID = getResources().getIdentifier(buttonID,"id", getPackageName());
        Button button3 = findViewById(resID);


        //View board = findViewById(R.id.board);
        Drawable verticalLine = ContextCompat.getDrawable(getApplicationContext(), R.drawable.vertical_line_centre);

        if (verticalLine != null) {
            //board.setBackground(verticalLine);
            button1.setBackground(verticalLine);
            button2.setBackground(verticalLine);
            button3.setBackground(verticalLine);
        }
    }

    private void drawDiagLine1() {
        diagLine1Drawn = true;
        View board = findViewById(R.id.board);
        Drawable diagonalLine1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.diagonal_line_vector_1);
        board.setBackground(diagonalLine1);
    }

    private void drawDiagLine2() {
        diagLine2Drawn = true;
        View board = findViewById(R.id.board);
        Drawable diagonalLine2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.diagonal_line_vector_2);
        board.setBackground(diagonalLine2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putInt("rowLineDrawn", rowLineDrawn);
        outState.putInt("colLineDrawn", colLineDrawn);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putBoolean("gameOver", gameOver);
        outState.putBoolean("diagLine1Drawn", diagLine1Drawn);
        outState.putBoolean("diagLine2Drawn", diagLine2Drawn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount     = savedInstanceState.getInt("roundCount");
        player1Points  = savedInstanceState.getInt("player1Points");
        player2Points  = savedInstanceState.getInt("player2Points");
        rowLineDrawn   = savedInstanceState.getInt("rowLineDrawn");
        colLineDrawn   = savedInstanceState.getInt("colLineDrawn");
        player1Turn    = savedInstanceState.getBoolean("player1Turn");
        gameOver       = savedInstanceState.getBoolean("gameOver");
        diagLine1Drawn = savedInstanceState.getBoolean("diagLine1Drawn");
        diagLine2Drawn = savedInstanceState.getBoolean("diagLine2Drawn");

        if (gameOver) {
            if (rowLineDrawn != -1) {
                drawRowLine(rowLineDrawn);
            } else if (colLineDrawn != -1) {
                drawColLine(colLineDrawn);
            } else if (diagLine1Drawn) {
                drawDiagLine1();
            } else if (diagLine2Drawn) {
                drawDiagLine2();
            }
        }
    }
}
