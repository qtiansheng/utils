package com.example.test.utils;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by wondertek on 2018/08/29.
 */

public interface GitHubService {

  /*  @GET("noAuth/update.msp")
    Observable<Result<String>> getUpdate(@QueryMap Map<String, String> options);*/

 /*   @FormUrlEncoded
    @POST(path)
    Observable<CommonResult<String>> signNew_isSign(@FieldMap Map<String, String> options);*/


    @POST("batchCheck")
    Observable<ResponseBody> signNew_isSign(@Body Map<String, Object> options);

}
