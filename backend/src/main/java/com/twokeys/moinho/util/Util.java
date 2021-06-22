package com.twokeys.moinho.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
	public static Double roundHalfUp2(Double value) {
		return Double.valueOf(new BigDecimal(value).setScale(4,RoundingMode.HALF_UP).toString());
	}
	public static Double roundUp0(Double value) {
		return Double.valueOf(new BigDecimal(value).setScale(0,RoundingMode.UP).toString());
	}
}
