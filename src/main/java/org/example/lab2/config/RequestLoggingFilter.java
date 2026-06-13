package org.example.lab2.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();
        CustomResponseWrapper responseWrapper = new CustomResponseWrapper(httpResponse);

        try {
            chain.doFilter(httpRequest, responseWrapper);
        } finally {
            long endTime = System.currentTimeMillis();
            int status = responseWrapper.getStatus();

            log.info("Request: {} {}, Response Status: {}, Time: {}ms",
                    httpRequest.getMethod(), httpRequest.getRequestURI(), status, endTime - startTime);
        }

        // Копируем результат в оригинальный ответ
        byte[] responseData = responseWrapper.getDataStream();
        httpResponse.getOutputStream().write(responseData);
    }

    public static class CustomResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private PrintWriter writer;

        public CustomResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    // Not implemented for this simple wrapper
                }
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (writer == null) {
                writer = new PrintWriter(new OutputStreamWriter(outputStream));
            }
            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            }
            outputStream.flush();
        }

        public byte[] getDataStream() {
            return outputStream.toByteArray();
        }
    }
}
