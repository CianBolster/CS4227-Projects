/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseInterface.java
*   Approximate use: 30%
**/

package database;

import java.util.ArrayList;

public interface DatabaseInterface {

    public abstract boolean canLogin(String username, String password) throws Exception;

    /* References the local Player class */
    public abstract String getPlayerDetails(String username) throws Exception;

    public abstract ArrayList<Integer> getPlayerFriendList(int playerID) throws Exception;

    public abstract ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception;

    public abstract void createPlayer(String username, String password, String email) throws Exception;

    public abstract int createParty(int partyLeaderID) throws Exception;

    public abstract ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception;

    public abstract boolean isPartyFull(int partyID) throws Exception;

    public abstract void addPlayerToParty(int playerID, int partyID) throws Exception;

    public abstract void removePlayerFromParty(int partyID, int playerID) throws Exception;

    public abstract int checkUserNameAndEmail(String username, String email) throws Exception;

    public abstract boolean doesPartyExist(int partyID) throws Exception;

    public abstract int doesPlayerExist(String username) throws Exception;

    public abstract boolean isPlayerInParty(int playerID) throws Exception;

    public abstract void addInvite(int senderID, int receiverID, int partyID) throws Exception;

    public abstract void removeInvite(int senderID, int receiverID, int partyID) throws Exception;
}
