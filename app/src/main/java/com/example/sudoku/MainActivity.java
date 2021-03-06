package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private class Cell {
        boolean fixed;
        int value;
        Button bt;

        public Cell(int initvalue, Context THIS){
            value = initvalue;
            if (value!=0) fixed = true;
            else fixed = false;
            bt = new Button(THIS);
            if (fixed) bt.setText(String.valueOf(value));
            else bt.setTextColor(Color.RED);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fixed) return;
                    value++;
                    if (value>9) value=1;
                    bt.setText(String.valueOf(value));
                    if (isCorrect()){
                        isCorrect.setText("");
                        if (!isCompleted()){
                            isCompleted.setText("");
                        }
                        else {
                            isCompleted.setText("Rozwiązałeś Sudoku!");
                        }
                    }
                    else {
                        isCorrect.setText("Błąd w rozwiązaniu");
                    }
                }
            });
        }
    }

    boolean isCompleted() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (table[i][j].value==0)
                    return false;
                return true;
    }

    boolean isAreaCorrect(int i1, int j1, int i2, int j2){
        boolean[] seen=new boolean[10];
        for (int i=1;i<=9;i++) seen[i]=false;
        for (int i=i1;i<i2;i++) {
            for (int j=j1;j<j2;j++){
                int value=table[i][j].value;
                if (value!=0) {
                    if (seen[value]) return false;
                    seen[value]=true;
                }
            }
        }
        return true;
    }

    boolean isCorrect(){
        for (int i=0;i<9;i++)
            if (!isAreaCorrect(i,0,i+1,9))
                return false;
            for(int j=0;j<9;j++)
                if(!isAreaCorrect(0,j+1,9,j)) return false;
                for (int i=0;i<3;i++)
                    for(int j=0;j<3;j++)
                        if(!isAreaCorrect(3*i,3*j,3*i+3,3*j+3))
                            return false;
                        return true;
    }

    Cell [] [] table;
    int [] generatedSudoku;
    TableLayout tl;
    TextView isCorrect;
    TextView isCompleted;
    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SudokuGenerator gen=new SudokuGenerator(18);
        generatedSudoku = gen.getSudoku();
        table=new Cell[9][9];
        tl=new TableLayout(this);
        for (int i=0; i<9; i++) {
            TableRow tr=new TableRow(this);
            for (int j=0; j<9;j++) {
                int s=generatedSudoku[i*9+j];
                table[i][j]=new Cell(s,this);
                tr.addView(table[i][j].bt);
            }
            tl.addView(tr);
        }
        tl.setShrinkAllColumns(true);
        tl.setStretchAllColumns(true);
        isCorrect=new TextView(this);
        isCompleted=new TextView(this);
        linLayout=new LinearLayout(this);
        linLayout.addView(tl);
        linLayout.addView(isCorrect);
        linLayout.addView(isCompleted);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linLayout);
    }
}