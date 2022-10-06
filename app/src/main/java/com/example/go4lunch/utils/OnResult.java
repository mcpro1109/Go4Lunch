package com.example.go4lunch.utils;

public interface OnResult<T>{
    void onSuccess(T data);
    void onFailure();
}