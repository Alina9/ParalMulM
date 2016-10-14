package edu.spbu;

import java.io.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;

public class MatrixTest {
    BufferedReader s;
    SparseMatrix ASparse1;
    DenseMatrix ADense1;
    SparseMatrix ASparse2;
    DenseMatrix ADense2;
    SparseMatrix result;
    DenseMatrix result1;



public MatrixTest() {
    try {
        s = new BufferedReader(new FileReader("src/tests/test1.txt"));
        ASparse1 = new SparseMatrix(s);
        s = new BufferedReader(new FileReader("src/tests/test1.txt"));
        ADense1 = new DenseMatrix(s);

        s = new BufferedReader(new FileReader("src/tests/test2.txt"));
        ASparse2 = new SparseMatrix(s);
        s = new BufferedReader(new FileReader("src/tests/test2.txt"));
        ADense2 = new DenseMatrix(s);

        s = new BufferedReader(new FileReader("src/tests/test_result.txt"));
        result = new SparseMatrix(s);
        s = new BufferedReader(new FileReader("src/tests/test_result.txt"));
        result1 = new DenseMatrix(s);
    } catch (IOException e)

    {
        e.printStackTrace();
    }
}


        @Test
        public void mulS_D () {
        SparseMatrix s_d = (SparseMatrix) ASparse1.mul(ADense2);
        result.equals(s_d);
    }

        @Test
        public void mulS_S () {
        SparseMatrix s_s = (SparseMatrix) ASparse1.mul(ASparse2);
        result.equals(s_s);;

    }

       @Test
        public void mulD_S () {
        SparseMatrix d_s = (SparseMatrix) ADense1.mul(ASparse2);
           result.equals(d_s);;

    }
        @Test
        public void mulD_D () {
        DenseMatrix d_d = (DenseMatrix) ADense1.mul(ADense2);
        result1.equals(d_d);;

    }


    }

