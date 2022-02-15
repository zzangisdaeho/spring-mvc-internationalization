package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage(){
        String hello = ms.getMessage("hello", null, null);
        System.out.println(hello);
        Assertions.assertThat(hello).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode(){
        Assertions.assertThatThrownBy(() -> ms.getMessage("no_code", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage(){
        String no_code = ms.getMessage("no_code", null, "기본 메세지",null);

        System.out.println(no_code);
        Assertions.assertThat(no_code).isEqualTo("기본 메세지");
    }

    @Test
    void argumentMessage(){
//        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        String message = ms.getMessage("hello.name", new String[]{"Spring"}, null);
        // hello.name 을 찾고 그 뒤에 넘긴 매개변수 Array 중 0번째 index를 붙이기 때문에 "안녕 Spring"이 나오게된다
        System.out.println(message);
        Assertions.assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang(){
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREAN)).isEqualTo("안녕");
    }

    @Test
    void enLang(){
        System.out.println(Locale.ENGLISH); // Spring이 자동으로 en을 붙인다.
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
