package todolist.huji.ac.il.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;



public class TodoListManagerActivity extends AppCompatActivity {
    private ListAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        adapter = new ListAdapter(getApplicationContext());
        lv = (ListView) findViewById(R.id.lstTodoItems);
        lv.setAdapter(adapter);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                alertDialogBuilder.setTitle(adapter.getElement(pos));
                alertDialogBuilder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.removeElemnt(pos);
                        lv.invalidateViews();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            EditText et = (EditText) findViewById(R.id.edtNewItem);
            String etString =et.getText().toString();
            if(!etString.isEmpty()) {
                adapter.addElement(et.getText().toString());
                lv.invalidateViews();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
