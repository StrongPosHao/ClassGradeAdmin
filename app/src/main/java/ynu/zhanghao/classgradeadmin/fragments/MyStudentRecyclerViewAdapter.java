package ynu.zhanghao.classgradeadmin.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import ynu.zhanghao.classgradeadmin.db.StudentItem;
import ynu.zhanghao.classgradeadmin.fragments.StudentFragment.OnListFragmentInteractionListener;
import ynu.zhanghao.classgradeadmin.fragments.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter<MyStudentRecyclerViewAdapter.ViewHolder> {

    private final List<StudentItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyStudentRecyclerViewAdapter(List<StudentItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_students, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getStudentNo());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mMajorView.setText(mValues.get(position).getMajor());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        StudentItem studentItem = mValues.get(position);
        SQLiteDatabase db = MainActivity.getDb();
        db.delete("student", "studentNo = ?", new String[]{studentItem.getStudentNo()});
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void changeItem(int position) {
        StudentItem studentItem = mValues.get(position);
        Activity nowActivity = ActivityCollector.activities.get(ActivityCollector.activities.size() - 1);
        Intent intent = new Intent(nowActivity, ChangeActivity.class);
        intent.putExtra("studentNo", studentItem.getStudentNo());
        intent.putExtra("studentName", studentItem.getName());
        intent.putExtra("major", studentItem.getMajor());
        intent.putExtra("fragmentId", 3);
        nowActivity.startActivity(intent);
    }

    public void showPopMenu(View view, final int pos) {
        android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.removeItem:
                        MyStudentRecyclerViewAdapter.this.removeItem(pos);
                        break;
                    case R.id.changeItem:
                        MyStudentRecyclerViewAdapter.this.changeItem(pos);
                        break;
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
        public final TextView mMajorView;
        public StudentItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mMajorView = (TextView) view.findViewById(R.id.major);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
