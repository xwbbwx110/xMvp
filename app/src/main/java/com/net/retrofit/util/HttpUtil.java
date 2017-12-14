package com.net.retrofit.util;



import com.net.retrofit.HttpResult;
import com.net.retrofit.http.ApiException;
import com.net.retrofit.http.ProgressSubscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //   ┃　　　┃   神兽保佑
 * //   ┃　　　┃   代码无BUG！
 * //   ┃　　　┗━━━┓
 * //   ┃　　　　　　　┣┓
 * //   ┃　　　　　　　┏┛
 * //   ┗┓┓┏━┳┓┏┛
 * //     ┃┫┫　┃┫┫
 * //     ┗┻┛　┗┻┛
 * <p>
 * Created by helin on 2016/10/10 11:32.
 */

public class HttpUtil {

    /**
     * 构造方法私有
     */
    private HttpUtil() {

    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {
                    subscriber.onNext(data);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public <T> Observable getSubscribe(Observable<HttpResult<T>> ob) {
        return  ob.map(new Function<HttpResult<T>, T>() {
            @Override
            public T apply(HttpResult<T> result) throws Exception {
                if (result.list != null){
                    return result.list;
                }else if (result.data != null){
                    return result.data;
                }else{
                    if (result.code == 500){
                        throw new ApiException(result.msg);
                    }else{
                        return (T) result.msg;
                    }
                }
            }
        });
    }


    public void toSubscribe(Observable ob,  Observer subscriber) {
        getSubscribe(ob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void toProgressSubscribe(Observable ob, final ProgressSubscriber subscriber) {
        getSubscribe(ob).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                //显示Dialog和一些其他操作
                subscriber.showProgressDialog();
            }
        }).subscribe(subscriber);
    }

}
