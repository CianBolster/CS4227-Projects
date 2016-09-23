package session;

public class PartyInviteFactory extends InviteFactory {

    @Override
    public Invite createInvite(int senderID, int receiverID, int partyID)
    {
        return new PartyInvite(senderID, receiverID, partyID);
    }
}
