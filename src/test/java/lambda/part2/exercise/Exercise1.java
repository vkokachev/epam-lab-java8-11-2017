package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.junit.Assert.*;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise1 {

    @Test
    public void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Integer> ageExtractor = Person::getAge;

        assertEquals(33, ageExtractor.apply(person).intValue());
    }

    @Test
    public void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        BiPredicate<Person, Person> sameAgesChecker = (p1, p2) -> p1.getAge() == p2.getAge();
    }

    private static String getFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    private static BiFunction<Person, Person, Integer> createExtractorAgeOfPersonWithTheLongestFullName(Function<Person, String> f) {
         return (person1, person2) ->
                 f.apply(person1).length() > f.apply(person2).length() ? person1.getAge() : person2.getAge();
    }

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        Function<Person, String> getFullName = Exercise1::getFullName;

        BiFunction<Person, Person, Integer> extractorAgeOfPersonWithTheLongestFullName = createExtractorAgeOfPersonWithTheLongestFullName(getFullName);

        assertEquals(33, extractorAgeOfPersonWithTheLongestFullName.apply(person1, person2).intValue());
    }
}