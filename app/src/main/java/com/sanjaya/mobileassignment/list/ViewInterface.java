package com.sanjaya.mobileassignment.list;

import com.sanjaya.mobileassignment.viewmodel.UserListItem;

import java.util.List;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public interface ViewInterface {

    void setUpAdapterAndView(List<UserListItem> listOfData, boolean hasMore);

    void addNextDataSet(List<UserListItem> listOfData, boolean hasMore);

    void showErrorMessage(String error);

    void showLoadingIndicator();

}
