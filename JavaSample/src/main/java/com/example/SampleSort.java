package com.example;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleSort {

    /**
     * コレクションのソートのサンプル.
     * <p>
     * ウォームアップなどしてないので性能は参考程度
     * </p>
     * @param args
     */
    public static void main(String... args) {

        SampleSort sample = new SampleSort();

        // ランダムデータの準備
        List<DataSet> sampleDsList = sample.randomDataSet(10);
        List<DataSet> mediumDsList = sample.randomDataSet(1_000);
        List<DataSet> largeDsList1 = sample.randomDataSet(100_000);
        List<DataSet> largeDsList2 = sample.randomDataSet(100_000);
        List<DataSet> largeDsList3 = sample.randomDataSet(100_000);

        //
        // ソート機能 比較
        //
        System.out.println("<<-- ソート機能 比較 ->>\r\n");

        // List.sort()
        System.out.println("*** List.sort() ***");
        sample.sample_ListSort(new ArrayList<>(sampleDsList), true);
        sample.sample_ListSort(new ArrayList<>(mediumDsList), false);
        sample.sample_ListSort(new ArrayList<>(largeDsList1), false);
        sample.sample_ListSort(new ArrayList<>(largeDsList2), false);
        sample.sample_ListSort(new ArrayList<>(largeDsList3), false);
        System.out.println();

        // StreamAPI sorted
        System.out.println("*** StreamAPI sorted ***");
        sample.sample_StreamSorted(new ArrayList<>(sampleDsList), true);
        sample.sample_StreamSorted(new ArrayList<>(mediumDsList), false);
        sample.sample_StreamSorted(new ArrayList<>(largeDsList1), false);
        sample.sample_StreamSorted(new ArrayList<>(largeDsList2), false);
        sample.sample_StreamSorted(new ArrayList<>(largeDsList3), false);
        System.out.println();

        // 比較用 Collections.sort()
        System.out.println("*** 比較用 Collections.sort() ***");
        sample.sample_CollectionsSort(new ArrayList<>(sampleDsList), true);
        sample.sample_CollectionsSort(new ArrayList<>(mediumDsList), false);
        sample.sample_CollectionsSort(new ArrayList<>(largeDsList1), false);
        sample.sample_CollectionsSort(new ArrayList<>(largeDsList2), false);
        sample.sample_CollectionsSort(new ArrayList<>(largeDsList3), false);
        System.out.println();
        System.out.println();

        //
        // ソート機能 処理比較
        //
        System.out.println("<<-- ソート機能 処理比較 -->>\r\n");

        // Comparator.comparing()
        System.out.println("*** Comparator.comparing() ***");
        sample.sample_ComparatorComparing(new ArrayList<>(sampleDsList), true);
        sample.sample_ComparatorComparing(new ArrayList<>(mediumDsList), false);
        sample.sample_ComparatorComparing(new ArrayList<>(largeDsList1), false);
        sample.sample_ComparatorComparing(new ArrayList<>(largeDsList2), false);
        sample.sample_ComparatorComparing(new ArrayList<>(largeDsList3), false);
        System.out.println();

        // 比較用自前ソート
        System.out.println("*** 比較用自前ソート ***");
        sample.sample_OneselfSort(new ArrayList<>(sampleDsList), true);
        sample.sample_OneselfSort(new ArrayList<>(mediumDsList), false);
        sample.sample_OneselfSort(new ArrayList<>(largeDsList1), false);
        sample.sample_OneselfSort(new ArrayList<>(largeDsList2), false);
        sample.sample_OneselfSort(new ArrayList<>(largeDsList3), false);
        System.out.println();

    }

    /** ランダムデータ生成 */
    private List<DataSet> randomDataSet(int size) {
        List<DataSet> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            DataSet ds = new DataSet();
            ds.setValue1("" + ((int) (Math.random() * 10)));
            ds.setValue2("" + ((int) (Math.random() * 10)) + ((int) (Math.random() * 10)));
            ds.setValue3(
                    "" + ((int) (Math.random() * 10)) + ((int) (Math.random() * 10)) + ((int) (Math.random() * 10)));
            list.add(ds);
        }
        return list;
    }

    /** java8~ List.sort() */
    private void sample_ListSort(List<DataSet> list, boolean print) {
        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // ソート処理
        list.sort(comparing(DataSet::getValue1).thenComparing(DataSet::getValue2).thenComparing(DataSet::getValue3));

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " ソート順：value1, value2, value3");
            System.out.println(method + " 結果 : " + list.toString());
        }
    }

    /** java8~ StreamAPI Sorted */
    private void sample_StreamSorted(List<DataSet> list, boolean print) {
        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // ソート処理
        List<DataSet> sortedList = list.stream()
                .sorted(comparing(DataSet::getValue1).thenComparing(DataSet::getValue2)
                        .thenComparing(DataSet::getValue3))
                .collect(toList());

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " ソート順：value1, value2, value3");
            System.out.println(method + " 結果 : " + sortedList.toString());
        }
    }

    /** Sort 比較用 Collections.sort() */
    private void sample_CollectionsSort(List<DataSet> list, boolean print) {
        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // ソート処理
        Collections.sort(list,
                comparing(DataSet::getValue1).thenComparing(DataSet::getValue2).thenComparing(DataSet::getValue3));

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " ソート順：value1, value2, value3");
            System.out.println(method + " 結果 : " + list.toString());
        }
    }

    /** java8~ Comparator.comparing() */
    private void sample_ComparatorComparing(List<DataSet> list, boolean print) {
        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // ソート処理
        list.sort(comparing(DataSet::getValue1).reversed().thenComparing(DataSet::getValue3)
                .thenComparing(DataSet::getValue2));

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " ソート順：value1 降順, value3, value2");
            System.out.println(method + " 結果 : " + list.toString());
        }
    }

    /** 比較用自前ソート */
    private void sample_OneselfSort(List<DataSet> list, boolean print) {
        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // ソート処理
        list.sort((o1, o2) -> {
            int ret = o2.getValue1().compareTo(o1.getValue1());
            if (ret == 0) {
                ret = o1.getValue3().compareTo(o2.getValue3());
                if (ret == 0) {
                    ret = o1.getValue2().compareTo(o2.getValue2());
                }
            }
            return ret;
        });

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " ソート順：value1 降順, value3, value2");
            System.out.println(method + " 結果 : " + list.toString());
        }
    }

    /** ソート対象オブジェクト */
    public static class DataSet {
        private String value1;
        private String value2;
        private String value3;

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        @Override
        public String toString() {
            return "\r\n    DataSet[ value1:" + value1 + ", value2:" + value2 + ", value3:" + value3 + " ]";
        }
    }

}