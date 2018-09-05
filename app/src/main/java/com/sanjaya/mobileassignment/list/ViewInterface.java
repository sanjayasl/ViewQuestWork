package com.sanjaya.mobileassignment.list;

import com.sanjaya.mobileassignment.viewmodel.UserListItem;

import java.util.List;

public interface ViewInterface {

    void setUpAdapterAndView(List<UserListItem> listOfData, boolean hasMore);

    void addNextDataSet(List<UserListItem> listOfData, boolean hasMore);

    void showErrorMessage(String error);

    void showLoadingIndicator();

}
