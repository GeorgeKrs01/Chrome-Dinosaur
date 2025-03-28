import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardwidth = 750;
        int boardeHeight = 250;

        JFrame frame = new JFrame("Chrome Dinosaur");
        frame.setSize(boardwidth, boardeHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ChromeDinosaur chromeDinosaur = new ChromeDinosaur();
        frame.add(chromeDinosaur);
        frame.pack();
        frame.setVisible(true);
    }
}