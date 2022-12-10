package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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

import kr.ac.koreatech.parkcymil.CourseScheduler.entity.AppData;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Basket;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.Course;
import kr.ac.koreatech.parkcymil.CourseScheduler.entity.CourseData;
import kr.ac.koreatech.parkcymil.CourseScheduler.view.listener.TableActionHandler;

public class BasketPanel extends AppPanel {

	private static final long serialVersionUID = -8329358148384536170L;
	private Dimension size = new Dimension(700, 250);
	private TimeTableModel ttModel = new TimeTableModel();
	private Basket basket;
	private JScrollPane tablePane;
	private JSplitPane vSplit;
	private JTable table;
	private JLabel creditLabel;
	private int credit = 0;
	
	public BasketPanel(Basket basket) {
		setLayout(null);
		setMinimumSize(size);
		setPreferredSize(size);
		setBackground(Color.LIGHT_GRAY);
		
		this.basket = basket;
		tablePane = createTable();
		vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel actionPanel = new JPanel(null);
		vSplit.setTopComponent(actionPanel);
		vSplit.setBottomComponent(tablePane);
		vSplit.setDividerSize(0);
		vSplit.setEnabled(false);
		vSplit.setBounds(0, 0, 700, 250);
		actionPanel.setMinimumSize(new Dimension(700, 50));
		
		creditLabel = new JLabel();
		JButton dropBtn = createDropButton();
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
	
	/**
	 * [Liberal Arts Selection Process]
	 * 1. Compute total credits of liberal arts in basket
	 * 2. Exclude candidates which surpass total credits
	 * 3. Exclude candidates which conflicts with any major subject
	 * 4. Shuffle candidates
	 * 5. Find any combination whichever is first
	 */
	private void enhanceBasket() {
		Set<Course> basketItems = basket.getCourses();
		
		if (basketItems.isEmpty()) {
			String title = "과목 추천 오류";
			String message = "담은 과목이 없습니다! 먼저 시간표를 검색하여 과목을 몇 개 담아주세요.";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int credit = basketItems.stream()
				.map(Course::getCredit)
				.reduce(0, Integer::sum);
		Basket newBasket = new Basket();
		
		for (Course c : basketItems) {
			if (!c.isMajor()) continue;
			
			newBasket.pick(c);
			credit -= c.getCredit();
		}
		
		final int totalCredit = credit;
		Stream<Course> cStream = AppData.get().courseList
				.stream()
				.filter(Course::isLiberalArts)
				.filter(c -> {
					return (c.getCredit() <= totalCredit)
							&& (newBasket.getCourseInConflict(c) == null);
				});
		List<Course> candidate = new ArrayList<>(cStream.toList());
		Collections.shuffle(candidate);
		boolean success = fillBasket(newBasket, candidate, credit, 0);
		
		if (success) {
			for (Course c : basket.getCourses()) {
				if (!newBasket.contains(c)) {
					basket.drop(c);
					ttModel.removeItem(c);
				}
			}
			for (Course c : newBasket.getCourses()) {
				if (!basket.contains(c)) {
					basket.pick(c);
					ttModel.addItem(c);
				}
			}
			System.out.println();
			String title = "과목 추천 성공";
			String message = "전공 과목을 제외하고 학점을 고려하여 새로운 과목으로 재구성하였습니다!";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		} else {
			String title = "과목 추천 실패";
			String message = "새로운 시간표 조합을 찾는 데 실패했습니다.";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private boolean fillBasket(Basket basket, List<Course> candidate, int credit, int i) {
		Basket temp = new Basket();
		int exCredit = credit;
		
		for (int idx = i; idx < candidate.size(); ++idx) {
			Course c = candidate.get(idx);
			
			if (exCredit < c.getCredit() || temp.getCourseInConflict(c) != null)
				continue;
			
			exCredit -= c.getCredit();
			temp.pick(c);
			
			if (exCredit == 0) {
				temp.getCourses().forEach(basket::pick);
				return true;
			}
		}
		
		if (exCredit > 0 && i + 1 < candidate.size()) {
			return fillBasket(basket, candidate, credit, i + 1);
		} else {
			return false;
		}
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
	
	private JScrollPane createTable() {
		table = new JTable(ttModel);
		JScrollPane pane = new JScrollPane(table);
		TableActionHandler handler = new TableActionHandler(table, ttModel) {
			@Override
			public void onAction(Course c) {
				basket.drop(c);
				ttModel.removeItem(c);
			}
		};
		
		ttModel.resizeColumnWidth(table.getColumnModel());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(handler.getCellDoubleClickListener());
		ttModel.addTableModelListener(this::onTableChanged);
		basket.addPickListener(ttModel::addItem);
		return pane;
	}
	
	private JButton createDropButton() {
		TableActionHandler handler = new TableActionHandler(table, ttModel) {
			@Override
			public void onAction(Course c) {
				basket.drop(c);
				ttModel.removeItem(c);
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
				for (int i = 0; i < ttModel.getRowCount(); ++i) {
					basket.drop(ttModel.getItem(i));
				}
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
