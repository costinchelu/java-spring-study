package xmlutil;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public String marshal(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME);
    }
}
