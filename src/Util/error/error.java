package Util.error;

import Util.position;

abstract public class error extends RuntimeException {
    private position pos;
    private String message;

    public error(String msg, position pos) {
        this.pos = pos != null ? pos : new position(0, 0);
        this.message = msg;
    }

    public String toString() {
        return message + ": " + pos.toString();
    }
}
