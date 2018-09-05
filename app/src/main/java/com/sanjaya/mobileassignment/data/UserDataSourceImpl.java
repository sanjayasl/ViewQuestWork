package com.sanjaya.mobileassignment.data;

import android.util.Log;

import com.sanjaya.mobileassignment.datamodel.UserDataModel;
import com.sanjaya.mobileassignment.error.EmptyDatasetException;
import com.sanjaya.mobileassignment.viewmodel.ListViewModel;
import com.sanjaya.mobileassignment.viewmodel.UserListItem;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public class UserDataSourceImpl  implements UserDataSourceInterface {

    private HerokuRestAdapter restAdapter;

    @Inject
    public UserDataSourceImpl(HerokuRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public Flowable<ListViewModel> getUser(int offset, int limit) {
        return
                restAdapter.getUsers(offset, limit)
                .flatMap(new Function<UserDataModel, Publisher<ListViewModel>>() {
                    @Override
                    public Publisher<ListViewModel> apply(UserDataModel userDataModel) throws Exception {
                        //Log.e("TAG", userDataModel.toString());
                        List<UserListItem> listItems = new ArrayList<>();
                        if(userDataModel == null || userDataModel.getData() == null || userDataModel.getData().getUsers().size() == 0){
                            throw new EmptyDatasetException();
                        }

                        for(UserDataModel.UserData.User user : userDataModel.getData().getUsers()){
                            listItems.add(
                                    new UserListItem(
                                            user.getName(),
                                            user.getImage(),
                                            isEven(user.getItems()),
                                            user.getItems()
                                    )
                            );
                        }

                        return Flowable.just(ListViewModel.success(listItems, userDataModel.getData().hasMore()));
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static final boolean isEven(ArrayList<String> items){
        if(items == null || items.size() == 0)
            return true;

        return ((items.size() % 2) == 0) ? true : false;
    }

}
