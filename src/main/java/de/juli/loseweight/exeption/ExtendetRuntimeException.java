package de.juli.loseweight.exeption;

/**
 * Eine abstrakte Erweiterung der {@link RuntimeException} mit erweiterter
 * Informationsausgabe.<br>
 * <br>
 * @author ulrichkloodt
 *
 */
public abstract class ExtendetRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	ExtendetRuntimeException(String msg) {
		super(msg);
	}

	ExtendetRuntimeException(RuntimeException e) {
		super(e);
	}

	ExtendetRuntimeException(String msg, RuntimeException e) {
		super(msg, e);
	}

	ExtendetRuntimeException() {
		super();
	}

	static String getMsg(String pattern, Object... values) {
		String msg = pattern;
		int i = 0;
		while (msg.contains("%s") && i < values.length) {
			msg = msg.replaceFirst("%s", String.format("%s", values[i]));
			i++;
		}
		return msg;
	}
}
