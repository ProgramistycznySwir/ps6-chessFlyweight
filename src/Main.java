import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Main {
    public static void main(String[] paramArrayOfString) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Chessboard board = new Chessboard();
        
        JToolBar bar = new JToolBar();
        try {
            board.undo = new JButton(new ImageIcon("undo.png"));
            board.redo = new JButton(new ImageIcon("redo.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        board.undo.addActionListener(board.new UndoButton());
        board.redo.addActionListener(board.new RedoButton());
        board.undo.setEnabled(false);
        board.redo.setEnabled(false);
        bar.add(board.undo);
        bar.add(board.redo);
        
        frame.add(bar, BorderLayout.PAGE_START);
        frame.add(board);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
