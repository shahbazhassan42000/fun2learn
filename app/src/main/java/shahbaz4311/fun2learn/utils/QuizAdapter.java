package shahbaz4311.fun2learn.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shahbaz4311.fun2learn.R;

public class QuizAdapter extends BaseAdapter {
    private final Context context;
    private final List<List<Object>> quizzes;

    public QuizAdapter(Context context, List<List<Object>> quizzes) {
        this.context = context;
        this.quizzes = quizzes;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return quizzes.size();
    }

    @Override
    public Object getItem(int position) {
        return quizzes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_view, null, true);

            holder.quiz_no = (TextView) convertView.findViewById(R.id.quiz_no);
            holder.quiz_date = (TextView) convertView.findViewById(R.id.quiz_date);
            holder.quiz_marks = (TextView) convertView.findViewById(R.id.quiz_marks);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.quiz_no.setText(quizzes.get(position).get(0).toString());
        holder.quiz_date.setText(quizzes.get(position).get(1).toString());
        holder.quiz_marks.setText(quizzes.get(position).get(2).toString());
        //change quiz_marks color if score is less than 5
        if (Integer.parseInt(quizzes.get(position).get(2).toString()) < 5) {
            holder.quiz_marks.setTextColor(context.getColor(R.color.red));
        } else {
            holder.quiz_marks.setTextColor(context.getColor(R.color.green));
        }



        return convertView;
    }



    private static class ViewHolder {

        protected TextView quiz_no, quiz_date, quiz_marks;

    }

}
