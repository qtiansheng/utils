# utils
Android常用工具类,整理一些公共的工具类，方便使用。

增加RxJava + retrofit2.0 +Glide 的简单封装使用。


参考:
https://github.com/Tamicer/RetrofitClient
http://code.wequick.net/Small/cn/home

清除公共库：
gradlew cleanLib -q
编译公共库：
gradlew buildLib -q -Dbundle.arch=armeabi-v7a
编译业务单元：
gradlew buildBundle -q -Dbundle.arch=armeabi-v7a