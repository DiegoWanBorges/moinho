package com.twokeys.moinho.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Util {
	public static Double roundHalfUp2(Double value) {
		return Double.valueOf(new BigDecimal(value).setScale(2,RoundingMode.HALF_UP).toString());
	}
	public static Double roundUp0(Double value) {
		return Double.valueOf(new BigDecimal(value).setScale(0,RoundingMode.UP).toString());
	}
	public static LocalDate toLocalDate(Instant date) {
		return LocalDate.ofInstant(date, ZoneId.of("America/Sao_Paulo"));
	}
}
