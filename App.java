import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 360;
        int boardheight = 640;

        JFrame frame = new JFrame("Flappy Bird"); 
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird FlappyBird=new FlappyBird();
        frame.add(FlappyBird);
        frame.pack();
        FlappyBird.requestFocus();
        frame.setVisible(true);

    }
}
