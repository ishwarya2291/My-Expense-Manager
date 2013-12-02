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

public class PaymentMethodFragment extends Fragment {
	
	LinearLayout mLinearLayout;
	double pCreditAmt = 0, pDebitAmt = 0, pCashAmt = 0, pMiscAmt = 0;
	DatabaseHandler db;
	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
	
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.payment_method_fragment, container, false);
		db = new DatabaseHandler(getActivity());
        List<Expense> expenses = db.getAllExpenses();
        for (Expense ex : expenses) {
        	if(ex.getPaymentType().equals("Credit Card")){
        		pCreditAmt = pCreditAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getPaymentType().equals("Debit Card")){
        		pDebitAmt = pDebitAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getPaymentType().equals("Cash")){
        		pCashAmt = pCashAmt + Double.parseDouble(ex.getAmount());
        	}else if(ex.getPaymentType().equals("Miscellaneous")){
        		pMiscAmt = pMiscAmt + Double.parseDouble(ex.getAmount());
        	} 
        }
		CreatePieChart();
		
		return mLinearLayout;
	};
	
	private void CreatePieChart() {

		  // Pie Chart Section Names
		  String[] code = new String[] { "Credit Card", "Debit Card", "Cash", "Misc" };

		  // Pie Chart Section Value
		  double[] distribution = { pCreditAmt, pDebitAmt, pCashAmt, pMiscAmt };

		  // Color of each Pie Chart Sections
		  int[] colors = { Color.rgb(255, 174, 174), Color.rgb(176, 229, 124), 
				  Color.rgb(86, 186, 236), Color.rgb(255, 236, 148)};

//		  int[] colors = { Color.RED, Color.GREEN, 
//				  Color.BLUE, Color.YELLOW};
		  
		  // Instantiating CategorySeries to plot Pie Chart
		// Instantiating CategorySeries to plot Pie Chart
		  CategorySeries distributionSeries = new CategorySeries(
		    "Spending by Payment Method");
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
		  defaultRenderer.setChartTitle("Spending by Payment Method");
		  defaultRenderer.setChartTitleTextSize(50);
		  defaultRenderer.setZoomButtonsVisible(true);
		  defaultRenderer.setBackgroundColor(45454545);

	
		  View view = ChartFactory.getPieChartView(getActivity(), distributionSeries, defaultRenderer);
		  mLinearLayout.addView(view);
		 }
}
