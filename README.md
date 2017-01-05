# EasyWeather
一个简易的天气App, 原型是根据第二行代码郭神最后一个天气项目.
App样式参考了书上的样子但也有一些修改.
项目使用到的第三方开源库如下:
1.网络请求相关
  compile 'com.squareup.retrofit2:retrofit:2.1.0'
  compile 'com.google.code.gson:gson:2.3'
  compile 'com.squareup.retrofit2:converter-gson:2.1.0'
  compile 'com.squareup.okhttp3:okhttp:3.5.0'
  compile 'com.squareup.okhttp3:logging-interceptor:3.5.0'
 2.权限封装
  compile 'com.mylhyl:acp:1.1.7'
 3.日志打印
  compile 'com.orhanobut:logger:1.15'
 4.View绑定
  compile 'com.jakewharton:butterknife:8.4.0'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
 5.图片加载
  compile 'com.squareup.picasso:picasso:2.5.2'
 6.本地数据存储
  apply plugin: 'realm-android'
  classpath "io.realm:realm-gradle-plugin:2.2.1"
