package thymeleaf.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void message() {
        String result = ms.getMessage("hi", null, null);

        assertThat(result).isEqualTo("하이");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void defaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        String message = ms.getMessage("hi.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("하이 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hi", null, null)).isEqualTo("하이");
        assertThat(ms.getMessage("hi", null, Locale.KOREA)).isEqualTo("하이");
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("hi", null, Locale.ENGLISH)).isEqualTo("hi");
    }
}
