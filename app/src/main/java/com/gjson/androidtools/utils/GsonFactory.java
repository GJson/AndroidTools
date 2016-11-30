package com.gjson.androidtools.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gjson on 2016/10/21.
 * Name GjsonFactory
 * Version 1.0
 */
public class GsonFactory {

    private static Gson gson;

    private GsonFactory() {
    }

    public static Gson getInstance() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setLenient();
            gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            gsonBuilder.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            gson = gsonBuilder.create();
        }
        return gson;
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return df.parse(json.getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }
}
