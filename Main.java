import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Producto> productos = new ArrayList<>();
    private static List<Proveedor> proveedores = new ArrayList<>();
    private static List<OrdenCompra> ordenes = new ArrayList<>();
    private static int contadorOrdenes = 1;

    public static void main(String[] args) {
        cargarDatosIniciales();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Selecciona una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarProducto(scanner);
                    case 2 -> listarProductos();
                    case 3 -> listarProveedores();
                    case 4 -> registrarProveedor(scanner);
                    case 5 -> crearYProcesarOrden(scanner);
                    case 6 -> listarOrdenes();
                    case 0 -> System.out.println("\nSaliendo del sistema. ¡Hasta pronto!");
                    default -> System.out.println("\nOpción no válida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n[Error] " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void cargarDatosIniciales() {
        productos.add(new Producto("P001", "Arroz Diana 1kg", 3200.0, 25));
        productos.add(new Producto("P002", "Aceite Girasol 900ml", 7800.0, 15));
        productos.add(new Producto("P003", "Leche Entera 1L", 3600.0, 30));
        productos.add(new Producto("P004", "Pan Tajado", 4500.0, 12));

        proveedores.add(new ProveedorMayorista("900111222-1", "Distribuidora Central", "3001234567", 20, 0.10));
        proveedores.add(new ProveedorLocal("900333444-5", "Granja La Sabana", "3109876543", 0.05));
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE COMPRAS - SUPERMERCADO ===");
        System.out.println("1. Registrar nuevo producto");
        System.out.println("2. Listar productos e inventario");
        System.out.println("3. Listar proveedores");
        System.out.println("4. Registrar nuevo proveedor");
        System.out.println("5. Crear y procesar orden de compra");
        System.out.println("6. Ver órdenes de compra realizadas");
        System.out.println("0. Salir");
    }

    private static void registrarProducto(Scanner scanner) {
        System.out.println("\n--- Registrar Producto ---");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        double precio = leerDouble(scanner, "Precio de compra unitario: ");
        int stock = leerEntero(scanner, "Stock inicial: ");

        productos.add(new Producto(codigo, nombre, precio, stock));
        System.out.println("Producto registrado con éxito.");
    }

    private static void listarProductos() {
        System.out.println("\n--- Inventario de Productos ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i).toString());
        }
    }

    private static void listarProveedores() {
        System.out.println("\n--- Proveedores Registrados ---");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (int i = 0; i < proveedores.size(); i++) {
            System.out.println((i + 1) + ". " + proveedores.get(i).toString());
        }
    }

    private static void registrarProveedor(Scanner scanner) {
        System.out.println("\n--- Registrar Proveedor ---");
        System.out.println("Tipo: 1) Mayorista  2) Local");
        int tipo = leerEntero(scanner, "Elige el tipo: ");
        System.out.print("NIT: ");
        String nit = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();

        if (tipo == 1) {
            int minUnidades = leerEntero(scanner, "Unidades mínimas para descuento: ");
            double porc = leerDouble(scanner, "Porcentaje de descuento (ej. 0.10 para 10%): ");
            proveedores.add(new ProveedorMayorista(nit, nombre, telefono, minUnidades, porc));
        } else if (tipo == 2) {
            double porcFijo = leerDouble(scanner, "Porcentaje de descuento fijo (ej. 0.05 para 5%): ");
            proveedores.add(new ProveedorLocal(nit, nombre, telefono, porcFijo));
        } else {
            System.out.println("Tipo no válido. No se registró el proveedor.");
            return;
        }
        System.out.println("Proveedor registrado con éxito.");
    }

    private static void crearYProcesarOrden(Scanner scanner) {
        if (productos.isEmpty() || proveedores.isEmpty()) {
            System.out.println("Debes tener al menos un producto y un proveedor registrados.");
            return;
        }

        listarProveedores();
        int idxProv = leerEntero(scanner, "Selecciona el número de proveedor: ") - 1;
        if (idxProv < 0 || idxProv >= proveedores.size()) {
            System.out.println("Proveedor inválido.");
            return;
        }

        String codigoOrden = String.format("OC-%03d", contadorOrdenes++);
        OrdenCompra orden = new OrdenCompra(codigoOrden, proveedores.get(idxProv));

        boolean agregarMas = true;
        while (agregarMas) {
            listarProductos();
            int idxProd = leerEntero(scanner, "Selecciona el número de producto: ") - 1;
            if (idxProd < 0 || idxProd >= productos.size()) {
                System.out.println("Producto inválido.");
                continue;
            }

            int cantidad = leerEntero(scanner, "Cantidad a comprar: ");
            double impuesto = leerDouble(scanner, "Tasa de impuesto (ej. 0.19 para 19%, 0 para exento): ");

            orden.agregarDetalle(new DetalleCompra(productos.get(idxProd), cantidad, impuesto));

            System.out.print("¿Deseas agregar otro producto a la orden? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            agregarMas = resp.equals("s");
        }

        orden.procesarOrden();
        ordenes.add(orden);
        System.out.println(orden.generarReporte());
    }

    private static void listarOrdenes() {
        System.out.println("\n--- Órdenes de Compra ---");
        if (ordenes.isEmpty()) {
            System.out.println("Aún no se han generado órdenes.");
            return;
        }
        for (OrdenCompra o : ordenes) {
            System.out.println(o.generarReporte());
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }
}