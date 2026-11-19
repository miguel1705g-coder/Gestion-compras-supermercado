public class ProveedorLocal extends Proveedor {
    private double porcentajeDescuentoFijo; // Ejemplo: 0.05 = 5%

    public ProveedorLocal(String nit, String nombre, String telefono, double porcentajeDescuentoFijo) {
        super(nit, nombre, telefono);
        this.porcentajeDescuentoFijo = Math.max(0.0, Math.min(1.0, porcentajeDescuentoFijo));
    }

    @Override
    public String getTipoProveedor() {
        return "Local";
    }

    @Override
    public double calcularDescuento(double subtotal, int totalUnidades) {
        return subtotal * porcentajeDescuentoFijo;
    }
}