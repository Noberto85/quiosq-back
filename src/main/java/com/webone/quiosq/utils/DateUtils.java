package com.webone.quiosq.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {

    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    public static LocalDateTime convert(OffsetDateTime date) {
        return date
            .atZoneSameInstant(ZoneId.of(AMERICA_SAO_PAULO))
            .toLocalDateTime();
    }

    public static OffsetDateTime convert(LocalDateTime date) {
        ZoneId saoPaulo = ZoneId.of(AMERICA_SAO_PAULO);
        return date.atZone(saoPaulo).toOffsetDateTime();
    }


}
