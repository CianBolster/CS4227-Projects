/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/
*   Approximate use: 70%
**/
package userinterface;

import session.SessionInformation;

public class MenuFactory {

    private SessionInformation sessionInfo = null;

    public MenuFactory() {
    }

    public MenuFactory(SessionInformation sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Menu getMenu(int menuID, MenuManager menuMgr) {
        Menu menu = null;

        switch (menuID) {
            case 1:
                menu = new StartScreenUI(500, 500);
                menu.setMenuManager(menuMgr);
                break;
            case 2:
                menu = new MainMenuUI();
                menu.setMenuManager(menuMgr);
                break; 
            case 3:
                menu = new PartyUI();
                menu.setMenuManager(menuMgr);
                break;
            case 4:
                menu = new MessageUI();
                menu.setMenuManager(menuMgr);
                break;
        }
        menu.setSessionInformation(sessionInfo);
        menu.setMenuManager(menuMgr);
        return menu;
    }
}
