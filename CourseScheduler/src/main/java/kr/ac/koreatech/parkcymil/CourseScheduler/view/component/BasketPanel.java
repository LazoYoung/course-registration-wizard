package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Basket;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.listener.TableActionHandler;

public class BasketPanel extends AppPanel {

	private static final long serialVersionUID = -8329358148384536170L;
	private Dimension size = new Dimension(700, 250);
	private TimeTableModel ttModel;
	private JScrollPane tablePane;
	private JSplitPane vSplit;
	private JTable table;
	private JLabel creditLabel;
	private int credit = 0;
	
	public BasketPanel(Basket basket, TimeTableModel ttModel) {
		setLayout(null);
		setMinimumSize(size);
		setPreferredSize(size);
		setBackground(Color.LIGHT_GRAY);
		
		this.ttModel = ttModel;
		tablePane = createTable(basket);
		vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel actionPanel = new JPanel(null);
		vSplit.setTopComponent(actionPanel);
		vSplit.setBottomComponent(tablePane);
		vSplit.setDividerSize(0);
		vSplit.setEnabled(false);
		vSplit.setBounds(0, 0, 700, 250);
		actionPanel.setMinimumSize(new Dimension(700, 50));
		
		creditLabel = new JLabel();
		JButton dropBtn = createDropButton(basket);
		JButton clearBtn = createClearButton();
		JButton enhanceBtn = createEnhanceButton();
		creditLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		actionPanel.add(dropBtn);
		actionPanel.add(clearBtn);
		actionPanel.add(enhanceBtn);
		actionPanel.add(creditLabel);
		add(vSplit);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				onResizeComponent(e);
			}
		});
	}
	
	public TimeTableModel getTableModel() {
		return ttModel;
	}
	
	private void enhanceBasket() {
		// TODO Implement enhance button
		JOptionPane.showMessageDialog(null, "This feature is not ready.");
	}
	
	private void updateComponents(int panelWidth) {
		int width = 100;
		creditLabel.setText("학점: " + credit);
		creditLabel.setBounds(panelWidth - width, 0, width, 50);
	}
	
	private void onResizeComponent(ComponentEvent e) {
		int width = e.getComponent().getWidth();
		int height = e.getComponent().getHeight();
		vSplit.setBounds(0, 0, width, height);
		vSplit.updateUI();
		tablePane.setBounds(0, 50, width, height - 50);
		tablePane.updateUI();
		updateComponents(width);
	}
	
	private void onTableChanged(TableModelEvent e) {
		TableModel model = (TableModel) e.getSource();
		int c = CourseData.CREDIT.getColumn();
		credit = 0;
		
		for (int r = 0; r < model.getRowCount(); ++r)
			credit += (int) model.getValueAt(r, c);
		
		updateComponents(getWidth());
	}
	
	private JScrollPane createTable(Basket basket) {
		table = new JTable(ttModel);
		JScrollPane pane = new JScrollPane(table);
		TableActionHandler handler = new TableActionHandler(table, ttModel) {
			@Override
			public void onItemTransfer(Course c) {
				ttModel.removeItem(c);
				basket.drop(c);
			}
		};
		
		ttModel.resizeColumnWidth(table.getColumnModel());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(handler.getDoubleClickListener());
		ttModel.addTableModelListener(this::onTableChanged);
		basket.addPickListener(ttModel::addItem);
		return pane;
	}
	
	private JButton createDropButton(Basket basket) {
		TableActionHandler handler = new TableActionHandler(table, ttModel) {
			@Override
			public void onItemTransfer(Course c) {
				ttModel.removeItem(c);
				basket.drop(c);
			}
		};
		JButton button = new JButton("과목 빼기");
		button.setBounds(30, 10, 100, 30);
		button.addMouseListener(handler.getButtonListener());
		return button;
	}
	
	private JButton createClearButton() {
		JButton button = new JButton("초기화");
		button.setBounds(150, 10, 80, 30);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ttModel.clearItems();
			}
		});
		return button;
	}
	
	private JButton createEnhanceButton() {
		JButton button = new JButton("과목 추천");
		button.setBounds(250, 10, 100, 30);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				enhanceBasket();
			}
		});
		return button;
	}

}
