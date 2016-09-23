/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/MenuUI.java
*   Approximate use: 30%
**/
package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageUI extends Menu {

    public MessageUI() {
        showMessageMenu();
    }

    public void showMessageMenu() {
        JPanel mainMenuPanel = new JPanel();
        BorderLayout mainMenuLayout = new BorderLayout();
        mainMenuPanel.setLayout(mainMenuLayout);

        JPanel topBarPanel = new JPanel();
        FlowLayout topBarLayout = new FlowLayout();
        topBarLayout.setAlignment(FlowLayout.RIGHT);
        topBarPanel.setLayout(topBarLayout);
        JLabel uiLabel = new JLabel("Messages");
        topBarPanel.add(uiLabel);
        JLabel spacer = new JLabel("          ");
        topBarPanel.add(spacer);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sessionInfo.logPlayerOut();
                menuMgr.getMenuFromFactory(1);

            }
        });
        topBarPanel.add(logoutButton);
        mainMenuPanel.add(topBarPanel, mainMenuLayout.NORTH);

        JPanel centerMenuPanel = new JPanel();
        BorderLayout centerMenuLayout = new BorderLayout();
        centerMenuPanel.setLayout(centerMenuLayout);
        centerMenuPanel.setBackground(Color.blue);

        // list of members and leave party button on top row
        JPanel topCenterMenuPanel = new JPanel();
        FlowLayout topCenterMenuLayout = new FlowLayout();
        topCenterMenuLayout.setAlignment(FlowLayout.LEFT);
        topCenterMenuPanel.setLayout(topCenterMenuLayout);

        JPanel midCenterMenuPanel = new JPanel();
        FlowLayout midCenterMenuLayout = new FlowLayout();
        midCenterMenuLayout.setAlignment(FlowLayout.LEFT);
        midCenterMenuPanel.setLayout(midCenterMenuLayout);
        JTextArea inviteList = new JTextArea();
        JScrollPane inviteScroller = new JScrollPane(inviteList);
        inviteScroller.setPreferredSize(new Dimension(300, 150));
        populateInviteList(inviteList);
        midCenterMenuPanel.add(inviteScroller);
        JPanel inviteOptionPanel = new JPanel();
        GridLayout inviteOptionLayout = new GridLayout(3, 0);
        inviteOptionPanel.setLayout(inviteOptionLayout);
        JButton acceptInviteButton = new JButton("Accept Invite");
        acceptInviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sessionInfo.isPlayerInParty()) {
                    JOptionPane.showMessageDialog(null, "To join a new party please leave the party you\nare currently in.", null, JOptionPane.WARNING_MESSAGE);
                } else {
                    try {//first check if already in a party
                        int userid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the friend whose"
                                + "\ninvite you would like to accept."));
                        if (sessionInfo.isFriend(userid)) {
                            sessionInfo.addPlayerToParty(sessionInfo.getPartyIDFromSenderInvite(userid));
                            sessionInfo.removeInvite(userid);
                            sessionInfo.getPlayerInvites();
                            menuMgr.getMenuFromFactory(4);
                        } else {
                            JOptionPane.showMessageDialog(null, "Not a valid friend ID.", null, JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Input invalid. Please enter the ID of a friend.", null, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        inviteOptionPanel.add(acceptInviteButton);
        JButton declineInviteButton = new JButton("Decline Invite");
        declineInviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userid = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the ID of the friend whose"
                            + "\ninvite you would like to decline."));
                    if (sessionInfo.isFriend(userid)) {
                        sessionInfo.removeInvite(userid);
                        sessionInfo.getPlayerInvites();
                        menuMgr.getMenuFromFactory(4);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Not a valid friend ID.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Input invalid. Please enter the ID of a friend.", null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        inviteOptionPanel.add(declineInviteButton);
         JButton refreshInviteButton = new JButton("Refresh Invites");
        refreshInviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sessionInfo.getPlayerInvites();
                populateInviteList(inviteList);
            }
        });
        
        inviteOptionPanel.add(refreshInviteButton);
        midCenterMenuPanel.add(inviteOptionPanel);
        centerMenuPanel.add(midCenterMenuPanel, centerMenuLayout.CENTER);

        mainMenuPanel.add(centerMenuPanel, mainMenuLayout.CENTER);

        JPanel bottomBarPanel = new JPanel();
        FlowLayout bottomBarLayout = new FlowLayout();
        bottomBarLayout.setAlignment(FlowLayout.LEFT);
        bottomBarPanel.setLayout(bottomBarLayout);
        JButton returnButton = new JButton("<-Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //sessionInfo.logPlayerOut();
                menuMgr.getMenuFromFactory(2);

            }
        });
        bottomBarPanel.add(returnButton);
        mainMenuPanel.add(bottomBarPanel, mainMenuLayout.SOUTH);
        panel = mainMenuPanel;
    }

    @Override
    public JPanel getMenuPanel() {
        return panel;
    }

    @Override
    public void setMenuManager(MenuManager menuMgr) {
        this.menuMgr = menuMgr;
    }

    private void populateInviteList(JTextArea inviteList) {
        inviteList.setText("");
        inviteList.append("Party Invitations:\n");
        if (sessionInfo != null) {
            ArrayList<String> invitations = sessionInfo.getInviteMessages(); // get invite messages
            if (invitations.size() > 0) {
                inviteList.append(invitations.get(0) + "\n");
                for (int i = 1; i < invitations.size(); i++) {
                    inviteList.append("" + invitations.get(i) + "\n");
                }
            }
        }
    }
}
