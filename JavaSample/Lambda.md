# Let's use lambda
ラムダ式について書きます。
是非使っていきましょう。

---
#### どのバージョンから使えるの？
→Java8から

#### 理解するために必要な知識は？
→以下の知識が不足しているとちょっと難しいかも。

 - インターフェースと実装
 - 無名クラス（匿名クラス）
 - 総称型（ジェネリクス）
 - 型推論

#### とりあえず、どんなの？
→とりあえず、こんなの。代入部がラムダ式になってるよ。
```
Function<String, Integer> func1 = s -> s.length();

Function<String, Integer> func2 = (s) -> s.length();

Function<String, Integer> func3 = s -> {
    return s.length();
};
```

- `func1～3`は全部同じ内容のラムダ式。  
- 基本的には 「 `->` 」の前が引数のパラメータ名（この例では `s`）になってて、後に処理の内容（この例では `return s.length()`）を書く。  
- 書き方の細かいルールは最後に書くよ。


#### ラムダ式を使わない書き方はできないの？
→もちろんできるよ。  
　以下のコードの `func4` は `func1～3` と同じ内容になっているよ。  
　もともと`func4`の様な書き方しかできなかったのが、`func1〜3`の様な簡略化した書き方ができる様になったという感じだね。  
　*JavaScript*の*無名function*(コールバックに設定したりするやつ)と似ているのがわかるかな？
```
// これはラムダ式ではありません。
Function<String, Integer> func4 = new Function<String, Integer>() {

    @Override
    public Integer apply(String s) {
        return s.length();
    }

};
```
 *JavaScriptの無名functionの例：*
```
let jsfunc1 = new function() { //なんか処理 };
let jsfunc2 = () => { //なんか処理 };
```

#### うーん、func4もあまり見ない書き方だけどどういうこと・・・？
→`func4`は ***Functionインターフェース*** を実装した ***無名クラス*** を作るコードになっているよ。  
　`func1～4`まで同じ実装なので、どれも*無名クラス*を作っているだけということになるね。  
　つまり、`func1～3`の記述は今までと違った書き方で*無名クラス*を作っているだけなんだよ。


#### 無名クラス？
→細かい説明は省くけど、今回の`func1～4`のコードは Java標準の ***Functionインターフェース*** を実装したクラスを ***クラス名を定義せずに*** 作成しているということを覚えておいてね。

　まず、*Functionインターフェース* のソースコードを見てね。以下の様になってるよ。
```
public interface Function<T, R> {

    R apply(T t);

    ・・・省略・・・
}
```
ポイントは総称型`<T, R> `なんだけど、 *Functionインターフェース* には型パラメータ`T`, `R`が必要で、***抽象メソッドが１つ*** だけあるね。それが `apply` だね。  
そして `func4` の例では `apply`メソッド をオーバーライドして実装しているよね？  
つまり `func4` の中身は *Functionインターフェース* の `apply` メソッドを実装した ***名前を定義していないクラスのインスタンス*** というわけなんだよ。（それで無名クラスっていうんだね）

さらに、`func4` の変数型は `Function<String, Integer>` だったよね。（`<>`は総称型）  
これは型パラメータ`T` を `String`, `R` を `Integer` で実装しているという意味になるよ。  
`apply` メソッドにも 型パラメータ`T`, `R` があるけど、この型パラメータもそれぞれ `String`, `Integer` になるんだ。

だから `func4` でオーバーライドした `apply` メソッドは「*public* ***Integer*** *apply(* ***String*** *s)*」となっているんだよ。


#### 名前のないクラスを作ってるだけなら、インターフェースの実装以外にも無名クラスは作れるの？
→もちろん作れるよ。  
　無名クラスは普通のクラスや抽象クラスもベースにして作れるよ。  
　だけど何でもラムダ式で書けるわけじゃないんだよ。

　ラムダ式はJavaで ***関数型プログラミング*** を行うために導入された書き方で、  
　無名クラス作るコードを簡単に書くためのコードではないんだ。

　とても重要なんだけれど、ラムダ式で作れる無名クラスは「***関数型インターフェース</font>***」を実装する必要があって、これが無名クラスを関数として振舞わせるための重要な**ルール**になっているんだよ。  
　<font color="red">関数型インターフェースの実装クラスだけがラムダ式で書ける</font>ということなんだ。


