package com.hdq.identity_service.utils;

import java.util.Set;

public class SetUtil {
    public static <E> boolean isEmpty(Set<E> set) {
        return set == null || set.isEmpty();
    }

    public static <E> boolean isNotEmpty(Set<E> set) {
        return set != null && !set.isEmpty();
    }

}
