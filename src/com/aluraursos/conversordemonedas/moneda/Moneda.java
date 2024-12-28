package com.aluraursos.conversordemonedas.moneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Moneda {
    @SerializedName("base_code")
    private String baseCode;

    @SerializedName("conversion_rates")
    private JsonObject conversionRates;

    // Getters y Setters
    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String base_code) {
        this.baseCode = base_code;
    }

    public JsonObject getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(JsonObject conversion_rates) {
        this.conversionRates = conversion_rates;
    }

    public static Moneda conversorMoneda(String busqueda, double valor) {
        String direccion;
        String aConvertir;
        String monedaActual;

        // Validar si el valor de "busqueda" es válido
        if (busqueda.equalsIgnoreCase("1")) {

            monedaActual = "ARS";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/ARS";
            aConvertir = "CLP";

        } else if (busqueda.equalsIgnoreCase("2")) {

            monedaActual = "BOB";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/BOB";
            aConvertir = "BRL";

        } else if (busqueda.equalsIgnoreCase("3")) {

            monedaActual = "BRL";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/BRL";
            aConvertir = "USD";

        } else if (busqueda.equalsIgnoreCase("4")) {

            monedaActual = "CLP";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/CLP";
            aConvertir = "USD";

        } else if (busqueda.equalsIgnoreCase("5")) {

            monedaActual = "COP";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/COP";
            aConvertir = "BRL";

        } else if (busqueda.equalsIgnoreCase("6")) {

            monedaActual = "USD";
            direccion = "https://v6.exchangerate-api.com/v6/647fe5de638afb0312f7f83b/latest/USD";
            aConvertir = "COP";

        } else {

            // Manejo del caso donde "busqueda" no es válida
            System.out.println("Error: el valor de búsqueda debe estar entre 1 y 6.");
            return null;

        }

        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            // Enviar solicitud y obtener respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Obtener el cuerpo de la respuesta como JSON
            String json = response.body();

            // Usar Gson para deserializar el JSON
            Gson gson = new Gson();
            Moneda moneda = gson.fromJson(json, Moneda.class);

            // Conversion
            for (var entry : moneda.getConversionRates().entrySet()) {

                String currencyCode = entry.getKey();

                if (aConvertir.equals(currencyCode)) {  // Comparar valores de cadenas usando equals

                    double exchangeRate = entry.getValue().getAsDouble();  // Asumiendo que el valor es un Double
                    double valorConvertido = valor * exchangeRate;
                    System.out.printf("Los %.2f [%s] equivalen a %.3f [%s].\n", valor, monedaActual, valorConvertido, aConvertir);
                    System.out.println("----------------------------------------------------------------------------\n");

                }
            }


            return moneda;

        } catch (IllegalArgumentException e) {

            System.out.println("Error en la URI, verifica la dirección.");

        } catch (Exception e) {

            System.out.println("Error al realizar la solicitud: " + e.getMessage());

        }

        return null;
    }


}
