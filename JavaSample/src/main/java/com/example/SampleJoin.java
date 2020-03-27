package com.example;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

public class SampleJoin {

    private final String first = "「";
    private final String end = "」";
    private final String delimiter = "：";

    /**
     * 文字列連結サンプル.
     * <p>
     * ウォームアップなどしてないので性能は参考程度
     * </p>
     *
     * @param args
     */
    public static void main(String... args) {

        SampleJoin sample = new SampleJoin();

        // 連結対象文字列結合の生成
        List<String> sampleList = Arrays.asList("りんご", "ごりら", "ラッパ", "ぱんだ", "団子");
        List<String> sampleList2 = sample.randomData(20);
        List<String> mediumList = sample.randomData(1_000);
        List<String> largeList1 = sample.randomData(1_000_000);
        List<String> largeList2 = sample.randomData(1_000_000);
        List<String> largeList3 = sample.randomData(1_000_000);

        //
        // 文文字列連結機能
        //
        System.out.println("<<-- 文字列連結機能 -->>\r\n");

        // java8~ StringJoinner
        System.out.println("*** java8~ StringJoinner ***\r\n");
        sample.sample_StringJoinner(Collections.emptyList(), true);
        sample.sample_StringJoinner(new ArrayList<>(sampleList), true);
        sample.sample_StringJoinner(new ArrayList<>(sampleList2), true);
        sample.sample_StringJoinner(new ArrayList<>(mediumList), false);
        sample.sample_StringJoinner(new ArrayList<>(largeList1), false);
        sample.sample_StringJoinner(new ArrayList<>(largeList2), false);
        sample.sample_StringJoinner(new ArrayList<>(largeList3), false);
        System.out.println();
        System.out.println();

        // java8~ StreamAPI Collectors.joining
        System.out.println("*** java8~ StreamAPI Collectors.joining ***\r\n");
        sample.sample_StreamJoining(Collections.emptyList(), true);
        sample.sample_StreamJoining(new ArrayList<>(sampleList), true);
        sample.sample_StringJoinner(new ArrayList<>(sampleList2), true);
        sample.sample_StreamJoining(new ArrayList<>(mediumList), false);
        sample.sample_StreamJoining(new ArrayList<>(largeList1), false);
        sample.sample_StreamJoining(new ArrayList<>(largeList2), false);
        sample.sample_StreamJoining(new ArrayList<>(largeList3), false);
        System.out.println();
        System.out.println();

        // 比較用 StringBuilder
        System.out.println("*** 比較用 StringBuilder ***\r\n");
        sample.sample_StringBuilder(Collections.emptyList(), true);
        sample.sample_StringBuilder(new ArrayList<>(sampleList), true);
        sample.sample_StringJoinner(new ArrayList<>(sampleList2), true);
        sample.sample_StringBuilder(new ArrayList<>(mediumList), false);
        sample.sample_StringBuilder(new ArrayList<>(largeList1), false);
        sample.sample_StringBuilder(new ArrayList<>(largeList2), false);
        sample.sample_StringBuilder(new ArrayList<>(largeList3), false);
    }

    /**
     * ランダムデータの生成
     * @param size
     * @return
     */
    private List<String> randomData(int size) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }

    /** java8~ StringJoinner */
    private void sample_StringJoinner(List<String> list, boolean print) {

        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // クラス生成
        StringJoiner joiner = new StringJoiner(delimiter, first, end);

        // 文字列連結
        list.forEach(joiner::add);

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " 結果 : " + joiner.toString());
        }
    }

    /** java8~ StreamAPI Collectors.joining */
    private void sample_StreamJoining(List<String> list, boolean print) {

        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // 文字列連結
        String joinString = list.stream().collect(Collectors.joining(delimiter, first, end));

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " 結果 : " + joinString);
        }
    }

    /** StringJoinner 比較用 */
    private void sample_StringBuilder(List<String> list, boolean print) {

        String method = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Instant before = Instant.now();

        // クラス生成
        StringBuilder builder = new StringBuilder();

        // 文字列連結
        builder.append(first);
        list.forEach(str -> builder.append(str).append(delimiter));
        if (!list.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(end);

        Instant after = Instant.now();
        System.out.println(method + " 性能 : size = " + list.size() + ", 時間 = "
                + (after.toEpochMilli() - before.toEpochMilli()) + "ms");
        if (print) {
            System.out.println(method + " 結果 : " + builder.toString());
        }
    }

}