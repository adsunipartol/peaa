
package br.peaa.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ServicoException extends Exception {

    /**
     * Creates a new instance of
     * <code>LoginException</code> without detail message.
     */
    public ServicoException() {
    }

    /**
     * Constructs an instance of
     * <code>LoginException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServicoException(String msg) {
        super(msg);
    }

    public ServicoException(Throwable cause) {
        super(cause);
    }

    public ServicoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
