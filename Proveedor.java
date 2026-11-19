public abstract class Proveedor {
    private String nit;
    private String nombre;
    private String telefono;

    public Proveedor(String nit, String nombre, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public abstract String getTipoProveedor();

    public abstract double calcularDescuento(double subtotal, int totalUnidades);

    @Override
    public String toString() {
        return String.format("%s (%s) - NIT: %s | Tel: %s", nombre, getTipoProveedor(), nit, telefono);
    }
}