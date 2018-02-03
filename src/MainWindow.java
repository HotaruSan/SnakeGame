import javax.swing.*;

/**
 * Created by HotaruSan on 03.02.2018.
 * Greate thanks to Irina Galkina https://www.youtube.com/watch?v=TJvcYxfQ3J0
 * Create some mini game like snake
 * In this class we set settings of the windows like size and position
 */
public class MainWindow extends JFrame{
    public MainWindow () {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //height is 345 because upper toolbar of the window include in overall size
        setSize(320,345);
        setLocation(400,400);
        //include our main logic game class
        add(new GameField());
        setVisible(true);
    }
public static void main (String args[]){
        MainWindow mw = new MainWindow();
}
}
