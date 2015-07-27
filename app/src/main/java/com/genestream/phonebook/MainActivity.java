package com.genestream.phonebook;

import android.database.CursorIndexOutOfBoundsException;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        for (String s : getPhoneBook()) {
            if(s != null)
                adapter.add(s);
        }

        listView.setAdapter(adapter);
    }

    private String[] getPhoneBook() {
        String[] phoneBook;

        // get device content
        ContentResolver contentResolver = getContentResolver();

        // get phone book
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, null);

        // get phone count
        phoneBook = new String[cursor.getColumnCount()];
        Log.v("test", "" + phoneBook.length);
        if (phoneBook.length > 0)
            for (int index = 0; index < phoneBook.length; index++) {
                try {
                    cursor.moveToNext();

                    // get name
                    String name = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    // get phone number
                    String number = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    phoneBook[index] = name + "\t" + number;
                    Log.v("", phoneBook[index]);
                } catch (CursorIndexOutOfBoundsException ex) {
                    Log.e("Error", ex.getMessage());
                }
            }

        return phoneBook;
    }
}