package theic2.xenobyteport.gui.swing;

import theic2.xenobyteport.api.Xeno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class InfoGui extends XenoJFrame {

    private JLabel author, desc, disLink;
    private MouseAdapter linkClicker;
    private JPanel fieldsPanel;

    public InfoGui() {
        super("Инфо", DISPOSE_ON_CLOSE);
    }

    @Override
    public void createObjects() {
        linkClicker = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI(((JLabel) e.getSource()).getText()));
                            dispose();
                        } catch (Exception ex) {
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JComponent) e.getSource()).setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JComponent) e.getSource()).setForeground(Color.BLACK);
            }
        };
        author = new JLabel("  " + Xeno.mod_name + " version " + Xeno.mod_version + " (C) " + Xeno.author + "  ");
        disLink = new JLabel("https://discord.gg/jgvWk9cttk");
        desc = new JLabel("Special for fucking loliland and some other MineVerse dauns...");
        fieldsPanel = new JPanel();
    }

    @Override
    public void configurate() {
        disLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        author.setHorizontalAlignment(JTextField.CENTER);
        desc.setHorizontalAlignment(JTextField.CENTER);
        fieldsPanel.setLayout(new GridBagLayout());
        disLink.addMouseListener(linkClicker);
        accept.setText(" ОК ");
        disLink.setFont(FONT);
        author.setFont(FONT);
        desc.setFont(FONT);
    }

    @Override
    public void addElements() {
        fieldsPanel.add(author, GBC);
        fieldsPanel.add(desc, GBC);
        fieldsPanel.add(disLink, GBC);
        buttonsBar.add(accept);
        add(fieldsPanel);
        add(buttonsBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}