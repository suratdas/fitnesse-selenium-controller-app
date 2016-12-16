package com.company;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Created by Surat.Das on 2/25/2016.
 */
public class AutomationSetup extends JFrame {
    private JPanel setupPanel;
    private JTabbedPane tabbedPane1;
    private JPanel fitnesseTab;
    private JButton fitnesseFolderButton;
    private JTextField centralWikiTextField;
    private JTextField txtHost;
    private JTextField txtPort;
    private JButton startFitnesseButton;
    private JButton openFolderButton;
    private JTextArea txtLogs;
    private JButton launchFitnessePageButton;
    private JButton clearLogsButton;
    private JButton stopFitnesseButton;
    private JPanel seleniumTab;
    private JTextField txtHubPort;
    private JButton btnStopHub;
    private JTextField txtIEPort;
    private JButton btnStopIE;
    private JButton btnFirefoxStop;
    private JTextField txtFFPort;
    //private JTextField txtSafariPort;
    //private JButton btnSafariStop;
    private JTextArea txtSeleniumLogArea;
    private JButton btnIERun;
    private JButton btnFirefoxRunButton;
    //private JButton btnSafariStart;
    private JLabel ieLabel;
    private JButton btnRunHub;
    private JButton btnSeleniumClearLogs;
    private JLabel labelFirefox;
    private JLabel labelChromeLabel;
    private JLabel labelHub;
    private JButton btnStopAllButton;
    private JButton btnRunAllButton;
    private JButton btnChromeRun;
    private JButton btnChromeStop;
    private JTextField txtChromePort;
    private JTextField txtIEVersion;
    private JLabel lblIEIcon;
    private JTextField textFirefoxVersion;
    private JLabel lblFirefoxIcon;
    private JTextField textChromeVersion;
    //private JTextField textSafariVersion;
    //private JLabel labelSafariIcon;
    private JLabel labelChromeIcon;
    private JButton refreshGridButton;
    private JButton viewButton;
    private JLabel labelHelpAbout;
    private JPanel helpPanel;
    private JButton resetThisToolButton;
    private JLabel labelVersion;
    private JTextField txtFitnesseCommandLineArguments;
    private JTextField txtMethodName;
    private JTextField txtFileExtensions;
    private JTextField txtFindTextSelectedFolder;
    private JButton btnFindTextChangeFolder;
    private JButton btnSearch;
    private JProgressBar progressBar1;
    private JTextArea textArea1;
    private JLabel labelFindTextStatus;
    private JCheckBox showFullPathCheckBox;
    private JCheckBox showLineNumbersCheckBox;
    private JScrollPane findTextScrollPane;
    private JScrollPane scrollPane;
    private JTextArea txtHelpText;
    JTextArea txtFindTextOutput;

    private String fitnesseHost = "localhost";
    private String fitnessePort = "80";
    private String fitnesseCommandLineArgumentString = "";
    private String finesseRootLocation = "";
    private String seleniumHubPort = "4444";
    private String seleniumIEPort = "5555";
    private String seleniumFirefoxPort = "5556";
    private String seleniumChromePort = "5557";
    //private String seleniumSafariPort = "5558";
    private String seleniumJarFileName = "";

    java.util.List<String> extensionsList = new ArrayList<>();


