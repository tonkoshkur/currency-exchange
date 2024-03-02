package ua.tonkoshkur.currency.exchange.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class WithPatchServlet extends HttpServlet {

    private static final String METHOD_PATCH = "PATCH";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase(METHOD_PATCH)) {
            this.doPatch(req, resp);
            return;
        }

        super.service(req, resp);
    }

    protected abstract void doPatch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;
}
