package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * 総称型 の学習.
 */
public class ExGenerics {

    /**
     * <p>
     * 総称型 を使う。<br>
     * </p>
     */
    public static void main(String[] args) {

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型 のリスト の学習
        //
        //////////////////////////////////////////////////////////////////
        // Number または Numberの実装クラス（サブクラス）を追加可能。
        // Numberとしての機能を持っているクラスであれば追加できるということ。
        // 取り出すときは、Number型で取り出し、サブクラスとしての機能を使うためにはキャストが必要。
        List<Number> numList1 = new ArrayList<>();
        numList1.add(new Integer(1));
        numList1.add(new Long(2l));
        numList1.add(new Float(3.33f));
        numList1.add(new Double(4.44d));
        //numList1.add(new Object()); // Objectは追加できません

        // Number 型で取得できます。
        for (Number num : numList1) {
            System.out.println(num);
        }

        // numList1には総称型がNumberのListのサブクラスしか格納できません。
        //numList1 = new ArrayList<Object>(); // コンパイルエラー
        //numList1 = new ArrayList<Integer>(); // コンパイルエラー
        numList1 = new ArrayList<Number>(); // OK
        numList1 = new LinkedList<Number>(); // OK
        numList1 = new Vector<Number>(); // OK

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型 のリスト <? extends Number> の学習
        //
        //////////////////////////////////////////////////////////////////
        List<? extends Number> numList2 = new ArrayList<>();

        // <? extends Number> は Numberとそのサブクラスのリストを格納できます。
        numList2 = new ArrayList<Number>();
        numList2 = new ArrayList<Integer>();
        numList2 = new ArrayList<Double>();

        // このリストに要素を追加することはできません。
        //numList2.add(new Double(1d)); //エラー
        //numList2.add((Number) new Double(1d)); //キャストしてもエラー
        //numList2.add(new Object()); //もちろんObjectだってダメ

        // なぜなら、numList2の実体の型を特定できないからです。
        // numList2 の実体が ArrayList<Integer> であれば、このリストには Integer しか追加できません。
        // Integer 以外の Numberのサブクラスは、Integerとして振舞うことができないからです。
        // numList2 の実体が ArrayList<Double> であれば、このリストには Double しか追加できません。
        // Double 以外の Numberのサブクラスは、Doubleとして振舞うことができないからです。
        // そのため、<? extens Number> のような総称型リストには何も追加できないのです。

        numList2 = Arrays.asList(new Integer(1));

        // 取り出すときは、Number型になります。
        // Number型～そのサブクラスであることが保証されているので、Number型で取り出せます。
        numList2.get(0);

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型 のリスト <? super Number> の学習
        //
        //////////////////////////////////////////////////////////////////
        List<? super Number> numList3 = new ArrayList<>();

        // <? super Number> は Object～Number型 までのリストしか作れません。
        numList3 = new ArrayList<Object>();
        numList3 = new ArrayList<Number>();
        //numList3 = new ArrayList<Integer>(); // コンパイルエラー
        //numList3 = new ArrayList<Double>(); // コンパイルエラー

        // numList3には、Number型とそのサブクラスしか追加できません。
        // Integerは Object,Numberどちらにも継承関係があるので、それらの機能を備えています。
        // Objectは、Numberの機能を備えていません。
        // numList3の実体が何であっても、総称型クラスと同じふるまいができるのは、Numberとそのサブクラスだけです。
        // そのため、Number型とそのサブクラスしか追加できないのです。
        numList3.add(new Integer(1));
        numList3.add(new Long(2l));
        //numList3.add(new Object()); // コンパイルエラー

        // 取り出すときは、Object型になります。
        // Object～Number型のリストなので、numList3の実体がObjectであればNumber型として振舞うことはできません。
        // そのため、取り出すときは必ずObject型になります。
        numList3.get(0);

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型(型パラメータ)メソッド実装例の使用例1
        //
        //////////////////////////////////////////////////////////////////
        // 任意の型を引数にできるメソッド
        System.out.println("\r\n＊＊＊＊＊\r\n＊＊＊＊＊");
        System.out.println("//// 総称型(型パラメータ)メソッド実装例の使用例1  :: 任意の型を引数にできるメソッド ////");
        System.out.println("＊＊＊＊＊\r\n＊＊＊＊＊");
        method1(new Integer(1));
        method1("METHOD1");
        method1(LocalDateTime.now());

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型(型パラメータ)メソッド実装例の使用例2
        //
        //////////////////////////////////////////////////////////////////
        // 任意の型を引数にでき、引数と同じ型で受け取れるメソッド
        System.out.println("\r\n＊＊＊＊＊\r\n＊＊＊＊＊");
        System.out.println("//// 総称型(型パラメータ)メソッド実装例の使用例2  :: 任意の型を引数にでき、引数と同じ型で受け取れるメソッド ////");
        System.out.println("＊＊＊＊＊\r\n＊＊＊＊＊");
        Integer[] array1 = method2(new Integer(1), new Integer(2), new Integer(3));
        String[] array2 = method2("METHOD2-1");
        LocalDateTime[] array3 = method2(LocalDateTime.now(), LocalDateTime.now().plusDays(1l));
        System.out.println("   return " + Arrays.toString(array1));
        System.out.println("   return " + Arrays.toString(array2));
        System.out.println("   return " + Arrays.toString(array3));

        //////////////////////////////////////////////////////////////////
        //
        // XXX 総称型クラス実装例の使用例
        //
        //////////////////////////////////////////////////////////////////
        System.out.println("\r\n＊＊＊＊＊\r\n＊＊＊＊＊");
        System.out.println("//// 総称型クラス実装例の使用例 ////");
        System.out.println("＊＊＊＊＊\r\n＊＊＊＊＊");

        GeneSample<Dog> dog = new GeneSample<>();
        GeneSample<Cat> cat = new GeneSample<>();
        GeneSample<Rabbit> rabbit = new GeneSample<>();
        GeneSample<?> unknown = new GeneSample<>();

        dog.welcome(new Dog(), "わんこ");
        cat.welcome(new Cat(), "にゃんこ");
        rabbit.welcome(new Rabbit(), "うさうさ");
        // unknown is not yet welcome.

        System.out.println("   return Love=" + dog.makeLove().getLove());
        System.out.println("   return Love=" + cat.makeLove().getLove());
        System.out.println("   return Love=" + rabbit.makeLove().getLove());
        System.out.println("   return " + unknown.makeLove());

    }

