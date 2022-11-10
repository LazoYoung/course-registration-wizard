package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.AppData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Department;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.listener.CoursePickListener;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.listener.DepartmentSelectListener;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.listener.SearchEventHandler;

public class BrowserPanel extends AppPanel {
	
	private static final long serialVersionUID = -5838395870111051633L;
	
	private JTable table;
	private TableRowSorter<TimeTableModel> sorter;
	private RowFilter<TimeTableModel, Integer> searchFilter = null;
	private RowFilter<TimeTableModel, Integer> departmentFilter = null;
	private JTextField searchFld = createSearchField();
	private JButton pickBtn;
	private Insets btnMargin = new Insets(0, 0, 0, 0);

	protected BrowserPanel() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setMinimumSize(new Dimension(700, 200));
		
		JScrollPane tablePane = createTable();
		JLabel searchTxt = createSearchLabel();
		JButton searchBtn = createSearchButton();
		JLabel deptTxt = createDepartmentLabel();
		JComboBox<String> deptBox = createDepartmentComboBox();
		pickBtn = createPickButton();
		
		updateComponents();
		addAll(searchTxt, searchFld, searchBtn, deptTxt, deptBox, pickBtn, tablePane);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
				tablePane.setBounds(0, 50, width, height - 50);
				updateComponents();
			}
		});
	}

	public JTextField createSearchField() {
		JTextField field = new JTextField(10);
		KeyAdapter adapter = SearchEventHandler.getKeyAdapter(this);
		field.setBounds(60, 10, 200, 30);
		field.addKeyListener(adapter);
		return field;
	}
	
	public JTextField getSearchField() {
		return searchFld;
	}
	
	public void setSearchFilter(RowFilter<TimeTableModel, Integer> filter) {
		searchFilter = filter;
		updateFilter();
	}
	
	public void setDepartmentFilter(RowFilter<TimeTableModel, Integer> filter) {
		departmentFilter = filter;
		updateFilter();
	}
	
	private void updateFilter() {
		sorter.setRowFilter(new RowFilter<TimeTableModel, Integer>() {
			@Override
			public boolean include(Entry<? extends TimeTableModel, ? extends Integer> entry) {
				boolean c1 = (searchFilter != null) ? searchFilter.include(entry) : true;
				boolean c2 = (departmentFilter != null) ? departmentFilter.include(entry) : true;
				
				return c1 && c2;
			}
		});
	}
	
	private void updateComponents() {
		pickBtn.setBounds(getWidth() - 130, 10, 100, 30);
	}
	
	private JLabel createSearchLabel() {
		JLabel label = new JLabel("검색");
		label.setBounds(0, 10, 50, 30);
		label.setHorizontalAlignment(JLabel.RIGHT);
		return label;
	}
	
	private JButton createSearchButton() {
		// Icon by Chanut
		ImageIcon icon = getIcon("search.png", 20);
		JButton button = new JButton(icon);
		MouseAdapter adapter = SearchEventHandler.getMouseAdapter(this);
		button.setBounds(260, 10, 30, 30);
		button.setMargin(btnMargin);
		button.addMouseListener(adapter);
		return button;
	}
	
	private JLabel createDepartmentLabel() {
		JLabel label = new JLabel("학부");
		label.setBounds(300, 10, 50, 30);
		label.setHorizontalAlignment(JLabel.RIGHT);
		return label;
	}
	
	private JComboBox<String> createDepartmentComboBox() {
		JComboBox<String> box = new JComboBox<String>();
		box.setBounds(360, 10, 160, 30);
		box.addActionListener(new DepartmentSelectListener(this));
		box.addItem("전체");
		
		for (Department m : Department.values())
			box.addItem(m.getText());
		
		return box;
	}
	
	private JButton createPickButton() {
		 JButton button = new JButton("과목 담기");
		 MouseAdapter adapter = new CoursePickListener();
		 button.setMargin(btnMargin);
		 button.addMouseListener(adapter);
		 return button;
	}
	
	private JScrollPane createTable() {
		List<Course> courseList = AppData.get().getCourseList();
		TimeTableModel ttModel = new TimeTableModel(courseList);
		sorter = new TableRowSorter<TimeTableModel>(ttModel);
		table = new JTable(ttModel);
		JScrollPane pane = new JScrollPane(table);
		
		table.setFillsViewportHeight(true);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return pane;
	}
	
}
