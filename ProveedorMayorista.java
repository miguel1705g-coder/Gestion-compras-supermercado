public class ProveedorMayorista extends Proveedor {
    private int unidadesMinimas;
    private double porcentajeDescuento; // Ejemplo: 0.10 = 10%

    public ProveedorMayorista(String nit, String nombre, String telefono, int unidadesMinimas, double porcentajeDescuento) {
        super(nit, nombre, telefono);
        this.unidadesMinimas = Math.max(1, unidadesMinimas);
        this.porcentajeDescuento = Math.max(0.0, Math.min(1.0, porcentajeDescuento));
    }

    @Override
    public String getTipoProveedor() {
        return "Mayorista";
    }

    @Override
    public double calcularDescuento(double subtotal, int totalUnidades) {
        if (totalUnidades >= unidadesMinimas) {
            return subtotal * porcentajeDescuento;
        }
        return 0.0;
    }
}