/**
 * 
 */
package br.com.totvs.plugins.bacen.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

import br.com.totvs.plugins.bacen.constants.Constants;

/**
 * @author rsdelia
 *
 */
public final class Util {

	public Util() {
		super();
	}

	public static <T> boolean isNullOrEmpty(T obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return Constants.EMPTY.equals(obj.toString().trim());
		}
		if (obj instanceof Integer) {
			Integer tmp = (Integer) obj;
			return tmp == 0;
		}
		if (obj instanceof BigInteger) {
			return BigInteger.ZERO.compareTo((BigInteger) obj) >= Constants.ZERO;
		}
		if (obj instanceof BigDecimal) {
			return BigDecimal.ZERO.compareTo((BigDecimal) obj) >= Constants.ZERO;
		}
		if (obj instanceof Long) {
			return Long.valueOf(Constants.ZERO).compareTo((Long) obj) >= Constants.ZERO;
		}
		return false;
	}

}
