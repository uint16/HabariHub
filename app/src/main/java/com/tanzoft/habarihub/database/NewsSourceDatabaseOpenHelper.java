package com.tanzoft.habarihub.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tanzoft.habarihub.datamodels.NewsSource;

public class NewsSourceDatabaseOpenHelper extends SQLiteOpenHelper {
	
	private Context context;
	private SQLiteDatabase database;

	
	//Name of the database
		public static final String DATABASE_NAME ="news_sources.db";
		
		//Database path
		public static final String DATABASE_PATH="/data/data/com.tanzoft.habarihub/databases/";
		
	
		//Version of the database
		public  static final int DATABASE_VERSION = 1;
		
		public static final String EXACT_PATH=DATABASE_PATH+DATABASE_NAME;


	public NewsSourceDatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		NewsSourceDatabase.onCreate(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		NewsSourceDatabase.onUpgrade(db, oldVersion, newVersion);
	}
	
	//Adding blogs  to the database
	public void addBlog(NewsSource item){

		SQLiteDatabase db = this.getWritableDatabase();
		//Inserting a row
		db.insert(NewsSourceDatabase.DATABASE_TABLE_BLOGS, null, addItemInfo(item));
	
	}

	////Adding blogs  to the database
	public void addNewsPaper(NewsSource item){

		SQLiteDatabase db = this.getWritableDatabase();

		//Inserting a row

		db.insert(NewsSourceDatabase.DATABASE_TABLE_BLOGS, null, addItemInfo(item));
	
	}

	// Getting All blogs from the database
	public ArrayList<NewsSource> getAllBlogs() {
		ArrayList<NewsSource> itemList = new ArrayList<NewsSource>();
		// Select All Query
		String selectQuery = "SELECT * FROM "+NewsSourceDatabase.DATABASE_TABLE_BLOGS+" ORDER BY "+NewsSourceDatabase.COLUMN_ID+" DESC ";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {

			do {

				NewsSource item = new NewsSource();
				item.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_ID))));
				item.setDisplayName(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_TITLE)));
				item.setUrl(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_LINK)));

				// Adding contact to list
				itemList.add(item);
				
				Log.i("dbhelper ", "blog added");

			} while (cursor.moveToNext());

		}
		cursor.close();
		db.close();
		return itemList;

	}

	// Getting All news papers from the database
		public ArrayList<NewsSource> getAllNews() {
			ArrayList<NewsSource> itemList = new ArrayList<NewsSource>();
			
			
			
			// Select All Query
			String selectQuery = "SELECT * FROM "+NewsSourceDatabase.DATABASE_TABLE_NEWSPAPERS+" ORDER BY "+NewsSourceDatabase.COLUMN_ID+" DESC ";
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {

				do {

					NewsSource item = new NewsSource();
					item.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_ID))));
					item.setDisplayName(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_TITLE)));
					item.setUrl(cursor.getString(cursor.getColumnIndex(NewsSourceDatabase.COLUMN_LINK)));

					// Adding contact to list
					itemList.add(item);
					Log.i("dbhelper ","item added");

				} while (cursor.moveToNext());

			}
			cursor.close();
			// close inserting data from database
			db.close();
			return itemList;

		}
	

	private ContentValues addItemInfo(NewsSource item) {

		ContentValues values = new ContentValues();	
		values.put(NewsSourceDatabase.COLUMN_TITLE, item.getDisplayName());//Client's name
		values.put(NewsSourceDatabase.COLUMN_LINK, item.getUrl());
		return values;
	}

	public void createEmptyDatabase() throws IOException{
		/*
		 * This method creates an empty database in the system and overwrites it with the
		 * provided populated database from the assets folder*/

		boolean databaseExists=verifyDatabaseExistence();

		if(databaseExists){
			//do nothing because the database already exists
		}else{

			/*
			 * By executing this block an empty database will be created into the default system path 
			 * of Digicom application i.e /data/data/com.gottibujiku.adigicom.android.ui/databases/
			 * So that it will be possible to overwrite that database with the pre-populated database*/
			this.getWritableDatabase();
			
			try{

				overwriteDatabase();

			}catch (IOException e){

				throw new Error("Error Overwriting Database");
			}

		}


	}

	private void overwriteDatabase() throws IOException{

		/*
		 * Copies the database from the local assets folder and overwrites the created empty database
		 * in the system path of the application where it can handled and used
		 * This is done by transferring byte stream*/

		//Open your local database as the input stream
		InputStream inputDatabaseStream =context.getAssets().open(DATABASE_NAME);

		//Open the empty database as the output stream
		OutputStream outputDatabaseStream = new FileOutputStream(EXACT_PATH);

		//Transfer bytes from the inputDatabaseStream to OutputDatabaseStream

		byte[] buffer = new byte[1024];
		int length;
		while((length=inputDatabaseStream.read(buffer))>0){

			outputDatabaseStream.write(buffer,0,length);

		}

		//Close the streams
		outputDatabaseStream.flush();
		outputDatabaseStream.close();
		inputDatabaseStream.close();

	}

	private boolean verifyDatabaseExistence() {

		database=null;

		try{

			database = SQLiteDatabase.openDatabase(EXACT_PATH,null,SQLiteDatabase.OPEN_READONLY);

		}catch(SQLiteException e){

			//Database does not exist yet 
			return false;
		}


		database.close();
		return true;
	}

	@Override
	public synchronized void close(){

		if(database!=null){

			database.close();

		}

		super.close();
	}

	public void openDatabase(){

		SQLiteDatabase db = this.getWritableDatabase();

	}

}
