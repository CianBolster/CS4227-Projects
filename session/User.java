package session;

public abstract class User {
    protected int id;
    protected String name;

    public abstract int getId();

    public abstract void setId(int id);

    public abstract String getName();

    public abstract void setName(String name);
}
