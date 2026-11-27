package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.bean.PaymentMethodBean;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpireDateDeserializerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializesExpiryMonthToFirstDayOfMonth() throws Exception {
        PaymentMethodBean paymentMethod = objectMapper.readValue(
                "{\"expireDate\":\"04/2028\"}",
                PaymentMethodBean.class
        );

        assertEquals(Date.valueOf("2028-04-01"), paymentMethod.getExpireDate());
    }

    @Test
    void rejectsMonthOutsideCalendarRange() {
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(
                "{\"expireDate\":\"13/2028\"}",
                PaymentMethodBean.class
        ));
    }

    @Test
    void rejectsDateThatDoesNotMatchMonthAndYearFormat() {
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(
                "{\"expireDate\":\"04-2028\"}",
                PaymentMethodBean.class
        ));
    }
}
