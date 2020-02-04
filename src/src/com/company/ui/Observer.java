package src.com.company.ui;

import java.util.ArrayList;
import java.util.List;

public interface Observer {
	
	ArrayList<RowEntry> getRows();
	
	void update(ArrayList<String> messages);

}
