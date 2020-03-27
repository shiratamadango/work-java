package com.example;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class SampleForkJoinPool {

    /**
     * ForkJoinPool のサンプル.
     *
     * @param args
     */
    public static void main(String... args) {

        // フィボナッチ数列
        // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657...

        // n 個めのフィボナッチ数を特定する
        int n = 10;

        ForkJoinPool pool = new ForkJoinPool();
        // 処理が終わるまで待ち合わせを行い、結果を受け取る
        int res = pool.invoke(new Fibonacci(n));
        System.out.println("result = " + res);
    }
}

/**
 * 結果を生成する再帰的なForkJoinTask.
 * <br>
 * フィボナッチ数列を計算する
 */
class Fibonacci extends RecursiveTask<Integer> {

    final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    public ForkJoinTask<Integer> forkLog() {
        System.out.println("  fork:" + this.n);
        return super.fork();
    }

    public Integer joinLog() {
        System.out.println("  join:" + this.n);
        return super.join();
    }

    @Override
    protected Integer compute() {

        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        // 非同期で計算を開始する
        f1.forkLog();
        Fibonacci f2 = new Fibonacci(n - 2);
        // f2 の計算完了後に f1の計算結果を待ってから
        return f2.compute() + f1.joinLog();
    }
}
