package ui;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import model.Evento;
import service.EventoService;
public class AgendaControllerFx {
    private JFrame frame;
    JCalendar calendar;

    public AgendaControllerFx() {
        frame = new JFrame("Agenda de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendar = new JCalendar();
        frame.add(calendar, BorderLayout.CENTER);
        frame.setVisible(true);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgendaControllerFx::new);
    }
}
