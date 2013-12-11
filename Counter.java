
public class Counter {
    private int counter;

    public Counter(int minutes, int seconds) {
        counter = (minutes*60)+seconds;
    }

    public void decrement() {
        counter--;    
    }

    public void setCounter(int minutes, int seconds) {
        counter = (minutes*60)+seconds;
    }

    public int getCounter() {
        return counter;
    }

    private int minutes() {
        return counter/60;
    }

    private int seconds() {
        return counter%60;
    }

    private String displayTime(int time) {
        if (time < 10) {
            return "0" + time;
        }
        else {
            return "" + time;
        }
    }

    public String toString() {
       return displayTime(minutes()) + ":" + displayTime(seconds());
    }
}