package com.iswarya.myexpensemanager;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class CategoryFragment extends Fragment{
	
	LinearLayout mLinearLayout;
	double cAirfareAmt = 0, cBusAmt = 0, cCarAmt = 0, cEntAmt = 0, cFoodAmt = 0, cFuelAmt = 0, cGroAmt = 0,
				cHotelAmt = 0, cLaundryAmt = 0, cMedAmt = 0, cMobileAmt = 0, cTrainAmt = 0, cMiscAmt = 0;
	DatabaseHandler db;
	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
	
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.category_fragment, container, false);
		db = new DatabaseHandler(getActivity());
        List<Expense> expenses = db.getAllExpenses();
        for (Expense ex : expenses) {
        	if(ex.getCategory().equals("Airfare")){
        		cAirfareAmt = cAirfareAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Bus")){
        		cBusAmt = cBusAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Car")){
        		cCarAmt = cCarAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Entertainment")){
        		cEntAmt = cEntAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Food")){
        		cFoodAmt = cFoodAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Fuel")){
        		cFuelAmt = cFuelAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Groceries")){
        		cGroAmt = cGroAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Hotel")){
        		cHotelAmt = cHotelAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Laundry")){
        		cLaundryAmt = cLaundryAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Medical")){
        		cMedAmt = cMedAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Mobile")){
        		cMobileAmt = cMobileAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Train")){
        		cTrainAmt = cTrainAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getCategory().equals("Miscellaneous")){
        		cMiscAmt = cMiscAmt + Double.parseDouble(ex.getAmount());
        	} 
        }
                
		CreatePieChart();
		
		return mLinearLayout;
	};
	
	private void CreatePieChart() {

		  // Pie Chart Section Names
		  String[] code = new String[] { "Airfare", "Bus", "Car", "Entertainment", "Food", "Fuel", "Groceries",
				  "Hotel", "Laundry", "Medical", "Mobile", "Train", "Misc" };

		  // Pie Chart Section Value
		  double[] distribution = { cAirfareAmt, cBusAmt, cCarAmt, cEntAmt, cFoodAmt, cFuelAmt, cGroAmt, 
				  cHotelAmt, cLaundryAmt, cMedAmt, cMobileAmt, cTrainAmt, cMiscAmt };

		  // Color of each Pie Chart Sections
		  int[] colors = { Color.rgb(255, 236, 148), Color.rgb(255, 174, 174), Color.rgb(2, 132, 130), Color.rgb(176, 229, 124), 
				  Color.rgb(180, 216, 231), Color.rgb(86, 186, 236), Color.rgb(115, 44, 123), Color.rgb(185, 88, 53), 
				  Color.rgb(33, 182, 168), Color.rgb(122, 62, 72), Color.rgb(232, 17, 15), Color.rgb(19, 48, 80), Color.rgb(255, 222, 0) };

		  // Instantiating CategorySeries to plot Pie Chart
		  CategorySeries distributionSeries = new CategorySeries(
		    "Spending by Category");
		  for (int i = 0; i < distribution.length; i++) {
		   // Adding a slice with its values and name to the Pie Chart
		   distributionSeries.add(code[i], distribution[i]);
		  }
		  // Instantiating a renderer for the Pie Chart
		  DefaultRenderer defaultRenderer = new DefaultRenderer();
		  for (int i = 0; i < distribution.length; i++) {
		   SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
		   seriesRenderer.setColor(colors[i]);
		   seriesRenderer.setDisplayChartValues(true);
		   // Adding a renderer for a slice
		   defaultRenderer.addSeriesRenderer(seriesRenderer);
		  }
		  defaultRenderer.setLabelsColor(Color.BLACK);
		  defaultRenderer.setLabelsTextSize(30);
		  defaultRenderer.setLegendTextSize(30);
		  defaultRenderer.setChartTitle("Spending by Category");
		  defaultRenderer.setChartTitleTextSize(50);
		  defaultRenderer.setZoomButtonsVisible(true);
		  defaultRenderer.setBackgroundColor(45454545);

	
		  View view = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
		  mLinearLayout.addView(view);
		 }
}
