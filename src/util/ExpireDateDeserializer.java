package util;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ExpireDateDeserializer  extends JsonDeserializer<Date> {
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException{
        String dateString = jp.getText(); // format: "MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(dateString, formatter);
        return Date.valueOf(yearMonth.atDay(1));
    }
}
