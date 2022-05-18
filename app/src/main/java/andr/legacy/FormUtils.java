package andr.legacy;

import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FormUtils {
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private FormUtils() {
    }

    public static String getTextValue(TextView textView) {
        if (Objects.isNull(textView)) return "";
        if (Objects.isNull(textView.getText())) return "";
        return textView.getText().toString();
    }

    public static void setTextValue(TextView textView, String text) {
        if (Objects.isNull(textView)) return;
        textView.setText(text);
    }

    public static LocalDate getTextDate(TextView textView, DateTimeFormatter formatter) {
        String text = getTextValue(textView);
        if (StringUtils.isBlank(text)) return null;
        return LocalDate.parse(text, formatter);
    }

    public static LocalDate getTextDate(TextView textView) {
        return getTextDate(textView, SIMPLE_DATE_FORMAT);
    }
}
