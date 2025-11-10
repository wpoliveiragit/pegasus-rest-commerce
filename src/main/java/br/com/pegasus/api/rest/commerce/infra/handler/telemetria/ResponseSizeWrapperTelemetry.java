package br.com.pegasus.api.rest.commerce.infra.handler.telemetria;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/** Wrapper de resposta para medir tamanho */
public class ResponseSizeWrapperTelemetry extends HttpServletResponseWrapper {

  private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  private final ServletOutputStream outputStream = createServletOutputStream(buffer);
  private PrintWriter writer;

  public ResponseSizeWrapperTelemetry(HttpServletResponse response) {
    super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() {
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() {
    if (writer == null) {
      writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
    }
    return writer;
  }

  public long getContentSize() {
    return buffer.size();
  }

  public void flushToResponse() throws IOException {
    super.getOutputStream().write(buffer.toByteArray());
    super.getOutputStream().flush();
  }

  private static ServletOutputStream createServletOutputStream(ByteArrayOutputStream buffer) {
    return new ServletOutputStream() {
      @Override
      public void write(int b) {
        buffer.write(b);
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setWriteListener(jakarta.servlet.WriteListener listener) {
      }
    };
  }
}
