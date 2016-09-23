/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/Window.java
*   Approximate use: 50%
**/
package userinterface;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JFrame implements UIObserver {

    private JPanel displayedMenuPanel = null;

    public Screen() {
        super("Party maker prototype");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void update(UISubject s) {
        dropMenuPanel();
        Menu updatedMenu = s.getUpdatedMenu();
        displayedMenuPanel = updatedMenu.getMenuPanel();
        add(displayedMenuPanel);
        setVisible(true);
    }

    public void dropMenuPanel() {
        if (displayedMenuPanel != null) {
            remove(displayedMenuPanel);
            displayedMenuPanel = null;
        }
    }
}
