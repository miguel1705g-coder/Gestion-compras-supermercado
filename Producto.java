public class Producto {
    private String codigo;
    private String nombre;
    private double precioCompra;
    private int stock;

    public Producto(String codigo, String nombre, double precioCompra, int stockInicial) {
        this.codigo = codigo;
        this.nombre = nombre;
        setPrecioCompra(precioCompra);
        this.stock = Math.max(0, stockInicial);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        if (precioCompra < 0) {
            throw new IllegalArgumentException("El precio de compra no puede ser negativo.");
        }
        this.precioCompra = precioCompra;
    }

    public int getStock() {
        return stock;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser mayor que cero.");
        }
        this.stock += cantidad;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reducir debe ser mayor que cero.");
        }
        if (cantidad > this.stock) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + nombre);
        }
        this.stock -= cantidad;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Costo: $%.2f | Stock: %d", codigo, nombre, precioCompra, stock);
    }
}