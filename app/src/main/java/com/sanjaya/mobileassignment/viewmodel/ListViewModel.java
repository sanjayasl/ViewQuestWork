package com.sanjaya.mobileassignment.viewmodel;

import java.util.List;

public final class ListViewModel {
    final boolean hasError;
    final boolean isLoading;
    final boolean hasMore;
    final String errorMessage;
    private List<UserListItem> userList;

    public ListViewModel(boolean hasError, boolean isLoading, boolean hasMore, String errorMessage, List<UserListItem> userList) {
        this.hasError = hasError;
        this.isLoading = isLoading;
        this.hasMore = hasMore;
        this.errorMessage = errorMessage;
        this.userList = userList;
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<UserListItem> getUserList() {
        return userList;
    }

    public static ListViewModel loading(){
        return new ListViewModel(false,
                true, false, null, null);
    }

    public static ListViewModel success(List<UserListItem> repoList, boolean hasMore){
        return new ListViewModel(false, false, hasMore, null, repoList);
    }

    public static ListViewModel error(String errorMessage){
        return new ListViewModel(true, false, false, errorMessage, null);
    }

}
