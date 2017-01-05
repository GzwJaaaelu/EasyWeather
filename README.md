# EasyWeather
一个简易的天气App, 原型是根据第二行代码郭神最后一个天气项目.<br />
App样式参考了书上的样子但也有一些修改.<br />
项目使用到的第三方开源库如下:<br />
1.网络请求相关<br />
  compile 'com.squareup.retrofit2:retrofit:2.1.0'<br />
  compile 'com.google.code.gson:gson:2.3'<br />
  compile 'com.squareup.retrofit2:converter-gson:2.1.0'<br />
  compile 'com.squareup.okhttp3:okhttp:3.5.0'<br />
  compile 'com.squareup.okhttp3:logging-interceptor:3.5.0'<br />
 2.权限封装<br />
  compile 'com.mylhyl:acp:1.1.7'<br />
 3.日志打印<br />
  compile 'com.orhanobut:logger:1.15'<br />
 4.View绑定<br />
  compile 'com.jakewharton:butterknife:8.4.0'<br />
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'<br />
 5.图片加载<br />
  compile 'com.squareup.picasso:picasso:2.5.2'<br />
 6.本地数据存储<br />
  apply plugin: 'realm-android'<br />
  classpath "io.realm:realm-gradle-plugin:2.2.1"<br />
