package com.example;

public class AnonymousClass {

  public static void main(String... args) {

    AnonymousClass main = new AnonymousClass();
    main.example1();
    main.example2();
  }

  // Objectを継承した無名クラスの作成例
  public void example1() {

    System.out.println("//// example1 ////");

    // 無名クラスの実装例１
    Object obj1 = new Object() {

      // この無名クラスは、何もオーバーライドしてないから、
      // Objectと同じ振る舞いしかしないよ。
      // つまり、obj1は無名クラスとして実装する意味がないよ。

    };

    // 無名クラスの実装例２
    Object obj2 = new Object() {

      // 追加メソッド
      public void tukaenai() {

        // 無名クラスにpublicメソッド"tukaenai"を追加したけど
        // 無名クラスだから、このメソッドを外部から実行することはできないね。
        // 無名クラスにメソッドを追加するならprivateでいいね。
        // ※例外として、リフレクション使えば実行できるか？

        System.out.println("publicなのに、外部から呼べないメソッドだよ。tukaenaiね。");
      }

      @Override
      public String toString() {

        // この無名クラスで実装したメソッド"tukaenai"を呼ぶ。
        // 内部からはもちろん実行できる。
        tukaenai();

        return "toStringをオーバーライドしたよ。";
      }
    };

    System.out.println("obj1=" + obj1);
    System.out.println("obj2=" + obj2);
    // obj2.tukaenai(); // コンパイルエラーになるよ。

    System.out.println("///////////////////\r\n");
  }

  // Runnableインターフェースを実装した無名クラスの作成例
  public void example2() {

    System.out.println("//// example2 ////");

    Runnable run1 = new Runnable() {

      @Override
      public void run() {

        // インターフェースや抽象クラスをベースに無名クラスを作る場合
        // 抽象メソッドを実装（オーバーライド）しなければならない。
        // XXX 実装したけど特に何もしないよ。
      }

    };

    Runnable run2 = new Runnable() {

      @Override
      public void run() {

        System.out.println("runメソッドを実装したよ。");
      }

    };

    run1.run();
    run2.run();

    System.out.println("///////////////////\r\n");
  }

}