    public static BufferedImage makeRoundedCorner(ImageIcon image, int cornerRadius) {
        int w = image.getIconWidth();
        int h = image.getIconHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLUE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w - 3, h - 3, cornerRadius, cornerRadius));


        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image.getImage(), 0, 0, null);

        g2.dispose();

        return output;
    }

    public AutomationSetup() {

        final JFrame f = new JFrame("File Chooser");
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String valueFromConfig = null;
        try {
            valueFromConfig = readValueFromConfig("port").trim();
        } catch (Exception e1) {
        }
        fitnessePort = (valueFromConfig != null) ? valueFromConfig : fitnessePort;
        txtPort.setText(fitnessePort);
        try {
            fitnesseCommandLineArgumentString = readValueFromConfig("fitnesseCommandLineArguments");
        } catch (Exception e) {
        }
        try {
            String value = readValueFromConfig("fitnesseLocation");
            if (value != null && !value.isEmpty()) centralWikiTextField.setText(value);
        } catch (Exception e) {
        }
        if (fitnesseCommandLineArgumentString == null)
            fitnesseCommandLineArgumentString = "-e 0 -p " + fitnessePort;
        txtFitnesseCommandLineArguments.setText(fitnesseCommandLineArgumentString);
        txtLogs.setLineWrap(true);

        //ImageIcon runIcon = new ImageIcon("ToolResources/play.png");
        ImageIcon runIcon = new ImageIcon(getClass().getResource("/com/company/Play.png"));
        //ImageIcon stopIcon = new ImageIcon("ToolResources/stop.png");
        ImageIcon stopIcon = new ImageIcon(getClass().getResource("/com/company/stop.png"));
        //ImageIcon hubIcon = new ImageIcon("ToolResources/grid.png");
        ImageIcon hubIcon = new ImageIcon(getClass().getResource("/com/company/grid.png"));
        labelHub.setIcon(hubIcon);

        /*
        btnRunHub.setBorderPainted(false);
        btnRunHub.setFocusPainted(false);
        btnRunHub.setContentAreaFilled(false);
        */

        /*
        try {
            ImageIO.write(makeRoundedCorner(stopIcon, 80), "png", new File("stop.rounded.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        try {
            runIcon = new ImageIcon(getClass().getResource("play.rounded.png"));
            stopIcon = new ImageIcon(getClass().getResource("stop.rounded.png"));
        } catch (Exception e) {
        }

        btnRunHub.setIcon(runIcon);
        btnStopHub.setIcon(stopIcon);
        /*
        ImageIcon ieIcon = new ImageIcon("ToolResources/internet_explorer.png");
        ImageIcon firefoxIcon = new ImageIcon("ToolResources/firefox.png");
        ImageIcon chromeIcon = new ImageIcon("ToolResources/chrome.png");
        ImageIcon safariIcon = new ImageIcon("ToolResources/safari.png");
        */
        ImageIcon ieIcon = new ImageIcon(getClass().getResource("/com/company/internet_explorer.png"));
        ImageIcon firefoxIcon = new ImageIcon(getClass().getResource("/com/company/firefox.png"));
        ImageIcon chromeIcon = new ImageIcon(getClass().getResource("/com/company/chrome.png"));
        //ImageIcon safariIcon = new ImageIcon(getClass().getResource("/com/company/safari.png"));

        lblIEIcon.setIcon(ieIcon);
        btnIERun.setIcon(runIcon);
        btnStopIE.setIcon(stopIcon);
        lblFirefoxIcon.setIcon(firefoxIcon);
        btnFirefoxRunButton.setIcon(runIcon);
        btnFirefoxStop.setIcon(stopIcon);
        labelChromeIcon.setIcon(chromeIcon);
        btnChromeRun.setIcon(runIcon);
        btnChromeStop.setIcon(stopIcon);
        //labelSafariIcon.setIcon(safariIcon);
        //btnSafariStart.setIcon(runIcon);
        //btnSafariStop.setIcon(stopIcon);

        labelHelpAbout.setText("If you see any issue please leave a comment in github.");

        //Setup the Selenium tab port text boxes
        SetupSeleniumTextBoxes();

        //Show Hide the play stop buttons
        setRunStopIcons("hub");
        setRunStopIcons("ie");
        setRunStopIcons("firefox");
        setRunStopIcons("chrome");
        //setRunStopIcons("safari");

        seleniumJarFileName = getLatestSeleniumFilename();

        fitnesseFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfc.showOpenDialog(f);
                centralWikiTextField.setText(jfc.getSelectedFile().getAbsolutePath());
            }
        });

        startFitnesseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtPort.getText().trim().length() > 1) fitnessePort = txtPort.getText().trim();
                if (isPortInUse(Integer.parseInt(txtPort.getText().trim()))) {
                    txtLogs.append(getCurrentTimeStamp() + "Port is blocked by Fitnesse or other program. You can try to stop Fitnesse.");
                } else if (fileExists("fitnesse-standalone.jar")) {
                    try {
                        Runtime.getRuntime().exec("java -jar fitnesse-standalone.jar " + txtFitnesseCommandLineArguments.getText());
                        txtLogs.append(getCurrentTimeStamp() + "Fitnesse is running.");
                        writeToConfig("port:" + fitnessePort);
                        writeToConfig("fitnesseLocation:" + centralWikiTextField.getText());
                        if (!fitnesseCommandLineArgumentString.equals(txtFitnesseCommandLineArguments.getText())) {
                            fitnesseCommandLineArgumentString = txtFitnesseCommandLineArguments.getText();
                            writeToConfig("fitnesseCommandLineArguments:" + fitnesseCommandLineArgumentString);
                        }
                    } catch (Exception e1) {
                        txtLogs.append(getCurrentTimeStamp() + e1.toString());
                    }
                } else {
                    txtLogs.append(getCurrentTimeStamp() + "fitnesse-standalone.jar file is missing. Please place it in the same location as this tool.");
                    showDialog("Fitnesse file is missing", "fitnesse-standalone.jar file is missing. Please place it in the same location as this tool.\nDownload from http://fitnesse.org/FitNesseDownload.");
                }
            }
        });

        launchFitnessePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                String enteredFitnessePort = txtPort.getText().trim();
                fitnessePort = (enteredFitnessePort.length() > 1) ? enteredFitnessePort : fitnessePort;
                if (!isPortInUse(Integer.valueOf(fitnessePort))) {
                    txtLogs.append(getCurrentTimeStamp() + "Fitnesse is not running on this port.");
                } else {
                    String launchFitnessePort = (fitnessePort.equals("80")) ? "" : ":" + fitnessePort;
                    try {
                        desktop.browse(URI.create(new URI("http://" + txtHost.getText().replace(" ", "")) + launchFitnessePort));
                    } catch (Exception e1) {
                        txtLogs.append(getCurrentTimeStamp() + e1.toString());
                    }
                }
            }
        });

        stopFitnesseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (killProcess(Integer.parseInt(fitnessePort)).contains("not in use"))
                        throw new Exception("Port is already free.");
                    txtLogs.append(getCurrentTimeStamp() + "Released port: " + fitnessePort);
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.getMessage());
                }
            }
        });

        openFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("explorer.exe " + centralWikiTextField.getText() + "\"");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        clearLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtLogs.setText("");
            }
        });

        btnRunHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    seleniumHubPort = txtHubPort.getText().trim();
                    String hubCommand = "java -jar " + seleniumJarFileName + " -role hub -port " + seleniumHubPort;
                    if (runSeleniumHubNode(seleniumJarFileName, hubCommand)) {
                        txtSeleniumLogArea.append(getCurrentTimeStamp() + "Selenium hub is started on port " + seleniumHubPort);
                        writeToConfig("seleniumHubPort:" + seleniumHubPort);
                        btnRunHub.setVisible(false);
                        btnStopHub.setVisible(true);
                        try {
                            reminderToCheckConfig();
                        } catch (Exception e1) {
                        }
                    }
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.toString());
                }
            }
        });

        btnStopHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killProcess(Integer.parseInt(txtHubPort.getText().trim())).contains("released port")) {
                    btnStopHub.setVisible(false);
                    btnRunHub.setVisible(true);
                }
            }
        });

        btnSeleniumClearLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSeleniumLogArea.setText("");
            }
        });

        btnIERun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fileExists("IEDriverServer.exe")) {
                    showDialog("IE Driver file is missing.", "Please place IEDriverServer.exe in the same location as this tool.");
                    return;
                }
                try {
                    seleniumIEPort = txtIEPort.getText().trim();
                    String browserVersionFromTextInput = txtIEVersion.getText().trim();
                    String browserVersionInNodeCommand = (browserVersionFromTextInput.length() > 0) ? "version=" + browserVersionFromTextInput + "," : "";
                    String ieBatCommand = "java -jar -Dwebdriver.ie.driver=IEDriverServer.exe " + seleniumJarFileName + " -role node -hub http://localhost:" + seleniumHubPort + "/grid/register -maxSession 15 -browser browserName=\"internet explorer\"," + browserVersionInNodeCommand + "maxInstances=10 -port " + seleniumIEPort;
                    if (runSeleniumHubNode(seleniumJarFileName, ieBatCommand)) {
                        writeToConfig("seleniumIEPort:" + seleniumIEPort);
                        writeToConfig("seleniumIEVersion:" + browserVersionFromTextInput);
                        txtSeleniumLogArea.append(getCurrentTimeStamp() + "Selenium node is started on port " + seleniumIEPort);
                        btnIERun.setVisible(false);
                        btnStopIE.setVisible(true);
                        try {
                            reminderToCheckConfig();
                        } catch (Exception e1) {
                        }

                    }
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.toString());
                }
            }
        });

        btnStopIE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killProcess(Integer.parseInt(txtIEPort.getText().trim())).contains("released port")) {
                    btnStopIE.setVisible(false);
                    btnIERun.setVisible(true);
                }
            }
        });

        btnFirefoxRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fileExists("geckodriver.exe")) {
                    showDialog("IE Driver file is missing.", "Please place geckodriver.exe in the same location as this tool.");
                    return;
                }
                try {
                    seleniumFirefoxPort = txtFFPort.getText().trim();
                    String browserVersionFromTextInput = textFirefoxVersion.getText().trim();
                    String browserVersionInNodeCommand = (browserVersionFromTextInput.length() > 0) ? "version=" + browserVersionFromTextInput + "," : "";
                    String firefoxCommand = "java -jar " + seleniumJarFileName + " -role node -hub http://localhost:" + seleniumHubPort + "/grid/register -maxSession 15 -browser browserName=\"firefox\"," + browserVersionInNodeCommand + "maxInstances=10 -port " + seleniumFirefoxPort;
                    if (runSeleniumHubNode(seleniumJarFileName, firefoxCommand)) {
                        writeToConfig("seleniumFirefoxPort:" + seleniumFirefoxPort);
                        writeToConfig("seleniumFirefoxVersion:" + browserVersionFromTextInput);
                        txtSeleniumLogArea.append(getCurrentTimeStamp() + "Selenium node is started on port " + seleniumFirefoxPort);
                        btnFirefoxRunButton.setVisible(false);
                        btnFirefoxStop.setVisible(true);
                        try {
                            reminderToCheckConfig();
                        } catch (Exception e1) {
                        }
                    }
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.toString());
                }
            }
        });

        btnFirefoxStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killProcess(Integer.parseInt(txtFFPort.getText().trim())).contains("released port")) {
                    btnFirefoxStop.setVisible(false);
                    btnFirefoxRunButton.setVisible(true);
                }
            }
        });

        btnChromeRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fileExists("chromedriver.exe")) {
                    showDialog("Chromedriver file is missing.", "Please place chromedriver.exe in the same location as this tool.");
                    return;
                }
                try {
                    seleniumChromePort = txtChromePort.getText().trim();
                    String browserVersionFromTextInput = textChromeVersion.getText().trim();
                    String browserVersionInNodeCommand = (browserVersionFromTextInput.length() > 0) ? "version=" + browserVersionFromTextInput + "," : "";
                    String chromeCommand = "java -jar -Dwebdriver.chrome.driver=chromedriver.exe " + seleniumJarFileName + " -role node -hub http://localhost:" + seleniumHubPort + "/grid/register -maxSession 15 -browser browserName=\"chrome\"," + browserVersionInNodeCommand + "maxInstances=10 -port " + seleniumChromePort;
                    if (runSeleniumHubNode(seleniumJarFileName, chromeCommand)) {
                        writeToConfig("seleniumChromePort:" + seleniumChromePort);
                        writeToConfig("seleniumChromeVersion:" + browserVersionFromTextInput);
                        txtSeleniumLogArea.append(getCurrentTimeStamp() + "Selenium node is started on port " + seleniumChromePort);
                        btnChromeRun.setVisible(false);
                        btnChromeStop.setVisible(true);
                        try {
                            reminderToCheckConfig();
                        } catch (Exception e1) {
                        }
                    }
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.toString());
                }
            }
        });

        btnChromeStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killProcess(Integer.parseInt(txtChromePort.getText().trim())).contains("released port")) {
                    btnChromeStop.setVisible(false);
                    btnChromeRun.setVisible(true);
                }
            }
        });

       /* btnSafariStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    seleniumSafariPort = txtSafariPort.getText().trim();
                    String browserVersionFromTextInput = textSafariVersion.getText().trim();
                    String browserVersionInNodeCommand = (browserVersionFromTextInput.length() > 0) ? "version=" + browserVersionFromTextInput + "," : "";
                    String safariCommand = "java -jar " + seleniumJarFileName + " -role node -hub http://localhost:" + seleniumHubPort + "/grid/register -maxSession 15 -browser browserName=\"safari\"," + browserVersionInNodeCommand + "maxInstances=10 -port " + seleniumSafariPort;
                    if (runSeleniumHubNode(seleniumJarFileName, safariCommand)) {
                        writeToConfig("seleniumSafariPort:" + seleniumSafariPort);
                        writeToConfig("seleniumSafariVersion:" + browserVersionFromTextInput);
                        txtSeleniumLogArea.append(getCurrentTimeStamp() + "Selenium node is started on port " + seleniumSafariPort);
                        btnSafariStart.setVisible(false);
                        btnSafariStop.setVisible(true);
                        try {
                            reminderToCheckConfig();
                        } catch (Exception e1) {
                        }
                    }
                } catch (Exception e1) {
                    txtLogs.append(getCurrentTimeStamp() + e1.toString());
                }
            }
        });*/
