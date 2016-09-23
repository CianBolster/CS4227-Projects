package session;

public class PartyInvite extends Invite
{
    public PartyInvite(int senderID, int receiverID, int partyID)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.partyID = partyID;
    }
    @Override
    public int getSenderID() {
        return senderID;
    }

    @Override
    public int getReceiverID() {
        return receiverID;
    }

    @Override
    public String getMessage() {
        return "Party Invite received from User " + senderID;
    }

    @Override
    public boolean equals(Invite otherInvite) {
        return (senderID == otherInvite.getSenderID() && partyID == otherInvite.partyID);
    }

    @Override
    public int getPartyID() {
        return partyID;
    }
}
