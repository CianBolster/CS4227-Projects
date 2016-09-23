package session;

public abstract class Invite
{
    protected int senderID;
    protected int receiverID;
    protected int partyID;
    protected String message;
    
    public abstract int getSenderID();
    
    public abstract int getReceiverID();
    
    public abstract int getPartyID();
    
    public abstract String getMessage();
    
    public abstract boolean equals(Invite otherInvite);
}