    //XXX////////////////////////////////////////////////////////////////
    /**
     * 総称型(型パラメータ)メソッドの実装例<br>
     * 任意の型Tを引数に処理を行うメソッド
     *
     * @param param
     */
    public static <E> void method1(final E param) {

        System.out.println("method1 : " + param);
    }

    //XXX////////////////////////////////////////////////////////////////
    /**
     * 総称型(型パラメータ)メソッドの実装例<br>
     * 任意の型Tを引数に配列にして返却するメソッド
     *
     * @param param
     * @return
     */
    @SafeVarargs
    public static <E> E[] method2(final E... param) {

        System.out.println("method2 : " + Arrays.toString(param));

        return param;
    }

    //XXX////////////////////////////////////////////////////////////////
    /**
     * 総称型クラスの実装例<br>
     *
     * 任意の型T を 指定してインスタンスを生成できるクラス<br>
     * 任意の型T は Animalクラスを継承または実装している必要がある<br>
     * ※内部クラスなので、static修飾子がついている<br>
     *
     * @param <T>
     */
    public static class GeneSample<T extends Animal> {

        private T myFamily = null;

        /**
         * XXX 任意の動物を家族に迎え入れる
         *
         * @param animal
         * @param name
         */
        public void welcome(T animal, String name) {
            myFamily = animal;
            myFamily.giveName(name);
        }

        /**
         * XXX 可愛がるメソッド
         * <br><br>
         * 名前を呼んで、ご飯をあげて、なでます。<br>
         * すると、Loveが返ってきます。
         *
         * @return Love
         */
        public Love makeLove() {

            if (myFamily == null) {
                System.out.println("Call GeneSample.makeLove : I have no family.");
                return null;
            }

            System.out.println("Call GeneSample.makeLove : " + myFamily);

            myFamily.callName();
            myFamily.giveFood();
            myFamily.stroke();
            myFamily.getLove();

            return myFamily.getLove();
        }
    }

    interface Animal {

        Love giveName(String name);

        Love callName();

        Love giveFood();

        Love stroke();

        Love getLove();

    }

    public static class Dog implements Animal {

        private Love love = new Love();

        private String name = "nameless";

        @Override
        public Love giveName(String name) {
            this.name = name;
            love.incrementLove(100);
            return love;
        }

        @Override
        public Love callName() {
            love.incrementLove(5);
            return love;
        }

        @Override
        public Love giveFood() {
            love.incrementLove(5);
            return love;
        }

        @Override
        public Love stroke() {
            love.incrementLove(7);
            return love;
        }

        @Override
        public Love getLove() {
            return love;
        }

        @Override
        public String toString() {
            return "Dog ," + name + ", Love " + love.getLove();
        }

    }

    public static class Cat implements Animal {

        private Love love = new Love();
        private String name = "nameless";

        @Override
        public Love giveName(String name) {
            this.name = name;
            love.incrementLove(100);
            return love;
        }

        @Override
        public Love callName() {
            love.incrementLove(3);
            return love;
        }

        @Override
        public Love giveFood() {
            love.incrementLove(5);
            return love;
        }

        @Override
        public Love stroke() {
            love.incrementLove(2);
            return love;
        }

        @Override
        public Love getLove() {
            return love;
        }

        @Override
        public String toString() {
            return "Cat ," + name + ", Love " + love.getLove();
        }
    }

    public static class Rabbit implements Animal {

        private Love love = new Love();
        private String name = "nameless";

        @Override
        public Love giveName(String name) {
            this.name = name;
            love.incrementLove(100);
            return love;
        }

        @Override
        public Love callName() {
            love.incrementLove(3);
            return love;
        }

        @Override
        public Love giveFood() {
            love.incrementLove(5);
            return love;
        }

        @Override
        public Love stroke() {
            love.incrementLove(3);
            return love;
        }

        @Override
        public Love getLove() {
            return love;
        }

        @Override
        public String toString() {
            return "Rabbit ," + name + ", Love " + love.getLove();
        }
    }

    public static class Love {

        int love = 0;

        void incrementLove() {
            love++;
        }

        void incrementLove(int love) {
            this.love += love;
        }

        int getLove() {
            return love;
        }
    }

}
