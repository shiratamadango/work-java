package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * FunctionalInterface の学習.
 */
public class ExFunction {

    /**
     * <p>
     * Java8 から使用できる {@link FunctionalInterface} を使う。<br>
     * <br>
     * ・総称型についてわかること。<br>
     * ・ラムダ式がわかること。<br>
     * </p>
     */
    public static void main(String[] args) {

        // XXX 注意：
        //    ここにある例のように、関数型インターフェースを変数定義するような使い方は殆どされない。
        //    StreamAPIなどと合わせて使うのが一般的。（多分）

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX Function<T, R>
        //    Tを引数にRを返却する実装をする。
        //    T:String, R:Integer の例.
        //
        System.out.println("<<Function>>");
        Function<String, Integer> func1 = s -> s.length();

        System.out.println("この実装は与えられた文字の文字数を返却するものです");
        System.out.println("func1-1 : " + func1.apply("与えられた文字の文字数を返却します"));
        System.out.println("func1-2 : " + func1.apply("あいうえお"));

        System.out.println("\r\n<<Function>> ラムダ式を使わない場合");
        Function<String, Integer> func1_not_lambda = new Function<String, Integer>() {

            @Override
            public Integer apply(String s) {
                return s.length();
            }

        };

        System.out.println("この実装は与えられた文字の文字数を返却するものです");
        System.out.println("func1-1 not lambda : " + func1_not_lambda.apply("与えられた文字の文字数を返却します"));
        System.out.println("func1-2 not lambda : " + func1_not_lambda.apply("あいうえお"));

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX BiFunction<T, U, R>
        //    T, Uを引数にRを返却する実装をする。
        //    T:Integer, U:String, R:List<String> の例.
        //
        System.out.println("\r\n<<BiFunction>>");
        BiFunction<Integer, String, List<String>> func2 = (i, s) -> {

            List<String> list = new ArrayList<>();

            for (int k = 1; k <= i; k++) {
                list.add(s + k);
            }

            return list;
        };

        System.out.println("func2-1 : " + func2.apply(3, "この実装は与えられた数分の与えられた文字＋連番のリストを返却するものです"));
        System.out.println("func2-2 : " + func2.apply(5, "リストデータ"));

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX UnaryOperator<T>
        //    Tを引数にTを返却する実装をする。
        //    T:Integer の例.
        //
        System.out.println("\r\n<<UnaryOperator>>");
        UnaryOperator<Integer> func3 = i -> i * 2;

        System.out.println("この実装は与えられた数を2倍にして返却するものです");
        System.out.println("func3-1 : " + func3.apply(10));
        System.out.println("func3-2 : " + func3.apply(123));

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX BinaryOperator<T>
        //    T, Tを引数にTを返却する実装をする。
        //    T:Integer の例.
        //
        System.out.println("\r\n<<BinaryOperator>>");
        BinaryOperator<Integer> func4 = (i1, i2) -> i1 * i2;

        System.out.println("この実装は与えられた数2つを乗算して返却するものです");
        System.out.println("func4-1 : " + func4.apply(12, 34));
        System.out.println("func4-2 : " + func4.apply(123, 345));

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX Supplier<T>
        //    引数なしでTを返却する実装をする。
        //    T:String の例.
        //
        System.out.println("\r\n<<Supplier>>");
        Supplier<String> supplier1 = () -> "この実装で引数なしで実行し、この文字列を返却するものです";
        System.out.println("supplier1 : " + supplier1.get());

        // 現在日時をフォーマットして返却します
        Supplier<String> supplier2 = () -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("supplier2 : " + supplier2.get());

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX Consumer<T>
        //    Tを引数に処理を実行し、何も返却しない実装をする。
        //    T:String の例.
        //
        System.out.println("\r\n<<Consumer>>");
        Consumer<String> consumer1 = s -> System.err.println(s);
        consumer1.accept("consumer1 : この実装は、与えられた文字をシステムエラーに出力するものです。返却値はありません。");

        Consumer<String> consumer2 = System.err::println;
        consumer2.accept("consumer2 : このようなメソッド参照という書き方もできます");

        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
        }
        System.out.println("\r\n***************************************\r\n");

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX @FunctionalInterface
        //
        System.out.println("<<MyFunction>>");
        System.out.println("関数型インターフェースは抽象メソッドが1つのみのインターフェースのことです");
        System.out.println("自作したものも関数型インタフェースとして使えます");

        MyFunction myFunc1 = (f, d, i, l) -> {

            // 様々なNumberクラスのサブクラスを受け取り、リストに入れて昇順ソートして返却
            List<Number> list = Arrays.asList(f, d, i, l);
            list.sort(Comparator.comparingDouble(n -> n.doubleValue()));

            return list;
        };
        System.out.println("myFunc1-1 : " + myFunc1.sample(1.2f, 0.34d, 5, 6l));
        System.out.println("myFunc1-2 : " + myFunc1.sample(12.3f, 45.6d, 70, 8l));

        System.out.println("\r\n***************************************\r\n");

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX @FunctionalInterface アノテーションをつけない場合
        //
        System.out.println("<<MyFunctionNoAnnotation>>");
        System.out.println("@FunctionalInterface アノテーションをつけなくても");
        System.out.println("抽象メソッドが１つのみのインターフェースは関数型インターフェースとして使うことができますが、");
        System.out.println("関数型インターフェースとして定義していることを明確にするため、つけておくことをお勧めします");

        MyFunctionNoAnnotation myFunc2 = (f, d, i, l) -> {

            // 様々なNumberクラスのサブクラスを受け取り、リストに入れて降順ソートして返却
            List<Number> list = Arrays.asList(f, d, i, l);
            list.sort(Comparator.<Number> comparingDouble(n -> n.doubleValue()).reversed());

            return list;
        };

        System.out.println("myFunc2-1 : " + myFunc2.sample(1.2f, 0.34d, 5, 6l));
        System.out.println("myFunc2-2 : " + myFunc2.sample(12.3f, 45.6d, 70, 8l));

        System.out.println("\r\n***************************************\r\n");

        ////////////////////////////////////////////////////////////////////////////////////////
        //
        // XXX @FunctionalInterface defaultメソッドを持つ場合
        //
        System.out.println("<<MyFunctionDefault>>");
        MyFunctionDefault myFunc3 = (f, d, i, l) -> {

            // 様々なNumberクラスのサブクラスを受け取り、リストに入れて返却
            return Arrays.asList(f, d, i, l);
        };

        System.out.println("myFunc3-1 : " + myFunc3.sample(1.2f, 0.34d, 5, 6l));
        System.out.println("myFunc3-2 : " + myFunc3.sample(12.3f, 45.6d, 70, 8l));

        // デフォルトメソッドを実行する。
        myFunc3.process();

    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // 内部クラスなのでstatic修飾子つけてます。

    /**
     * @FunctionalInterface 実装例
     */
    @FunctionalInterface
    static interface MyFunction {

        List<Number> sample(Float f, Double d, Integer i, Long l);
    }

    /**
     * @FunctionalInterface 実装例, アノテーションなし
     */
    static interface MyFunctionNoAnnotation {

        List<Number> sample(Float f, Double d, Integer i, Long l);
    }

    /**
     * @FunctionalInterface 実装例, defaultメソッドあり
     */
    @FunctionalInterface
    static interface MyFunctionDefault {

        List<Number> sample(Float f, Double d, Integer i, Long l);

        default void process() {

            System.out.println("このような実装されたメソッド（デフォルトメソッド）をインターフェースに書くことができます");
            System.out.println("デフォルトメソッドであれば、関数型インターフェースの要件を損ないません");
        }
    }

}
