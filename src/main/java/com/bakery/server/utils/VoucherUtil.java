package com.bakery.server.utils;

import com.bakery.server.model.request.VoucherRequest;

import java.util.Random;

public class VoucherUtil {
    private static final Random random = new Random(System.currentTimeMillis());

    public static String generate(int length) {
        VoucherRequest request = new VoucherRequest(length);
        return generate(request);
    }

    public static String generate(String pattern) {
        VoucherRequest request = new VoucherRequest(pattern);
        return generate(request);
    }

    public static String generate(VoucherRequest request) {
        StringBuilder sb = new StringBuilder();
        char[] chars = request.getCharset().toCharArray();
        char[] pattern = request.getPattern().toCharArray();

        if (request.getPrefix() != null) {
            sb.append(request.getPrefix());
        }

        for (char c : pattern) {
            if (c == VoucherRequest.PATTERN_PLACEHOLDER) {
                sb.append(chars[random.nextInt(chars.length)]);
            } else {
                sb.append(c);
            }
        }

        if (request.getPostfix() != null) {
            sb.append(request.getPostfix());
        }

        return sb.toString();
    }
}
