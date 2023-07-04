package etu1856.framework.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import etu1856.framework.Mapping;
import util.Util;
import etu1856.framework.ModelView;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    private Util util;

    @Override
    public void init() throws ServletException {
        super.init();
        try {

            this.util = new Util();
            this.mappingUrls = new HashMap<>();
            final String tomPath = "/WEB-INF/classes/";
            String path = getServletContext().getRealPath(tomPath);
            util.loadMapping(path, tomPath, mappingUrls);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        url = util.processUrl(url, request.getContextPath());

        try {
            Mapping map = mappingUrls.get(url);

            if (map == null) throw new Exception("Not Found");

            ModelView mv = util.invokeMethod(request, map);
            util.setAttributeRequest(request, mv);
            request.getRequestDispatcher(mv.getView()).forward(request, response);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {

        } catch (ServletException | IOException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
