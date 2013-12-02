package com.iswarya.myexpensemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;
 
    // Database Name
    public static final String DATABASE_NAME = "expenseManagerDatabase";
 
    // Contacts table name
    public static final String TABLE_EXPENSE_DETAILS = "expenseDetails";
 
    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_DATE = "date";
    public static final String KEY_NOTE = "note";
    public static final String KEY_ACCOUNT_TYPE = "account_type";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PAYMENT_TYPE = "payment_type";
    public static final String KEY_RECEIPT = "receipt";
    public int count;
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSE_DETAILS + "(" 
    			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNT + " TEXT,"
    			+ KEY_DATE + " DATE," + KEY_NOTE + " TEXT,"
    			+ KEY_ACCOUNT_TYPE + " TEXT," + KEY_CATEGORY + " TEXT,"
    			+ KEY_PAYMENT_TYPE + " TEXT," + KEY_RECEIPT + " TEXT" +")";
    	db.execSQL(CREATE_EXPENSES_TABLE);
    }
    

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// Drop older table if existed
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_DETAILS);

    	// Create tables again
    	onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new expense  -- Inserting new record
    public void addExpense(Expense expense){
    	SQLiteDatabase db = this.getWritableDatabase();			// this keyword is a reference to SQLiteOpenHelper
    	
    	ContentValues values = new ContentValues();				// stores key value pairs (column name and value)
    	values.put(KEY_AMOUNT, expense.getAmount());
    	values.put(KEY_DATE, expense.getDate());
    	values.put(KEY_NOTE, expense.getNote());
    	values.put(KEY_ACCOUNT_TYPE, expense.getAccountType());
    	values.put(KEY_CATEGORY, expense.getCategory());
    	values.put(KEY_PAYMENT_TYPE, expense.getPaymentType());
    	values.put(KEY_RECEIPT, expense.getReceipt());
    	
    	// Inserting Row
        db.insert(TABLE_EXPENSE_DETAILS, null, values);
        db.close(); // Closing database connection
    	
    }
     
    // Getting single expense  -- Reading rows
    public Expense getExpense(int id){
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(TABLE_EXPENSE_DETAILS, new String[] { KEY_ID,
                KEY_AMOUNT, KEY_DATE, KEY_NOTE, KEY_ACCOUNT_TYPE, KEY_CATEGORY, KEY_PAYMENT_TYPE, KEY_RECEIPT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
   	
    	if (cursor != null)
            cursor.moveToFirst();
     
        Expense expense = new Expense(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
    	    	
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        
        // return expense
        return expense;
	
    }
     
    // Getting All Expenses
    public List<Expense> getAllExpenses(){
    	 List<Expense> expenseList = new ArrayList<Expense>();
    	
    	// Select All Query
    	String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE_DETAILS;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);				// cursor gives read and write access to the data that is going to be returned
        															// from this database query
     // looping through all rows and adding to list
        if(cursor != null && !cursor.isClosed()){
        	if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();
                    expense.setId(Integer.parseInt(cursor.getString(0)));
                    expense.setAmount(cursor.getString(1));
                    expense.setDate(cursor.getString(2));
                    expense.setNote(cursor.getString(3));
                    expense.setAccountType(cursor.getString(4));
                    expense.setCategory(cursor.getString(5));
                    expense.setPaymentType(cursor.getString(6));
                    expense.setReceipt(cursor.getString(7));
                    // Adding contact to list
                    expenseList.add(expense);
                } while (cursor.moveToNext());
            }
        	
            cursor.close();
        }
        // return contact list
        return expenseList;
    }
     
    // Getting expenses Count
    public int getExpensesCount(){
    	String countQuery = "SELECT  * FROM " + TABLE_EXPENSE_DETAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
       // cursor.close();
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
        	cursor.close();
        }
        // return count
        return count;
    }
      
    // Updating single expense
    public int updateExpense(Expense expense, int id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, expense.getAmount());
    	values.put(KEY_DATE, expense.getDate());
    	values.put(KEY_NOTE, expense.getNote());
    	values.put(KEY_ACCOUNT_TYPE, expense.getAccountType());
    	values.put(KEY_CATEGORY, expense.getCategory());
    	values.put(KEY_PAYMENT_TYPE, expense.getPaymentType());
    	values.put(KEY_RECEIPT, expense.getReceipt()); 
    	
    	System.out.println("In dbhelper class" + expense.getId());
    	
    	// updating row
        return db.update(TABLE_EXPENSE_DETAILS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id)});   
    }
     
    // Deleting single expense
    public void deleteExpense(Expense expense){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE_DETAILS, KEY_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) });
        db.close();	
    }    
}