import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;

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
        
        final JFormattedTextField input = new JFormattedTextField(createFormatter("##:##"));
        input.setText("00:00");
        
        final JTextField description = new JTextField(FIELD_WIDTH);
        
        createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    addTimer(
                        description.getText(),
                        input.getText(),
                        frame
                    );
                }
            });

        constructor.add(createButton);
        constructor.add(input);
        constructor.add(description);
    }

    private static MaskFormatter createFormatter(String format) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
        } catch (java.text.ParseException exc) {
            System.err.println("Formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    private static void addTimer(String description, final String input, final JFrame frame) {
        String[] str_array = input.split(":");
        
        try {
            final int minutes = Integer.parseInt(str_array[0]);
            final int seconds = Integer.parseInt(str_array[1]);

            final Counter counter = new Counter(minutes, seconds);
            final Timer timer = new Timer(1000, null);

            JPanel timerPanel = new JPanel();
            JButton startButton = new JButton("Start");
            final JTextField timerDisplay = new JTextField(FIELD_WIDTH);
            timerDisplay.setEditable(false);
            JLabel timerDescription = new JLabel(description);
            timerDescription.setBackground(Color.RED);

            timerPanel.add(startButton);
            timerPanel.add(timerDisplay);
            timerPanel.add(timerDescription);
            
            timer.setInitialDelay(0);
            timer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (counter.getCounter() == 0) {
                        counter.setCounter(minutes, seconds);
                        timerDisplay.setText(counter.toString());
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
                    else if (!timer.isRunning()) {
                        timer.restart();
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