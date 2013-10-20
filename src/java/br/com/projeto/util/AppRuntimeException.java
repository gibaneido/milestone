/**
 * 
 */
package br.com.projeto.util;

import java.util.logging.Logger;

/**
 * @author mg
 * 
 */
@SuppressWarnings("serial")
public class AppRuntimeException extends RuntimeException {

    private static Logger logger = Logger.getLogger(AppRuntimeException.class.getName());

    public AppRuntimeException() {
        super();
    }

    public AppRuntimeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        logger.severe(arg0 + " - " + arg1);
    }

    public AppRuntimeException(String arg0) {
        super(arg0);
        logger.severe(arg0);
    }

    public AppRuntimeException(Throwable arg0) {
        super(arg0);
        logger.severe(arg0 + "");
    }

}
