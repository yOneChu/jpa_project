package com.jpabok.jpashop.domain.exception;

public class NotEnughStockException extends RuntimeException{


    public NotEnughStockException() {
        super();
    }

    public NotEnughStockException(String message) {
        super(message);
    }

    public NotEnughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnughStockException(Throwable cause) {
        super(cause);
    }

}
