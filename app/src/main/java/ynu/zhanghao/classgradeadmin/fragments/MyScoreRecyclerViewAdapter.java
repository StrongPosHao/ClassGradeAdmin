package ynu.zhanghao.classgradeadmin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import ynu.zhanghao.classgradeadmin.R;
import ynu.zhanghao.classgradeadmin.activity.ActivityCollector;
import ynu.zhanghao.classgradeadmin.activity.ChangeActivity;
import ynu.zhanghao.classgradeadmin.activity.MainActivity;
import ynu.zhanghao.classgradeadmin.db.StudentScoreItem;
import ynu.zhanghao.classgradeadmin.fragments.ScoreFragment.OnListFragmentInteractionListener;

public class MyScoreRecyclerViewAdapter extends RecyclerView.Adapter<MyScoreRecyclerViewAdapter.ViewHolder> {

    private final List<StudentScoreItem> mValues;
    private final OnListFragmentInteractionListener mListener;


    public MyScoreRecyclerViewAdapter(List<StudentScoreItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_score, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getStudentNo());
        holder.mContentView.setText(mValues.get(position).getStudentName());
        holder.mScoreView.setText(Integer.toString(mValues.get(position).getScore()));

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
        StudentScoreItem studentScoreItem = mValues.get(position);
        SQLiteDatabase db = MainActivity.getDb();
        db.delete("enroll", "studentNo = ? AND courseNo = ?",
                new String[]{studentScoreItem.getStudentNo(), studentScoreItem.getClassNo()});
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void changeItem(int position) {
        StudentScoreItem studentScoreItem = mValues.get(position);
        Activity nowActivity = ActivityCollector.activities.get(ActivityCollector.activities.size() - 1);
        Intent intent = new Intent(nowActivity, ChangeActivity.class);
        intent.putExtra("studentNo", studentScoreItem.getStudentNo());
        intent.putExtra("studentName", studentScoreItem.getStudentName());
        intent.putExtra("courseNo", studentScoreItem.getClassNo());
        intent.putExtra("score", studentScoreItem.getScore());
        intent.putExtra("fragmentId", 2);
        nowActivity.startActivity(intent);
    }

    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.removeItem:
                        MyScoreRecyclerViewAdapter.this.removeItem(pos);
                        break;
                    case R.id.changeItem:
                        MyScoreRecyclerViewAdapter.this.changeItem(pos);
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
        public final TextView mScoreView;
        public StudentScoreItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mScoreView = (TextView) view.findViewById(R.id.score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
