package com.example;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SampleThread {

    private static final int count = 20;

    /**
     * SynchronousQueue を使用した Thread のサンプル.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {

        // ブロッキング・キュー
        SynchronousQueue<String> queue = new SynchronousQueue<String>();

        // 同期Integer
        AtomicInteger number = new AtomicInteger(100001);
        AtomicInteger atomicCnt = new AtomicInteger();

        // キューに追加するタスク
        Runnable r1 = () -> {
            int threadNum = atomicCnt.incrementAndGet();
            try {
                for (int i = 0; i < count; i++) {
                    queue.put("Thread" + threadNum + " :: " + String.valueOf(number.getAndIncrement()));
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // キューから取り出すタスク
        Runnable r2 = () -> {
            int threadNum = atomicCnt.incrementAndGet();
            try {
                for (int i = 0; i < count; i++) {
                    System.out.println(LocalTime.now() + " :: Thread" + threadNum + " :: take=<" + queue.take() + ">");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService es = Executors.newCachedThreadPool();
        try {
            // put 10 × 3
            es.submit(new Thread(r1));
            es.submit(new Thread(r1));
            es.submit(new Thread(r1));

            // take 10 × 3 (1 canceled)
            CompletableFuture<?> futureAll = CompletableFuture.allOf(CompletableFuture.runAsync(r2, es),
                    CompletableFuture.runAsync(r2, es));
            Future<?> future3 = es.submit(new Thread(r2));

            // 複数スレッド実行時のCancelタイミングの確認
            Thread.sleep(500);
            future3.cancel(true);
            System.out.println(LocalTime.now() + " :: Thread canceled = " + future3.cancel(true));

            // 終了待ち
            futureAll.get();

            es.shutdown();
            System.out.println(LocalTime.now() + " :: isCancelled = " + future3.isCancelled());

        } finally {
            es.shutdownNow();
        }
    }
}
