import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {

    private static GeoService geoService;
    private static LocalizationService localizationService;
    private static MessageSender messageSender;
    private static Map<String, String> headers;


    // перед запуском тестов создаем заглушки классов GeoServiceImpl и LocalizationServiceImpl
    // создаем мапу для хранения айпишников
    @BeforeAll
    public static void before(){
        geoService = Mockito.mock(GeoServiceImpl.class);
        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<String, String>();
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    public void test_param_method_language_when_ip(String ip, Location location, String message) {

        // настраиваем заглушки geoService и localizationService
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(message);

        // передаем значения айпишника из параметров в мапу
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        // запускаем выполненние метода send
        String messageSent = messageSender.send(headers);

        // сравниваем полученное значение с ожидаемым
        Assertions.assertEquals(message, messageSent);
    }

    public static Stream<Arguments> methodSource(){
        return Stream.of(
                Arguments.of("172.134.0.15",
                        new Location("Moscow", Country.RUSSIA, null, 0),
                        "Добро пожаловать"),

                Arguments.of("96.134.0.15",
                        new Location("New York", Country.USA, null,  0),
                        "Welcome"),

                Arguments.of("",
                        new Location("Berlin", Country.GERMANY, null,  0),
                        "Welcome")

        );
    }


}
