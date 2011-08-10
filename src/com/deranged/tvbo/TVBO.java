package com.deranged.tvbo;
//git@github.com:cboursnell/TVBO.git

// TODO Add support for reactor. Possibly remove progress and buildtime for making units and research
//        and use only the progress of 'constructing' and 'constructing2'
//      Add maxQueueLength which is 1 for normal and 2 for with reactor
//
//      Add SCActionScout that takes 1 minute and uses up 1 SCV
//      ADD SCActionScan that uses up 50 energy
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TVBO {

	private Model model;

	private JFrame		frame;
	private View		 viewPanel;
	private JPanel		 sidePanel;
	private JPanel		   unitsPanel;
	private JLabel 		    unitsLabel;
	private JComboBox	    unitsDropDown;
	private JButton		    unitsAddButton;
	private JPanel		   buildingsPanel;
	private JLabel 		    buildingsLabel;
	private JComboBox	    buildingsDropDown;
	private JButton		    buildingsAddButton;
	private JPanel		   researchPanel;
	private JLabel 		    researchLabel;
	private JComboBox	    researchDropDown;
	private JButton		    researchAddButton;
	private JPanel		   totalsPanel;
	private JLabel		    totalsLabel;
	private JTextArea	    textArea;
	private JPanel		   buttonsPanel;
	private JTextField	    textField;
	private JButton		    loadButton;
	private JButton		    saveButton;
	private JButton		    clearButton;
	private JButton		    printButton;

	private int frameWidth=1300;
	private int frameHeight=1000;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TVBO window = new TVBO();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TVBO() {
		model = new Model();
		initialize();
		model.setup();
		model.play();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		viewPanel = new View(model);
		frame.setTitle("Terran Visual Build Order Designer - Cyanophage");

		sidePanel = new JPanel();
		sidePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(3)
						.addComponent(viewPanel, GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
						.addGap(3)
						.addComponent(sidePanel, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
						.addGap(3))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(3)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(viewPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
								.addComponent(sidePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE))
								.addGap(3))
				);
		unitsPanel = new JPanel();
		unitsPanel.setBorder(null);
		unitsPanel.setBackground(SystemColor.control);
		buildingsPanel = new JPanel();
		buildingsPanel.setBorder(null);
		buildingsPanel.setBackground(SystemColor.control);
		researchPanel = new JPanel();
		researchPanel.setBorder(null);
		researchPanel.setBackground(SystemColor.control);
		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(SystemColor.control);
		totalsPanel = new JPanel();
		totalsPanel.setBorder(null);
		totalsPanel.setBackground(SystemColor.control);
		GroupLayout gl_sidePanel = new GroupLayout(sidePanel);
		gl_sidePanel.setHorizontalGroup(
				gl_sidePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sidePanel.createSequentialGroup()
						.addGap(3)
						.addGroup(gl_sidePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(unitsPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
								.addGroup(gl_sidePanel.createSequentialGroup()
										.addComponent(buildingsPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_sidePanel.createSequentialGroup()
												.addComponent(researchPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(gl_sidePanel.createSequentialGroup()
														.addComponent(buttonsPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED))
														.addGroup(gl_sidePanel.createSequentialGroup()
																.addComponent(totalsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.RELATED)))
																.addGap(3))
				);
		gl_sidePanel.setVerticalGroup(
				gl_sidePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sidePanel.createSequentialGroup()
						.addGap(3)
						.addComponent(unitsPanel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addGap(3)
						.addComponent(buildingsPanel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addGap(3)
						.addComponent(researchPanel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addGap(3)
						.addComponent(totalsPanel, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
						.addGap(3)
						.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGap(3))
				);
		totalsLabel = new JLabel("Totals");
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		textArea.setBackground(SystemColor.control);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		GroupLayout gl_totalsPanel = new GroupLayout(totalsPanel);
		gl_totalsPanel.setHorizontalGroup(
				gl_totalsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_totalsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_totalsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(totalsLabel)
								.addComponent(textArea))
								.addContainerGap())
				);
		gl_totalsPanel.setVerticalGroup(
				gl_totalsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_totalsPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(totalsLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addGap(3))
				);
		totalsPanel.setLayout(gl_totalsPanel);
		textField = new JTextField();
		textField.setColumns(10);
		loadButton = new JButton("Load");
		saveButton = new JButton("Save");
		clearButton = new JButton("Clear");
		printButton = new JButton("Print");
		// BUTTONS PANEL //////////////////////////////////////////////////////////////////////////
		GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
		gl_buttonsPanel.setHorizontalGroup(
				gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
								.addGroup(gl_buttonsPanel.createSequentialGroup()
										.addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(27))
										.addGroup(gl_buttonsPanel.createSequentialGroup()
												.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(printButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap())
				);
		gl_buttonsPanel.setVerticalGroup(
				gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonsPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_buttonsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(loadButton)
								.addComponent(saveButton))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_buttonsPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(clearButton)
										.addComponent(printButton))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		buttonsPanel.setLayout(gl_buttonsPanel);
		// UNITS PANEL ////////////////////////////////////////////////////////////////////////////
		unitsLabel = new JLabel("Units");
		unitsDropDown = new JComboBox(model.getUnitOptions());
		unitsAddButton = new JButton("Add");
		GroupLayout gl_unitsPanel = new GroupLayout(unitsPanel);
		gl_unitsPanel.setHorizontalGroup(
				gl_unitsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_unitsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_unitsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(unitsLabel)
								.addComponent(unitsDropDown, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(unitsAddButton, Alignment.TRAILING))
								.addContainerGap())
				);
		gl_unitsPanel.setVerticalGroup(
				gl_unitsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_unitsPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(unitsLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(unitsDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(unitsAddButton)
						.addContainerGap(20, Short.MAX_VALUE))
				);
		unitsPanel.setLayout(gl_unitsPanel);

		// BUILDINGS PANEL ////////////////////////////////////////////////////////////////////////
		buildingsLabel = new JLabel("Buildings");
		buildingsDropDown = new JComboBox(model.getBuildingOptions());
		buildingsAddButton = new JButton("Add");
		GroupLayout gl_buildingsPanel = new GroupLayout(buildingsPanel);
		gl_buildingsPanel.setHorizontalGroup(
				gl_buildingsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buildingsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_buildingsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(buildingsLabel)
								.addComponent(buildingsDropDown, 0, 208, Short.MAX_VALUE)
								.addComponent(buildingsAddButton, Alignment.TRAILING))
								.addContainerGap())
				);
		gl_buildingsPanel.setVerticalGroup(
				gl_buildingsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buildingsPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(buildingsLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buildingsDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buildingsAddButton)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		buildingsPanel.setLayout(gl_buildingsPanel);

		// RESEARCH PANEL ////////////////////////////////////////////////////////////////////////
		researchLabel = new JLabel("Research");
		researchDropDown = new JComboBox(model.getResearchOptions());
		researchAddButton = new JButton("Add");
		GroupLayout gl_researchPanel = new GroupLayout(researchPanel);
		gl_researchPanel.setHorizontalGroup(
				gl_researchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_researchPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_researchPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(researchLabel)
								.addComponent(researchDropDown, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(researchAddButton, Alignment.TRAILING))
								.addContainerGap())
				);
		gl_researchPanel.setVerticalGroup(
				gl_researchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_researchPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(researchLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(researchDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(researchAddButton)
						.addContainerGap(20, Short.MAX_VALUE))
				);
		researchPanel.setLayout(gl_researchPanel);

		sidePanel.setLayout(gl_sidePanel);
		frame.getContentPane().setLayout(groupLayout);
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(800, 500));
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		if(dim.width<frameWidth) {
			frameWidth=dim.width;
		}
		if(dim.height<frameHeight) {
			frameHeight=dim.height;
		}
		frame.setBounds(0, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// EVENT HANDLERS /////////////////////////////////////////////////////////////////////////

		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				model.setWidth(viewPanel.getWidth());
				model.setHeight(viewPanel.getHeight());
				System.out.println(model.getWidth() +" x "+model.getHeight());
			}
		});
		
		viewPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.isShiftDown()) {
					model.selectMultipleAction(e.getX(), e.getY());
				} else {
					model.selectAction(e.getX(), e.getY());
				}
				viewPanel.requestFocus();
				viewPanel.repaint();
				textArea.setText(model.setTotalsText());
			}
		});


		unitsAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addUnitAction((String)unitsDropDown.getSelectedItem());
				model.reset();
				model.play();
				textArea.setText(model.setTotalsText());
				viewPanel.repaint();
			}
		});
		buildingsAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.addBuildingAction((String)buildingsDropDown.getSelectedItem());
				model.reset();
				model.play();
				textArea.setText(model.setTotalsText());
				viewPanel.repaint();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.save();
			}
		});

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model.load()) {
					model.reset();
					model.play();
					viewPanel.repaint();
				}
			}
		});

		viewPanel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==KeyEvent.VK_A ||e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_NUMPAD4) {
					model.moveSelected(-1,0);
					model.reset();
					model.play();
					viewPanel.repaint();
				} else if(e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_RIGHT|| e.getKeyCode()==KeyEvent.VK_NUMPAD6) {
					model.moveSelected(1,0);
					model.reset();
					model.play();
					viewPanel.repaint();			
				} else if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_NUMPAD8) {
					model.moveSelected(0,-1);
					model.reset();
					model.play();
					viewPanel.repaint();			
				} else if(e.getKeyCode()==KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_DOWN|| e.getKeyCode()==KeyEvent.VK_NUMPAD2) {
					model.moveSelected(0,1);
					model.reset();
					model.play();
					viewPanel.repaint();		
				} else if(e.getKeyCode()==KeyEvent.VK_Q|| e.getKeyCode()==KeyEvent.VK_NUMPAD7) {
					model.moveSelected(-30,0);
					model.reset();
					model.play();
					viewPanel.repaint();			
				} else if(e.getKeyCode()==KeyEvent.VK_E|| e.getKeyCode()==KeyEvent.VK_NUMPAD9) {
					model.moveSelected(30,0);
					model.reset();
					model.play();
					viewPanel.repaint();		
				} else if(e.getKeyCode()==KeyEvent.VK_R ||e.getKeyCode()==KeyEvent.VK_NUMPAD5) {
					model.moveSelectedToEarliest();
					model.reset();
					model.play();
					viewPanel.repaint();		
				} else if(e.getKeyCode()==KeyEvent.VK_DELETE || e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
					model.deleteAction();
					model.reset();
					model.play();
					viewPanel.repaint();				
				} else if(e.getKeyCode()==107) {
					//System.out.println("Pressed PLUS");
					model.changeScale(0.1);
					viewPanel.repaint();
				} else if(e.getKeyCode()==109) {
					//System.out.println("Pressed MINUS");
					model.changeScale(-0.1);
					viewPanel.repaint();
				}  else if(e.getKeyCode()==KeyEvent.VK_Z || e.getKeyCode()==KeyEvent.VK_NUMPAD1) {
					model.scroll(-30);
					viewPanel.repaint();
				} else if(e.getKeyCode()==KeyEvent.VK_X || e.getKeyCode()==KeyEvent.VK_NUMPAD3) {
					model.scroll(30);
					viewPanel.repaint();
				}
				textArea.setText(model.setTotalsText());
			}
		});















	}
}
