/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/DatabaseAccess.java
*   Approximate use: 5% 
**/
package database;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseAccess implements DatabaseInterface {

    private PrintWriter pWriter;
    private FileWriter fWriter;
    private File file;
    private Scanner fileReader;
    private final String USER_DETAILS_FILE = "UserDetails.txt";
    private final String FRIEND_DETAILS_FILE = "FriendDetails.txt";
    private final String PARTY_DETAILS_FILE = "PartyDetails.txt";
    private final String INVITE_DETAILS_FILE = "InviteDetails.txt";

    public DatabaseAccess() throws Exception {

    }

    @Override
    public boolean canLogin(String username, String password) throws Exception {
        file = new File(USER_DETAILS_FILE);
        if (file.exists()) {
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] lineFromFile = (fileReader.nextLine()).split(",");
                if (lineFromFile[2].equals(password)) {
                    fileReader.close();
                    file = null;
                    return true;
                }
            }
        }

        fileReader.close();
        file = null;
        return false;
    }

    @Override
    public String getPlayerDetails(String username) throws Exception {
        file = new File(USER_DETAILS_FILE);
        fileReader = new Scanner(file);
        String playerDetails = "";
        while (fileReader.hasNextLine()) {
            String[] lineFromFile = (fileReader.nextLine()).split(",");
            if (lineFromFile[1].equals(username)) {
                playerDetails += lineFromFile[0] + ",";
                playerDetails += lineFromFile[1] + ",";
                playerDetails += lineFromFile[3] + ",";
            }
        }
        fileReader.close();
        file = null;
        return playerDetails;
    }

    @Override
    public ArrayList<Integer> getPlayerFriendList(int playerID) throws Exception {
        String pidStr = playerID + "";
        ArrayList<Integer> friendIDs = new ArrayList<Integer>();
        file = new File(FRIEND_DETAILS_FILE);
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String lineFromFile[] = (fileReader.nextLine()).split(",");
            if (pidStr.equals(lineFromFile[0])) {
                friendIDs.add(Integer.parseInt(lineFromFile[1]));
            } else if (pidStr.equals(lineFromFile[1])){
                friendIDs.add(Integer.parseInt(lineFromFile[0]));
            }
        }
        fileReader.close();
        file = null;

        return friendIDs;
    }

    @Override
    public ArrayList<Integer[]> getPlayerInvites(int playerID) throws Exception {
        ArrayList<Integer[]> idList = new ArrayList<Integer[]>();
        Integer [] ids = new Integer [2];
        file = new File(INVITE_DETAILS_FILE);
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String[] lineFromFile = (fileReader.nextLine()).split(",");
            if (playerID == Integer.parseInt(lineFromFile[1])) {
                ids[0] = Integer.parseInt(lineFromFile[0]);
                ids[1] = Integer.parseInt(lineFromFile[2]);
                if (!idList.contains(ids)) {
                    idList.add(ids);
                }
            }
        }
        fileReader.close();
        file = null;
        return idList;
    }

    @Override
    public void createPlayer(String username, String password, String email) throws Exception {
        file = new File(USER_DETAILS_FILE);
        ArrayList<String> userDetails = fileToList();
        String playerDetails = "";

        if (userDetails.size() > 0) {
            String[] lastEntry = (userDetails.get(userDetails.size() - 1)).split(",");
            int newPlayerID = Integer.parseInt(lastEntry[0]) + 1;
            playerDetails = newPlayerID + "," + username + "," + password + "," + email;
        } else {
            playerDetails += 1 + "," + username + "," + password + "," + email;
        }
        fWriter = new FileWriter(file, true);
        pWriter = new PrintWriter(fWriter);
        pWriter.println(playerDetails);
        pWriter.close();
        fWriter.close();
        file = null;
    }

    @Override
    public int createParty(int partyLeaderID) throws Exception {
        file = new File(PARTY_DETAILS_FILE);
        ArrayList<String> parties = fileToList();
        String newParty = "";
        int newPartyID = 1;
        if(parties.size() > 0){
		String [] lastEntry = (parties.get(parties.size() - 1)).split(",");
		newPartyID = Integer.parseInt(lastEntry[0]) + 1;
		newParty += newPartyID + "," + partyLeaderID;
                }
                else{
                    newParty += 1 + "," +  partyLeaderID;
                }

        fWriter = new FileWriter(file, true);
        pWriter = new PrintWriter(fWriter);
        pWriter.println(newParty);
        pWriter.close();
        fWriter.close();
        file = null;

        return newPartyID;
    }

    @Override
    public ArrayList<Integer> getPartyDetails(int partyID, int playerID) throws Exception {
        file = new File(PARTY_DETAILS_FILE);
        fileReader = new Scanner(file);
        ArrayList<Integer> partyDetails = new ArrayList<Integer>();
        while (fileReader.hasNextLine()) {
            String lineFromFile[] = fileReader.nextLine().split(",");
            if (partyID == Integer.parseInt(lineFromFile[0])) {
                for (String x : lineFromFile) {
                    partyDetails.add(Integer.parseInt(x));
                }

            }
        }
        fileReader.close();
        file = null;
        if (!partyDetails.contains(playerID)) {
            partyDetails.clear();
        }
        return partyDetails;
    }

    @Override
    public boolean isPartyFull(int partyID) throws Exception {
        boolean partyFull = true;
        file = new File(PARTY_DETAILS_FILE);
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String lineFromFile[] = fileReader.nextLine().split(",");
            if (partyID == Integer.parseInt(lineFromFile[0])) {
                if (lineFromFile.length < 6) {
                    partyFull = false;
                    break;
                }
            }
        }
        fileReader.close();
        file = null;
        return partyFull;
    }

    @Override
    public void addPlayerToParty(int playerID, int partyID) throws Exception {
        file = new File(PARTY_DETAILS_FILE);
        ArrayList<String> parties = fileToList();
    
        for (int i = 0; i < parties.size(); i++) {
            String[] lineFromFile = parties.get(i).split(",");
            if (partyID == Integer.parseInt(lineFromFile[0])) {
                String amendedParty = parties.get(i);
                amendedParty += "," + playerID;
                parties.remove(i);
                parties.add(i, amendedParty);
                break;
            }
        }
        writeToFile(parties, false);
        file = null;
    }

    @Override
    public void removePlayerFromParty(int partyID, int playerID) throws Exception {
        file = new File(PARTY_DETAILS_FILE);
        ArrayList<String> parties = fileToList();

        for (int i = 0; i < parties.size(); i++) {
            String amendedParty = "";
            String[] partyInfo = (parties.get(i)).split(",");
            if (partyID == Integer.parseInt(partyInfo[0])) {
                amendedParty += partyInfo[0];
                for (int j = 1; j < partyInfo.length; j++) {
                    if (playerID != Integer.parseInt(partyInfo[j])) {
                        amendedParty += "," + partyInfo[j];
                    }
                }
                parties.remove(i);
                if (amendedParty.contains(",")) {
                    parties.add(i, amendedParty);
                }
                break;
            }
        }
        writeToFile(parties, false);
        file = null;
    }

    @Override
    public int checkUserNameAndEmail(String username, String email) throws Exception {
        file = new File(USER_DETAILS_FILE);
        fileReader = new Scanner(file);

        while (fileReader.hasNextLine()) {
            String[] lineFromFile = (fileReader.nextLine()).split(",");
            if (lineFromFile[1].equals(username)) {
                fileReader.close();
                file = null;
                return 0;
            } else if (lineFromFile[3].equals(email)) {
                fileReader.close();
                file = null;
                return 1;
            }
        }
        fileReader.close();
        file = null;
        return 2;
    }

    @Override
    public boolean doesPartyExist(int partyID) throws Exception {
        file = new File(PARTY_DETAILS_FILE);
        fileReader = new Scanner(file);
        boolean partyExists = false;
        while (fileReader.hasNextLine()) {
            String[] lineFromFile = fileReader.nextLine().split(",");
            if (partyID == Integer.parseInt(lineFromFile[0]));
            {
                partyExists = true;
            }
        }
        fileReader.close();
        file = null;
        return partyExists;
    }

    @Override
    public int doesPlayerExist(String username) throws Exception {
        file = new File(USER_DETAILS_FILE);
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String[] lineFromFile = fileReader.nextLine().split(",");
            if (username.equals(lineFromFile[1])) {
                fileReader.close();
                file = null;
                return Integer.parseInt(lineFromFile[0]);
            }
        }
        fileReader.close();
        file = null;
        return 0;
    }

    @Override
    public boolean isPlayerInParty(int playerID) throws Exception {
        boolean isInParty = false;
        file = new File(PARTY_DETAILS_FILE);
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String[] lineFromFile = fileReader.nextLine().split(",");
            for (int i = 1; i < lineFromFile.length; i++) {
                if (playerID == Integer.parseInt(lineFromFile[i])) {
                    isInParty = true;
                    break;
                }
            }
        }
        fileReader.close();
        file = null;

        return isInParty;
    }

    @Override  
    public void addInvite(int senderID, int receiverID, int partyID) throws Exception {
        file = new File(INVITE_DETAILS_FILE);
        String newInvite = "" + senderID + "," + receiverID + "," + partyID;

        fWriter = new FileWriter(file, true);
        pWriter = new PrintWriter(fWriter);
        pWriter.println(newInvite);
        pWriter.close();
        fWriter.close();
        file = null;
    }

    @Override
    public void removeInvite(int senderID, int receiverID, int partyID) throws Exception {
        file = new File(INVITE_DETAILS_FILE);
        ArrayList<String> invites = fileToList();
    
        for (int i = 0; i < invites.size(); i++) {
            String[] lineFromFile = invites.get(i).split(",");
            if (senderID == Integer.parseInt(lineFromFile[0]) && receiverID == Integer.parseInt(lineFromFile[1])
                    && partyID == Integer.parseInt(lineFromFile[2])) {
                invites.remove(i);
                break;
            }
        }
        writeToFile(invites, false);
        file = null;
    }
    
    private ArrayList<String> fileToList() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                list.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (Exception e) {
        }
        return list;
    }
    
    private void writeToFile(ArrayList<String> list, boolean amended) {
        try {
            fWriter = new FileWriter(file, amended);
            pWriter = new PrintWriter(fWriter);
            for (int i = 0; i < list.size(); i++) {
                pWriter.println(list.get(i));
            }

            pWriter.close();
            fWriter.close();
        } catch (Exception e) {
        }
    }
}