#### 関数型インターフェースって何？
→Java8で導入された `@FunctionalInterface` アノテーションのついたインターフェースのこと。  
　***抽象メソッドが１つのみのインターフェース*** というルールがあるよ。  
　`@FunctionalInterface` アノテーションがついていなくても、抽象メソッドが１つのみのインターフェースであれば関数型インターフェースとして識別されるよ。

 - Java8で追加された代表的なものは以下の通り。

  - `Function`・・・引数を１つもらって実行し、何かを返却するための関数型インターフェース
  - `Supplier`・・・引数なしで実行し、何かを返却するための関数型インターフェース
  - `Consumer`・・・引数を１つもらって、何かの処理を行い、何も返却しない関数型インターフェース

　引数や戻りの型は、総称型になっていて自由に使えるよ。  
　これらの仲間は他にもたくさんあって `java.util.function`パッケージに入ってるよ。  
　プリミティブ型に対応したものもあるよ。（`IntFunction`、`IntToLongFunction` 等々）  
　<font size="2pt">*※総称型ではラッパークラスしか扱えないので、オートボクシングなどで性能劣化させないためにプリミティブ型用の関数型インタフェースを使うよ。*</font>

　他にもJava7以前から存在する *関数型インターフェースの要件を満たすインターフェース* には Java8から `@FunctionalInterface` アノテーションがついたよ。

 - `Runnable`・・・・マルチスレッドなどで処理を実装するためのインターフェース。（引数なし/戻り値なし）
 - `Callable`・・・・マルチスレッドなどで処理を実装するためのインターフェース。（引数なし/戻り値あり）
 - `Comparator`・・・オブジェクトの順序の規則（大小関係も含む）を実装するためのインターフェース。

これまでは作ったクラスに順序の要件があるときに`implements Comparator`して使うことが主だったけど
Java8以降はラムダ式で使うことも増えたよ。  
（Example:
[*ExFunction.java*](https://github.com/shiratamadango/work-java/JavaSample/src/main/java/com/example/ExFunction.java)
)


#### ラムダ式では <font color="red">実装クラスのpublicなメソッドが１つとなること</font> がルール付けられているから無名クラスを擬似的に関数の様に扱えるっていうこと？
→そうだね。  
- `public`なメソッドが必ず一つということは、メソッド名が分からなくても `reflection` を使えば実行すべきメソッドを取り出すことができるということでもあるよね。

- 導入部で書いた`func1～3`のラムダ式には、`func4`の書き方にはあったメソッド名(`apply`)がどこにも出て来てないでしょう？  **抽象メソッドが一つしかない** から、実装すべきメソッドが暗黙的に特定できるので、メソッド名(`apply`)を記述する必要がないからこういう書き方になったんだろうね。  

- さらに、実装するメソッドが暗黙的に決まるから**引数**と**戻り値の型**も暗黙的に`apply`と同じになるということだね。  
　だからラムダ式の「`->`」の左側には型名の記述が必要ないんだよ。(パラメータ名のみになっている)

- ラムダ式は無名クラスの作成を最大限簡略化した書き方といっていいかもしれないね。


#### でもこんなのいつ使うの？
→一般的な使い方は以下の通り。あとはStreamAPIでとても使うよ。とてもとても使うよ。
　`func1～3`のように変数に入れて使うことはあまりないかな。  

*コーディング例：*
```
// Number型のリストをdouble値でソートするコード。
List<Number> list = new ArrayList<>();
list.sort((c1, c2) -> Double.compare(c1.doubleValue(), c2.doubleValue()));
```
- 上の例では、`list`は `List<Number>`型。  
`list.sort` の引数は `Comparator<? super E>`型。（`<? super E>`については後ほど）  
- 型パラメータ"`E`"は、`list`の総称型と同じになるので `Comparator<? super Number>`型になるよ。  
- `sort`の引数の`Comparator`の総称型は`Object`～`Number`型までということだから`Number`のサブクラス(`Integer`や`Long` 等々)ではダメということだね。


#### Comparator について教えて！
→関数型インターフェースの１つ。

  - `Comparator`で実装が必要な抽象メソッドは「`int compare(T o1, T o2)`なので、2つの `T`型 のパラメータをもらって `int` を返却するように実装する必要があるよ。  
 　型パラメータ"`T`"は`Comparator<T>`の総称型と同じなんだけど、ラムダ式ではこの型は型推論で決定するので、今回の例では「`int compare(Number o1, Number o2)`」となるよ。  
 　つまりラムダ式の「`(c1, c2) -> `」の`(c1, c2)`は、`(Number o1, Number o2)`とういうことだね。  
 　そして「`->`」の右側は`int`を返却する実装を記述する。ただし、ただ`int`値を返せばいいわけではなく、`Comparator.compare`メソッドに定められた実装ルールを満たす（*o1, o2を比較して、同じなら値0を、o1がo2より小さいければ0未満を、o1がo2より大きいければ1以上を返却する*）必要がある。  
