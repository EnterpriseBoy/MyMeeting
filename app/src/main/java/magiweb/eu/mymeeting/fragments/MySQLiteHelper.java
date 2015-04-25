package magiweb.eu.mymeeting.fragments;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StudentRecordsDB";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create student records table
        String CREATE_STUDENTRECORDS_TABLE = "CREATE TABLE IF NOT EXISTS StudentRecords ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, "+
                "phone TEXT, "+
                "module TEXT, "+
                "course TEXT )";
        // create student records table
        db.execSQL(CREATE_STUDENTRECORDS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older student records table if existed
        db.execSQL("DROP TABLE IF EXISTS StudentRecords");
        // create fresh student records table
        this.onCreate(db);
    }
    
    /**
     * CRUD operations (create "add", read "get", update, delete) student record + get all student records + delete all student records
     */
 
    // Student table name
    private static final String TABLE_STUDENTS = "StudentRecords";
    
    // Student Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_MODULE = "module";
    private static final String KEY_COURSE = "course";
 
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_PHONE, KEY_MODULE, KEY_COURSE};
 
    
    public void addStudent(Student student){
        Log.d("addStudent", student.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName()); // get name 
        values.put(KEY_PHONE, student.getPhone()); // get phone
        values.put(KEY_MODULE, student.getModule()); // get module
        values.put(KEY_COURSE, student.getCourse()); // get course
        // 3. insert
        db.insert(TABLE_STUDENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close(); 
    }
 
    public Student getStudent(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_STUDENTS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        // 4. build student object
        Student student = new Student();
        student.setId(Integer.parseInt(cursor.getString(0)));
        student.setName(cursor.getString(1));
        student.setPhone(cursor.getString(2));
        student.setModule(cursor.getString(3));
        student.setCourse(cursor.getString(4));
        Log.d("getStudent("+id+")", student.toString());
        // 5. return student
        return student;
    }
 
    // Get All Students
    public List<Student> getAllStudents() {
        List<Student> students = new LinkedList<Student>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_STUDENTS;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build student and add it to list
        Student student = null;
        if (cursor.moveToFirst()) {
            do {
            	student = new Student();
            	student.setId(Integer.parseInt(cursor.getString(0)));
            	student.setName(cursor.getString(1));
            	student.setPhone(cursor.getString(2));
            	student.setModule(cursor.getString(3));
            	student.setCourse(cursor.getString(4));
                // Add student to students
            	students.add(student);
            } while (cursor.moveToNext());
        }
        Log.d("getAllStudents()", students.toString());
        // return students
        return students;
    }
 
     // Updating single student
    public int updateStudent(Student student) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", student.getName()); // get name 
        values.put("phone", student.getPhone()); // get phone
        values.put("module", student.getModule()); // get module 
        values.put("course", student.getCourse()); // get course
        // 3. updating row
        int i = db.update(TABLE_STUDENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(student.getId()) }); //selection args
        // 4. close
        db.close();
        return i;
    }
 
    // Deleting single student
    public void deleteStudent(Student student) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_STUDENTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(student.getId()) });
        // 3. close
        db.close();
        Log.d("deleteStudent", student.toString());
    }
    
    // Deleting all students
    public void deleteAllStudents() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM StudentRecords");
        // 3. close
        db.close();
    }
 
}