/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/Panel.java
*   Approximate use: 80%
**/
package userinterface;

import javax.swing.JPanel;
import session.SessionInformation;


public abstract class Menu {

    protected MenuManager menuMgr;
    protected SessionInformation sessionInfo;
    protected JPanel panel;

    public Menu() {

    }

    public abstract JPanel getMenuPanel();

    public void setMenuManager(MenuManager menuMgr) {
        this.menuMgr = menuMgr;
    }

    public void setSessionInformation(SessionInformation sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
}
