package com.iswarya.myexpensemanager;

import java.util.ArrayList;
import java.util.List;

public class Expense {
	private int mId;
	private String mAmount;
	private String mDate;
	private String mNote;
	private String mAccountType;
	private String mCategory;
	private String mPaymentType;
	private String mReceipt;
	private int mIcon;
	
	// Empty constructor
    public Expense(){
         
    }
	
	// constructor
	public Expense(String date, String category, String amount, int icon){
		mDate = date;
		mCategory = category;
		mAmount = amount;
		mIcon = icon;
		
	}
	
	// constructor
	public Expense(int id, String amount, String date, String note, String accountType, String category, String paymentType, int icon, String receipt){
		mId = id;
		mAmount = amount;
		mDate = date;
		mNote = note;
		mAccountType = accountType;
		mCategory = category;
		mPaymentType = paymentType;
		mIcon = icon;
		mReceipt = receipt;
	}
	
	// constructor
	public Expense(String amount, String date, String note, String accountType, String category, String paymentType, int icon, String receipt){
		mAmount = amount;
		mDate = date;
		mNote = note;
		mAccountType = accountType;
		mCategory = category;
		mPaymentType = paymentType;
		mIcon = icon;
		mReceipt = receipt;
	}
	
	// constructor
	public Expense(String amount, String date, String note, String accountType, String category, String paymentType, String receipt){
		mAmount = amount;
		mDate = date;
		mNote = note;
		mAccountType = accountType;
		mCategory = category;
		mPaymentType = paymentType;
		mReceipt = receipt;
	}
		
	// constructor
		public Expense(int id, String amount, String date, String note, String accountType, String category, String paymentType, String receipt){
			mId = id;
			mAmount = amount;
			mDate = date;
			mNote = note;
			mAccountType = accountType;
			mCategory = category;
			mPaymentType = paymentType;
			mReceipt = receipt;
		}
	
	
//	@Override
//	public String toString(){
//		return mDate + " " + mCategory + " " + mAmount;
//	}
	
	public static List<Expense> generateList(){
		List<Expense> list = new ArrayList<Expense>();
		list.add(new Expense("9/22/13","Airfare","200", R.drawable.plane));
		list.add(new Expense("9/12/13","Groceries","200", R.drawable.groceries));
		list.add(new Expense("9/22/13","Mobile","50", R.drawable.mobile));
		list.add(new Expense("9/26/13","Fuel","10", R.drawable.fuel));
		
		return list;
	}
	
	
	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getAmount() {
		return mAmount;
	}
	
	public void setAmount(String amount) {
		mAmount = amount;
	}
	
	public String getDate() {
		return mDate;
	}
	
	public void setDate(String date) {
		mDate = date;
	}
	
	public String getNote() {
		return mNote;
	}

	public void setNote(String note) {
		mNote = note;
	}

	public String getAccountType() {
		return mAccountType;
	}

	public void setAccountType(String accountType) {
		mAccountType = accountType;
	}
	
	public String getCategory() {
		return mCategory;
	}
	
	public void setCategory(String category) {
		mCategory = category;
	}

	public String getPaymentType() {
		return mPaymentType;
	}

	public void setPaymentType(String paymentType) {
		mPaymentType = paymentType;
	}

	public int getIcon() {
		return mIcon;
	}

	public void setIcon(int icon) {
		mIcon = icon;
	}

	public String getReceipt() {
		return mReceipt;
	}

	public void setReceipt(String receipt) {
		mReceipt = receipt;
	}

	
	

}
