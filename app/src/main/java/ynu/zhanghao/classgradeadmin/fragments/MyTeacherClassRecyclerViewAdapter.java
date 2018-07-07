package ynu.zhanghao.classgradeadmin.fragments;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ynu.zhanghao.classgradeadmin.R;
import ynu.zhanghao.classgradeadmin.db.CourseContent;
import ynu.zhanghao.classgradeadmin.fragments.TeacherClassFragment.OnListFragmentInteractionListener;
import ynu.zhanghao.classgradeadmin.fragments.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTeacherClassRecyclerViewAdapter extends RecyclerView.Adapter<MyTeacherClassRecyclerViewAdapter.ViewHolder> {

    private final List<CourseContent.CourseItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTeacherClassRecyclerViewAdapter(List<CourseContent.CourseItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_teacherclass, parent, false);
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
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mCapacityView;
        public CourseContent.CourseItem mItem;

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
