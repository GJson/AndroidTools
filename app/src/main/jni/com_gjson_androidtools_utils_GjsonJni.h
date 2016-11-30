//
// Created by clark on 2016/11/28.
//

#ifndef ANDROIDTOOLS_COM_GJSON_ANDROIDTOOLS_UTILS_GJSONJNI_H
#define ANDROIDTOOLS_COM_GJSON_ANDROIDTOOLS_UTILS_GJSONJNI_H

#endif //ANDROIDTOOLS_COM_GJSON_ANDROIDTOOLS_UTILS_GJSONJNI_H

JNIEXPORT jstring JNICALL Java_com_gjson_androidtools_utils_GjsonJni_sayHello
  (JNIEnv *, jclass);

JNIEXPORT jstring JNICALL Java_com_gjson_androidtools_utils_GjsonJni_converValue
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
