import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("methodSource")
    public void test_message_by_country(Country country, String expectedMessage){
        // arrange : Создаем объект класса
        LocalizationService localizationService = new LocalizationServiceImpl();

        // act : выполняем метод byIP объекта geoService
        String message = localizationService.locale(country);

        //сравниваем значения полей двух объектов
        Assertions.assertEquals(message, expectedMessage);
    }


    public static Stream<Arguments> methodSource(){
        return Stream.of(
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.BRAZIL, "Welcome")
        );


    }
}
