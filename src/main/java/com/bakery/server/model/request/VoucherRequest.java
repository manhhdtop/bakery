package com.bakery.server.model.request;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class VoucherRequest {
    public final static char PATTERN_PLACEHOLDER = '#';

    private final int length;
    private String charset;
    private String prefix;
    private String postfix;
    private final String pattern;

    public VoucherRequest(Integer length) {
        this(length, null, null, null, null);
    }

    public VoucherRequest(String pattern) {
        this(null, null, null, null, pattern);
    }

    public VoucherRequest(int length, String pattern) {
        this(length, null, null, null, pattern);
    }

    public VoucherRequest(Integer length, String charset, String prefix, String postfix, String pattern) {
        if (length == null) {
            length = 8;
        }

        if (charset == null) {
            charset = Charset.ALPHANUMERIC.value;
        }

        if (pattern == null) {
            char[] chars = new char[length];
            Arrays.fill(chars, PATTERN_PLACEHOLDER);
            pattern = new String(chars);
        }

        this.length = length;
        this.charset = charset;
        this.prefix = prefix;
        this.postfix = postfix;
        this.pattern = pattern;
    }

    public void withCharset(String charset) {
        this.charset = charset;
    }

    public void withPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void withPostfix(String postfix) {
        this.postfix = postfix;
    }

    public enum Charset {
        ALPHABETIC("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        ALPHANUMERIC("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        NUMBERS("0123456789");

        private String value;

        Charset(String value) {
            this.value = value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
