package com.example;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamAPI の学習.
 */
public class ExStream {

    /**
     * <p>
     * Java8 から使用できる StreamAPI を使う。<br>
     * <br>
     * ・総称型について理解していること。<br>
     * ・FunctionalInterfaceついて理解していること。<br>
     * </p>
     */
    public static void main(String[] args) {

        // リストを作る
        System.out.println("//// sample1 :: リストを作る ////");
        List<Dataset> list = sample1(1, 5);
        System.out.println();
        System.out.println();

        // List → Map変換
        System.out.println("//// sample2 :: List → Map変換 ////");
        sample2(list);
        System.out.println();
        System.out.println();

        // グルーピング
        System.out.println("//// sample3 :: グルーピング ////");
        sample3(sample1(3, 10));
        System.out.println();
        System.out.println();

        // 連結/集約
        System.out.println("//// sample4 :: 連結/集約 ////");
        sample4(sample1(1, 5));
        System.out.println();
        System.out.println();

        // 抽出
        System.out.println("//// sample5 :: 抽出 ////");
        sample5(sample1(3, 10));
        System.out.println();
        System.out.println();

        // reduce
        System.out.println("//// sample6 :: reduce ////");
        sample6(sample1(1, 5));
        System.out.println();
        System.out.println();

        // flatMap
        System.out.println("//// sample7 :: flatMap ////");
        sample7(Arrays.asList(sample1(1, 3), sample1(2, 4), sample1(1, 2)));
        System.out.println();
        System.out.println();
    }

    /**
     * sample1
     * <br>
     * リストを作る
     */
    public static List<Dataset> sample1(int mag, int limit) {

        System.out.println("--- sample1 ---");

        AtomicInteger cnt = new AtomicInteger(1);

        return Stream.generate(() -> {
            int num = cnt.getAndIncrement();
            return new Dataset("Key:" + Math.floorDiv(num, mag), "Value:" + num, num);
        })
                .limit(limit) // このサイズに切り詰めます
                .peek(System.out::println) // peekはデバッグ用構文
                .collect(Collectors.toList());
    }

    /**
     * sample2
     * <br>
     * List → Map変換
     */
    public static void sample2(List<Dataset> list) {

        System.out.println("--- sample2 ---");

        // Key : Dataset
        Map<String, Dataset> map1 = list.stream().collect(Collectors.toMap(Dataset::getKey, Function.identity()));

        // 並列処理時
        Map<String, Dataset> map2 = list.parallelStream()
                .collect(Collectors.toConcurrentMap(Dataset::getKey, Function.identity()));

        // Key : Value
        Map<String, String> map3 = list.stream().collect(Collectors.toMap(Dataset::getKey, Dataset::getValue));

        // Key : Num
        Map<String, Integer> map4 = list.stream().collect(Collectors.toMap(Dataset::getKey, Dataset::getNum));

        // Num : Value
        Map<Integer, String> map5 = list.stream().collect(Collectors.toMap(Dataset::getNum, Dataset::getValue));

        Stream.of(map1, map2, map3, map4, map5).forEach(System.out::println);
    }

    /**
     * sample3
     * <br>
     * グルーピング
     */
    public static void sample3(List<Dataset> list) {

        System.out.println("--- sample3 ---");

        // Key で Dataset をグルーピング
        Map<String, List<Dataset>> group1 = list.stream().collect(Collectors.groupingBy(Dataset::getKey));

        // Key で グルーピングし、グループ結果を Value：Num でマッピング
        Map<String, Map<String, Integer>> group2 = list.stream()
                .collect(Collectors.groupingBy(Dataset::getKey, Collectors.toMap(Dataset::getValue, Dataset::getNum)));

        // Key で グルーピングし、グループ結果を Valueでリスト化
        Map<String, List<String>> group3 = list.stream()
                .collect(Collectors.groupingBy(Dataset::getKey,
                        Collectors.mapping(Dataset::getValue, Collectors.toList())));

        Stream.of(group1, group2, group3).forEach(System.out::println);

    }

