package com.company;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Surat.Das on 3/2/2016.
 */
public class SwingFXWebView extends JPanel {
    private Stage stage;
    private WebView browser;
    private JFXPanel jfxPanel;
    private JButton swingButton;
    private WebEngine webEngine;

    public SwingFXWebView() {
        initComponents();
    }

    public void entry() {
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();

                frame.getContentPane().add(new SwingFXWebView());

                frame.setMinimumSize(new Dimension(850, 500));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    private void initComponents() {

        jfxPanel = new JFXPanel();
        createScene();

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

        swingButton = new JButton();
        swingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        webEngine.reload();
                    }
                });
            }
        });
        swingButton.setText("Reload");

        add(swingButton, BorderLayout.SOUTH);
    }

    /**
     * createScene
     * <p>
     * Note: Key is that Scene needs to be created and run on "FX user thread"
     * NOT on the AWT-EventQueue Thread
     */
    private void createScene() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {

                stage = new Stage();

                stage.setTitle("Hello Java FX");
                stage.setResizable(true);

                Group root = new Group();
                Scene scene = new Scene(root, 80, 20);
                stage.setScene(scene);

                // Set up the embedded browser:
                browser = new WebView();
                webEngine = browser.getEngine();
                webEngine.load("http://localhost:4444/grid/console");

                ObservableList<Node> children = root.getChildren();
                children.add(browser);

                jfxPanel.setScene(scene);
            }
        });
    }
}