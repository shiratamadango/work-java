
# Learning Java

---

## JavaSample
Java学習者向けのサンプルコード

### メインクラス
- [*AnonymousClass.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/AnonymousClass.java)
  - 無名クラスのサンプル
- [*ExFunction.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/ExFunction.java)
  - Function インターフェースのサンプル
- [*ExGenerics.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/ExGenerics.java)
  - ジェネリクスのサンプル
- [*ExStream.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/ExStream.java)
  - Stream API のサンプル
- [*ExTryWithResources.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/ExTryWithResources.java)
  - try-with-resources のサンプル
- [*SampleForkJoinPool.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/SampleForkJoinPool.java)
  - ForkJoinPool のサンプル
- [*SampleJoin.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/SampleJoin.java)
  - 文字列連結のサンプル
- [*SampleSort.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/SampleSort.java)
  - リストのソートのサンプル
- [*SampleThread.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/SampleThread.java)
  - 非同期処理のサンプル
- [*ValidatingSubSetOfSortedSet.java*](https://github.com/shiratamadango/work-java/blob/master/JavaSample/src/main/java/com/example/ValidatingSubSetOfSortedSet.java)
  - SortedSet(TreeSet)のsubSetメソッドの検証

### build & 実行方法
```
mkdir 'workdir name'
cd 'workdir name'
git clone https://github.com/shiratamadango/work-java.git
cd JavaSample
gradle build
gradle launch -Pmain=ExFunction
```
<font size="2pt">
-Pmain=実行したいメインクラス
</font>
</br>
<font size="2pt">
Windowsの場合は`gradlew`コマンドになります。
</font>


---
