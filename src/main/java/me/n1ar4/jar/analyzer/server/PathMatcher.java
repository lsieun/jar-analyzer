package me.n1ar4.jar.analyzer.server;

import fi.iki.elonen.NanoHTTPD;
import me.n1ar4.jar.analyzer.server.handler.GetMethodsByClassHandler;
import me.n1ar4.jar.analyzer.server.handler.GetJarByClassHandler;
import me.n1ar4.jar.analyzer.server.handler.GetJarListHandler;
import me.n1ar4.jar.analyzer.server.handler.base.HttpHandler;

import java.util.HashMap;
import java.util.Map;

public class PathMatcher {
    public static Map<String, HttpHandler> handlers = new HashMap<>();

    static {
        handlers.put("/api/get_jars_list", new GetJarListHandler());
        handlers.put("/api/get_jar_by_class", new GetJarByClassHandler());
        handlers.put("/api/get_methods_by_class", new GetMethodsByClassHandler());
    }

    public static NanoHTTPD.Response handleReq(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();
        for (Map.Entry<String, HttpHandler> entry : handlers.entrySet()) {
            if (uri.startsWith(entry.getKey())) {
                return entry.getValue().handle(session);
            }
        }
        return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.NOT_FOUND,
                "text/plain",
                "JAR ANALYZER SERVER\n" +
                        "URI NOT FOUND");
    }
}