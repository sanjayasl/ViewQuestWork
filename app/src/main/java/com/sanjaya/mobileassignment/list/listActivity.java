package com.sanjaya.mobileassignment.list;

import android.app.ListActivity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sanjaya.mobileassignment.R;
import com.sanjaya.mobileassignment.viewmodel.UserListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class listActivity extends AppCompatActivity implements ViewInterface {

    static final int VIEW_LIMIT = 10;
    private int offsetPage = 0;
    private List<UserListItem> listOfData = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Toolbar toolbar;
    private String user;
    private ContentLoadingProgressBar progressBar;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int idValue = 1001;

    @Inject
    ListPresenter listPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.rec_list_activity);
        progressBar = findViewById(R.id.pgb_list_activity);
        layoutInflater = getLayoutInflater();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if(!isLoading) {
                    isLoading = true;
                    offsetPage += 10;

                    loadNextPage();
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                linearLayoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        this,
                        R.drawable.divider_white
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.show();
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listPresenter.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!listPresenter.isStart())
            listPresenter.start(offsetPage, VIEW_LIMIT);
    }

    @Override
    public void setUpAdapterAndView(final List<UserListItem> listOfData, final boolean hasMore) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.hide();
                recyclerView.setVisibility(View.VISIBLE);

                addDataToRV(listOfData, hasMore);
            }
        });

