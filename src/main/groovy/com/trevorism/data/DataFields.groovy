package com.trevorism.data

class DataFields {

    static Object valueOf(Map<String, Object> row, String field) {
        if (row == null || field == null)
            return null
        if (row.containsKey(field))
            return row[field]

        def match = row.find { k, v -> k?.equalsIgnoreCase(field) }
        return match?.value
    }

    static boolean isStructured(Object value) {
        return value instanceof Collection || value instanceof Map || value?.class?.isArray()
    }

    static int compare(Object left, Object right) {
        if (left == null && right == null)
            return 0
        if (left == null)
            return -1
        if (right == null)
            return 1

        if (left instanceof Comparable && right instanceof Comparable && !isStructured(left) && !isStructured(right)) {
            try {
                return (left <=> right)
            } catch (Exception ignored) {
            }
        }

        return left.toString() <=> right.toString()
    }
}
