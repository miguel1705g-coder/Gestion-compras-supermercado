public class DetalleCompra {
    private Producto producto;
    private int cantidad;
    private double tasaImpuesto; // Ejemplo: 0.19 = 19% IVA

    public DetalleCompra(Producto producto, int cantidad, double tasaImpuesto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.tasaImpuesto = Math.max(0.0, tasaImpuesto);
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecioCompra() * cantidad;
    }

    public double calcularImpuesto() {
        return calcularSubtotal() * tasaImpuesto;
    }

    public double calcularTotalLinea() {
        return calcularSubtotal() + calcularImpuesto();
    }

    @Override
    public String toString() {
        return String.format("- %s x%d | Subtotal: $%.2f | Impuesto: $%.2f | Total: $%.2f",
                producto.getNombre(), cantidad, calcularSubtotal(), calcularImpuesto(), calcularTotalLinea());
    }
}
