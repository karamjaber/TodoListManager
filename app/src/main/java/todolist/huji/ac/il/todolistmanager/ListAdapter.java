package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by karam on 11/03/2016.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> titleInput;
    private ArrayList<Date> dateInput;
    private Activity mActivity;

    public ListAdapter(Context mContext,Activity mActivity) {
        this.mContext = mContext;
        this.titleInput = new ArrayList<>();
        this.dateInput = new ArrayList<>();
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return this.titleInput.size();
    }
    public void addElement(String str,Date date){
        dateInput.add(date);
        titleInput.add(str);
    }
    public void removeElemnt(int idx){
        titleInput.remove(idx);
        dateInput.remove(idx);
    }
    public String getElement(int idx){
        return titleInput.get(idx);
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
//    public String parseDate(Date date){
//
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_listview, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTodoTitle);
        txtTitle.setText(titleInput.get(position));
        TextView dateView = (TextView) rowView.findViewById(R.id.txtTodoDueDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        // (3) create a new String using the date format we want
        String strDate = formatter.format(dateInput.get(position));
        dateView.setText(strDate);
        Date currentDate =new Date();
        boolean isColorRED = false;
        if(currentDate.getYear()>dateInput.get(position).getYear()) {
            isColorRED=true;
        }else{
            if(currentDate.getYear() == dateInput.get(position).getYear()){
                if (currentDate.getMonth() > dateInput.get(position).getMonth())
                    isColorRED=true;
                else
                    if(currentDate.getMonth() == dateInput.get(position).getMonth())
                        if (currentDate.getDate() > dateInput.get(position).getDate())
                            isColorRED=true;
            }
        }
        if(isColorRED){
            dateView.setTextColor(Color.RED);
            txtTitle.setTextColor(Color.RED);
        }
        return rowView;
    }
}
