package partB;

public class ControllerDriver {
    public static void main(String[] args) {
        ThreadGroup TG1 = new ThreadGroup("TGOne");
        ThreadGroup TG2 = new ThreadGroup("TGTwo");
        ThreadGroup TG3 = new ThreadGroup("TGThree");

        ThreadGroup TGSAMENAME = new ThreadGroup("TGSAMENAME");
        ThreadGroup TGSAMENAME2 = new ThreadGroup("TGSAMENAME");

        ThreadGroup TGSUBOFTG1 = new ThreadGroup(TG1, "TGSUBOFTG1");

        new Thread(TG1, new DummyThreads(), "DummyTG1A").start();
        new Thread(TG1, new DummyThreads(), "DummyTG1B").start();
        new Thread(TG1, new DummyThreads(), "DummyTG1C").start();
        new Thread(TG1, new DummyThreads(), "DummyTG1D").start();

        new Thread(TG2, new DummyThreads(), "DummyTG2A").start();
        new Thread(TG2, new DummyThreads(), "DummyTG2B").start();
        new Thread(TG2, new DummyThreads(), "DummyTG2C").start();

        new Thread(TG3, new DummyThreads(), "DummyTG3A").start();
        new Thread(TG3, new DummyThreads(), "DummyTG3B").start();

        new Controller();


    
        new Thread(TGSAMENAME, new DummyThreads(), "DummyTGSAMENAMEA").start();
        new Thread(TGSAMENAME, new DummyThreads(), "DummyTGSAMENAMEB").start();
        new Thread(TGSAMENAME, new DummyThreads(), "DummyTGSAMENAMEC").start();
        new Thread(TGSAMENAME, new DummyThreads(), "DummyTGSAMENAMED").start();

        new Thread(TGSAMENAME2,new DummyThreads(), "DummyTGSAMENAME2A").start();
        new Thread(TGSAMENAME2,new DummyThreads(), "DummyTGSAMENAME2B").start();
        new Thread(TGSAMENAME2,new DummyThreads(), "DummyTGSAMENAME2C").start();

        new Thread(TGSUBOFTG1,new DummyThreads(), "DummyChildGroup1").start();
        new Thread(TGSUBOFTG1,new DummyThreads(), "DummyChildGroup2").start();

    }
}

/*
ThreadGroups made:
TG1 (Parent = main / uspecified)
TG2 (Parent = main / uspecified)
TG3 (Parent = main / uspecified)
TGSAMENAME (Parent = main / uspecified)
TGSAMENAME (Parent = main / uspecified) (Stored under local variable TGSAMENAME2. Primarily used to test filtering)
TGSUBOFTG1 (Parent = TG1)



 */