package lambda.part3.exercise;

import com.google.common.primitives.Chars;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private final List<T> source;
        private final Function<T, List<R>> flatMapping;

        private LazyFlatMapHelper(List<T> source, Function<T, List<R>> flatMapping) {
            this.source = source;
            this.flatMapping = flatMapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(this.source, t -> {
                List<U> toMapped = new ArrayList<>();
                for (R r : flatMapping.apply(t)) {
                    toMapped.add(mapping.apply(r));
                }

                return toMapped;
            });
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            // TODO реализация
            return new LazyFlatMapHelper<>(this.source, t -> {
                List<U> toMapped = new ArrayList<>();
                for (R r : this.flatMapping.apply(t)) {
                    toMapped.addAll(flatMapping.apply(r));
                }

                return toMapped;
            });
        }

        public List<R> force() {
            // TODO реализация
            List<R> forcedList = new ArrayList<>();
            for (T t : source)
                forcedList.addAll(this.flatMapping.apply(t));
            return forcedList;
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(pos -> Chars.asList(pos.toCharArray()))
                .map(Integer::valueOf)
                .force();
        // TODO                 LazyFlatMapHelper.from(employees)
        // TODO                                  .flatMap(Employee -> JobHistoryEntry)
        // TODO                                  .map(JobHistoryEntry -> String(position))
        // TODO                                  .flatMap(String -> Character(letter))
        // TODO                                  .map(Character -> Integer(code letter)
        // TODO                                  .getMapped();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
