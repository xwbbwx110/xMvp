/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.net.retrofit.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class ProxyHandler implements InvocationHandler {

    private final static String TAG = "Token_Proxy";

    private final static String TOKEN = "token";

    private final static int REFRESH_TOKEN_VALID_TIME = 30;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean mIsTokenNeedRefresh;

    private Object mProxyObject;

    public ProxyHandler(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(null).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {
                try {
                    try {
                        if (mIsTokenNeedRefresh) {
                            updateMethodToken(method, args);
                        }
                        return (Observable<?>) method.invoke(mProxyObject, args);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Throwable throwable) {
//                        if (throwable instanceof TokenInvalidException) {
                            Log.d("xxx5", "catch TokenInvalidException ");
//                            return refreshTokenWhenTokenInvalid();
//                        } else if (throwable instanceof TokenNotExistException) {
//                            // Token 不存在，执行退出登录的操作。（为了防止多个请求，都出现 Token 不存在的问题，
//                            // 这里需要取消当前所有的网络请求）
//                            mGlobalManager.exitLogin();
//                            return Observable.error(throwable);
//                        }
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {
            // Have refreshed the token successfully in the valid time.
            Map<String, String> params = new HashMap<>();
            params.put("refreshToken", "19935-10d4eccca8374c4f908f3026f6575130");
            params.put("token", "19935-c65328331df145d4a1f8f4a20d360bb0");

            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                mIsTokenNeedRefresh = true;
                return Observable.just(true);
            } else {
                // call the refresh token api.
                Log.d("xxx5", "call the refresh token api. ");
//                RetrofitUtil.getInstance().get(IApiService.class).refreshToken(params).subscribe(new Subscriber<ApiModel<RefreshTokenRequest>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mRefreshTokenError = e;
//                    }
//
//                    @Override
//                    public void onNext(ApiModel<RefreshTokenRequest> model) {
//                        if (model != null) {
//                            mIsTokenNeedRefresh = true;
//                            tokenChangedTime = new Date().getTime();
//                            Log.d(TAG, "Refresh token success, time = " + tokenChangedTime);
//
//                            Log.d("xxx5", "token= " + model.data.token);
//                        }
//                    }
//                });
                if (mRefreshTokenError != null) {
                    return Observable.error(mRefreshTokenError);
                } else {
                    return Observable.just(true);
                }
            }
        }
    }

    /**
     * Update the token of the args in the method.
     *
     * PS： 因为这里使用的是 GET 请求，所以这里就需要对 Query 的参数名称为 token 的方法。
     * 若是 POST 请求，或者使用 Body ，自行替换。因为 参数数组已经知道，进行遍历找到相应的值，进行替换即可（更新为新的 token 值）。
     */
    private void updateMethodToken(Method method, Object[] args) {
//        if (mIsTokenNeedRefresh && !TextUtils.isEmpty(GlobalToken.getToken())) {
//            Annotation[][] annotationsArray = method.getParameterAnnotations();
//            Annotation[] annotations;
//            if (annotationsArray != null && annotationsArray.length > 0) {
//                for (int i = 0; i < annotationsArray.length; i++) {
//                    annotations = annotationsArray[i];
//                    for (Annotation annotation : annotations) {
//                        if (annotation instanceof Query) {
//                            if (TOKEN.equals(((Query) annotation).value())) {
//                                args[i] = GlobalToken.getToken();
//                            }
//                        }
//                    }
//                }
//            }
//            mIsTokenNeedRefresh = false;
//        }
    }
}
