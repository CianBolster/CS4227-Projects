/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/PanelManager.java
*   Approximate use: 80%
**/
package userinterface;

public class MenuManager implements UISubject {

    private MenuFactory menuFac;
    private Screen screen;
    private Menu updatedMenu;

    public MenuManager(MenuFactory menuFac) {
        this.menuFac = menuFac;
    }

    @Override
    public void registerObserver(Screen scr) {
        screen = scr;
        getMenuFromFactory(1);
        notifyObserver();
    }

    @Override
    public void removeObserver() {
        screen = null;
    }

    @Override
    public void notifyObserver() {
        screen.update(this);
    }

    public void getMenuFromFactory(int menuID) {
        updatedMenu = menuFac.getMenu(menuID, this);
        notifyObserver();
    }

    @Override
    public Menu getUpdatedMenu() {
        return updatedMenu;
    }

    public void setUpdatedMenu(Menu menu) {
        updatedMenu = menu;
    }
}
