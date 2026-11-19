import java.util.ArrayList;
import java.util.List;

public class OrdenCompra {
    private String numeroOrden;
    private Proveedor proveedor;
    private List<DetalleCompra> detalles;
    private boolean procesada;

    public OrdenCompra(String numeroOrden, Proveedor proveedor) {
        this.numeroOrden = numeroOrden;
        this.proveedor = proveedor;
        this.detalles = new ArrayList<>();
        this.procesada = false;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public boolean isProcesada() {
        return procesada;
    }

    public void agregarDetalle(DetalleCompra detalle) {
        if (procesada) {
            throw new IllegalStateException("No se pueden agregar ítems a una orden ya procesada.");
        }
        detalles.add(detalle);
    }

    public int getTotalUnidades() {
        int total = 0;
        for (DetalleCompra d : detalles) {
            total += d.getCantidad();
        }
        return total;
    }

    public double calcularSubtotalBruto() {
        double suma = 0.0;
        for (DetalleCompra d : detalles) {
            suma += d.calcularSubtotal();
        }
        return suma;
    }

    public double calcularDescuento() {
        return proveedor.calcularDescuento(calcularSubtotalBruto(), getTotalUnidades());
    }

    public double calcularImpuestos() {
        double impuestos = 0.0;
        for (DetalleCompra d : detalles) {
            impuestos += d.calcularImpuesto();
        }
        return impuestos;
    }

    public double calcularTotalFinal() {
        return (calcularSubtotalBruto() - calcularDescuento()) + calcularImpuestos();
    }

    public void procesarOrden() {
        if (procesada) {
            throw new IllegalStateException("Esta orden ya fue procesada.");
        }
        if (detalles.isEmpty()) {
            throw new IllegalStateException("La orden no tiene productos agregados.");
        }
        for (DetalleCompra d : detalles) {
            d.getProducto().aumentarStock(d.getCantidad());
        }
        procesada = true;
    }

    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================ ORDEN DE COMPRA: ").append(numeroOrden).append(" ================\n");
        sb.append("Proveedor: ").append(proveedor.toString()).append("\n");
        sb.append("Estado: ").append(procesada ? "PROCESADA (Stock actualizado)" : "PENDIENTE").append("\n");
        sb.append("----------------------------------------------------------------\n");
        for (DetalleCompra d : detalles) {
            sb.append(d.toString()).append("\n");
        }
        sb.append("----------------------------------------------------------------\n");
        sb.append(String.format("Total Unidades: %d\n", getTotalUnidades()));
        sb.append(String.format("Subtotal Bruto: $%.2f\n", calcularSubtotalBruto()));
        sb.append(String.format("Descuento (%s): -$%.2f\n", proveedor.getTipoProveedor(), calcularDescuento()));
        sb.append(String.format("Impuestos: +$%.2f\n", calcularImpuestos()));
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", calcularTotalFinal()));
        sb.append("================================================================\n");
        return sb.toString();
    }
}