package dados2D;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServidorWeb {
    private final int puerto = 8080;
    private HttpServer server;
    private Juego juego;

    public ServidorWeb() throws IOException {
        juego = new Juego();
        server = HttpServer.create(new InetSocketAddress(puerto), 0);
        server.createContext("/", new ManejadorArchivos());
        server.createContext("/accion", new ManejadorAcciones());
        server.setExecutor(null);
    }

    public void iniciar() {
        server.start();
        System.out.println("Servidor iniciado en http://localhost:" + puerto);
        System.out.println("Abre tu navegador y visita la URL para jugar.");
    }

    public void detener() {
        server.stop(0);
    }

    // Maneja archivos estáticos como index.html
    static class ManejadorArchivos implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String ruta = exchange.getRequestURI().getPath();
            if (ruta.equals("/")) {
                ruta = "/index.html";
            }

            String recursoPath = "recursos" + ruta;
            InputStream in = ServidorWeb.class.getClassLoader().getResourceAsStream(recursoPath);

            if (in != null) {
                exchange.sendResponseHeaders(200, 0);
                OutputStream out = exchange.getResponseBody();
                in.transferTo(out);
                out.close();
            } else {
                String respuesta = "404 (No encontrado)";
                exchange.sendResponseHeaders(404, respuesta.length());
                OutputStream out = exchange.getResponseBody();
                out.write(respuesta.getBytes());
                out.close();
            }
        }
    }

    // Maneja las acciones del juego
    class ManejadorAcciones implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String tipo = exchange.getRequestURI().getQuery();
            if (tipo == null || !tipo.contains("tipo=")) {
                String respuesta = "Acción no válida.";
                enviarRespuesta(exchange, respuesta);
                return;
            }

            String accion = tipo.split("=")[1];

            String respuesta = "";
            switch (accion) {
                case "atacar":
                    respuesta = juego.atacarConDadoHtml();
                    break;
                case "defender":
                    respuesta = "¡Has decidido defender!";
                    break;
                case "estado":
                    respuesta = juego.obtenerEstado();
                    break;
                default:
                    respuesta = "Acción no reconocida.";
            }

            enviarRespuesta(exchange, respuesta);
        }

        private void enviarRespuesta(HttpExchange exchange, String respuesta) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, respuesta.getBytes().length);
            OutputStream out = exchange.getResponseBody();
            out.write(respuesta.getBytes());
            out.close();
        }
    }
}