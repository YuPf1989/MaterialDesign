package com.rain.materialdesign.net.mygsonconvert;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.rain.materialdesign.mvp.model.entity.BaseResp;
import com.rain.materialdesign.net.exception.ApiException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Author:rain
 * Date:2017/11/13 11:57
 * Description:
 */

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.e("response", "convert: response" + response);
        BaseResp re = mGson.fromJson(response, BaseResp.class);
        //关注的重点，自定义响应码中非0的情况，一律抛出ApiException异常。
        //这样，我们就成功的将该异常交给onError()去处理了。
        if (!re.isOK()) {
            value.close();
            throw new ApiException(re.getErrorCode(), re.getErrorMsg());
        }

        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        // mGson.toJson(re.getData()).getBytes()传输的直接是内层数据
        // data直接为null时，创建空对象
        Object data = re.getData();
        if (data == null) {
            data = new Object();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(mGson.toJson(data).getBytes());
        InputStreamReader reader = new InputStreamReader(bis, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
