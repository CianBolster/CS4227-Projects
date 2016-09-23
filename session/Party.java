package session;

import java.util.ArrayList;

public class Party implements SessionObserver {

    private int id;
    private int leaderID;
    private ArrayList<Integer> partyMembers;

    public Party() {
        partyMembers = new ArrayList<Integer>();
    }

    @Override
    public void update(SessionSubject s) {
        ArrayList<Integer> partyInfo = s.getState();
        if (!partyInfo.isEmpty()) {
            id = partyInfo.get(0);
            partyMembers.clear();
            for (int i = 1; i < partyInfo.size(); i++) {
                partyMembers.add(partyInfo.get(i));
            }
            leaderID = partyMembers.get(0);
        } else {
            id = 0;
            leaderID = 0;
            partyMembers.clear();
        }
    }

    public boolean isPartyLeader(int id) {
        return (leaderID == id);
    }

    public boolean doesPartyExist() {
        if (partyMembers.size() == 0) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public int getPartySize() {
        return (partyMembers.size() > 0) ? (partyMembers.size()) : 0;
    }
    public ArrayList<Integer> getPartyMembers()
    {
        return partyMembers;
    }
    public boolean isMember(int id)
    {
        boolean validMember = false;
        if(partyMembers.size() > 1 && partyMembers.contains(id))
        {
            validMember = true;
        }
        return validMember;
    }
}
