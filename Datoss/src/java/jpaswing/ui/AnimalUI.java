package jpaswing.ui;

import jpaswing.entity.Animal;
import jpaswing.repository.AnimalRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnimalUI extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JPanel panel1;
    private JTable animalTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton deleteButton;
    private Animal animal;
    private AnimalRepository animalRepository;

    public AnimalUI() {
        setTitle("Animal Maintenance");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        initComponents();
    }

    public AnimalUI(AnimalRepository animalRepository) {
        this();
        this.animalRepository = animalRepository;
        this.animal = animalRepository.findFirstByOrderByIdAsc();
        updateData();
        updateTable();
    }

    private void updateData() {
        if (this.animal != null) {
            idField.setText(Long.toString(this.animal.getId()));
            nameField.setText(this.animal.getName());
        }
    }

    private void updateTable() {
        List<Animal> animals = animalRepository.findAllByOrderByIdAsc();
        tableModel.setRowCount(0);
        for (Animal animal : animals) {
            tableModel.addRow(new Object[]{animal.getId(), animal.getName()});
        }
    }

    private void initComponents() {
        panel1 = new JPanel();
        idField = new JTextField(10);
        nameField = new JTextField(10);
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        animalTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(animalTable);
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        JLabel l;

        setLayout(new BorderLayout());
        panel1.setLayout(null);
        panel1.setPreferredSize(new Dimension(500, 100));

        l = new JLabel("ID:");
        l.setBounds(10, 10, 70, 20);
        panel1.add(l);
        idField.setEnabled(false);
        idField.setBounds(10 + 80, 10, 200, 20);
        panel1.add(idField);

        l = new JLabel("Name:");
        l.setBounds(10, 40, 70, 20);
        panel1.add(l);
        nameField.setBounds(10 + 80, 40, 200, 20);
        panel1.add(nameField);

        addButton.setBounds(300, 10, 100, 20);
        deleteButton.setBounds(300, 40, 100, 20);
        panel1.add(addButton);
        panel1.add(deleteButton);

        add(panel1, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addAnimal());
        deleteButton.addActionListener(e -> deleteAnimal());
    }

    private void addAnimal() {
        String name = nameField.getText();
        if (name != null && !name.trim().isEmpty()) {
            Animal newAnimal = new Animal(name);
            animalRepository.save(newAnimal);
            updateTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Name field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAnimal() {
        int selectedRow = animalTable.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) animalTable.getValueAt(selectedRow, 0);
            animalRepository.deleteById(id);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "No animal selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
    }
}
