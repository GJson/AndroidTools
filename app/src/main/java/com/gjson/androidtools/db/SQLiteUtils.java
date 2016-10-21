package com.gjson.androidtools.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.gjson.androidtools.utils.GsonFactory;
import com.gjson.androidtools.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description：DB OPERATOR UTILS.
 * <p/>
 * Notice: Cursor的字段名或者别名一定要和Class的属性一致.
 * <p/>
 * Created by gjson on 16/10/21.
 * <p/>
 * Name SQLiteUtils
 * <p/>
 * Version 1.0
 */
public class SQLiteUtils {



    private final static String LOG_TAG = "DOOIOO_SQLiteUtils";
    private final static StringBuffer mBuffer = new StringBuffer();

    /**
     * 根据提供的class类型生成建表sql语句</br> 基本数据类型会以相应对应格式存储,如String以TEXT格式等.
     * List类型会转换为JSON字串后存入到数据库中.
     *
     * @param classs       提供的class类型
     * @param tableName    表名(可以为空，为空时，以类名作为表名)
     * @param extraColumns 除了class对象基本属性列外,可以添加额外的表列,且额外的表列属性均为TEXT
     * @return 建表sql语句字串
     */
    public static String createTableSql(Class<?> classs, String tableName, String... extraColumns) {
        mBuffer.setLength(0);
        mBuffer.append("CREATE TABLE IF NOT EXISTS ");
        if (!TextUtils.isEmpty(tableName)) {
            mBuffer.append(tableName);
        } else {
            String className = classs.getSimpleName();
            if (className.contains("[]")) {
                throw new IllegalArgumentException("Can not create array class table");
            }
            mBuffer.append(classs.getSimpleName());
        }
        mBuffer.append("(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        Field[] fields = classs.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.getType() == Integer.class) {
                mBuffer.append(field.getName() + " INTEGER,");
            } else if (field.getType() == Double.class || field.getType() == Float.class) {
                mBuffer.append(field.getName() + " REAL,");
            } else if (field.getType() == String.class) {
                mBuffer.append(field.getName() + " TEXT,");
            } else if (field.getType() == Boolean.class) {
                mBuffer.append(field.getName() + " INTEGER,");
            } else if (field.getType() == List.class || field.getType() == ArrayList.class) {
                mBuffer.append(field.getName() + " TEXT,");
            }
        }
        if (extraColumns != null && extraColumns.length != 0) {
            for (int i = 0; i < extraColumns.length; i++) {
                mBuffer.append(extraColumns[i]);
                if (i != extraColumns.length - 1) {
                    mBuffer.append(",");
                } else {
                    mBuffer.append(")");
                }
            }
        }
        return mBuffer.toString();
    }


    /**
     * 将对象插入到指定的数据库表中</br> 如果对象属性中包含List类型,将List泛型类型通过Gson转换为json字串后以 <h1>
     * 泛型类型具体类名:List转换后的字串形式</h1></br> 具体如：{"calssName":"Json value"}形式.
     *
     * @param obj                 待插入数据对象(仅支持基本类型和String对象，不能存储对象类型)
     * @param db                  数据库对象
     * @param tableName           表名
     * @param extraColumnAndValue 除了class对象属性值外,需要添加的额外的列和值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException </code>
     */
    public static void storeObject(Object obj, SQLiteDatabase db, String tableName, HashMap<String, String> extraColumnAndValue)
            throws IllegalAccessException, IllegalArgumentException {
        Field[] fields = obj.getClass().getDeclaredFields();
        ContentValues contentValue = new ContentValues();
        if (extraColumnAndValue != null) {
            Set<String> keySet = extraColumnAndValue.keySet();
            for (String key : keySet) {
                contentValue.put(key, extraColumnAndValue.get(key));
            }
        }
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Class<?> type = field.getType();
            if (type == int.class || type == Integer.class) {
                Integer value = (Integer) field.get(obj);
                if (value != null) {
                    contentValue.put(fieldName, value);
                } else {
                    contentValue.putNull(fieldName);
                }
            } else if (type == float.class || type == Float.class) {
                Float value = (Float) field.get(obj);
                if (value != null) {
                    contentValue.put(fieldName, value);
                } else {
                    contentValue.putNull(fieldName);
                }
            } else if (type == double.class || type == Double.class) {
                Double value = (Double) field.get(obj);
                if (value != null) {
                    contentValue.put(fieldName, value);
                } else {
                    contentValue.putNull(fieldName);
                }
            } else if (type == String.class) {
                String value = (String) field.get(obj);
                if (value != null) {
                    contentValue.put(fieldName, value);
                } else {
                    contentValue.putNull(fieldName);
                }
            } else if (type == Boolean.class) {
                Boolean value = (Boolean) field.get(obj);
                if (value != null) {
                    contentValue.put(fieldName, value == true ? 1 : 0);
                } else {
                    contentValue.putNull(fieldName);
                }
            } else if (type == List.class || type == ArrayList.class) {
                // 获取List中的泛型参数类型
                String className = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].toString().split(" ")[1];
                List<?> list = (List<?>) field.get(obj);
                JSONArray jsonArray = new JSONArray();
                if (list != null) {
                    try {
                        for (int i = 0; i < list.size(); i++) {
                            String json = GsonFactory.getInstance().toJson(list.get(i));
                            jsonArray.put(i, json);
                        }
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put(className, jsonArray.toString());
                        contentValue.put(fieldName, jsonObj.toString());
                    } catch (Exception e) {
                        contentValue.putNull(fieldName);
                    }
                } else {
                    contentValue.putNull(fieldName);
                }
            }
        }
        db.beginTransaction();
        db.insert(tableName, null, contentValue);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 通过SQL语句获得对应的object(无查询参数)
     *
     * @param db
     * @param sql
     * @param classs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object sql2Object(SQLiteDatabase db, String sql, Class classs) {
        Cursor cursor = db.rawQuery(sql, null);
        return cursor2Object(cursor, classs);
    }

    /**
     * 通过SQL语句获得对应的object(有查询参数)
     *
     * @param db
     * @param sql
     * @param selectionArgs
     * @param classs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object sql2Object(SQLiteDatabase db, String sql,
                                    String[] selectionArgs, Class classs) {
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor2Object(cursor, classs);
    }


    /**
     * 通过SQL语句获得对应的object的List。(无查询参数)
     * Cursor的字段名或者别名一定要和object的成员名一样
     *
     * @param db
     * @param sql
     * @param classs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List sql2ObjectList(SQLiteDatabase db, String sql, Class classs) {
        Cursor cursor = db.rawQuery(sql, null);
        return cursor2ObjectList(classs, cursor);
    }

    /**
     * 通过SQL语句获得对应的object的List。(有查询参数)
     * Cursor的字段名或者别名一定要和object的成员名一样
     *
     * @param db
     * @param sql
     * @param classs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List sql2ObjectList(SQLiteDatabase db, String sql, String[] selectionArgs, Class classs) {
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor2ObjectList(classs, cursor);
    }


    /**
     * /**
     * 通过Cursor转换成对应的Object。
     * 注意：Cursor里的字段名（可用别名）必须要和Object的属性名一致
     *
     * @param cursor
     * @param classs
     * @return
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public static Object cursor2Object(Cursor cursor, Class classs) {
        if (cursor == null) {
            return null;
        }
        Object obj;
        int i = 1;
        try {
            cursor.moveToNext();
            obj = setValues2Fields(cursor, classs);

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(LOG_TAG, "ERROR @：cursor2Object");
            return null;
        } finally {
            cursor.close();
        }
    }

    /**
     * 通过Cursor转换成对应的Object集合。
     * 注意：Cursor里的字段名（可用别名）必须要和Object的属性名一致
     *
     * @param cursor
     * @param classs
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List cursor2ObjectList(Class classs, Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List list = new ArrayList();
        Object obj;
        try {
            while (cursor.moveToNext()) {
                obj = setValues2Fields(cursor, classs);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(LOG_TAG, "ERROR @：cursor2ObjectList");
            return null;
        } finally {
            cursor.close();
        }
    }

    /**
     * 把cusor的值赋值与类属性
     *
     * @param classs
     * @param cursor
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private static Object setValues2Fields(Cursor cursor, Class classs)
            throws Exception {
        String[] columnNames = cursor.getColumnNames();// 字段数组
        Object obj = classs.newInstance();
        Field[] fields = classs.getFields();

        for (Field _field : fields) {
            Class<? extends Object> typeClass = _field.getType();// 属性类型

            //依次遍历拿到column和class 属性一致，方可赋值；
            //如果直接赋值 当class 和table 中属性不一致时 会造成exception；故而需要遍历cursor的columns

            for (int j = 0; j < columnNames.length; j++) {
                String columnName = columnNames[j];
                typeClass = getBasicClass(typeClass);
                boolean isBasicType = isBasicType(typeClass);

                if (isBasicType) {


                    if (columnName.equalsIgnoreCase(_field.getName())) {
                        String _str = cursor.getString(cursor.getColumnIndex(columnName));
                        if (_str == null) {
                            break;
                        }
                        _str = _str == null ? "" : _str;
                        if (typeClass.equals(Boolean.class)) {
                            _str = _str.equals("1") ? "true" : _str;

                        }
                        Constructor<? extends Object> cons = typeClass
                                .getConstructor(String.class);
                        Object attribute = cons.newInstance(_str);
                        _field.setAccessible(true);
                        _field.set(obj, attribute);
                        break;
                    }
                } else {
                    Object obj2 = setValues2Fields(cursor, typeClass);// 递归
                    _field.set(obj, obj2);
                    break;
                }

            }
        }
        return obj;
    }

    /**
     * 判断是否为基本类型
     *
     * @param typeClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static boolean isBasicType(Class typeClass) {
        if (typeClass.equals(Integer.class) || typeClass.equals(Long.class)
                || typeClass.equals(Float.class)
                || typeClass.equals(Double.class)
                || typeClass.equals(Boolean.class)
                || typeClass.equals(Byte.class)
                || typeClass.equals(Short.class)
                || typeClass.equals(String.class)) {

            return true;

        } else {
            return false;
        }
    }

    /**
     * 获得包装类
     *
     * @param typeClass
     * @return
     */
    @SuppressWarnings("all")
    public static Class<? extends Object> getBasicClass(Class typeClass) {
        Class _class = basicMap.get(typeClass);
        if (_class == null)
            _class = typeClass;
        return _class;
    }

    @SuppressWarnings("rawtypes")
    private static Map<Class, Class> basicMap = new HashMap<Class, Class>();

    static {
        basicMap.put(byte.class, Byte.class);
        basicMap.put(short.class, Short.class);
        basicMap.put(int.class, Integer.class);
        basicMap.put(long.class, Long.class);
        basicMap.put(float.class, Float.class);
        basicMap.put(double.class, Double.class);
        basicMap.put(boolean.class, Boolean.class);
    }

}
