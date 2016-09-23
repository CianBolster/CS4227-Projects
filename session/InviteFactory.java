package session;

public abstract class InviteFactory
{
    public abstract Invite createInvite(int senderID, int receiverID, int partyID);
}
