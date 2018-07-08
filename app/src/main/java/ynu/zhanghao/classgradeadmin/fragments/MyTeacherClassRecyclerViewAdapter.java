package ynu.zhanghao.classgradeadmin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ynu.zhanghao.classgradeadmin.ActivityCollector;
import ynu.zhanghao.classgradeadmin.ChangeActivity;
import ynu.zhanghao.classgradeadmin.MainActivity;
import ynu.zhanghao.classgradeadmin.R;
import ynu.zhanghao.classgradeadmin.db.CourseItem;
import ynu.zhanghao.classgradeadmin.fragments.TeacherClassFragment.OnListFragmentInteractionListener;
import ynu.zhanghao.classgradeadmin.fragments.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTeacherClassRecyclerViewAdapter extends RecyclerView.Adapter<MyTeacherClassRecyclerViewAdapter.ViewHolder> {

    private final List<CourseItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    public MyTeacherClassRecyclerViewAdapter(List<CourseItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_teacherclass, parent, false);
//        final ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCourseNo());
        holder.mContentView.setText(mValues.get(position).getName());

//        Log.d("E", mValues.get(position).getCapacity());
        holder.mCapacityView.setText(Integer.toString(mValues.get(position).getCapacity()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    MainActivity mainActivity = (MainActivity) ActivityCollector.activities.get(ActivityCollector.activities.size() - 1);
                    String courseNo = holder.mItem.getCourseNo();
                    ScoreFragment.setStudentScoreItems(mainActivity.listAllStudentScore(courseNo));
                    mainActivity.getNavigationBar().selectTab(1);
                    mainActivity.ShowFragment(1);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopMenu(v, holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void removeItem(int position) {
        CourseItem courseItem = mValues.get(position);
        SQLiteDatabase db = MainActivity.getDb();
        db.delete("Course", "courseNo = ?", new String[]{courseItem.getCourseNo()});
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void changeItem(int position) {
        CourseItem courseItem = mValues.get(position);
        Activity nowActivity = ActivityCollector.activities.get(ActivityCollector.activities.size() - 1);
        Intent intent = new Intent(nowActivity, ChangeActivity.class);
        intent.putExtra("courseNo", courseItem.getCourseNo());
        intent.putExtra("courseName", courseItem.getName());
        intent.putExtra("capacity", courseItem.getCapacity());
        intent.putExtra("fragmentId", 1);
        nowActivity.startActivity(intent);
        notifyDataSetChanged();
    }


    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.removeItem:
                        MyTeacherClassRecyclerViewAdapter.this.removeItem(pos); break;
                    case R.id.changeItem:
                        MyTeacherClassRecyclerViewAdapter.this.changeItem(pos); break;
                }
                return false;
            }
        });
        popupMenu.show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mCapacityView;
        public CourseItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.txt_content);
            mCapacityView = view.findViewById(R.id.txt_capacity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
