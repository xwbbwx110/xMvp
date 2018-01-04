/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.net.retrofit.converter;

import com.google.gson.TypeAdapter;
import com.xwb.wb.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            LogUtils.e("ResponseBody", "MyGsonResponseBodyConverter convert begin" );
            T apiModel =  adapter.fromJson(value.charStream());
            LogUtils.e("ResponseBody", "MyGsonResponseBodyConverter convert");
//            if (ifapiModel.result .equals(ErrorCode.TOKEN_NOT_EXIST) ) {
//                throw new TokenNotExistException();
//            } else if (apiModel.result .equals( ErrorCode.TOKEN_INVALID)) {
//                Log.d("xxx5", "throw TokenInvalidException ");
//                throw new TokenInvalidException();
//            }
//            else if (!apiModel.success) {
//                // 特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
//                throw new ApiException();
//            } else if (apiModel.success) {
//                return apiModel.data;
//            }
            return apiModel;
        } finally {
            value.close();
        }
//        return null;
//        try {
//            ApiModel apiModel = (ApiModel)adapter.fromJson(value.charStream());
//            Log.d("xxx5", "model111111 = " + apiModel.code);
//            return apiModel;
//        } finally {
//            value.close();
//        }
    }
}
