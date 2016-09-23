/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/ProcessInput.java
*   Approximate use: 40%
**/
package session;

import database.DatabaseInterface;
import java.util.ArrayList;

public class SessionInformation {

    private static SessionInformation sessionInfo = null;
    private DatabaseInterface database;
    private InviteFactory inviteFactory;
    private Player player = null;
    private Party party;
    
    private SessionInformation() {
        setPlayer();
        party = new Party();
        player.attach(party);
        inviteFactory = new PartyInviteFactory();
    }

    public static SessionInformation getInstance() {
        if (sessionInfo == null) {
            sessionInfo = new SessionInformation();
        }

        return sessionInfo;
    }

    public void setPlayer() {
        player = Player.getInstance();
    }

    public void setDbConnection(DatabaseInterface database) {
        this.database = database;
    }

    public boolean canUserLogin(String username, String password) {
        boolean canLogin = false;

        try {
            canLogin = database.canLogin(username, password);
            if (canLogin) {
                getPlayerDetails(username);
                getPlayerFriendList();
                getPlayerInvites();
            }
        } catch (Exception e) {
            System.out.println("Error logging in: " + e.toString());
        }

        return canLogin;
    }

    public boolean doesPlayerExist(String username) {
        boolean exists = false;

        try {
            if (database.doesPlayerExist(username) != 0) {
                exists = true;
            }
        } catch (Exception e) {
            System.out.println("doesPlayerExist Error: " + e.toString());
        }

        return exists;
    }

    public int checkUsernameEmail(String username, String email) {
        int existsType = -1;

        try {
            existsType = database.checkUserNameAndEmail(username, email);
        } catch (Exception e) {
            System.out.println("Error validaing username/email: " + e.toString());
        }

        return existsType;
    }

    public void createPlayer(String username, String password, String email) {
        try {
            database.createPlayer(username, password, email);
        } catch (Exception e) {
            System.out.println("Error creating player: " + e.toString());
        }
    }

    public void getPlayerDetails(String username) {
        try {
            String[] details = database.getPlayerDetails(username).split(",");

            this.player.setId(Integer.parseInt(details[0]));
            this.player.setName(details[1]);
            this.player.setEmail(details[2]);
        } catch (Exception e) {
            System.out.println("Error getting player details: " + e.toString());
        }
    }

    public void getPlayerFriendList() {
        try {
            ArrayList<Integer> friends = database.getPlayerFriendList(player.getId());
            if (friends.size() > 0) {
                for (int i = 0; i < friends.size(); i++) {
                    player.addFriend(friends.get(i));
                }
            }
        } catch (Exception e) {
        }
    }

    public void getPlayerInvites() {
        try {
            ArrayList<Integer[]> invites = database.getPlayerInvites(player.getId());
            if (invites.size() > 0) {
                for (int i = 0; i < invites.size(); i++) {
                    Invite newInvite = inviteFactory.createInvite(invites.get(i)[0], player.getId(), invites.get(i)[1]);
                    player.addInvite(newInvite);
                }
            }
        } catch (Exception e) {
        }
    }

    public void getPartyDetails() {
        try {
            ArrayList<Integer> partyDetails = database.getPartyDetails(party.getId(), player.getId());
            
            player.clearPartyInformation();
            player.updatePartyInformation(partyDetails);
            player.nofity();
        } catch (Exception e) {
        }
    }

    public boolean isPlayerInParty() {
        return party.doesPartyExist();
    }

    public boolean isMemberOfParty(int id) {
        return party.isMember(id);
    }

    public boolean isFriend(int id) {
        return player.isFriend(id);
    }

    public void createParty() {
        try {
            int partyID = database.createParty(player.getId());
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.nofity();
        } catch (Exception e) {

        }
    }

    public void leaveParty() {
        try {
            int partyID = party.getId();
            System.out.println(partyID);
            System.out.println(player.getId());
            database.removePlayerFromParty(partyID, player.getId());
            player.clearPartyInformation();
            player.nofity();
        } catch (Exception e) {
        }
    }

    public String getPlayerName() {
        return player.getName();
    }

    public void logPlayerOut() {
        if (party.doesPartyExist()) {
            leaveParty();
        }
        System.out.println("logged out");
        player.resetValues();
    }

    public boolean isPartyLeader() {
        return party.isPartyLeader(player.getId());
    }

    public int getPartySize() {
        return party.getPartySize();
    }

    public void addPlayerToParty(int partyID) {
        try {
            player.clearPartyInformation();
            player.addToPartyInformation(partyID);
            player.addToPartyInformation(player.getId());
            player.nofity();
            database.addPlayerToParty(player.getId(), partyID);

            getPartyDetails();
        } catch (Exception e) {
        }
    }

    public void removePlayerFromParty(int playerID) {
        try {
            database.removePlayerFromParty(party.getId(), playerID);
        } catch (Exception e) {
        }
    }

    public ArrayList<String> getInviteMessages() {
        ArrayList<String> invMsg = new ArrayList<String>();
        ArrayList<Invite> invites = player.getInvites();
        for (int i = 0; i < invites.size(); i++) {
            invMsg.add(invites.get(i).getMessage());
        }

        return invMsg;
    }

    public ArrayList<Integer> getPartyMembers() {
        return party.getPartyMembers();
    }

    public void sendInvite(int friendToInvite) {
        try {
            database.addInvite(player.getId(), friendToInvite, party.getId());
        } catch (Exception ex) {
        }
    }

    public int getPartyIDFromSenderInvite(int playerID)
    {
        ArrayList<Invite> myInvites = player.getInvites();
        int partyID = 0;
        for (int i = 0; i < myInvites.size(); i++) {
            if (playerID == myInvites.get(i).getSenderID()) {
                partyID = myInvites.get(i).getPartyID();
                break;
            }
        }
        System.out.println(partyID);
        return partyID;
    }
    public void removeInvite(int playerID) {
        int partyID = getPartyIDFromSenderInvite(playerID);
        player.removeInvite(playerID, partyID);
        try {
            database.removeInvite(playerID, player.getId(), partyID);
        } catch (Exception e) {
            System.out.println("check 4");
        }
    }
    public int getPlayerId()
    {
        return player.getId();
    }
}
