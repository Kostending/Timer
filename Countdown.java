import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

public class Countdown {

    private static final int FIELD_WIDTH = 4;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("Countdown Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel (System LookAndFeel) on this platform.");
            System.err.println("Using the default look and feel.");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class for specified look and feel: System LookAndFeel");
            System.err.println("Did you include the L&F library in the class path?");
            System.err.println("Using the default look and feel.");
        }
        catch (Exception e) {
            System.err.println("Couldn't get specified look and feel (System LookAndFeel);, for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        }

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        //Set up the content pane.
        addComponentsToPane(frame);

        //Display the window.
        SwingUtilities.updateComponentTreeUI(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public static void addComponentsToPane(final JFrame frame) {
        final JPanel constructor = new JPanel();

        addConstructer(constructor, frame);

        frame.add(constructor);
    }

    private static void addConstructer(final JPanel constructor, final JFrame frame) {
        JButton createButton = new JButton("Create Timer");
        final JTextField inputMinutes = new JTextField(FIELD_WIDTH);
        final JTextField inputSeconds = new JTextField(FIELD_WIDTH);
        
        createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    addTimer(
                        "Start", 
                        inputMinutes.getText(),
                        inputSeconds.getText(), 
                        frame
                    );
                }
            });

        constructor.add(createButton);
        constructor.add(inputMinutes);
        constructor.add(inputSeconds);
    }

    private static void addTimer(String buttonName, final String inputMinutes, final String inputSeconds, final JFrame frame) {
        if (inputMinutes.equals("") || inputSeconds.equals("")) {
            return;
        }

        try {
            final int minutes = Integer.parseInt(inputMinutes);
            final int seconds = Integer.parseInt(inputSeconds);

            final Counter counter = new Counter(minutes, seconds);
            final Timer timer = new Timer(1000, null);

            JPanel timerPanel = new JPanel();
            JButton startButton = new JButton(buttonName);
            final JTextField timerDisplay = new JTextField(FIELD_WIDTH);

            timerPanel.add(startButton);
            timerPanel.add(timerDisplay);
            
            timer.setInitialDelay(0);
            timer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (counter.getCounter() == 0) {
                        timer.stop();
                    }
                    else {
                        counter.decrement();
                        timerDisplay.setText(counter.toString());
                    }
                }
            });

            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (timer.isRunning()) {
                        counter.setCounter(minutes, seconds);
                        timerDisplay.setText(counter.toString());
                    }
                    else {
                        timer.start();
                    }
                }
            }); 

            timerDisplay.setText(counter.toString());

            frame.add(timerPanel);
            frame.pack();
        }
        catch (NumberFormatException exception) {
            return;
        }
    }
}