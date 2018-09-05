package com.sanjaya.mobileassignment.list;

import android.arch.lifecycle.LifecycleObserver;
import android.util.Log;

import com.sanjaya.mobileassignment.data.UserDataSourceInterface;
import com.sanjaya.mobileassignment.util.BaseSchedulerProvider;
import com.sanjaya.mobileassignment.viewmodel.ListViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.DisposableSubscriber;

public class ListPresenter implements LifecycleObserver {

    private boolean isStart = false;
    private ViewInterface view;
    private UserDataSourceInterface dataSource;
    private CompositeDisposable disposables;
    private BaseSchedulerProvider scheduler;

    @Inject
    public ListPresenter(ViewInterface view,
                         UserDataSourceInterface dataSource,
                         BaseSchedulerProvider scheduler) {
        this.view = view;
        this.dataSource = dataSource;
        this.disposables = new CompositeDisposable();
        this.scheduler = scheduler;
    }

    public void start(int offset, int limit) {
        isStart = true;
        getListFromDataSource(offset, limit);
    }

    public void next(int offset, int limit) {
        isStart = true;
        getNextDataSetFromDataSource(offset, limit);
    }

    public void stop() {
        isStart = false;
        disposables.clear();
    }

    public boolean isStart() {
        return isStart;
    }

    public void getListFromDataSource(int offset, int limit) {
        disposables.add(
                dataSource.getUser(offset, limit)
                        .observeOn(scheduler.getUiScheduler())
                        .startWith(
                                ListViewModel.loading()
                        )
                        .onErrorReturn(
                                //handle exceptions which occur outside of the response object from retrofit
                                new Function<Throwable, ListViewModel>() {
                                    @Override
                                    public ListViewModel apply(Throwable throwable) throws Exception {

                                        return ListViewModel.error(throwable.getMessage());
                                    }
                                }
                        )
                        .subscribeWith(new DisposableSubscriber<ListViewModel>() {
                            @Override
                            public void onNext(ListViewModel uiModel) {
                                if (uiModel.hasError()) {
                                    view.showErrorMessage(uiModel.getErrorMessage());
                                    //view.startMainActivity();
                                } else if (uiModel.isLoading()) {
                                    view.showLoadingIndicator();
                                } else {
                                    view.setUpAdapterAndView(uiModel.getUserList(), uiModel.hasMore());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.d("ERROR", t.getMessage() + " " + t.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("Data", "data loading compeleted.");
                            }
                        })

        );
    }

    public void getNextDataSetFromDataSource(int offset, int limit){
        disposables.add(
                dataSource.getUser(offset, limit)
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(new DisposableSubscriber<ListViewModel>() {
                            @Override
                            public void onNext(ListViewModel uiModel) {
                                if (uiModel.hasError()) {
                                    view.showErrorMessage(uiModel.getErrorMessage());
                                } else {
                                    view.addNextDataSet(uiModel.getUserList(), uiModel.hasMore());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.d("ERROR", t.getMessage() + " " + t.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("Data", "data loading compeleted.");
                            }
                        })
        );
    }

}
