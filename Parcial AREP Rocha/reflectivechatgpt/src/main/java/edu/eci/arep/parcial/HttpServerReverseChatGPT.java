package edu.eci.arep.parcial;

import java.net.*;
import java.io.*;

public class HttpServerReverseChatGPT {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        Boolean running = true;
        while (running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);

                // GET
                if(inputLine.startsWith("GET") && !inputLine.startsWith("GET /fav")){
                    uriString = inputLine;
                }


                if (!in.ready()) {
                    break;
                }
            }

            transformUri(uriString);
            
            

            outputLine = htmlPage();
            // outputLine += outputLineResponse();

            out.println(outputLine);
            out.close();
            in.close();
        }

        clientSocket.close();
        serverSocket.close();
    }

    private static void transformUri(String uriString){
        System.out.println(uriString);
        String command = "";

        if(!uriString.equals("")){
            command = uriString.split("comando=")[1].split(" ")[0];
        }
                
        System.out.println(command);
    }

    private static String htmlPage() {
        String response = "/ HTTP/1.1\n\r" +
                "200 OK\n\r" +
                "\n\r" +
                "<!DOCTYPE html>\n\r" +
                "<html>\n\r" +
                "    <head>\n\r" +
                "        <title>Form Example</title>\n\r" +
                "        <meta charset=\"UTF-8\">\n\r" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\r" +
                "    </head>\n\r" +
                "    <body>\n\r" +
                "        <h1>Form with GET</h1>\n\r" +
                "        <form action=\"/hello\">\n\r" +
                "            <label for=\"name\">Name:</label><br>\n\r" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n\r" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n\r" +
                "        </form> \n\r" +
                "        <div id=\"getrespmsg\"></div>\n\r" +
                "\n\r" +
                "        <script>\n\r" +
                "            function loadGetMsg() {\n\r" +
                "                let nameVar = document.getElementById(\"name\").value;\n\r" +
                "                const xhttp = new XMLHttpRequest();\n\r" +
                "                xhttp.onload = function() {\n\r" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n\r" +
                "                    this.responseText;\n\r" +
                "                }\n\r" +
                "                xhttp.open(\"GET\", \"/consulta?comando=\"+nameVar);\n\r" +
                "                xhttp.send();\n\r" +
                "            }\n\r" +
                "        </script>\n\r" +
                "\n\r" +
                "        <h1>Form with POST</h1>\n\r" +
                "        <form action=\"/hellopost\">\n\r" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n\r" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n\r" +
                "        </form>\n\r" +
                "        \n\r" +
                "        <div id=\"postrespmsg\"></div>\n\r" +
                "        \n\r" +
                "        <script>\n\r" +
                "            function loadPostMsg(name){\n\r" +
                "                let url = \"/consultaPost?comando=\" + name.value;\n\r" +
                "\n\r" +
                "                fetch (url, {method: 'POST'})\n\r" +
                "                    .then(x => x.text())\n\r" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n\r" +
                "            }\n\r" +
                "        </script>\n\r" +
                "    </body>\n\r" +
                "</html>";

        return response;
    }

    private static String outputLineResponse() {
        String outputLine = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Title of the document</title>\n" +
                "</head>" +
                "<body>" +
                "<h1>Mi propio mensaje</h1>" +
                "</body>" +
                "</html>";

        return outputLine;
    }
}
