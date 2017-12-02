package com.toan_itc.baoonline.sqlite_database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection extends SQLiteOpenHelper {
	
	private static String DB_PATH = "data/data/com.toan_itc.baoonline/databases/";
    private static String DB_NAME = "BaoOnline.sqlite";
    private SQLiteDatabase myDataBase;
    private Context myContext = null;
	private static final int Database_Version=1;
	@SuppressLint("NewApi")
	public Connection(Context context) {
    	super(context, DB_NAME, null,Database_Version);
        this.myContext = context;
    }
	public void createDataBase() throws IOException{
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Tạo Database lỗi");
        	}
    }
	
	public void copyDataBase() throws IOException{
		 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
	public boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
	
	public void openDataBase() throws SQLException{

    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
	    if(myDataBase != null)
		    myDataBase.close();
	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}
    //Upgrade database vesion new
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int Database_Vesion, int newVersion) {
		// TODO Auto-generated method stub
        int upgradeTo = Database_Vesion + 1;
        while (upgradeTo <= newVersion)
        {
            myContext.deleteDatabase(DB_NAME);
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Tạo Database lỗi");
            }
            upgradeTo++;
        }
        Log.wtf("vesion"+Database_Vesion+"up"+newVersion,"hehe");
    }
}
