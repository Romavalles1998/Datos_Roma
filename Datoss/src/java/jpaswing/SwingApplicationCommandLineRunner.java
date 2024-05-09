package jpaswing;

import jpaswing.repository.AnimalRepository;
import jpaswing.ui.AnimalUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class SwingApplicationCommandLineRunner implements CommandLineRunner {
    private final AnimalRepository animalRepository;

    @Autowired
    public SwingApplicationCommandLineRunner(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void run(String... args) {
        EventQueue.invokeLater(() -> new AnimalUI(animalRepository).setVisible(true));
    }
}
