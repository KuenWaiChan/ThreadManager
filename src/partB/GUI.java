package partB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import java.util.Arrays;

public class GUI {

    // Buttons
    private JButton refreshButton;  //todo check redundancy of this button
    private JButton clearSearchButton;
    private JButton startThreadButton;
    private JButton stopThreadButton;

    private JTextField searchField;

    // ScrollPanes for ThreadGroups and Threads
    private JScrollPane threadGroupListScrollPane;
    private JScrollPane threadsListScrollPane;

    private JList<String> threadGroupsList;
    private JTable threadTable;

    private ThreadGroup[] threadGroupCache;
    private Thread[] threadsCache;

    private ActionListener listener;
//SearchField: text box search bar to filter by thread name

    //this constructor is for testing only
    GUI(ActionListener listener) {
        this.listener = listener;

        // Creating window and setting properties.
        JFrame frame = new JFrame("ThreadManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 506));

        //setting up buttons
        refreshButton = new JButton("Manual Refresh");
        refreshButton.addActionListener(listener);


        clearSearchButton = new JButton("Clear Search");
        clearSearchButton.addActionListener(listener);


        startThreadButton = new JButton("Start Thread");
        startThreadButton.addActionListener(listener);


        stopThreadButton = new JButton("Stop Thread");
        stopThreadButton.addActionListener(listener);

        searchField = new JTextField();
        searchField.getDocument().addDocumentListener((DocumentListener) listener);

        // Manipulating the main panel from the frame.
        JPanel mainPane = (JPanel) frame.getContentPane();
        mainPane.setLayout(new BorderLayout());

        // Creating side buttons, and split pane
        mainPane.add(setUpMenuPane(), BorderLayout.EAST);
        mainPane.add(setUpSplitPane());

        // Displaying the window.
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel setUpMenuPane() {
        // Creating panel for right side menu buttons. (Displayed vertically).
        JPanel menuPane = new JPanel();
        menuPane.setLayout(new GridLayout(0, 1, 12, 12));

        JPanel buttonMenuPane = new JPanel();
        buttonMenuPane.setLayout(new GridLayout(0, 1, 12, 12));

        JPanel searchMenuPane = new JPanel();
        searchMenuPane.setLayout(new GridLayout(0, 1, 12, 12));

        buttonMenuPane.setBorder(new EmptyBorder(35, 0, 0, 0));
        searchMenuPane.setBorder(new EmptyBorder(35, 0, 0, 0));

        //buttonMenuPane.add(refreshButton);
        buttonMenuPane.add(startThreadButton);
        buttonMenuPane.add(stopThreadButton);

        searchMenuPane.add(new JLabel("Search: "));
        searchMenuPane.add(searchField);
        searchMenuPane.add(clearSearchButton);

        menuPane.add(buttonMenuPane);
        menuPane.add(searchMenuPane);

        // Menu panel is added to a panel with flowing layout for presentation.
        JPanel flowingMenuPane = new JPanel();
        flowingMenuPane.add(menuPane);

        return flowingMenuPane;
    }

    private JSplitPane setUpSplitPane() {
        // Creating main content pane for ThreadGroups (Left Component of SplitPane)
        JPanel threadGroupPane = new JPanel(new BorderLayout());
        threadGroupPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        // Title for ThreadGroupsPane
        JPanel threadGroupTitlePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        threadGroupTitlePane.setBorder(new EmptyBorder(0, 0, 6, 0));
        JLabel threadGroupTitle = new JLabel("ThreadGroups", SwingConstants.LEFT);
        threadGroupTitlePane.add(threadGroupTitle);
        threadGroupPane.add(threadGroupTitlePane, BorderLayout.NORTH);

        // Creating main content pane for Threads (Right Component of SplitPane)
        JPanel threadsPane = new JPanel(new BorderLayout());
        threadsPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        // Title for ThreadGroupsPane
        JPanel threadsTitlePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        threadsTitlePane.setBorder(new EmptyBorder(0, 0, 6, 0));
        JLabel threadsTitle = new JLabel("Threads", SwingConstants.LEFT);
        threadsTitlePane.add(threadsTitle);
        threadsPane.add(threadsTitlePane, BorderLayout.NORTH);

        // Creating split pane with said components, and setting properties. Adding pane to the frame's main panel
        JSplitPane threadSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, threadGroupPane, threadsPane);
        threadSplitPane.setResizeWeight(0.15);

        // Adding threadGroupListScrollPane and threadsListScrollPane to respective panels
        threadGroupListScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        threadsListScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        threadGroupPane.add(threadGroupListScrollPane);
        threadsPane.add(threadsListScrollPane);

        return threadSplitPane;
    }


    void setThreadGroupList(ThreadGroup[] threadGroups) {
        threadGroupCache = Arrays.copyOf(threadGroups, threadGroups.length);

        String[] threadGroupNames = new String[threadGroups.length + 1];
        threadGroupNames[0] = "(No Filter)";

        for (int i = 0; i < threadGroups.length; i++) {
            threadGroupNames[i + 1] = threadGroups[i].getName();
        }

        threadGroupsList = new JList<>(threadGroupNames);
        threadGroupsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        threadGroupsList.setSelectedIndex(0);
        threadGroupListScrollPane.setViewportView(threadGroupsList);

        threadGroupsList.addListSelectionListener((ListSelectionListener) listener);


    }

    void setThreadsList(Thread[] threads) {
        threadsCache = Arrays.copyOf(threads, threads.length);

        String[] columnHeaders = {"ThreadName", "ID", "State", "Priority", "Daemon"};
        String[][] data = new String[threads.length][5];

        for (int i = 0; i < threads.length; i++) {
            data[i][0] = threads[i].getName();
            data[i][1] = String.valueOf(threads[i].getId());
            data[i][2] = String.valueOf(threads[i].getState());
            data[i][3] = String.valueOf(threads[i].getPriority());
            data[i][4] = String.valueOf(threads[i].isDaemon());
        }

        threadTable = new ThreadTableModel(data, columnHeaders);

        threadTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        threadTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        threadTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        threadTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        threadTable.getColumnModel().getColumn(4).setPreferredWidth(61);
        threadTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        threadTable.getTableHeader().setReorderingAllowed(false);

        threadTable.setRowSelectionAllowed(true);
        threadTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        threadsListScrollPane.setViewportView(threadTable);
    }

    ThreadGroup getSelectedThreadGroup() {
        int i = threadGroupsList.getSelectedIndex();

        if (i == 0) {
            return null;
        } else {
            return (threadGroupCache[i - 1]);
        }
    }

    Thread getSelectedThread() {
        int i = threadTable.getSelectedRow();
        if (i < 0) {
            return null;
        }
        return (threadsCache[i]);
    }

    String getSearchText() {
        String searchTerm = "";

        int docLength = searchField.getDocument().getLength();
        try {
            searchTerm = searchField.getDocument().getText(0, docLength);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return searchTerm;
    }

    void clearSearchText() {
        int docLength = searchField.getDocument().getLength();
        try {
            searchField.getDocument().remove(0, docLength);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    ThreadGroup[] getThreadGroupCache() {
        return threadGroupCache;
    }

    Thread[] getThreadsCache() {
        return threadsCache;
    }

    void selectThreadGroup(int index) {
        threadGroupsList.setSelectedIndex(index);
    }
    void selectThread(int index) {
        threadTable.setRowSelectionInterval(index, index);
    }

    void clearThreadSelect() {
        threadTable.clearSelection();
    }
}