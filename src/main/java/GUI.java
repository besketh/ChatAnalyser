import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;

public class GUI implements ActionListener {

    private JLabel label;
    private JPanel panel;
    private JFrame frame;
    private String path;
    private JFileChooser filechooser;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public GUI() {
        frame = new JFrame();

        filechooser = new JFileChooser();
        filechooser.addActionListener(this);

        JButton button = new JButton("Continue");
        button.addActionListener(this);

        label = new JLabel("Welcome to the Chat Analyser: Select File to be analysed below");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        panel.add(filechooser);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chat Analyser GUI");
        frame.pack();
        frame.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (actionEvent.getSource().toString().contains("JFileChooser"))
        {
            setPath(filechooser.getSelectedFile().getPath().toString());
            label.setText("File Chosen: " + path);

            //Input from file to token frequency hashmaps for each person in chat
            LinkedHashMap<String, Integer> chatter1TokenFreq = Input.convertWhatsappChatToTokenFreqHashMap(path, "Ben", 17, 1);

            //Zipf stats

            System.out.println(" ");
            System.out.println("Zipf Stats (" + Processor.countTokensFromZipfMap(Processor.sortByFrequency(chatter1TokenFreq, 1)) + " words total): ");

            label.setText(Output.printZipfMapToString(Processor.sortByFrequency(chatter1TokenFreq, 1)));
            System.out.println(" ");

            Stats.linearRegressionizer(Stats.zipfMapToLog10_RF_XY(Processor.sortByFrequency(chatter1TokenFreq, 1)));
            System.out.println(" ");


        }
        else if (actionEvent.getSource().toString().contains("JButton"))
        {
            label.setText("Button pushed");
        }



    }
}
