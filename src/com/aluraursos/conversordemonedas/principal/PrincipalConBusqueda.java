package com.aluraursos.conversordemonedas.principal;

import com.aluraursos.conversordemonedas.moneda.Moneda;

import java.util.Locale;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import com.google.gson.FieldNamingPolicy;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

public class PrincipalConBusqueda {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Usar un Scanner con el Locale en inglés para evitar problemas con el formato de los números decimales
        Scanner lectura = new Scanner(System.in).useLocale(Locale.US);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (true) {

            System.out.println("Bienvenido al conversor de moneda. Por favor seleccione alguna de las siguientes opciones:\n" +
                    "1. Peso argentino a Peso chileno.\n" +
                    "2. Boliviano a Real brasileño.\n" +
                    "3. Real brasileño a Dólares.\n" +
                    "4. Peso chileno a Dólares.\n" +
                    "5. Peso colombiano a Real brasileño.\n" +
                    "6. Dólar estadounidense a Peso colombiano.\n" +
                    "7. Salir.\n");

            String busqueda = lectura.nextLine();

            // Verificar que la opción esté entre 1 y 7
            if (busqueda.equalsIgnoreCase("7")) {
                System.out.println("Saliendo...");
                break;

            } else if (!busqueda.matches("[1-6]")) {
                System.out.println("Opción inválida. Por favor, seleccione una opción entre 1 y 7.\n");
                continue;  // Vuelve a pedir la opción
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("Ingrese el valor monetario a convertir:\n");

            Scanner valorAConvertir = new Scanner(System.in).useLocale(Locale.US);
            double valor = 0;
            boolean entradaValida = false;

            // Controlar que el valor sea numérico
            while (!entradaValida) {

                try {
                    valor = valorAConvertir.nextDouble();  // Intentar leer un número
                    if (valor < 0) {

                        System.out.println("Por favor, ingrese un valor positivo.\n");
                        valorAConvertir.nextLine();  // Limpiar el buffer de entrada

                    } else {

                        entradaValida = true;  // Si es válido, salir del bucle

                    }

                } catch (InputMismatchException e) {

                    // Si no es un número, se captura la excepción y muestra un mensaje
                    System.out.println("Por favor, ingrese un valor numérico válido.\n");
                    valorAConvertir.nextLine();  // Limpiar el buffer de entrada para el siguiente intento

                }
            }

            // Llamar al metodo de conversión con el valor ingresado y la opción seleccionada
            Moneda moneda = Moneda.conversorMoneda(busqueda, valor);
            // Puedes hacer algo con 'moneda' aquí, como imprimir el resultado de la conversión
        }
    }
}

