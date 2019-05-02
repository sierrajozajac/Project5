import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Main
{
	private static List<MesoStation> allStations = new ArrayList<MesoStation>();
	private static int currHammingDist = 0;
	private static String currStation = "";
	private static DefaultComboBoxModel<String> compareOptions;
	
	// GUI declarations
	private static JFrame frame;
	private static JPanel panel;
	private static JPanel leftPanel;
	private static JPanel rightPanel;
	private static GridBagConstraints c;
	private static JLabel inputLabel;
	private static JTextField inputText;
	private static JSlider inputSlider;
	private static JButton showStationButton;
	private static JTextArea showStationText;
	private static JLabel compareLabel;
	private static JComboBox<String> compareComboBox;
	private static JButton compareWithButton;
	private static JLabel distance0Label;
	private static JTextField distance0Text;
	private static JLabel distance1Label;
	private static JTextField distance1Text;
	private static JLabel distance2Label;
	private static JTextField distance2Text;
	private static JLabel distance3Label;
	private static JTextField distance3Text;
	private static JLabel distance4Label;
	private static JTextField distance4Text;
	private static JButton addStationButton;
	private static JTextField addStationText;
	private static JTree treeView;
	private static DefaultMutableTreeNode dist0;
	private static DefaultMutableTreeNode dist1;
	private static DefaultMutableTreeNode dist2;
	private static DefaultMutableTreeNode dist3;
	private static DefaultMutableTreeNode dist4;
	
	
	public static void main(String[] args) throws IOException 
	{
		setupVariables();
		setupGUI();
		
	}
	
	private static void setupVariables() throws IOException {
		// Populate allStations
		File file = new File("src/Mesonet.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String line;
		MesoStation tempStation;
		br.readLine();
		br.readLine();
		br.readLine();
		br.readLine();
		while ((line = br.readLine()) != null) {
			tempStation = new MesoStation(line.substring(2, 6));
			allStations.add(tempStation);
		}
		br.close();
	}
	
	private static void setupGUI() {
		// Initialize the panel and frame
		Dimension size = new Dimension(500,600);
		frame = new JFrame("Hamming Distance");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(size);
		frame.setMinimumSize(size);
		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(size);
		panel.setMinimumSize(size);
		
		// Initialize the left panel
		leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setSize(200, 600);
		leftPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 5;
		
		// Set input functionality
		inputLabel = new JLabel("Enter Hamming Dist:");
		inputText = new JTextField();
		inputText.setSize(100, 20);
		inputText.setMinimumSize(new Dimension(100,20));
		inputSlider = new JSlider(JSlider.HORIZONTAL,0,4,0);
		inputSlider.setMajorTickSpacing(1);
		inputSlider.setMaximum(4);
		inputSlider.setMinimum(0);
		
		inputSlider.addChangeListener((a) -> {
			currHammingDist = inputSlider.getValue();
			inputText.setText(currHammingDist + "");
			reloadTree();
		});
		
		inputText.addActionListener((e) -> {
			currHammingDist = Integer.parseInt(inputText.getText());
			inputSlider.setValue(currHammingDist);
			reloadTree();
		});
				
		// Add input functionality to left panel
		c.fill = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		leftPanel.add(inputLabel, c);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		leftPanel.add(inputText, c);
		c.fill = GridBagConstraints.CENTER;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 2;
		leftPanel.add(inputSlider, c);		
		
		// Show stations functionality
		showStationButton = new JButton("Show Station");
		showStationText = new JTextArea(10,10);
		//showStationText.setMinimumSize(new Dimension(190,220));
		//showStationText.setMaximumSize(new Dimension(190,220));
		showStationText.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(showStationText);
		//scrollPane.setMinimumSize(new Dimension(190,220));
		//scrollPane.setMaximumSize(new Dimension(190,220));
		
		showStationButton.addActionListener((a) -> {
			MesoStation tempStation = new MesoStation(currStation);
			
			String stations = "";
			
			for (MesoStation temp : allStations) {
				int hammingDist = getHammingDistance(tempStation, temp);
				if (hammingDist == currHammingDist) {
					stations += temp.getStID() + "\n";
				}
			}
			
			showStationText.setText(stations);
			reloadTree();
		});
		
		// Add show stations functionality to left panel
		c.fill = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 2;
		leftPanel.add(showStationButton, c);
		c.gridy = 4;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 3;
		c.weightx = 1;
		leftPanel.add(scrollPane, c);
		
		// Chosen station functionality
		compareLabel = new JLabel("Compare with:");
		compareOptions = new DefaultComboBoxModel<String>();
		compareOptions.addElement("NULL");
		compareComboBox = new JComboBox<String>(compareOptions);
		compareComboBox.setSelectedIndex(0);
		compareWithButton = new JButton("Calculate HD");
		
		compareComboBox.addActionListener((a) -> {
			currStation = (String)compareComboBox.getSelectedItem();
		});
		
		compareWithButton.addActionListener((a) -> {
			if (currStation.equalsIgnoreCase("")) {
				return;
			}
			// Calculate the number of stations with that hamming dist
			int numDist0 = 0;
			int numDist1 = 0;
			int numDist2 = 0;
			int numDist3 = 0;
			int numDist4 = 0;
			
			MesoStation curr = new MesoStation(currStation);
			
			for (MesoStation temp : allStations) {
				int hammingDist = getHammingDistance(curr, temp);
				switch (hammingDist) {
				case 0:
					numDist0 ++;
					break;
				case 1:
					numDist1 ++;
					break;
				case 2:
					numDist2 ++;
					break;
				case 3: 
					numDist3 ++;
					break;
				case 4:
					numDist4 ++;
					break;
				}
			}
			
			distance0Text.setText(numDist0 + "");
			distance1Text.setText(numDist1 + "");
			distance2Text.setText(numDist2 + "");
			distance3Text.setText(numDist3 + "");
			distance4Text.setText(numDist4 + "");
			
			reloadTree();
		});
		
		// Add station functionality to left panel
		c.fill = GridBagConstraints.WEST;
		c.gridy = 5;
		leftPanel.add(compareLabel, c);
		c.fill = GridBagConstraints.EAST;
		c.gridx = 1;
		leftPanel.add(compareComboBox, c);
		c.fill = GridBagConstraints.WEST;
		c.gridy = 6;
		c.gridx = 0;
		leftPanel.add(compareWithButton, c);
		
		// Distances and their text fields
		distance0Label = new JLabel("Distance 0");
		distance0Text = new JTextField();
		distance0Text.setSize(80, 20);
		distance1Label = new JLabel("Distance 1");
		distance1Text = new JTextField();
		distance1Text.setSize(80, 20);
		distance2Label = new JLabel("Distance 2");
		distance2Text = new JTextField();
		distance2Text.setSize(80, 20);
		distance3Label = new JLabel("Distance 3");
		distance3Text = new JTextField();
		distance3Text.setSize(80, 20);
		distance4Label = new JLabel("Distance 4");
		distance4Text = new JTextField();
		distance1Text.setSize(80, 20);
		
		distance0Text.setEditable(false);
		distance1Text.setEditable(false);
		distance2Text.setEditable(false);
		distance3Text.setEditable(false);
		distance4Text.setEditable(false);
		
		// Add distance labels to left panel
		c.fill = GridBagConstraints.CENTER;
		c.gridy = 7;
		leftPanel.add(distance0Label, c);
		c.gridy = 8;
		leftPanel.add(distance1Label, c);
		c.gridy = 9;
		leftPanel.add(distance2Label, c);
		c.gridy = 10;
		leftPanel.add(distance3Label, c);
		c.gridy = 11;
		leftPanel.add(distance4Label, c);
		
		// Add distance text fields to left panel
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 7;
		leftPanel.add(distance0Text, c);
		c.gridy = 8;
		leftPanel.add(distance1Text, c);
		c.gridy = 9;
		leftPanel.add(distance2Text, c);
		c.gridy = 10;
		leftPanel.add(distance3Text, c);	
		c.gridy = 11;
		leftPanel.add(distance4Text, c);
		
		// Add station functionality
		addStationButton = new JButton("Add Station");
		addStationText = new JTextField();
		addStationText.setSize(80,20);
		
		addStationButton.addActionListener((a) -> {
			currStation = addStationText.getText();
			compareOptions.addElement(currStation);
			compareComboBox = new JComboBox<String>(compareOptions);
			compareComboBox.setSelectedItem(currStation);
			//compareComboBox.repaint();
			//leftPanel.repaint();
			if (!allStations.contains(new MesoStation(currStation))) {
				allStations.add(new MesoStation(currStation));
			}
			reloadTree();
		});
		
		// Add "add station" functionality to left panel
		c.fill = GridBagConstraints.CENTER;
		c.gridy = 12;
		c.gridx = 0;
		leftPanel.add(addStationButton, c);
		c.gridx = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		leftPanel.add(addStationText, c);
		
		// Comparison tree
		rightPanel = new JPanel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("All stations");
		treeView = new JTree(top);
		dist0 = new DefaultMutableTreeNode("Distance 0");
		dist1 = new DefaultMutableTreeNode("Distance 1");
		dist2 = new DefaultMutableTreeNode("Distance 2");
		dist3 = new DefaultMutableTreeNode("Distance 3");
		dist4 = new DefaultMutableTreeNode("Distance 4");
		top.add(dist0);
		top.add(dist1);
		top.add(dist2);
		top.add(dist3);
		top.add(dist4);
		rightPanel.add(treeView);

		if (!currStation.equalsIgnoreCase("")) {
			reloadTree();
		}
		
		// Adding everything
		panel.add(leftPanel);
		panel.add(rightPanel);
		frame.add(panel);
		frame.setVisible(true); 
	}
	
	private static int getHammingDistance(MesoStation one, MesoStation two) {
		int ret = 4;
		
		char[] oneAry = one.getStID().toCharArray();
		char[] twoAry = two.getStID().toCharArray();
		
		for (int i=0; i<4; i++) {
			if (oneAry[i] == twoAry[i]) {
				ret--;
			}
		}
		
		return ret;
	}
	
	private static void reloadTree() {
		MesoStation curr = new MesoStation(currStation);
		
		for (MesoStation temp : allStations) {
			
			int hammingDist = getHammingDistance(curr, temp);
			DefaultMutableTreeNode currNode = new DefaultMutableTreeNode(temp.getStID());
			switch (hammingDist) {
			case 0:
				dist0.add(currNode);
				break;
			case 1:
				dist1.add(currNode);
				break;
			case 2:
				dist2.add(currNode);
				break;
			case 3: 
				dist3.add(currNode);
				break;
			case 4:
				dist4.add(currNode);
				break;
			}
		}
	}
}