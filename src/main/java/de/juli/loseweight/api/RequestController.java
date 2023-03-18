package de.juli.loseweight.api;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Superklasse für die Page-Controller mit Basis-Feldern und Funktionen<br>
 * <br>
 * @author ulrichkloodt
 *
 */
public abstract class RequestController {
	protected static final String LOGIN = "login";
	protected static final String MSG = "msg";
	
	/**
	 * Session Verwaltung holt die Session aus dem aktuellen Request
	 * 
	 * @return
	 */
	protected static HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true);
	}

	/**
	 * Ein Objekt über dessen Namen aus dem Session-Objekt holen
	 * 
	 * @param key
	 * @return
	 */
	protected static Optional<Object> session(String key) {
		Object value = session().getAttribute(key);
		if(null == value) {
			return Optional.empty();
		}
		return Optional.of(value); 
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	protected static boolean isLogedIn() {
		Optional<Object> optional = session(LOGIN);
		if(optional.isPresent()) {
			return (boolean) optional.get();
		} else {
			return false;
		}
	}

	/**
	 * Ein Objekt über dessen Namen im Session-Objekt hinterlegen<br>
	 * <br>
	 * @param key
	 * @param value
	 */
	protected static void session(String key, Object value) {
		session().setAttribute(key, value);
	}
	
	/**
	 * Macht aus einem StackTraceElement Array einen String<br>
	 * <br>
	 * @param stackTrace
	 * @return
	 */
	protected String buildStacktrace(StackTraceElement[] stackTrace) {
		StringBuilder sb = new StringBuilder();
		Arrays.asList(stackTrace).forEach(entry -> sb.append(String.format("%s%n",  entry)));
		return sb.toString();
	}
}
