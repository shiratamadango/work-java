# 無名クラス（匿名クラス）って？

無名クラスについて書きます。

---

クラスやインターフェースを継承/実装して作る無名のクラスインスタンスのこと。  
　型を定義せずにインスタンスを作るということがポイント。  
　共通部とか複雑な処理やテストコード、ライブラリ内のコードなんかで見かけることがある。

以下はサンプルコードとその解説。  
[*AnonymousClass.java*](https://github.com/shiratamadango/work-java/JavaSample/src/main/java/com/example/AnonymousClass.java)

---
#### *Objectを継承した無名クラスの作成例:*
```
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

}
```

obj1はObjectクラスと全く同じ振る舞いをする。  
obj2.toString() すると、内部で`tukaenai()`が実行されて  
> `publicなのに、外部から呼べないメソッドだよ。tukaenaiね。`  

と出力される。また、  
> `toStringをオーバーライドしたよ。`  

と返却される。  

　`obj2`に実装した `tukaenai`メソッドは`public`にもかかわらず<font color="red">外部から実行することはできない</font>よ。  
*（`obj2.tukaenai()`はコンパイルエラーになる）*   
　これは`obj2`を格納している変数の型が`Object`なので、`Object`クラスで定義されていないメソッドをそのまま呼ぶことができないからだね。  
　`tukaenai`メソッドを定義したクラスにキャストすれば実行することができる様になるよ。  
　だけど、サブクラスとしての名前が定義されていない（無名クラスだからね）から、
`onj1`も`obj2`も実装クラスにキャストすることもできないよ。  
　だから**この作成例は全く効果的な使い方ではない**ということだね。

　また、変数の型は`Object`だけど、中身は`Objectを継承して作ったサブクラスのインスタンス`だから、
`obj1`も`obj2`も`Object`と等価ではないよ。  

　`Object`型の変数に入ってるのに`Object`じゃないの？と思う人は、変数型とオブジェクトの実体の違いについて理解を深めよう。  
*（すべてのオブジェクトはObjectクラスの派生クラスだからObjectでしょ？というのはなしで！）*

  *◆◆◆◆補足◆◆◆◆*  
```
List<String> list1 = new ArrayList<>();
List<String> list2 = new LinkedList<>();
```
↑ `obj1`と`obj2`の違いは、この`list1`と`list2`の違いと同じ。  
　`list1`も`list2`も`List`と等価ではないけど、実体は`Listインターフェース`を実装クラス(`ArrayList`,`LinkedList`)なので、`List`として振舞うための機能を備えている。  
　ただし、`List`型の変数に格納しているので、`ArrayList`,`LinkedList`にあって`Listインターフェース`にない`public`メソッドはこのままでは使うことができない。  
*（実体にあった型でキャストすることで使える様になる）*

---
#### *Runnableインターフェースを実装した無名クラスの作成例:*
```
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

}
```
　`run1`も`run2`も変数型は同じ`Runnable`だけど、`run`メソッドの実装内容は異なるよ。  
　`run`メソッドは`Runnableインターフェース`に引数型/戻り型が定義されているから、外部からも実行できるよ。  
　だから、無名クラスは抽象クラスやインターフェースを実装/継承して作るのが効果的だと思うよ。  

　そしてインターフェースや抽象クラス以外をベースにした無名クラスは、クラス設計で定義した振る舞いを変則的に変えた（オーバーライドした）インスタンスを作るということになるから、ちょっとニッチな使い方になるんじゃないかな。  
*（本当にその変更が必要なら、クラス設計の時点でそのサブクラスを定義するだろうからね）*



　ちなみに、`run2` をクラス名「`Sample`」として定義する場合、以下のコードになるよ。あまり変わらないでしょ？
```
public class Sample implements Runnable {

  @Override
  public void run() {

    System.out.println("runメソッドを実装したよ。");

  }

}
```

---
おわり。
