@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

    long startTime = System.currentTimeMillis();

    // Сохраняем оригинальный Writer
    PrintWriter originalWriter = response.getWriter();
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(new BufferedWriter(stringWriter));
    
    // Заменяем Writer
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.setWriter(writer);

    try {
        chain.doFilter(request, response);
    } finally {
        // Получаем время выполнения
        long endTime = System.currentTimeMillis();
        String responseBody = stringWriter.toString();

        // Проверяем статус только после завершения обработки
        int status = response.getStatus();
        log.info("Request: {} {}, Response Status: {}, Time: {}ms",
                request.getMethod(), request.getRequestURI(), status, endTime - startTime);
    }
}