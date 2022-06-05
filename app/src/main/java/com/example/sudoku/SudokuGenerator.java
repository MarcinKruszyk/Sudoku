package com.example.sudoku;

public class SudokuGenerator {
    int n=9;
    int[][] mat = new int[n][n];
    int s=3;
    int missingDigits;

    SudokuGenerator(int missingDigits) {
        this.missingDigits=missingDigits;
        fillValues();
    }

    public void fillValues() {
        fillDiagonal();
        fillRemaining(0, s);
        removeKDigits();
    }

    void fillDiagonal() {
        for (int i=0; i<n; i=i+s)
            fillArea(i, i);
    }

    boolean unUsedInBox(int rowStart, int colStart, int value) {
        for (int i = 0; i<s; i++)
            for (int j = 0; j<s; j++)
                if (mat[rowStart+i][colStart+j]==value)
                    return false;
        return true;
    }

    void fillArea(int row,int col) {
        int value;
        for (int i=0; i<s; i++) {
            for (int j=0; j<s; j++) {
                do {
                    value = randomGenerator(n);
                }
                while (!unUsedInBox(row, col, value));
                mat[row+i][col+j] = value;
            }
        }
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random()*num+1));
    }

    boolean CheckIfSafe(int i,int j,int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%s, j-j%s, num));
    }

    boolean unUsedInRow(int i,int num) {
        for (int j = 0; j<n; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    boolean unUsedInCol(int j,int num) {
        for (int i = 0; i<n; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j) {
        if (j>=n && i<n-1) {
            i = i + 1;
            j = 0;
        }
        if (i>=n && j>=n)
            return true;
        if (i < s) {
            if (j < s)
                j = s;
        }
        else if (i < n-s) {
            if (j==(int)(i/s)*s)
                j =  j + s;
        }
        else {
            if (j == n-s) {
                i = i + 1;
                j = 0;
                if (i>=n)
                    return true;
            }
        }
        for (int num = 1; num<=n; num++) {
            if (CheckIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;
                mat[i][j] = 0;
            }
        }
        return false;
    }

    public void removeKDigits() {
        int count = missingDigits;
        while (count != 0)
        {
            int cellId = randomGenerator(n*n)-1;
            int i = (cellId/n);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;
            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    public int[] getSudoku() {
        int[] array = new int[81];
        for(int i = 0; i < n; i ++) {
            System.arraycopy(mat[i], 0, array, (i * mat.length), n);
        }
        return array;
    }
}
