/* Joel Goode - CMSC 350 - Project 2 11/8/22
 * This class is to handle Syntax exceptions and display a message.
 * Defines an unchecked exception that contains a constructor that allows a message to be supplied. 
 * It is thrown by the constructor of the Polynomial class 
 * should the supplied string contain coefficients or exponents of an improper type or should the exponents fail to be listed in strictly descending order. 
 */

import javax.swing.JOptionPane;

public class InvalidPolynomialSyntax extends RuntimeException{

    public InvalidPolynomialSyntax() {
    }

    public InvalidPolynomialSyntax(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message);
    }

    public InvalidPolynomialSyntax(Throwable cause) {
        super(cause);
        JOptionPane.showMessageDialog(null, cause);
    }

    public InvalidPolynomialSyntax(String message, Throwable cause) {
        super(message, cause);
        JOptionPane.showMessageDialog(null, message);
    }

    public InvalidPolynomialSyntax(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        JOptionPane.showMessageDialog(null, message);
    }
}
