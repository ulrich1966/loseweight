package de.juli.loseweight.exeption;

/**
 * Eine Exception die geworfen werden kann, wenn Model nicht erzeugt werden
 * kann.<br>
 * <br>
 * @author ulrichkloodt
 *
 */
public class ModelCreateException extends ExtendetRuntimeException {
	private static final long serialVersionUID = 1L;

	public ModelCreateException() {
		super();
	}

	public ModelCreateException(String msg) {
		super(msg);
	}

	public ModelCreateException(String pattern, Object... values) {
		super(getMsg(pattern, values));
	}
}
