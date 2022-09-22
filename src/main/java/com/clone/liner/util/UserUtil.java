package com.clone.liner.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserUtil {

    public static String formatDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