    /**
     * sample4
     * <br>
     * 連結/集約
     */
    public static void sample4(List<Dataset> list) {

        System.out.println("--- sample4 ---");

        // Key を連結
        String join1 = list.stream().map(Dataset::getKey).collect(Collectors.joining(" + "));

        // Value を連結
        String join2 = list.stream().map(Dataset::getValue).collect(Collectors.joining(",", "[Start]", "[End]"));

        // Num で集約
        IntSummaryStatistics sum = list.stream().mapToInt(Dataset::getNum).summaryStatistics();

        Stream.of(join1, join2, sum).forEach(System.out::println);

    }

    /**
     * sample5
     * <br>
     * 抽出
     */
    public static void sample5(List<Dataset> list) {

        System.out.println("--- sample5 ---");

        // XXX リストサイズが膨大な時は、filter で抽出するより、Map化やグルーピングした方がいい場合があります。

        // Key:2 を抽出して１つだけ返却
        Dataset exact1 = list.stream().filter(x -> "Key:2".equals(x.getKey())).findFirst().orElse(null);

        // Key:2 を抽出して１つだけ返却 / 並列処理 / 結果が複数あると、返却される値は安定しません。
        Dataset exact2_1 = list.parallelStream().filter(x -> "Key:2".equals(x.getKey())).findAny().orElse(null);
        Dataset exact2_2 = list.parallelStream().filter(x -> "Key:2".equals(x.getKey())).findAny().orElse(null);
        Dataset exact2_3 = list.parallelStream().filter(x -> "Key:2".equals(x.getKey())).findAny().orElse(null);

        // 存在しない条件で抽出
        Dataset exact3 = list.stream().filter(x -> "XXXXX".equals(x.getKey())).findFirst().orElse(null);

        // Key:2 を抽出してリスト化
        List<Dataset> exact4 = list.stream().filter(x -> "Key:2".equals(x.getKey())).collect(Collectors.toList());

        Stream.of(exact1, exact2_1, exact2_2, exact2_3, exact3, exact4).forEach(System.out::println);

    }

    /**
     * sample6
     * <br>
     * reduce
     */
    public static void sample6(List<Dataset> list) {

        System.out.println("--- sample6 ---");

        // １つのデータセットに情報を蓄積します。
        Dataset reduce = list.stream().reduce(new Dataset("", "", 0), (x1, x2) -> {
            x1.key += "_" + x2.key;
            x1.value += "_" + x2.value;
            x1.num = x1.num * 10 + x2.num;
            return x1;
        });

        Stream.of(reduce).forEach(System.out::println);

    }

    /**
     * sample7
     * <br>
     * flatMap
     */
    public static void sample7(List<List<Dataset>> list) {

        System.out.println("--- sample7 ---");

        // ネストしたリストをフラットにする
        List<Dataset> flat1 = list.stream().flatMap(subList -> subList.stream()).collect(Collectors.toList());

        // ネストしたリストをフラットにして重複を除外
        List<Dataset> flat2 = list.stream()
                .flatMap(subList -> subList.stream())
                .collect(Collectors.groupingBy(x -> x.getKey() + "_" + x.getValue() + "_" + x.getNum(),
                        Collectors.reducing((x1, x2) -> x1)))
                .values()
                .stream()
                .map(Optional::get)
                .collect(Collectors.toList());

        Stream.of(list, flat1, flat2).forEach(System.out::println);

    }

    /**
     * Dataset
     *
     */
    public static class Dataset {

        private String key;

        private String value;

        private Integer num;

        public Dataset(String key, String value, Integer num) {
            super();
            this.key = key;
            this.value = value;
            this.num = num;
        }

        /**
         * get key.
         *
         * @return key
         */
        public String getKey() {
            return key;
        }

        /**
         * set key.
         *
         * @param key key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * get value.
         *
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         * set value.
         *
         * @param value value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * get num.
         *
         * @return num
         */
        public Integer getNum() {
            return num;
        }

        /**
         * set num.
         *
         * @param num num
         */
        public void setNum(Integer num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Dataset [key=" + key + ", value=" + value + ", num=" + num + "]";
        }

    }
}
