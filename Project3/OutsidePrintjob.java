//subclass of Printjob that calculates cost
public class OutsidePrintjob extends Printjob{
    private int cost;

    public OutsidePrintjob(String user, int priority, int pages) {
        super(user, priority, pages);
        this.cost = pages * 10;
    }

    //public function to return cost
    public double getCost() {
        return totalCost();
    }
    
    //private function to calculate cost
    private double totalCost() {
        return cost / 100.0;
    }

    @Override
    public String toString() {
        return this.user + "\t" + this.userPriority + "\t" + this.pages + "\t$" + totalCost();
    }
}
