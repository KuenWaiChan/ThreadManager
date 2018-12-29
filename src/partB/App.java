package partB;

import java.lang.ThreadGroup;
import java.lang.Thread;
import java.util.ArrayList;


class App {

    private int dummyThreadCount;

    App() {
        dummyThreadCount = 0;
    }

    void printAll() {
        ThreadGroup[] groups = getAllThreadGroups();

        for (ThreadGroup i : groups) {
            printThreadGroupInfo(i);
        }
    }

    ThreadGroup[] getAllThreadGroups() {
        ThreadGroup root = Thread.currentThread().getThreadGroup();

        while (root.getParent() != null) {
            root = root.getParent();
        }

        ArrayList<ThreadGroup> allThreadGroups = new ArrayList<>();
        allThreadGroups.add(root);

        ThreadGroup[] subThreadGroups = new ThreadGroup[root.activeGroupCount() * 2];
        root.enumerate(subThreadGroups);

        for (ThreadGroup i : subThreadGroups) {
            if (i != null) {
                allThreadGroups.add(i);
            }
        }

        ThreadGroup[] allThreadGroupsArray = new ThreadGroup[allThreadGroups.size()];

        return (allThreadGroups.toArray(allThreadGroupsArray));
    }

    Thread[] getAllThreads() {
        ThreadGroup root = Thread.currentThread().getThreadGroup();

        while (root.getParent() != null) {
            root = root.getParent();
        }

        Thread[] allThreads = new Thread[root.activeCount() * 2];
        root.enumerate(allThreads);

        ArrayList<Thread> cleanedThreadsList = new ArrayList<>();
        for (Thread i : allThreads) {
            if (i != null) {
                cleanedThreadsList.add(i);
            }
        }

        Thread[] cleanedThreadsArray = new Thread[cleanedThreadsList.size()];

        return (cleanedThreadsList.toArray(cleanedThreadsArray));

    }



    Thread[] getThreadsInGroup(ThreadGroup group) {
        ArrayList<Thread> cleanedThreadsInGroup = new ArrayList<>();

        Thread[] threadsInGroup = new Thread[group.activeCount() * 2];
        group.enumerate(threadsInGroup, false);

        for (Thread i : threadsInGroup) {
            if (i != null) {
                cleanedThreadsInGroup.add(i);
            }
        }

        Thread[] cleanedThreadsInGroupArray = new Thread[cleanedThreadsInGroup.size()];
        return (cleanedThreadsInGroup.toArray(cleanedThreadsInGroupArray));
    }


    private void printThreadGroupInfo(ThreadGroup group) {
        Thread[] threadsInGroup = getThreadsInGroup(group);

        System.out.println("Thread Group: " + group.getName());
        for (Thread i : threadsInGroup) {
            if (i != null) {
                System.out.printf("%-40s %-15s %-30s %-25s %-25s\n",
                        "Thread Name: " + i.getName(),
                        "Thread ID: " + i.getId(),
                        "Thread State: " + i.getState(),
                        "Thread Priority: " + i.getPriority(),
                        "Is Thread Daemon: " + i.isDaemon());
            }
        }

        System.out.println("\n");
    }

    void createThread() {
        String name = "DummyThread" + dummyThreadCount;
        (new Thread(new DummyThreads(), name)).start();

        dummyThreadCount++;
    }

    void threadStopper(Thread thread) {
        thread.interrupt();
    }
}