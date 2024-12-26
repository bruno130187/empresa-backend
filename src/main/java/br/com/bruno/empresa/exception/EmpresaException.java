package br.com.bruno.empresa.exception;

public class EmpresaException extends RuntimeException {

    public EmpresaException(String message) {
        super(String.valueOf(message));
    }

}
