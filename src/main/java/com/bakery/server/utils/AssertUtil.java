package com.bakery.server.utils;

import com.bakery.server.exception.BadRequestException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import static com.bakery.server.utils.MessageUtils.getMessage;


public class AssertUtil {
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void isBlank(String source, String message) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(source)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void isBlank(String source, Supplier<String> messageSupplier) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(source)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNotBlank(String source, String message) {
        if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void isNotBlank(String source, Supplier<String> messageSupplier) {
        if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void isFalse(boolean expression, Supplier<String> messageSupplier) {
        if (expression) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasLength(text)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasText(text)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BadRequestException(getMessage(message));
                }
            }
        }

    }

    public static void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BadRequestException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {

            for (Object element : collection) {
                if (element == null) {
                    throw new BadRequestException(getMessage(message));
                }
            }
        }

    }

    public static void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BadRequestException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BadRequestException(getMessage(message));
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return messageSupplier != null ? (String) messageSupplier.get() : null;
    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, (String) "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        notNull(type, (String) "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = obj != null ? obj.getClass().getName() : "null";
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + "Object of class [" + className + "] must be an instance of " + type;
        }

        throw new IllegalArgumentException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }
}
