package edu.spbu;

import java.io.*;

public class MatrixMain {
    public static void main(String[] args) {


        try {
            BufferedReader s = new BufferedReader(new FileReader("src/main/in1.txt"));
            SparseMatrix matrixASparse = new SparseMatrix(s);

            s = new BufferedReader(new FileReader("src/main/in2.txt"));
            SparseMatrix matrixBSparse = new SparseMatrix(s);

            s = new BufferedReader(new FileReader("src/main/in3.txt"));
            DenseMatrix matrixADense = new DenseMatrix(s);

            s = new BufferedReader(new FileReader("src/main/in4.txt"));
            DenseMatrix matrixBDense = new DenseMatrix(s);


            SparseMatrix s_s = (SparseMatrix) matrixASparse.mul(matrixBSparse);
            BufferedWriter sp = new BufferedWriter(new FileWriter(("src/main/SparseSparse.txt")));
            s_s.mapOut(sp);
            sp.close();


            DenseMatrix d_d = (DenseMatrix) matrixADense.mul(matrixBDense);
            BufferedWriter dn = new BufferedWriter(new FileWriter(("src/main/DenseDense.txt")));
            d_d.matOut(dn);
            dn.close();

            SparseMatrix d_s = (SparseMatrix) matrixADense.mul(matrixBSparse);
            sp = new BufferedWriter(new FileWriter(("src/main/DenseSparse.txt")));
            d_s.mapOut(sp);
            sp.close();

            SparseMatrix s_d = (SparseMatrix) matrixASparse.mul(matrixBDense);
            sp = new BufferedWriter(new FileWriter(("src/main/SparseDense.txt")));
            s_d.mapOut(sp);
            sp.close();


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
