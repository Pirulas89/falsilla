SpinnerNumberModel modelo = new SpinnerNumberModel(
    1,   // valor inicial
    0,   // mínimo
    10,  // máximo
    1    // incremento
);
JSpinner spinner = new JSpinner(modelo);
spinner.setEnabled(false);

👉 Obtener valor:

int valor = (int) spinner.getValue();

JRadioButton
Selección única (normalmente en grupo)
JRadioButton r1 = new JRadioButton("Opción 1");
r1.setEnabled(false); // desactivar
r1.setSelected(true); // marcar

👉 Agrupar:

ButtonGroup grupo = new ButtonGroup();
grupo.add(r1);

🔁 JToggleButton

Botón ON/OFF
JToggleButton t = new JToggleButton("Activar");
t.setSelected(true); // ON
t.setEnabled(false);

📝 JTextArea

Texto multilínea
JTextArea area = new JTextArea();
area.setText("Hola");
area.setEditable(false); // bloquear edición
area.setEnabled(false);
