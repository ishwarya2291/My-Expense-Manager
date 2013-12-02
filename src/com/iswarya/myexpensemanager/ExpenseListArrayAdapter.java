package com.iswarya.myexpensemanager;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpenseListArrayAdapter extends ArrayAdapter<Expense> {
	
	private List<Expense> mExpense;
	private Context mContext;
	
	public ExpenseListArrayAdapter(Context context, List<Expense> objects){
		super(context, R.layout.all_expenses_list_row, objects);
		mExpense = objects;
		mContext = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.all_expenses_list_row, parent, false);
		Expense expense = mExpense.get(position);
		
		ImageView icon = (ImageView) rowView.findViewById(R.id.list_image);
		TextView title = (TextView) rowView.findViewById(R.id.item_title);
		TextView date = (TextView) rowView.findViewById(R.id.item_date);
		TextView amount = (TextView) rowView.findViewById(R.id.item_amount);
		
		if(expense.getCategory().equals("Airfare")){
			icon.setImageResource(R.drawable.plane);
		}else if(expense.getCategory().equals("Bus")){
			icon.setImageResource(R.drawable.bus);
		}else if(expense.getCategory().equals("Car")){
			icon.setImageResource(R.drawable.car);
		}else if(expense.getCategory().equals("Entertainment")){
			icon.setImageResource(R.drawable.entertainment);
		}else if(expense.getCategory().equals("Food")){
			icon.setImageResource(R.drawable.food);
		}else if(expense.getCategory().equals("Fuel")){
			icon.setImageResource(R.drawable.fuel);
		}else if(expense.getCategory().equals("Groceries")){
			icon.setImageResource(R.drawable.groceries);
		}else if(expense.getCategory().equals("Hotel")){
			icon.setImageResource(R.drawable.hotel);
		}else if(expense.getCategory().equals("Laundry")){
			icon.setImageResource(R.drawable.laundry);
		}else if(expense.getCategory().equals("Medical")){
			icon.setImageResource(R.drawable.medicine);
		}else if(expense.getCategory().equals("Mobile")){
			icon.setImageResource(R.drawable.mobile);
		}else if(expense.getCategory().equals("Train")){
			icon.setImageResource(R.drawable.train);
		}else if(expense.getCategory().equals("Miscellaneous")){
			icon.setImageResource(R.drawable.miscellaneous);
		}
		
		title.setText(expense.getNote());
		date.setText(expense.getDate());
		amount.setText("$"+expense.getAmount());
		
		return rowView;
	}

}
