package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewTodoItemActivity extends Activity {
//    private MyDate datePicker;
    private DatePicker dpResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_todo_item);
        dpResult = (DatePicker) findViewById(R.id.datePicker);
    }
    public void btnOk(View v) throws ParseException {
        EditText txt = (EditText) findViewById(R.id.edtNewItem);
        String str = txt.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("title", str);
        Date date = new Date(dpResult.getYear() - 1900, dpResult.getMonth(), dpResult.getDayOfMonth());
        intent.putExtra("dueDate",date);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void btnCancel(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
