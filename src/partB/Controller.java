//TODO consider removing the manual refresh button
package partB;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener, DocumentListener, ListSelectionListener {
    
    private final int updatePeriodMS = 2500;


    private App model;
    private GUI view;

    private String searchFilter = "";

    Controller() {
        model = new App();
        view = new GUI(this);
        AutoRefresh timer = new AutoRefresh(this);

        initialiseThreadInfo();
        timer.scheduleAtFixedRate(timer.task, 0, updatePeriodMS);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Stop Thread")) {
//            System.out.println("STOPPERINO");
            if (view.getSelectedThread() != null) {
                threadStop();
            }

        } else if (e.getActionCommand().equals("Start Thread")) {
//            System.out.println("STARTERINO");
            threadStart();
        } else if (e.getActionCommand().equals("Clear Search")) {
//            System.out.println("CLEARERINO");
            searchClear();
        } else if (e.getActionCommand().equals("Manual Refresh")) {

//            System.out.println("REFRESHERINO");
            refresh();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
//        System.out.println("SEARCHCHANGE");
        searchChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
//        System.out.println("SEARCHCHANGE");
        searchChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
//        System.out.println("SEARCHCHANGE");
        searchChange();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
//            System.out.println("FILTERINO");
            filterGroupChange();
        }
    }

    private void initialiseThreadInfo() {
        refreshGUIThreadGroupPanel();
        refreshGUIThreadsPanel(null);
    }


    private void refreshGUIThreadGroupPanel() {
        ThreadGroup[] threadGroupList = model.getAllThreadGroups();
        view.setThreadGroupList(threadGroupList);
    }

    private void refreshGUIThreadsPanel(ThreadGroup TG) {

        Thread[] threadsList;
        if (TG == null) {
            threadsList = model.getAllThreads();
        } else {
            threadsList = model.getThreadsInGroup(TG);
        }

        if (searchFilter.equals("")) {
            view.setThreadsList(threadsList);
        } else {
            ArrayList<Thread> threadsStartingWithSearchFilter = new ArrayList<>();
            Thread[] cleanedFilteredThreads = {};
            for (Thread i : threadsList) {
                String threadToCheckName = i.getName().toLowerCase();
                threadToCheckName = threadToCheckName.replaceAll("\\s", "");

                String tempFilter = searchFilter.toLowerCase();
                tempFilter = tempFilter.replaceAll("\\s", "");

                if (threadToCheckName.startsWith(tempFilter)) {
                    threadsStartingWithSearchFilter.add(i);
                    cleanedFilteredThreads = new Thread[threadsStartingWithSearchFilter.size()];
                }
            }
            view.setThreadsList(threadsStartingWithSearchFilter.toArray(cleanedFilteredThreads));
        }
    }

    private void threadStop() {
        model.threadStopper(view.getSelectedThread());
        refresh();
    }

    private void threadStart() {
        model.createThread();
        refresh();
    }

    //empties the text field and inherently calls searchchange if textbox wasnt empty
    private void searchClear() {
        view.clearSearchText();
    }

    private void searchChange() {
        searchFilter = view.getSearchText();
//        System.out.println(searchFilter);

        refresh();
    }

    private void filterGroupChange() {
        refreshGUIThreadsPanel(view.getSelectedThreadGroup());
    }

    void refresh() {
//        System.out.println("TRIGGER");

        ThreadGroup TG = view.getSelectedThreadGroup();
        Thread T = view.getSelectedThread();
        refreshGUIThreadGroupPanel();
        refreshGUIThreadsPanel(TG);

        int TGIndex = 0;
        ThreadGroup[] guiTG = view.getThreadGroupCache();
        for (int i = 0; i < guiTG.length; i++) {
            if (guiTG[i] == TG) {
                TGIndex = i + 1;
            }
        }
        view.selectThreadGroup(TGIndex);

        int TIndex = -1;
        view.clearThreadSelect();

        Thread[] guiT = view.getThreadsCache();
        for (int i = 0; i < guiT.length; i++) {
            if (guiT[i] == T) {
                TIndex = i;
            }
        }
        if (TIndex >= 0) {
            view.selectThread(TIndex);
        }
    }
}

