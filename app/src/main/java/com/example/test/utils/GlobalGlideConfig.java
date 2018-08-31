
package com.example.test.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.example.test.utils.okhttps.HttpsUtils;


import java.io.InputStream;

/**
 * A {@link GlideModule} implementation to replace Glide's default
 * {@link java.net.HttpURLConnection} based {@link com.bumptech.glide.load.model.ModelLoader} with an OkHttp based
 * {@link com.bumptech.glide.load.model.ModelLoader}.
 *
 * <p>
 *     If you're using gradle, you can include this module simply by depending on the aar, the module will be merged
 *     in by manifest merger. For other build systems or for more more information, see
 *     {@link GlideModule}.
 * </p>
 */

@GlideModule
public class GlobalGlideConfig extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Do nothing.
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //配置glide网络加载框架
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpsUtils.getOKhttpsClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        //不使用清单配置的方式,减少初始化时间
        return false;
    }
}

