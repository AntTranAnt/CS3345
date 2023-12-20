public class Printjob implements Comparable<Printjob> {
    protected String user;
    protected int userPriority;
    protected int pages;
    protected int jobPriority;

    //constructor to create Printjob object
    public Printjob(String user, int priority, int pages) {
        this.user = user;
        this.userPriority = priority;
        this.pages = pages;
        this.jobPriority = this.userPriority * this.pages;
    }

    //public method to get print priority
    public int getUserPriority() {
        return this.userPriority;
    }

    //public method to return username
    public String getUser() {
        return this.user;
    }

    //return 0 if equal, -1 if smaller than another object, 1 if bigger than another object
    @Override
    public int compareTo(Printjob other) {
        if (this.jobPriority == other.jobPriority) {
            return 0;
        } else if (this.jobPriority < other.jobPriority) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.user + "\t" + this.userPriority + "\t" + this.pages + "\t";
    }
}