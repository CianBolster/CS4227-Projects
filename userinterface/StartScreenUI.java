/*
* Anon., in Collins, J. (ed.) (2015) ‘CS4125: Serenity Gaming – a sample project from Sem1 2014-2015’,
* CS4125: Systems Analysis & Design [online],
* available: http://www.csis.ul.ie/coursemodule/CS4125 [accessed 20 Oct 2015]
*
*   Adapted file: Serenity_Gaming/HomeUI.java
*   Approximate use: 90%
**/
package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class StartScreenUI extends Menu {

    private int SCREEN_WIDTH = 0;
    private int SCREEN_HEIGHT = 0;

    public StartScreenUI() {

    }

    public StartScreenUI(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        showLoginScreen();
    }

    public void showLoginScreen() {
        final JPanel loginScreen = new JPanel();

        GridBagLayout windowLayout = new GridBagLayout();
        windowLayout.columnWidths = new int[]{SCREEN_WIDTH / 3, SCREEN_WIDTH / 3, SCREEN_WIDTH / 3};
        windowLayout.rowHeights = new int[]{SCREEN_HEIGHT / 3, SCREEN_HEIGHT / 3, SCREEN_HEIGHT / 3};
        windowLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
        windowLayout.rowWeights = new double[]{0.0, 0.0, 1.0};
        loginScreen.setLayout(windowLayout);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;

        JPanel loginPanel = new JPanel();

        Border margin = new EmptyBorder(10, 10, 10, 10);
        Border loginBorder = loginPanel.getBorder();
        loginPanel.setBorder(new CompoundBorder(loginBorder, margin));

        loginPanel.setBackground(Color.gray);
        GridLayout loginLayout = new GridLayout(3, 2);
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginPanel.setLayout(loginLayout);
        JLabel usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);
        final JTextField getUsername = new JTextField();
        loginPanel.add(getUsername);
        JLabel passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);
        final JPasswordField getPassword = new JPasswordField();
        loginPanel.add(getPassword);
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);
        JButton registerButton = new JButton("Register");
        loginPanel.add(registerButton);
        loginScreen.add(loginPanel, cons);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = getUsername.getText().toLowerCase();
                String password = getPassword.getText();
                if (!username.equals("") && !password.equals("")) {
                    boolean login = sessionInfo.canUserLogin(username, password);

                    if (login) {
                        menuMgr.getMenuFromFactory(2);
                        System.out.println("logged in");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid login details");
                        getUsername.setText("");
                        getPassword.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fields Blank");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginScreen.setVisible(false);
                showRegisterScreen();
                menuMgr.notifyObserver();
            }
        });

        panel = loginScreen;
    }

    public void showRegisterScreen() {
        final JPanel registerPanel = new JPanel();

        GridBagLayout windowLayout = new GridBagLayout();
        windowLayout.columnWidths = new int[]{SCREEN_WIDTH / 4, SCREEN_WIDTH / 2, SCREEN_WIDTH / 4};
        windowLayout.rowHeights = new int[]{SCREEN_HEIGHT / 4, SCREEN_HEIGHT / 2, SCREEN_HEIGHT / 4};
        windowLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
        windowLayout.rowWeights = new double[]{0.0, 0.0, 1.0};
        registerPanel.setLayout(windowLayout);

        GridBagConstraints cons = new GridBagConstraints();
        cons.weighty = 1.0;
        cons.weightx = 1.0;
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridheight = 1;
        cons.gridwidth = 1;

        JPanel registerUserPanel = new JPanel();
        registerUserPanel.setMinimumSize(new Dimension(SCREEN_WIDTH / 2 + 20, SCREEN_HEIGHT / 2));
        Border margin = new EmptyBorder(10, 10, 10, 10);
        Border loginBorder = registerUserPanel.getBorder();
        registerUserPanel.setBorder(new CompoundBorder(loginBorder, margin));
        registerUserPanel.setBackground(Color.lightGray);

        GridLayout registerUserLayout = new GridLayout(7, 2);
        registerUserLayout.setHgap(10);
        registerUserLayout.setVgap(10);
        registerUserPanel.setLayout(registerUserLayout);

        final JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        registerUserPanel.add(errorLabel);
        registerUserPanel.add(new JLabel(""));
        JLabel usernameLabel = new JLabel("Username:");
        registerUserPanel.add(usernameLabel);
        final JTextField getUsername = new JTextField();
        registerUserPanel.add(getUsername);
        JLabel emailLabel = new JLabel("Email:");
        registerUserPanel.add(emailLabel);
        final JTextField getEmail = new JTextField();
        registerUserPanel.add(getEmail);
        JLabel passwordLabel = new JLabel("Password:");
        registerUserPanel.add(passwordLabel);
        final JPasswordField getPassword = new JPasswordField();
        registerUserPanel.add(getPassword);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        registerUserPanel.add(confirmPasswordLabel);
        final JPasswordField getConfirmPassword = new JPasswordField();
        registerUserPanel.add(getConfirmPassword);
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Regex to ensure email given is correct format */
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String passwordPattern = "";
                String username = getUsername.getText().toLowerCase();
                String email = getEmail.getText().toLowerCase();
                String password = getPassword.getText();
                String confirmPassword = getConfirmPassword.getText();

                if (!username.equals("") && !email.equals("")
                        && !password.equals("") && !confirmPassword.equals("")) {
                    if (password.equals(confirmPassword)) {
                        if (email.matches(emailPattern)) {
                            /* Checks if username and email are free to use */
                            int checkUsernameEmail = sessionInfo.checkUsernameEmail(username, email);
                            /* Username and email are free */
                            if (checkUsernameEmail == 2) {
                                sessionInfo.createPlayer(username, password, email);
                                JOptionPane.showMessageDialog(null, "User created!");
                                registerPanel.setVisible(false);
                                showLoginScreen();
                                menuMgr.notifyObserver();
                            } else {
                                /* Username already registered */
                                if (checkUsernameEmail == 0) {
                                    errorLabel.setText("Username already in use");
                                } /* Email already registered */ else {
                                    errorLabel.setText("Email already in use");
                                }
                            }
                        } /* Email does not match pattern */ else {
                            errorLabel.setText("Not a valid email address");
                        }
                    } /* Passwords do not match */ else {
                        errorLabel.setText("Incorrect Passwords");
                    }
                } /* Fields left blank */ else {
                    errorLabel.setText("Field(s) blank");
                }
            }
        });
        registerUserPanel.add(createButton);
        /* User cancels registration, redirected back to login screen */
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPanel.setVisible(false);
                showLoginScreen();
                menuMgr.notifyObserver();
            }
        });
        registerUserPanel.add(cancelButton);

        registerPanel.add(registerUserPanel, cons);
        panel = registerPanel;
    }

    @Override
    public JPanel getMenuPanel() {
        return this.panel;
    }
}
