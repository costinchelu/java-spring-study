package io.spring.batch.xmlutil;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd HH:mm:ssa";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING);

    @Override
    public String marshal(LocalDateTime v) {
        return new SimpleDateFormat(CUSTOM_FORMAT_STRING).format(v);
    }

    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, formatter);
    }
}
