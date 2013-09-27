package kr.co.datastreams.dsma.demo;

import kr.co.datastreams.dsma.ma.tokenizer.CharTypeTokenizer;
import kr.co.datastreams.dsma.ma.tokenizer.NewLineSeparator;
import kr.co.datastreams.dsma.ma.AnalysisJob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: shkim
 * Date: 13. 9. 9
 * Time: 오후 4:59
 * To change this template use File | Settings | File Templates.
 */
public class GUIDemo {

    private JFrame mainFrame = 	null;
    private JMenuBar menuBar = null;
    private JMenu menuFile = null;
    private JMenu menuHelp = null;
    private JMenuItem menuItemFileOpen = null;
    private JMenuItem menuItemAnalyze = null;
    private JTextArea inputTextArea = null;
    private JTextArea outputTextArea = null;
    private JSplitPane splitPaneTop = null;
    private JSplitPane splitPaneBottom = null;

    private JButton buttonAnalysis = null;
    private JRadioButton radioMultiThread = null;
    private JRadioButton radioSingleThread = null;
    private JCheckBox useCacheCheckbox = null;
    private JCheckBox useWordCountingCheckbox = null;

    private boolean multiThread = true;
    private boolean useCache = false;
    private boolean useWordCounting = false;

    public static void main(String[] args) {
        GUIDemo demo = new GUIDemo();
        demo.run();
    }

    public void run() {
        mainFrame = new JFrame();
        Toolkit kit = mainFrame.getToolkit();
        Dimension windowSize = kit.getScreenSize();

        mainFrame.setBounds(windowSize.width / 20, windowSize.height / 20,
                windowSize.width * 18 / 20, windowSize.height * 18 / 20);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setTitle("Datastreams Korean Analyzer(GUI Demo)");

        Font font = new Font("MonoSpaced", Font.PLAIN, 12);
        UIManager.put("TextArea.font", font);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().add(createPaneCenter(), BorderLayout.CENTER);
        mainFrame.getContentPane().add(createPaneNorth(), BorderLayout.NORTH);

        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuItemFileOpen = new JMenuItem("Open", KeyEvent.VK_0);
        menuItemFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.ALT_MASK));
        menuItemAnalyze = new JMenuItem("Analyze", KeyEvent.VK_1);
        menuItemFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));

        menuBar.add(menuFile);
        menuFile.add(menuItemFileOpen);
        menuFile.add(menuItemAnalyze);

        menuItemFileOpen.addActionListener(new SharedActionHandler());
        menuItemAnalyze.addActionListener(new SharedActionHandler());
        buttonAnalysis.addActionListener(new SharedActionHandler());
        radioMultiThread.addActionListener(new SharedActionHandler());
        radioSingleThread.addActionListener(new SharedActionHandler());
        useCacheCheckbox.addActionListener(new SharedActionHandler());
        useWordCountingCheckbox.addActionListener(new SharedActionHandler());

        mainFrame.setJMenuBar(menuBar);


        mainFrame.setVisible(true);
        splitPaneBottom.setDividerLocation(0.5);

    }


    private JComponent createPaneCenter() {
        splitPaneBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel panel = new JPanel(new GridLayout(1,1));
        panel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputTextArea = new JTextArea();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(inputTextArea);
        panel.add(scroll);
        splitPaneBottom.setLeftComponent(panel);

        panel = new JPanel(new GridLayout(1,1));
        panel.setBorder(BorderFactory.createTitledBorder("Result"));
        outputTextArea = new JTextArea();
        scroll = new JScrollPane();
        scroll.setViewportView(outputTextArea);
        panel.add(scroll);
        splitPaneBottom.setRightComponent(panel);

        splitPaneBottom.setOneTouchExpandable(true);

        return splitPaneBottom;
    }

    private JComponent createPaneNorth() {
        splitPaneTop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneTop.setLeftComponent(createOptions());
        splitPaneTop.setRightComponent(createButtons());
        splitPaneTop.setOneTouchExpandable(true);

        return splitPaneTop;
    }

    private Component createOptions() {
        JPanel controlPanel = new JPanel(new GridLayout(4,1));

        JPanel optionPanel = new JPanel(new GridLayout(2,1));
        radioMultiThread = new JRadioButton("Mutli-thread Mode", true);
        radioSingleThread = new JRadioButton("Single-thread Mode", false);

        optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        optionPanel.add(radioMultiThread);
        optionPanel.add(radioSingleThread);

        ButtonGroup groupThread = new ButtonGroup();
        groupThread.add(radioMultiThread);
        groupThread.add(radioSingleThread);

        useCacheCheckbox = new JCheckBox("Use Cache");
        optionPanel.add(useCacheCheckbox);

        useWordCountingCheckbox = new JCheckBox("Word Counting");
        optionPanel.add(useWordCountingCheckbox);

        controlPanel.add(optionPanel);

        return controlPanel;
    }

    private Component createButtons() {
        JPanel controlPanel = new JPanel(new GridLayout(4,1));
//        controlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        buttonAnalysis = new JButton("Analyze Text");

        JPanel buttonsPannel = new JPanel(new GridLayout(1, 4));
        buttonsPannel.setBorder(BorderFactory.createTitledBorder("Functions"));

        JPanel blank = new JPanel(new GridLayout(1, 1));
        blank.setBorder(BorderFactory.createEmptyBorder());
        JPanel blank2 = new JPanel(new GridLayout(1, 1));
        blank2.setBorder(BorderFactory.createEmptyBorder());
        JPanel blank3 = new JPanel(new GridLayout(1, 1));
        blank3.setBorder(BorderFactory.createEmptyBorder());
        JPanel blank4 = new JPanel(new GridLayout(1, 1));
        blank4.setBorder(BorderFactory.createEmptyBorder());


        buttonsPannel.add(buttonAnalysis);
        controlPanel.add(buttonsPannel);
        buttonsPannel.add(blank);
        buttonsPannel.add(blank2);
        buttonsPannel.add(blank3);
        buttonsPannel.add(blank4);

        buttonAnalysis.setEnabled(true);

        return controlPanel;
    }

    private class SharedActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Object source = e.getSource();

                if (source == menuItemFileOpen) {
                    fileOpen();
                } else if (source == menuItemAnalyze) {
                    anlyzeText();
                } else if (source == buttonAnalysis) {
                    anlyzeText();
                } else if (source == radioMultiThread) {
                    multiThread = true;
                } else if (source == radioSingleThread) {
                    multiThread = false;
                } else if (source == useCacheCheckbox) {
                    useCache = useCacheCheckbox.isSelected() ? true : false;
                } else if (source == useWordCountingCheckbox) {
                    useWordCounting = useWordCountingCheckbox.isSelected() ? true : false;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        private void anlyzeText() {
            String text = inputTextArea.getText();
            AnalysisJob job;
            if (text != null && text.length() > 0) {
                if (multiThread) {
                    int numOfThreads = Runtime.getRuntime().availableProcessors() == 1 ? 2 : Runtime.getRuntime().availableProcessors();
                    job = AnalysisJob.create(text, new NewLineSeparator(), new CharTypeTokenizer(), numOfThreads, useCache, useWordCounting);
                } else {
                    job = AnalysisJob.create(text, new NewLineSeparator(), new CharTypeTokenizer(), 1, useCache, useWordCounting);
                }

                job.start();
                outputTextArea.setText(job.getResultAsString());
            } else {
                outputTextArea.setText("");
            }
        }

        /**
         * Reads a text file, and use the text as the input data.
         */
        private void fileOpen() {
            JFileChooser chooser = new JFileChooser();

            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        inputTextArea.append(line + "\n");
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
