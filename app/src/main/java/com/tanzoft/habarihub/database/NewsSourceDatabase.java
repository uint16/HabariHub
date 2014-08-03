package com.tanzoft.habarihub.database;

import android.database.sqlite.SQLiteDatabase;

public class NewsSourceDatabase {

	/*
	 * Represents the structure of the database*/

	//Name of the blogs table
	public static final String DATABASE_TABLE_BLOGS="blogs";
	
	public static final String DATABASE_TABLE_NEWSPAPERS="newspapers";


	//Column id. _id used to comply with android requirements
	public static final String COLUMN_ID="_id";

	//Name of the first column,client name
	public static final String COLUMN_TITLE="_displayName";

	// description
	public static final String COLUMN_LINK="_url";



	public static final String CREATE_TABLE_BLOGS="create table "+ DATABASE_TABLE_BLOGS + " (" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TITLE + " text, "
			+ COLUMN_LINK + " text" + ");";
	
	public static final String CREATE_TABLE_NEWS="create table "+ DATABASE_TABLE_NEWSPAPERS + " (" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TITLE + " text, "
			+ COLUMN_LINK + " text" + ");";



	public static void onCreate(SQLiteDatabase db){

		db.execSQL(CREATE_TABLE_BLOGS);
		db.execSQL(CREATE_TABLE_NEWS);


	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * This method will be called only when the database is upgraded*/
		//Drop older table if it exists
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BLOGS);
	
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NEWSPAPERS);


		//create the table again
		onCreate(db);

	}



}


