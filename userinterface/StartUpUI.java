/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/BootUI.java
*   Approximate use: 85%
**/
package userinterface;

import database.DatabaseAccess;
import database.DatabaseInterface;
import session.SessionInformation;

public class StartUpUI {

    public StartUpUI() {

    }

    public void run() {
        try {
            /* Creates window to display GUI components */
            /* Observer to PanelManager */
            Screen screen = new Screen();
            /* Starts database connection */
            DatabaseInterface database = new DatabaseAccess();
            /* Will process input taken from client GUI */
            SessionInformation sessionInfo = SessionInformation.getInstance();
            sessionInfo.setDbConnection(database);
            /* Panel factory to display panels on window */
            MenuFactory menuFac = new MenuFactory(sessionInfo);
            /* Subject in observer pattern */
            MenuManager menuMgr = new MenuManager(menuFac);
            menuMgr.registerObserver(screen);
        } catch (Exception e) {
            System.out.println("Error bootingUI: " + e.toString());
        }
    }
}
