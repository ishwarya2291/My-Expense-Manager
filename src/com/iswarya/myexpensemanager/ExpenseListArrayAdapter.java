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
		
//		icon.setImageResource(expense.getIcon());
		title.setText(expense.getCategory());
		date.setText(expense.getDate());
		amount.setText("$"+expense.getAmount());
		
		return rowView;
	}

}
