package apt.tutorial;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class LunchList extends TabActivity {
	List<Restaurant> model=new ArrayList<Restaurant>();
	RestaurantAdapter adapter=null;
	EditText name=null;
	EditText address=null;
	RadioGroup types=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		name=(EditText)findViewById(R.id.name);
		address=(EditText)findViewById(R.id.addr);
		types=(RadioGroup)findViewById(R.id.types);
		
		Button save=(Button)findViewById(R.id.save);
		
		save.setOnClickListener(onSave);
		
		ListView list=(ListView)findViewById(R.id.restaurants);
		
		adapter=new RestaurantAdapter();
		list.setAdapter(adapter);
		
		TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
		
		spec.setContent(R.id.restaurants);
		spec.setIndicator("List", getResources()
																.getDrawable(R.drawable.list));
		getTabHost().addTab(spec);
		
		spec=getTabHost().newTabSpec("tag2");
		spec.setContent(R.id.details);
		spec.setIndicator("Details", getResources()
																	.getDrawable(R.drawable.restaurant));
		getTabHost().addTab(spec);
		
		getTabHost().setCurrentTab(0);
		
		list.setOnItemClickListener(onListClick);
	}
	
	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
			Restaurant r=new Restaurant();
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			switch (types.getCheckedRadioButtonId()) {
				case R.id.sit_down:
					r.setType("sit_down");
					break;
					
				case R.id.take_out:
					r.setType("take_out");
					break;
					
				case R.id.delivery:
					r.setType("delivery");
					break;
			}
			
			adapter.add(r);
		}
	};
	
	private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent,
														 View view, int position,
														 long id) {
			Restaurant r=model.get(position);
			
			name.setText(r.getName());
			address.setText(r.getAddress());
			
			if (r.getType().equals("sit_down")) {
				types.check(R.id.sit_down);
			}
			else if (r.getType().equals("take_out")) {
				types.check(R.id.take_out);
			}
			else {
				types.check(R.id.delivery);
			}
			
			getTabHost().setCurrentTab(1);
		}
	};
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		RestaurantAdapter() {
			super(LunchList.this,
						android.R.layout.simple_list_item_1,
						model);
		}
		
		public View getView(int position, View convertView,
												ViewGroup parent) {
			View row=convertView;
			RestaurantWrapper wrapper=null;
			
			if (row==null) {													
				LayoutInflater inflater=getLayoutInflater();
				
				row=inflater.inflate(R.layout.row, parent, false);
				wrapper=new RestaurantWrapper(row);
				row.setTag(wrapper);
			}
			else {
				wrapper=(RestaurantWrapper)row.getTag();
			}
			
			wrapper.populateFrom(model.get(position));
			
			return(row);
		}
	}
	
	class RestaurantWrapper {
		private TextView name=null;
		private TextView address=null;
		private ImageView icon=null;
		private View row=null;
		
		RestaurantWrapper(View row) {
			this.row=row;
		}
		
		void populateFrom(Restaurant r) {
			getName().setText(r.getName());
			getAddress().setText(r.getAddress());
	
			if (r.getType().equals("sit_down")) {
				getIcon().setImageResource(R.drawable.ball_red);
			}
			else if (r.getType().equals("take_out")) {
				getIcon().setImageResource(R.drawable.ball_yellow);
			}
			else {
				getIcon().setImageResource(R.drawable.ball_green);
			}
		}
		
		TextView getName() {
			if (name==null) {
				name=(TextView)row.findViewById(R.id.title);
			}
			
			return(name);
		}
		
		TextView getAddress() {
			if (address==null) {
				address=(TextView)row.findViewById(R.id.address);
			}
			
			return(address);
		}
		
		ImageView getIcon() {
			if (icon==null) {
				icon=(ImageView)row.findViewById(R.id.icon);
			}
			
			return(icon);
		}
	}
}
