package edu.spbu;

import java.util.*;
import java.io.*;


public class SparseMatrix  implements Matrix {

    public int size;
    public Map<Integer, row> map;

    public SparseMatrix(Map<Integer, row> m, int size) {
        this.size = size;
        this.map = m;
    }


    public SparseMatrix(int size) {
        this.size = size;
        this.map = new HashMap<>();
    }

    public SparseMatrix(BufferedReader s) {
        try {

            String temp = s.readLine();
            String[] arr = temp.split(" ");
            int k = arr.length;
            double number;
            size = k;
            map = new HashMap<Integer, row>();
            row tmap = new row();

            for (int j = 0; j < size; j++) {
                number = Double.parseDouble(arr[j]);
                if (number != 0.0) {
                    tmap.put(j, number);
                }
            }
            if (tmap != null) {
                map.put(0, tmap);
            }


            for (int i = 1; i < size; i++) {

                temp = s.readLine();
                arr = temp.split(" ");
                tmap = new row();
                for (int j = 0; j < size; j++) {
                    number = Double.parseDouble(arr[j]);
                    if (number != 0.0) {
                        tmap.put(j, number);
                    }
                }
                if (tmap != null) {
                    map.put(i, tmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Matrix mul(Matrix other) {
        if (other instanceof SparseMatrix) return this.mulSparseSparse((SparseMatrix) other);
        else return this.mulSparseDence((DenseMatrix) other);
    }

    public SparseMatrix MatrixSTrans() {
        Iterator<Map.Entry<Integer, row>> iter = map.entrySet().iterator(); // получаем строки
        HashMap<Integer, row> matrixTr = new HashMap<Integer, row>();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            Integer keyRow = (Integer) entry.getKey();// получаем номер строки
            HashMap<Integer, row> value = (HashMap<Integer, row>) entry.getValue(); // получаем элементы определенной строки
            Iterator iterRow = value.entrySet().iterator(); // берем по элементно
            while (iterRow.hasNext()) {
                row RowTr = new row();
                Map.Entry entryRow = (Map.Entry) iterRow.next();
                Integer keyElements = (Integer) entryRow.getKey();
                Double valueElements = (Double) entryRow.getValue();
                RowTr = matrixTr.get(keyElements);
                if (RowTr == null) {
                    RowTr = new row();
                }
                RowTr.put(keyRow, valueElements);
                matrixTr.put(keyElements, RowTr);
            }

        }
        return new SparseMatrix(matrixTr, size);
    }

    public SparseMatrix mulSparseSparse(SparseMatrix other) {

        SparseMatrix resS = new SparseMatrix(size);
        Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();
        other = other.MatrixSTrans();
        while (iter1.hasNext()) {
            Map.Entry entry1 = iter1.next();
            Integer key1 = (Integer) entry1.getKey();
            HashMap<Integer, Double> value1 = (HashMap<Integer, Double>) entry1.getValue();// строки первой матрицы
            row resRow = new row();
            Iterator<Map.Entry<Integer, row>> iter2 = other.map.entrySet().iterator();
            while (iter2.hasNext()) {
                Map.Entry entry2 = iter2.next();
                Integer key2 = (Integer) entry2.getKey();
                HashMap<Integer, Double> value2 = (HashMap<Integer, Double>) entry2.getValue();// строки второй матрицы
                Iterator iterElement = value1.entrySet().iterator();
                double resValue = 0;
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement1 = (Integer) entryElement.getKey();
                    Double valueElement1 = (Double) entryElement.getValue();
                    if (value2.get(keyElement1) != null) {
                        double a = value2.get(keyElement1);
                        resValue = resValue + valueElement1 * a;
                    }
                }
                if (resValue != 0) {
                    resRow.put(key2, resValue);
                }
            }
            if (resRow != null) {
                resS.map.put(key1, resRow);
            }
        }
        return resS;
    }

    public SparseMatrix mulSparseDence(DenseMatrix other) {
        SparseMatrix res = new SparseMatrix(size);
        other = other.MatrixSTrans();
        double[][] a = other.matrix;
        Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();// итератор спарс матрицы
        while (iter1.hasNext()) {
            Map.Entry entry1 = iter1.next();
            Integer key1 = (Integer) entry1.getKey();
            HashMap<Integer, Double> value1 = (HashMap<Integer, Double>) entry1.getValue();// получаем определенную строку
            row resRow = new row();
            for (int i = 0; i < size; i++) {
                double resValue = 0.0;
                Iterator iterElement = value1.entrySet().iterator(); // получаем элементы определенной строки
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement = (Integer) entryElement.getKey();// столбец элемента
                    Double valueElement = (Double) entryElement.getValue();// сам элемент
                    if (other.matrix[i][keyElement] != 0.0) {
                        resValue = resValue + valueElement * a[i][keyElement];
                    }
                }
                if (resValue != 0.0) {
                    resRow.put(i, resValue);
                }
            }
            if (resRow != null) {
                res.map.put(key1, resRow);
            }

        }

        return res;
    }


    public void mapOut(BufferedWriter sp) {
        try {
            double e;
            for (int i = 0; i < size; i++) {
                row a = map.get(i);
                if (a != null) {
                    for (int j = 0; j < size; j++) {
                        if (a.get(j) != null) {
                            e = a.get(j);
                            sp.write(e + " ");
                        } else {
                            sp.write("0.0" + " ");
                        }
                    }
                    sp.write("\n");

                } else {
                    for (int j = 0; j < size; j++) {
                        sp.write("0.0 ");
                    }
                    sp.write("\n");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public boolean equals(SparseMatrix other) {
        boolean t = true;
        for (int i = 0; i < size; i++) {
            row a = this.map.get(i);
            row b = other.map.get(i);
            for (int j = 0; j < size; j++) {
                if (a.get(j) != b.get(j)) {
                    t = false;
                }
            }
        }
        return t;
    }
}