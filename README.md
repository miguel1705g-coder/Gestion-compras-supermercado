# Sistema de Gestión de Compras de un Supermercado (POO)

Proyecto desarrollado para la asignatura **Programación Orientada a Objetos** (UNIMINUTO).

## Autores
- Miguel Ángel Gutiérrez Piñeros (ID: 1052802)
- Johan David López Menco (ID: 0111160)

## Descripción
Sistema orientado a objetos que automatiza el control de inventario, el registro de proveedores y el procesamiento de órdenes de compra de un supermercado, aplicando abstracción, encapsulamiento, herencia y polimorfismo.

## Estructura del Proyecto
- `Producto.java`: Gestión de productos, costos y validación de stock.
- `Proveedor.java`: Clase base abstracta para proveedores.
- `ProveedorMayorista.java` / `ProveedorLocal.java`: Especialización de proveedores y cálculo polimórfico de descuentos.
- `DetalleCompra.java`: Cálculo de subtotales e impuestos por ítem.
- `OrdenCompra.java`: Procesamiento de compras, actualización de stock y generación de reporte.
- `Main.java`: Menú interactivo por consola.

## Ejecución
Compilar y ejecutar desde `Main.java`.