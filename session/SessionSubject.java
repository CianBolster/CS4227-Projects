package session;

import java.util.ArrayList;

public interface SessionSubject
{
    public abstract void attach(SessionObserver o);
    
    public abstract void detach(SessionObserver o);
    
    public abstract void nofity();
    
    public abstract ArrayList<Integer> getState();
}
