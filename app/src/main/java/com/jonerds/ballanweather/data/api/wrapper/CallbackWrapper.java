package com.jonerds.ballanweather.data.api.wrapper;

import com.jonerds.ballanweather.ui.base.MvpView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class CallbackWrapper <T> extends DisposableObserver<T> {


    private MvpView mMvpView;

    public CallbackWrapper(MvpView mvpView) {
        mMvpView = mvpView;
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        handleError(mMvpView, e);
    }

    @Override
    public void onComplete() {

    }

    public static void handleError (MvpView mvpView, Throwable e){
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException)e).response().errorBody();
            mvpView.onUnknownError(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            mvpView.onTimeout();
        } else if (e instanceof IOException) {
            mvpView.onNetworkError();
        } else {
            mvpView.onUnknownError(e.getMessage());
        }
    }

    private static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}