package com.trevorism.data

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class DatastoreJsonDeserializer {

    private final Gson gson

    DatastoreJsonDeserializer(Gson gson) {
        this.gson = gson
    }

    def <T> T deserialize(String json, Class<T> clazz) {
        JsonElement root = JsonParser.parseString(json)
        if (!root.isJsonObject())
            return gson.fromJson(root, clazz)

        return gson.fromJson(restore(root.getAsJsonObject(), clazz), clazz)
    }

    private static JsonObject restore(JsonObject stored, Class clazz) {
        JsonObject restored = new JsonObject()
        stored.entrySet().each { entry ->
            Field field = findField(clazz, entry.key)
            if (field)
                restored.add(field.name, restoreValue(entry.value, field.type))
        }
        return restored
    }

    private static JsonElement restoreValue(JsonElement value, Class type) {
        if (!isFlattenedByDatastore(type))
            return value
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString())
            return value

        try {
            return JsonParser.parseString(value.getAsString())
        } catch (Exception ignored) {
            return value
        }
    }

    private static boolean isFlattenedByDatastore(Class type) {
        if (Collection.isAssignableFrom(type) || Map.isAssignableFrom(type) || type.isArray())
            return true

        return !type.isPrimitive() && !type.isEnum() && !type.name.startsWith("java.")
    }

    private static Field findField(Class clazz, String name) {
        for (Class current = clazz; current != null && current != Object; current = current.superclass) {
            Field match = current.declaredFields.find {
                !it.synthetic && !Modifier.isStatic(it.modifiers) && it.name.equalsIgnoreCase(name)
            }
            if (match)
                return match
        }
        return null
    }
}
