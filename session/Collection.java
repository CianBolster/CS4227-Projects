package session;

import java.util.ArrayList;

public interface Collection<E> {

    public abstract void add(E item);

    public abstract E get(int id);

    public abstract ArrayList<E> getAll();

    public abstract void remove(int id, int pid);
}
