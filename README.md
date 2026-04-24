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
