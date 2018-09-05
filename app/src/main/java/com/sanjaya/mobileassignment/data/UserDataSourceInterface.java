package com.sanjaya.mobileassignment.data;

import com.sanjaya.mobileassignment.viewmodel.ListViewModel;

import io.reactivex.Flowable;

public interface UserDataSourceInterface {

    Flowable<ListViewModel> getUser(int offset, int limit);
}
