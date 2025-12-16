package br.com.pegasus.api.rest.commerce.infra.telemetry;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseSizeWrapperTelemetry extends HttpServletResponseWrapper {

  private final ServletOutputStream outputStream;
  private final ByteArrayOutputStream buffer;
  private PrintWriter writer;

  public ResponseSizeWrapperTelemetry(HttpServletResponse response) {
    super(response);
    this.buffer = new ByteArrayOutputStream();
    this.outputStream = createServletOutputStream(buffer);
  }

  @Override
  public ServletOutputStream getOutputStream() {
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() {
    return (writer == null) //
        ? (writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8)) : writer;
  }

  public void flushToResponse() throws IOException {
    super.getOutputStream().write(buffer.toByteArray());
    super.getOutputStream().flush();
  }

  public long getContentSize() {
    return buffer.size();
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