/*
        btnSafariStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killProcess(Integer.parseInt(txtSafariPort.getText().trim())).contains("released port")) {
                    btnSafariStop.setVisible(false);
                    btnSafariStart.setVisible(true);
                }
            }
        });*/

        btnStopAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStopIE.doClick();
                btnFirefoxStop.doClick();
                btnChromeStop.doClick();
                //btnSafariStop.doClick();
            }
        });

        btnRunAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnIERun.doClick();
                btnFirefoxRunButton.doClick();
                btnChromeRun.doClick();
                //btnSafariStart.doClick();
            }
        });

        refreshGridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetupSeleniumTextBoxes();
                seleniumJarFileName = getLatestSeleniumFilename();
                setRunStopIcons("hub");
                setRunStopIcons("ie");
                setRunStopIcons("firefox");
                setRunStopIcons("chrome");
                //setRunStopIcons("safari");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingFXWebView swingFXWebView = new SwingFXWebView();
                swingFXWebView.entry();
            }
        });

        populateHelpTexts("help.txt");

        resetThisToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("config.ini");
                if (file.exists())
                    file.delete();
            }
        });

        txtFileExtensions.setText(readValueFromConfig("findTextFileExtensions") != null ? readValueFromConfig("findTextFileExtensions") : ".txt,.wiki");
        txtMethodName.setText(readValueFromConfig("findTextMethodName") != null ? readValueFromConfig("findTextMethodName") : "EnterMethodName");
        String valueOfselectedFolder = readValueFromConfig("findTextCurrentFolder") != null ? readValueFromConfig("findTextCurrentFolder") : System.getProperty("user.dir");
        txtFindTextSelectedFolder.setText(valueOfselectedFolder);

        progressBar1.setVisible(false);

        btnFindTextChangeFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfc.setCurrentDirectory(new File(valueOfselectedFolder));
                jfc.showOpenDialog(f);
                txtFindTextSelectedFolder.setText(jfc.getSelectedFile().getAbsolutePath());
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        labelFindTextStatus.setText("");
                        progressBar1.setVisible(true);
                        textArea1.setText("");
                        DefaultCaret caret = (DefaultCaret) textArea1.getCaret();
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                        searchTextInFiles();
                        progressBar1.setVisible(false);
                    }
                }).start();
            }
        });
    }

    private void searchTextInFiles() {
        if (readValueFromConfig("findTextFileExtensions") != txtFileExtensions.getText())
            writeToConfig("findTextFileExtensions:" + txtFileExtensions.getText());
        if (readValueFromConfig("findTextMethodName") != txtMethodName.getText())
            writeToConfig("findTextMethodName:" + txtMethodName.getText());
        if (readValueFromConfig("findTextCurrentFolder") != txtFindTextSelectedFolder.getText())
            writeToConfig("findTextCurrentFolder:" + txtFindTextSelectedFolder.getText());
        long startTime = System.currentTimeMillis();

        String methodName = txtMethodName.getText();
        java.util.List<String> methodWords = splitMethodNameIntoWords(methodName);
        java.util.List<String> fileNameList = new ArrayList<>();
        java.util.List<String> foundFileNameList = new ArrayList<>();
        extensionsList = Arrays.asList(readValueFromConfig("findTextFileExtensions").split(","));

        getFileNames(fileNameList, Paths.get(txtFindTextSelectedFolder.getText()));
        Predicate<String> stringPredicate = p -> findCorrectFiles(p);
        fileNameList.removeIf(stringPredicate);

        labelFindTextStatus.setText("Searching for \"" + methodName + "\" in " + fileNameList.size() + " files...");

        for (String fileName : fileNameList) {
            File file = new File(fileName);
            try {
                Scanner scanner = new Scanner(file);
                int lineNum = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase();
                    ++lineNum;
                    int count = 0;
                    for (String word : methodWords) {
                        if (!line.contains(word.toLowerCase())) {
                            break;
                        } else {
                            line = line.endsWith(word.toLowerCase()) ? line : line.split(word.toLowerCase())[1];
                            count++;
                        }
                    }
                    if (count == methodWords.size()) {
                        foundFileNameList.add(fileName);
                        String textToAppend = "\n";
                        textToAppend += showFullPathCheckBox.isSelected() ? fileName : fileName.replace(txtFindTextSelectedFolder.getText(), "");
                        textToAppend += showLineNumbersCheckBox.isSelected() ? " Line:" + lineNum : "";
                        textArea1.append(textToAppend);
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
            }
        }
        textArea1.append("\n");
        labelFindTextStatus.setText("Found " + foundFileNameList.size() + " files with given method name. Took " + (System.currentTimeMillis() - startTime) + " ms to search.");
    }

    private java.util.List<String> splitMethodNameIntoWords(String s) {
        String[] r = s.split("(?=\\p{Upper})");
        return Arrays.asList(r);
    }

    private java.util.List<String> getFileNames(java.util.List<String> fileNames, Path dir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if (path.toFile().isDirectory()) {
                    getFileNames(fileNames, path);
                } else {
                    fileNames.add(path.toAbsolutePath().toString());
                }
            }
        } catch (IOException e) {
        }
        return fileNames;
    }

    private boolean findCorrectFiles(String p) {
        for (String ext : extensionsList) {
            if (p.endsWith(ext.trim()))
                return false;
        }
        return true;
    }

    private void showDialog(String title, String detailsMessage) {
        JOptionPane.showMessageDialog(setupPanel,
                detailsMessage,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    private boolean runSeleniumHubNode(String seleniumJarFileName, String jarCommand) throws IOException {
        boolean running = false;
        if (seleniumJarFileName.length() < 5) {
            JOptionPane.showMessageDialog(setupPanel,
                    "Selenium jar file was not found on the same location as this tool. \nYou can download from http://docs.seleniumhq.org/download/ ",
                    "Selenium file not found",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Runtime.getRuntime().exec(jarCommand);
            running = true;
        }
        return running;
    }

    private void reminderToCheckConfig() {
        String show = "yes";
        try {
            if (readValueFromConfig("remindToViewGrid").contains("no"))
                show = "no";
        } catch (Exception e) {
        }
        if (show == "yes") {
            int n = JOptionPane.showConfirmDialog(
                    setupPanel,
                    "You should click \"View\" to confirm configuration whenever you start hub/node. \nIt's a good idea to check by running a fitnesse test. If the test still does not run, relauch this program.\n\nDo you want to be reminded again?",
                    "Check View",
                    JOptionPane.YES_NO_OPTION);
            if (n != 0)
                writeToConfig("remindToViewGrid:no");
        }
    }


    public AutomationSetup(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Automation Setup");
        frame.setContentPane(new AutomationSetup().setupPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setSize(new Dimension(600, 645));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //frame.setIconImage(ImageIO.read(new File("/com/company/automation.png")));
        URL url = ClassLoader.getSystemResource("com/company/automation.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        frame.setIconImage(img);
        frame.setResizable(false);
    }

    private void SetupSeleniumTextBoxes() {
        txtHubPort.setText(setTextBoxValues("seleniumHubPort"));
        txtIEPort.setText(setTextBoxValues("seleniumIEPort"));
        txtIEVersion.setText(setTextBoxValues("seleniumIEVersion"));
        txtFFPort.setText(setTextBoxValues("seleniumFirefoxPort"));
        textFirefoxVersion.setText(setTextBoxValues("seleniumFirefoxVersion"));
        txtChromePort.setText(setTextBoxValues("seleniumChromePort"));
        textChromeVersion.setText(setTextBoxValues("seleniumChromeVersion"));
        //txtSafariPort.setText(setTextBoxValues("seleniumSafariPort"));
        //textSafariVersion.setText(setTextBoxValues("seleniumSafariVersion"));
    }


    //This is not performance optimal
    private boolean isPortInUse(int port) {
        boolean portTaken = false;
        if (port > 1) {
            ServerSocket socket = null;
            try {
                socket = new ServerSocket(port);
            } catch (IOException e) {
                portTaken = true;
            } finally {
                if (socket != null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
            }
        }
        return portTaken;
    }

    private String getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        return ("\n" + new Timestamp(date.getTime()).toString().substring(0, 19)) + ": ";
    }

    private boolean writeToConfig(String lineToWrite) {
        try {
            File file = new File("config.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
            if (readValueFromConfig(lineToWrite.split(":")[0]) != null) {
                replaceLineInConfig(lineToWrite);
            } else {
                Files.write(Paths.get("config.ini"), ("\r\n" + lineToWrite).getBytes(), StandardOpenOption.APPEND);
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeToBat(String filename, String lineToWrite) {
        try {
            File file = new File(filename);
            if (file.exists())
                file.delete();
            file.createNewFile();
            Files.write(Paths.get(filename), lineToWrite.getBytes(), StandardOpenOption.WRITE);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void replaceLineInConfig(String lineToWrite) {
        try {
            // input the file content to the String "input"
            BufferedReader file = new BufferedReader(new FileReader("config.ini"));
            String line;
            String input = "";
            String key = lineToWrite.split(":")[0];
            String lineToReplace = "";
            while ((line = file.readLine()) != null) {
                input += line + '\r' + '\n';
                if (line.startsWith(key)) lineToReplace = line;
            }
            file.close();
            input = input.replace(lineToReplace, lineToWrite);

            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("config.ini");
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
        }
    }

    private String readValueFromConfig(String key) {
        String valueToReturn = null;
        try {
            File file = new File("config.ini");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(key)) {
                    valueToReturn = line.replace(key + ":", "");
                    break;
                }
            }
            br.close();

        } catch (Exception e) {
        }
        return valueToReturn;
    }

    private boolean fileExists(String fileName) {
        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    private String getLatestSeleniumFilename() {
        File f = new File(".");
        ArrayList<String> allFilesnames = new ArrayList<String>(Arrays.asList(f.list()));
        String latestJarFileName = "";
        int version = 1;
        for (String name : allFilesnames) {
            if (name.startsWith("selenium") && name.contains("standalone")) {
                int currentFileVersion = Integer.parseInt(name.replaceAll("\\D+", ""));
                if (version < currentFileVersion) {
                    latestJarFileName = name;
                    version = currentFileVersion;
                }
            }
        }
        return latestJarFileName;
    }

    private void setRunStopIcons(String browser) {
        if (browser.contains("hub")) {
            if (isPortInUse(Integer.parseInt(seleniumHubPort))) {
                btnRunHub.setVisible(false);
                btnStopHub.setVisible(true);
            } else {
                btnStopHub.setVisible(false);
                btnRunHub.setVisible(true);
            }
        }
        if (browser.contains("ie")) {
            if (isPortInUse(Integer.parseInt(seleniumIEPort))) {
                btnIERun.setVisible(false);
                btnStopIE.setVisible(true);
            } else {
                btnStopIE.setVisible(false);
                btnIERun.setVisible(true);
            }
        }
        if (browser.contains("firefox")) {
            if (isPortInUse(Integer.parseInt(seleniumFirefoxPort))) {
                btnFirefoxRunButton.setVisible(false);
                btnFirefoxStop.setVisible(true);
            } else {
                btnFirefoxStop.setVisible(false);
                btnFirefoxRunButton.setVisible(true);
            }
        }
        if (browser.contains("chrome")) {
            if (isPortInUse(Integer.parseInt(seleniumChromePort))) {
                btnChromeRun.setVisible(false);
                btnChromeStop.setVisible(true);
            } else {
                btnChromeStop.setVisible(false);
                btnChromeRun.setVisible(true);
            }
        }
        /*if (browser.contains("safari")) {
            if (isPortInUse(Integer.parseInt(seleniumSafariPort))) {
                btnSafariStart.setVisible(false);
                btnSafariStop.setVisible(true);
            } else {
                btnSafariStop.setVisible(false);
                btnSafariStart.setVisible(true);
            }
        }*/

    }

    private String setTextBoxValues(String value) {
        String temp = "";
        try {
            temp = readValueFromConfig(value);
        } catch (Exception e) {
        }
        if (value.contains("seleniumHubPort")) {
            return seleniumHubPort = temp != null ? temp : seleniumHubPort;
        }
        if (value.contains("seleniumIEPort")) {
            return seleniumIEPort = temp != null ? temp : seleniumIEPort;
        }
        if (value.contains("seleniumFirefoxPort")) {
            return seleniumFirefoxPort = temp != null ? temp : seleniumFirefoxPort;
        }
        if (value.contains("seleniumChromePort")) {
            return seleniumChromePort = temp != null ? temp : seleniumChromePort;
        }
        /*if (value.contains("seleniumSafariPort")) {
            seleniumSafariPort = temp != null ? temp : seleniumSafariPort;
            return seleniumSafariPort;
        }*/
        if (value.contains("findTextFileExtensions")) {
            return temp != null ? temp : ".txt,.wiki";
        }
        if (value.contains("findTextMethodName")) {
            return temp != null ? temp : "EnterMethodName";
        }
        if (value.contains("findTextCurrentFolder")) {
            return temp != null ? temp : ".txt,.wiki";
        }
        return temp;
    }

    private String killProcess(int port) {
        if (isPortInUse(port)) {
            int n = JOptionPane.showConfirmDialog(
                    setupPanel,
                    "Releasing Port " + port + " may close other applications (e.g. your browser). \n\nDo you want to proceed?",
                    "Confirm force kill",
                    JOptionPane.YES_NO_OPTION);
            if (n == 0)
                killPort(Integer.toString(port));
            else
                return "The port " + port + " may not have been released. You can try to relaunch this tool and try again.";
        }
        return "released port";
    }

    private void killPort(String portNumber) {
        try {
            String killingCommand = "cmd.exe /c FOR /F \"tokens=5 delims= \" %P IN ('netstat -a -n -o ^| findstr :" + portNumber + "') DO @ECHO %P && @IF NOT \"%P\"==\"0\" (Taskkill.exe /F /PID %P)";
            Runtime.getRuntime().exec(killingCommand);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void populateHelpTexts(String fileName) {
        try {
            helpPanel.setLayout(new GridLayout());
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            JTextArea jTextArea = new JTextArea();
            Font font = new Font("Arial", Font.PLAIN, 12);
            jTextArea.setLineWrap(true);
            jTextArea.setWrapStyleWord(true);
            jTextArea.setBackground(Color.lightGray);
            jTextArea.setFont(font);
            helpPanel.add(jTextArea);
            while ((line = br.readLine()) != null) {
                jTextArea.append(line + "\n");

            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
