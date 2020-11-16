package notate;

@Timed
public class RandomNotebook implements Notebook{
    private String message;
    //random amount of repeating. From 1 to 9;
    private int repeat = (int)(1+(Math.random()*9));

    public RandomNotebook() {

    }

    public void notateSomething() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("Note =  "+message);
        }
    }

    public void setMessage(String message) {
        this.message=message;
    }
}
