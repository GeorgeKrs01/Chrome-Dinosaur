import javax.swing.*;
import java.awt.*;

public class ChromeDinosaur extends JPanel {
    int boardWidth = 750;
    int boardHeight = 250;

    public ChromeDinosaur() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray);
    }

}
