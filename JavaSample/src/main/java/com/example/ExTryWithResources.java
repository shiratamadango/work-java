package com.example;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * try-with-resources の学習.
 * <br>
 * <pre>
 * Java7から使える、一部の try-catch-finally 構文に代わる書き方。
 * 例外発生時およびブロック終了時にAutoCloseableインターフェースの実装クラスをオートクローズしてくれる。
 * AutoCloseableインターフェースの代表的な実装クラスは、
 * Writer系,Reader系,OutputStream系,InputStream系,Socket系,Connection系等々たくさんあります。
 * ファイルやコネクションのクローズ漏れの防止になるので、
 * 例外処理でまだ利用していない人はぜひ try-with-resources に乗り換えましょう。
 * </pre>
 */
public class ExTryWithResources {

    /**
     * <p>
     * try-with-resources を使う。<br>
     * <br>
     * ・try-with-resources は {@link AutoCloseable} インターフェースまたは<br>
     *   そのサブインターフェース({@link Closeable}等)の実装クラスで使用できる
     * </p>
     */
    public static void main(String[] args) {

        ExTryWithResources obj = new ExTryWithResources();

        //
        //  try-with-resources を使用しない例 * 10
        //
        IntStream.rangeClosed(1, 10).forEachOrdered(num -> obj.exTryCatchFinally(num));

        System.out.println("\r\n***********************\r\n");

        //
        //  try-with-resources を使用した例 * 10
        //
        IntStream.rangeClosed(1, 10).forEachOrdered(num -> obj.exTryWithResources(num));

        System.out.println("\r\n***********************\r\n");

        //
        //  try-with-resources を使用した例（複数） * 10
        //
        IntStream.rangeClosed(1, 10).forEachOrdered(num -> obj.exTryWithResourcesMulti(num));
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * XXX try-with-resources を使用しない例
     */
    public void exTryCatchFinally(int num) {

        System.out.println("----- tryCatchFinally ----- " + num);

        MyResource src = null;
        try {
            src = MyResource.open(num);

            src.processing();

        } catch (TimeoutException e) {

            System.out.println("Open失敗時の例外処理 " + e.getMessage());

        } finally {

            if (src != null) {
                // Close処理でも例外が発生するので、ここも例外処理が必要です。
                try {
                    src.close();

                } catch (IOException e) {

                    System.out.println("Close失敗時の例外処理 " + e.getMessage());
                }
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * XXX try-with-resources を使用した例
     */
    public void exTryWithResources(int num) {

        System.out.println("----- tryWithResources ----- " + num);

        // finallyにClose処理を書かなくても、Closeが実行されます。
        try (MyResource src = MyResource.open(num)) {

            src.processing();

        } catch (TimeoutException e) {

            System.out.println("Open失敗時の例外処理 " + e.getMessage());

        } catch (IOException e) {

            System.out.println("Close失敗時の例外処理 " + e.getMessage());

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * XXX try-with-resources を使用した例（複数記載）
     */
    public void exTryWithResourcesMulti(int num) {

        System.out.println("----- exTryWithResourcesMulti ----- " + num);

        // finallyにClose処理を書かなくても、Closeが実行されます。
        // セミコロンで区切って複数書くこともできます。
        try (MyResource src1 = MyResource.open(num + 1000);
                MyResource src2 = MyResource.open(num + 2000);
                MyResource src3 = MyResource.open(num + 3000)) {

            src1.processing();
            src2.processing();
            src3.processing();

        } catch (TimeoutException e) {

            System.out.println("Open失敗時の例外処理 " + e.getMessage());

        } catch (IOException e) {

            System.out.println("Close失敗時の例外処理 " + e.getMessage());

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * {@link Closeable}インターフェースを実装したクラス.
     */
    private static class MyResource implements Closeable {

        private final int num;

        /**
         * 非公開コンストラクタ
         *
         * @param num
         */
        private MyResource(int num) {
            this.num = num;
        }

        /**
         * Open.<br>
         * MyResourcのファクトリメソッド
         *
         * @param num
         * @return
         * @throws Exception
         */
        public static MyResource open(int num) throws TimeoutException {

            // ランダムでOpenに失敗する
            if (Math.random() < 0.3d) {
                System.out.println("Open失敗 " + num);
                throw new TimeoutException("TimeoutException : Open失敗 " + num);
            }

            System.out.println("Open成功 " + num);

            return new MyResource(num);
        }

        /**
         * 適当な処理
         */
        public void processing() {

            System.out.println("なんかの処理 " + num);
        }

        @Override
        public void close() throws IOException {

            // ランダムでCloseに失敗する
            if (Math.random() < 0.6d) {
                System.out.println("Close失敗 " + num);
                throw new IOException("IOException : Close失敗 " + num);
            }

            System.out.println("Close成功 " + num);
        }
    }
}
