package todolist.huji.ac.il.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karam on 11/03/2016.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> input;

    public ListAdapter(Context mContext) {
        this.mContext = mContext;
        this.input = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.input.size();
    }
    public void addElement(String str){
        input.add(str);
    }
    public void removeElemnt(int idx){
        input.remove(idx);
    }
    public String getElement(int idx){
        return input.get(idx);
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(this.mContext);
        tv.setText(input.get(position));
        if (position %2 ==0)
            tv.setTextColor(Color.RED);
        else
            tv.setTextColor(Color.BLUE);
        return tv;
    }
}
