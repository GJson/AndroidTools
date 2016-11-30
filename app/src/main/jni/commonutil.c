//
// Created by gjson on 2016/11/25.
//

#include  <string.h>
#include  <jni.h>

const char *hello ="hello  i  am  jni ";
const char *converValue="welcome kfdsfahfskjfif33";

jstring
Java_com_gjson_androidtools_utils_GjsonJni_sayHello
        (JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, hello);
}

jstring
Java_com_gjson_androidtools_utils_GjsonJni_converValue
        (JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, converValue);
}