//        addDataToRV(listOfData, hasMore);
    }

    @Override
    public void addNextDataSet(final List<UserListItem> listOfData, final boolean hasMore) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeLoaderAddDataToRV(listOfData, hasMore);
            }
        });
    }

    private void removeLoaderAddDataToRV(List<UserListItem> listOfData, boolean hasMore){
        this.adapter.removeLoadingFooter();
        isLoading = false;
        addDataToRV(listOfData, hasMore);
    }

    private void addDataToRV(List<UserListItem> listOfData, boolean hasMore){
        //this.listOfData.addAll(listOfData);
        this.adapter.addAll(listOfData);
        this.adapter.notifyDataSetChanged();

        if(hasMore)
            this.adapter.addLoadingFooter();
        else
            isLastPage = true;
    }

    private void loadNextPage(){
        listPresenter.next(offsetPage, VIEW_LIMIT);
    }

    private class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        // View Types
        private static final int ITEM = 0;
        private static final int LOADING = 1;

        private Context context;

        private boolean isLoadingAdded = false;
        private boolean retryPageLoad = false;

        private PaginationAdapterCallback mCallback;

        private String errorMsg;

        public CustomAdapter(Context context) {
            this.context = context;
            //this.mCallback = (PaginationAdapterCallback) context;
            // listOfData = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType){
                case ITEM:
                    View itemView = layoutInflater.inflate(R.layout.item_data, parent, false);
                    return new CustomViewHolder(itemView);
                case LOADING:
                    View loadView = layoutInflater.inflate(R.layout.item_progress, parent, false);
                    return new LoadingViewHolder(loadView);
            }
            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)){
                case ITEM:
                    initItemData((CustomViewHolder) holder, listOfData.get(position));
                    break;
                case LOADING:
                    initLoading((LoadingViewHolder) holder);
                    break;
            }
        }

        private final void initItemData(final CustomViewHolder customHolder,final UserListItem userItem){
            //Log.e("TAG", "user name : " + userItem.getName() + " items size: " + String.valueOf(userItem.getItems().size()));

            Glide.with(listActivity.this)
                    .load(userItem.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(customHolder.userAvatar);

            int count = 0;
            //customHolder.userName.setText(userItem.getName());
            customHolder.tableLayout.removeAllViews();
            if(!userItem.isEven()){
                //Log.e("TAG", "user odd image : " + userItem.getItems().get(0));
                customHolder.tableLayout.addView(getTableRowOdd(userItem.getItems().get(count)), getTableLayoutParam());
                count = 1;
            }

            for(int i = count; i < userItem.getItems().size(); i+=2){
                customHolder.tableLayout.addView(getTableRowEven(
                        userItem.getItems().get(i), userItem.getItems().get(i+1)), getTableLayoutParam());
            }
        }

        private final View getTableRowOdd(String itemImage){
            View oddItemView = layoutInflater.inflate(R.layout.item_row_odd, null);
            oddItemView.setId(idValue++);
            ImageView itemView = (ImageView) oddItemView.findViewById(R.id.imageViewOdd);
            //itemView.setImageDrawable(getDrawable(R.drawable.blue_drawable));

            Glide.with(listActivity.this)
                    .load(itemImage)
                    .apply(new RequestOptions()
                            .override(1200, 1000)
                            //.placeholder(R.drawable.placeholder)
                            .centerCrop())
                    .into(itemView);

            //Log.e("Item Image: ", "image path : " + String.valueOf(itemImage));

            return oddItemView;
        }

        private final View getTableRowEven(String leftImage, String rightImage){
            View evenItemView = layoutInflater.inflate(R.layout.item_row_even, null);
            evenItemView.setId(idValue++);
            ImageView leftItemView = (ImageView) evenItemView.findViewById(R.id.leftSideImage);
            ImageView rightItemView = (ImageView) evenItemView.findViewById(R.id.rightSideImage);
            //itemView.setImageDrawable(getDrawable(R.drawable.blue_drawable));

            Glide.with(listActivity.this)
                    .load(leftImage)
                    .apply(new RequestOptions()
                            .override(400, 400)
                            //.placeholder(R.drawable.placeholder)
                            )
                    .into(leftItemView);

            Glide.with(listActivity.this)
                    .load(rightImage)
                    .apply(new RequestOptions()
                            .override(400, 400)
                            //.placeholder(R.drawable.placeholder)
                            )
                    .into(rightItemView);

            //Log.e("Item Image: ", "image path left : " + String.valueOf(leftImage));
            //Log.e("Item Image: ", "image path right : " + String.valueOf(rightImage));

            return evenItemView;
        }

        private final TableLayout.LayoutParams getTableLayoutParam(){
            TableLayout.LayoutParams layoutParams =  new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            return layoutParams;
        }

        private final void initLoading(final LoadingViewHolder loadingHolder){
            if (retryPageLoad) {
                loadingHolder.mErrorLayout.setVisibility(View.VISIBLE);
                loadingHolder.mProgressBar.setVisibility(View.GONE);

                loadingHolder.mErrorTxt.setText(
                        errorMsg != null ?
                                errorMsg :
                                getBaseContext().getString(R.string.error_msg_unknown));

            } else {
                loadingHolder.mErrorLayout.setVisibility(View.GONE);
                loadingHolder.mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        @Override
        public int getItemViewType(int position) {
            return  (position == listOfData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }

        public void add(UserListItem user) {
            listOfData.add(user);
            notifyItemInserted(listOfData.size() - 1);
        }

        public void addAll(List<UserListItem> userResults) {
            for (UserListItem user : userResults) {
                add(user);
            }
        }

        public void remove(UserListItem user) {
            int position = listOfData.indexOf(user);
            if (position > -1) {
                listOfData.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void clear() {
            isLoadingAdded = false;
            while (getItemCount() > 0) {
                remove(getItem(0));
            }
        }

        public void addLoadingFooter() {
            isLoadingAdded = true;
            add(new UserListItem());
        }

        public void removeLoadingFooter() {
            isLoadingAdded = false;

            int position = listOfData.size() - 1;
            UserListItem result = getItem(position);

            if (result != null) {
                listOfData.remove(position);
                notifyItemRemoved(position);
            }
        }

        public UserListItem getItem(int position) {
            return listOfData.get(position);
        }

        /**
         * Displays Pagination retry footer view along with appropriate errorMsg
         *
         * @param show
         * @param errorMsg to display if page load fails
         */
        public void showRetry(boolean show, @Nullable String errorMsg) {
            retryPageLoad = show;
            notifyItemChanged(listOfData.size() - 1);

            if (errorMsg != null) this.errorMsg = errorMsg;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            private ImageView userAvatar;
            private TextView userName;
            private TableLayout tableLayout;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.userAvatar = (ImageView) itemView.findViewById(R.id.imv_list_item);
                this.userName = (TextView) itemView.findViewById(R.id.textView);
                this.tableLayout = (TableLayout) itemView.findViewById(R.id.items_layout);
            }
        }

        class LoadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ProgressBar mProgressBar;
            private ImageButton mRetryBtn;
            private TextView mErrorTxt;
            private LinearLayout mErrorLayout;

            public LoadingViewHolder(View itemView) {
                super(itemView);

                mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
                mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
                mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
                mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

                mRetryBtn.setOnClickListener(this);
                mErrorLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.loadmore_retry:
                    case R.id.loadmore_errorlayout:

                        showRetry(false, null);
                        mCallback.retryPageLoad();

                        break;
                }
            }
        }
    }

}
