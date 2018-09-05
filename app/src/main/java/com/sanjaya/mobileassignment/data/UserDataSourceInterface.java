package com.sanjaya.mobileassignment.data;

import com.sanjaya.mobileassignment.viewmodel.ListViewModel;

import io.reactivex.Flowable;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public interface UserDataSourceInterface {

    Flowable<ListViewModel> getUser(int offset, int limit);
}
