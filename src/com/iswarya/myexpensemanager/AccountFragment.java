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

public class AccountFragment extends Fragment {

	LinearLayout mLinearLayout;
	DatabaseHandler db;
	double aBusinessAmt = 0, aFamilyAmt = 0, aFriendsAmt = 0, aHomeAmt = 0, aKidsAmt = 0, aPersonalAmt = 0, aWorkAmt = 0, aMiscAmt = 0;

	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
	
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.account_fragment, container, false);
		db = new DatabaseHandler(getActivity());
        List<Expense> expenses = db.getAllExpenses();
        for (Expense ex : expenses) {
        	if(ex.getAccountType().equals("Business")){
        		aBusinessAmt = aBusinessAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Family")){
        		aFamilyAmt = aFamilyAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Friends")){
        		aFriendsAmt = aFriendsAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Home")){
        		aHomeAmt = aHomeAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Kids")){
        		aKidsAmt = aKidsAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Personal")){
        		aPersonalAmt = aPersonalAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Work")){
        		aWorkAmt = aWorkAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getAccountType().equals("Miscellaneous")){
        		aMiscAmt = aMiscAmt + Double.parseDouble(ex.getAmount());
        	} 
        }
                
		CreatePieChart();
		
		return mLinearLayout;
	};
	
	private void CreatePieChart() {

		  // Pie Chart Section Names
		  String[] code = new String[] { "Business", "Family", "Friends", "Home", "Kids", "Personal", "Work", "Misc" };

		  // Pie Chart Section Value
		  double[] distribution = { aBusinessAmt, aFamilyAmt, aFriendsAmt, aHomeAmt, aKidsAmt, aPersonalAmt, aWorkAmt, aMiscAmt };

		  // Color of each Pie Chart Sections
		  int[] colors = { Color.rgb(255, 236, 148), Color.rgb(255, 174, 174), Color.rgb(255, 240, 170), Color.rgb(176, 229, 124), 
				  Color.rgb(180, 216, 231), Color.rgb(86, 186, 236), Color.rgb(115, 44, 123), Color.rgb(185, 88, 53)};

		  // Instantiating CategorySeries to plot Pie Chart
		  CategorySeries distributionSeries = new CategorySeries(
		    "Spending by Account");
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
		  defaultRenderer.setChartTitle("Spending by Account");
		  defaultRenderer.setChartTitleTextSize(50);
		  defaultRenderer.setZoomButtonsVisible(true);
		  defaultRenderer.setBackgroundColor(45454545);

	
		  View view = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
		  mLinearLayout.addView(view);
		 }
}
