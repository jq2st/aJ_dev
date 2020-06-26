package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Models.Person;
import Utils.Util;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSONS_DB = "CREATE TABLE " + Util.TABLE_PERSONS_NAME + "(" +
                Util.KEY_ID + " INTEGER PRIMARY KEY, " +
                Util.KEY_NAME + " TEXT," +
                Util.KEY_SURNAME + " TEXT," +
                Util.KEY_PHONE + " TEXT)";

        db.execSQL(CREATE_PERSONS_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_PERSONS_NAME);
        onCreate(db);
    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, person.getName());
        contentValues.put(Util.KEY_SURNAME, person.getSurname());
        contentValues.put(Util.KEY_PHONE, person.getPhone());
        db.insert(Util.TABLE_PERSONS_NAME, null, contentValues);
        db.close();
    }

    public List<Person> getPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Person> persons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Util.TABLE_PERSONS_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setSurname(cursor.getString(2));
                person.setPhone(cursor.getString(3));

                persons.add(person);
            }
            while (cursor.moveToNext());
        }
        return persons;
    }

    public Person getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_PERSONS_NAME, new String[] {Util.KEY_ID, Util.KEY_NAME, Util.KEY_SURNAME, Util.KEY_PHONE}, Util.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Person person = new Person(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return person;
    }

    public void editPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, person.getName());
        contentValues.put(Util.KEY_SURNAME, person.getSurname());
        contentValues.put(Util.KEY_PHONE, person.getPhone());
        Log.i("suk", contentValues.toString());
        int sup = db.update(Util.TABLE_PERSONS_NAME, contentValues, Util.KEY_ID + "=?", new String[]{String.valueOf(person.getId())});
        Log.i("sup", String.valueOf(sup));
    }

    public void deletePersons(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_PERSONS_NAME, Util.KEY_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
    }
}
