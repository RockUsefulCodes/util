package br.com.rockstom.util;

import static br.com.rockstom.util.StringUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public final class DateTimeUtil {
	
	public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm");
	
	public static final DateTimeFormatter BR_DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter BR_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	 
	
	public static final DateTimeFormatter EN_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter EN_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private DateTimeUtil() {
	}

	/**
	 * Convert date and time to <code>java.time.LocalDateTime</code>
	 * @author Wesley Luiz
	 * @param date
	 * @param time
	 * @return
	 */
	public static LocalDateTime toDateTime(String date, String time) {
		if (!isEmpty(date) && !isEmpty(time)) {
			String dateTime = date.concat(" ").concat(time);
			return toDateTime(dateTime);
		}
		
		return null;
	}

	/**
	 * Convert date and time to <code>java.time.LocalDateTime</code>
	 * @author Wesley Luiz
	 * @param date
	 * @param time
	 * @return
	 */
	public static LocalDateTime toDateTime(String dateTime) {
		if (!isEmpty(dateTime) ) {
			return parseToLocalDateTime(dateTime, DateTimeFormatter.ISO_INSTANT, BR_DATE_TIME_PATTERN, EN_DATE_TIME_PATTERN);
		}
		
		return null;
	}

	/**
	 * Convert date to <code>java.time.LocalDate</code>
	 * @author Wesley Luiz
	 * @param date
	 * @param time
	 * @return
	 */
	public static LocalDate toDate(String date) {
		if (!isEmpty(date)) {
			return parseToLocalDate(date,  EN_DATE_PATTERN, BR_DATE_PATTERN, DateTimeFormatter.ISO_INSTANT);
		}
		
		return null;
	}
	
	private static LocalDate parseToLocalDate(String date, DateTimeFormatter... formatters) {
		
		int index = 0;
		
		do {
			try {
				return LocalDate.parse(date, formatters[index]);
			} catch (DateTimeParseException e) {
				index++;
			} catch (IndexOutOfBoundsException e) {
				throw new RuntimeException("Any format was found");
			}
			
		} while(true);
	}
	
	private static LocalDateTime parseToLocalDateTime(String dateTime, DateTimeFormatter... formatters) {
		int index = 0;
		
		do {
			try {
				return LocalDateTime.parse(dateTime, formatters[index]);
			} catch (DateTimeParseException e) {
				index++;
			} catch (IndexOutOfBoundsException e) {
				throw new RuntimeException("Any format was found");
			}
			
		} while(true);
	}

	/**
	 * Convert time to <code>java.time.LocalTime</code>
	 * @author Wesley Luiz
	 * @param time
	 * @return
	 */
	public static LocalTime toTime(String time) {
		if (!isEmpty(time)) {
			return LocalTime.parse(time, TIME_PATTERN);
		}
		
		return null;
	}
	
	/**
	 * Convert time to <code>java.time.LocalTime</code>
	 * @author Wesley Luiz
	 * @param time
	 * @return
	 */
	public static LocalTime toTime(Date time) {
		return getZoneFromDate(time).toLocalTime();
	}
	
	/**
	 * Convert date to <code>java.utilDate</code>
	 * @author Wesley Luiz
	 * @param date
	 * @return
	 */
	public static Date toDate(TemporalAccessor date) {
		if (date != null && date instanceof LocalDate) {
			return Date.from(((LocalDate) date).atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else if (date != null && date instanceof LocalDateTime) {
			return Date.from(((LocalDateTime) date).atZone(ZoneId.systemDefault()).toInstant());
		}
		
		return null;
	}
	
	/**
	 * Convert date to <code>java.time.LocalDate</code>
	 * @author Wesley Luiz
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
		return getZoneFromDate(date).toLocalDate();
	}
	
	/**
	 * Convert date to <code>java.time.LocaDateTime</code>
	 * @author Wesley Luiz
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return getZoneFromDate(date).toLocalDateTime();
	}
	
	/**
	 * Parse date and time to <code>java.lang.String</code> 
	 * @author Wesley Luiz
	 * @param date
	 * @param time
	 * @return
	 */
	public static String parseDateTime(LocalDate date, LocalTime time) {
		return parseDateTime(LocalDateTime.of(date, time));
	}
	
	/**
	 * Parse dateTime to <code>java.lang.String</code> 
	 * @author Wesley Luiz
	 * @param dateTime
	 * @return
	 */
	public static String parseDateTime(TemporalAccessor dateTime) {
		if (dateTime != null) {
			return EN_DATE_TIME_PATTERN.format(dateTime);
		}
		
		return null;
	}

	public static String parseDateTime(TemporalAccessor dateTime, String format) {
		if (dateTime != null) {
			return DateTimeFormatter.ofPattern(format).format(dateTime);
		}
		
		return null;
	}

	/**
	 * Parse date to <code>java.lang.String</code> 
	 * @author Wesley Luiz
	 * @param date
	 * @return
	 */
	public static String parseDate(TemporalAccessor date) {
		if (date != null) {
			return EN_DATE_PATTERN.format(date);
		}
		
		return null;
	}
	
	/**
	 * Parse time to <code>java.lang.String</code> 
	 * @author Wesley Luiz
	 * @since 25 de set de 2017
	 * @param time
	 * @return
	 */
	public static String parseTime(TemporalAccessor time) {
		if (time != null) {
			return TIME_PATTERN.format(time);
		}
		
		return null;
	}
	
	private static ZonedDateTime getZoneFromDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault());
	}

	/**
	 * Convert any local date time to universal date time
	 * @author Wesley Luiz
	 * @param dateTime
	 * @return
	 */
	public static ZonedDateTime toUTCDateTime(ZonedDateTime dateTime) {
		ZoneId zoneId = ZoneId.of("UTC");
		return dateTime.withZoneSameInstant(zoneId);
	}

	/**
	 * Convert any local date time to universal date time
	 * @author Wesley Luiz
	 * @param dateTime
	 * @param zoneId
	 * @return
	 */
	public static ZonedDateTime toUTCDateTime(LocalDateTime dateTime, ZoneId zoneId) {
		ZonedDateTime baseDateTime = ZonedDateTime.of(dateTime, zoneId);
		return toUTCDateTime(baseDateTime);
	}

	/**
	 * Convert any local date time to universal date time
	 * @author Wesley Luiz
	 * @param dateTime
	 * @param zoneId
	 * @return
	 */
	public static ZonedDateTime toUTCDateTime(LocalDateTime dateTime, String zoneId) {
		return toUTCDateTime(dateTime, ZoneId.of(zoneId));
	}
}
