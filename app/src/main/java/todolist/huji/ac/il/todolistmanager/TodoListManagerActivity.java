package todolist.huji.ac.il.todolistmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TodoListManagerActivity extends AppCompatActivity {
    private ListAdapter adapter;
    private ListView lv;
    private DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        mydb = new DBHelper(this);
        Cursor rs = mydb.getData();
        adapter = new ListAdapter(getApplicationContext(),TodoListManagerActivity.this);

        if(rs.getCount() > 0){
            //rs.getCount();
            reproduceAllData(rs);}
        lv = (ListView) findViewById(R.id.lstTodoItems);
        lv.setAdapter(adapter);
        final Context con = this;
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);

                final String currTitle = adapter.getElement(pos);
                alertDialogBuilder.setTitle(currTitle);
                alertDialogBuilder.setNegativeButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteById(adapter.getId(pos));
                        adapter.removeElemnt(pos);
                        adapter.notifyDataSetChanged();
                        lv.invalidateViews();
                    }
                });
                if(currTitle.toLowerCase().contains("call")) {
                    alertDialogBuilder.setPositiveButton(adapter.getElement(pos), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String[] splitedStr = currTitle.split(" ");
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + splitedStr[1]));
                            startActivity(intent);
                        }
                    });
                }
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void reproduceAllData(Cursor rs){
        rs.moveToFirst();
                    String nam = rs.getString(rs.getColumnIndex("due"));
        do {
            String[] splitedDate = rs.getString(rs.getColumnIndex("due")).split("-");
            adapter.addElement(rs.getString(rs.getColumnIndex("title")), new Date(Integer.parseInt(splitedDate[2]) - 1900, Integer.parseInt(splitedDate[1])-1, Integer.parseInt(splitedDate[0])),rs.getString(rs.getColumnIndex("_id")));
        }while(rs.moveToNext());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItemAdd) {
            Intent intent = new Intent(getApplicationContext(),AddNewTodoItemActivity.class);
            startActivityForResult(intent,11);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==11 ){
            if (resultCode ==  RESULT_OK) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = formatter.format((Date)data.getSerializableExtra("dueDate")).toString();
                mydb.insertContact(data.getStringExtra("title"),strDate);
                adapter.addElement(data.getStringExtra("title"), (Date)data.getSerializableExtra("dueDate"),mydb.getLastElement());
                adapter.notifyDataSetChanged();
                lv.invalidateViews();
            }
        }
    }
}
