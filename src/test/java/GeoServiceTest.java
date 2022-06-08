import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.stream.Stream;

public class GeoServiceTest {

    @ParameterizedTest
    @MethodSource("methodSource")
    public void test_location_by_ip(String ip, Location expectedLocation) {

        // arrange : Создаем объект класса
        GeoService geoService = new GeoServiceImpl();

        // act : выполняем метод byIP объекта geoService
        Location location = geoService.byIp(ip);

        //сравниваем значения полей двух объектов
        assertThat(location, samePropertyValuesAs(expectedLocation));
    }

    public static Stream<Arguments> methodSource(){
        return Stream.of(
                Arguments.of("172.134.0.15",
                        new Location("Moscow", Country.RUSSIA, null, 0)),

                Arguments.of("96.0.0.15",
                        new Location("New York", Country.USA, null,  0)),

                Arguments.of("127.0.0.1",
                        new Location(null, null, null,  0)),

                Arguments.of("96.44.183.149",
                                new Location("New York", Country.USA, " 10th Avenue", 32)),

                Arguments.of("172.0.32.11",
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15))
        );


    }
}
