package com.example;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ValidatingSubSetOfSortedSet {

    /**
     * SortedSet の subSet メソッドの結果を検証.
     *
     * @param args
     */
    public static void main(String[] args) {

        // ソート順  :: first sort " obj.num < obj.num " ,  second sort " obj.str = null < obj.str < obj.str "
        Comparator<MyObj> comparator = Comparator.<MyObj> comparingInt(x -> x.num)
                .thenComparing(Comparator.comparing(x -> x.str, Comparator.nullsFirst(Comparator.naturalOrder())));
        // SortedSet
        SortedSet<MyObj> set = new TreeSet<>(comparator);

        // データ追加
        set.add(new MyObj(1, "1"));
        set.add(new MyObj(1, "2"));
        set.add(new MyObj(2, "1")); // *
        set.add(new MyObj(3, "1")); // *
        set.add(new MyObj(3, "2")); // *
        set.add(new MyObj(3, null)); // *
        set.add(new MyObj(5, null));
        set.add(new MyObj(5, "1"));
        set.add(new MyObj(5, "2"));

        // このセットのfromElement (これを含む) - toElement (これを含まない)の要素範囲を持つ部分のビューを返します。
        SortedSet<MyObj> sub = set.subSet(new MyObj(2, null), new MyObj(5, null));
        sub.forEach(System.out::println);
    }

    /** ソート対象オブジェクト */
    public static class MyObj {
        public int num = 0;
        public String str = null;

        public MyObj(int num, String str) {
            this.num = num;
            this.str = str;
        }

        @Override
        public String toString() {
            return "MyObj: { num:" + num + ", str:" + str + " }";
        }
    }
}