　***インターフェースの抽象メソッドの仕様として決まってるので、必ず仕様に準拠させること。***

  - 「Number型のリストをdouble値でソートするコード」は別解として以下のようにも書ける。  
  恐らくこちらのほうが一般的。
  ```
  // Number型のリストをdouble値でソートするコードの別解。
  List<Number> list = new ArrayList<>();
  list.sort(Comparator.comparingDouble(n -> n.doubleValue()));`
  ```
  `Comparator.comparingDouble` の引数は `ToDoubleFunction<? super T>`型で、ラムダ式で`ToDoubleFunction`を実装(実装部：`n -> n.doubleValue()`)した無名クラスを作って引数として渡しているよ。  
  このメソッドは`Comparator<Number>`を返却する仕様になっているよ。  
   _`ToDoubleFunction`も関数型インターフェースの１つ。_


#### Comparatorインターフェースは、抽象メソッドが１つには見えないけど・・・？
→まず、`Object`クラスにあるメソッドはこのルールから除外されるよ。  
- `Object`クラスで実装済みなので、そもそも抽象メソッドに該当しない。（`equals` や `toString` 等々）  
- Java8から導入された `default`修飾子がついたメソッドも実装済みなので抽象メソッドに該当しない。


#### というか <? super Number> とか <? super E> ってなんなの？
→総称型（ジェネリクス）だよ。  
　よく見るものだと `List<String>` とか `List<Integer>` とかだね。  
　ほかにも `<? extends Number>` とかの書き方もあるよ。  

- `<? super Number>`であれば、`Number` とそのすべてのスーパークラス(`Object`まで)を表し、`<? extends Number>`であれば、`Number` とそのすべてのサブクラス(孫も含む)を表す。

- `<? super E>`の `E` は型パラメータと言って`E`とか`T`とかの存在しない型になっている場合は**任意の型**を表しているよ。これは別に`E`じゃなくてもOK。

- よく使われるのは`T`とか`V`とか`R`とか。`E`はelement,`T`はType,`V`はvalue,`R`はreturn等の頭文字からきてるっぽいよ。

- 型パラメータはインスタンス生成時に決まるものと、型推論で決まるものがあるけどこの説明はちょっと割愛するよ。

(Example: [*ExGenerics.java*](https://github.com/shiratamadango/work-java/JavaSample/src/main/java/com/example/ExGenerics.java))


#### ラムダ式の書き方のルールを教えて！
→はいどうぞ。

 - 基本形は次の通り。
```
(param1, param2) -> {

    // param1, param2 を使ったなんかの処理
    Hoge hoge = new Hoge(param1, param2);

    // 結果を返却。型はインターフェースの仕様に準拠。
    return hoge;
}
```
「 `->` 」を基準に、左側(`()`)がパラメータ、右側(`{}`)が実装内容。  
パラメータは括弧`()`で囲む。  
実装部はブロック`{}`で囲む。  
返却値は `return` してあげる。


 - パラメータが１つの時は括弧`()`を省略できる。
```
param -> {

    // param を使ったなんかの処理
    Hoge hoge = new Hoge(param);

    // 結果を返却。型はインターフェースの仕様に準拠。
    return hoge;
}
```


 - パラメータがない時は括弧`()`だけを書く。
```
() -> {

    // なんかの処理
    Hoge hoge = new Hoge();

    // 結果を返却。型はインターフェースの仕様に準拠。
    return hoge;
}
```


 - 返却値がない時(`void`)は、`return`は書かなくていい。
```
(param1, param2) -> {

    // param1, param2 を使ったなんかの処理
    System.out.println(param1);
    System.out.println(param2);
}
```


 - 返却値があって、その処理内容が１ステップで書ける場合は、ブロック`{}`と`return`を省略して書くことができる。  
```
(param1, param2) -> param1.equals(param2)
```
↑`param1.equals(param2)`の結果(*boolean*)を返却している。  

  返却値がなく、実装内容も返却値のない処理である場合も同じルールでOK。  
```
(param) -> System.out.println(param)
とか
param -> System.out.println(param)
```


 - パラメータが１つで返却値がなく、その処理内容が1ステップかつこのパラメータを引数に実行する場合、**メソッド参照** という書き方ができる。  
```
// ラムダ式
list.forEach(data -> System.out.println(data));
```
↑これを↓こう書ける  
```
// ラムダ式（メソッド参照）
list.forEach(System.out::println);
```

---
おわり。
