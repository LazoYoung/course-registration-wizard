package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;

public class BasketPanel extends AppPanel {

	private static final long serialVersionUID = -8329358148384536170L;
	private TimeTableModel ttModel;
	private JTable table;
	private JLabel creditLabel;
	private int credit = 0;
	
	public BasketPanel() {
		setLayout(null);
		setMinimumSize(new Dimension(700, 250));
		setBackground(Color.LIGHT_GRAY);
		
		JScrollPane tablePane = createTable();
		JSplitPane vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel actionPanel = new JPanel(null);
		vSplit.setTopComponent(actionPanel);
		vSplit.setBottomComponent(tablePane);
		vSplit.setDividerSize(0);
		vSplit.setEnabled(false);
		vSplit.setBounds(0, 0, 700, 250);
		actionPanel.setMinimumSize(new Dimension(700, 50));
		
		creditLabel = new JLabel();
		JButton dropBtn = new JButton("과목 빼기");
		JButton clearBtn = new JButton("초기화");
		JButton enhanceBtn = new JButton("과목 추천");
		creditLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		dropBtn.setBounds(30, 10, 100, 30);
		clearBtn.setBounds(150, 10, 80, 30);
		enhanceBtn.setBounds(250, 10, 100, 30);
		actionPanel.add(dropBtn);
		actionPanel.add(clearBtn);
		actionPanel.add(enhanceBtn);
		actionPanel.add(creditLabel);
		add(vSplit);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
				vSplit.setBounds(0, 0, width, height);
				tablePane.setBounds(0, 50, width, height - 50);
				updateComponents(width);
			}
		});
	}
	
	public void addCourse(Course c) {
		ttModel.addItem(c);
	}
	
	private JScrollPane createTable() {
		ttModel = new TimeTableModel();
		table = new JTable(ttModel);
		JScrollPane pane = new JScrollPane(table);
		
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		ttModel.addTableModelListener(e -> {
			TableModel model = (TableModel) e.getSource();
			int c = CourseData.CREDIT.getColumn();
			credit = 0;
			
			for (int r = 0; r < model.getRowCount(); ++r)
				credit += (int) model.getValueAt(r, c);
			
			updateComponents(getWidth());
		});
		
		return pane;
	}

	private void updateComponents(int panelWidth) {
		int width = 100;
		creditLabel.setText("학점: " + credit);
		creditLabel.setBounds(panelWidth - width, 0, width, 50);
	}

}
