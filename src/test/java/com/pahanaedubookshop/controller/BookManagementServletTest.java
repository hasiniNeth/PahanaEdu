package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Book;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Manual unit tests for BookManagementServlet without Mockito.
 */
public class BookManagementServletTest {

    private BookManagementServlet servlet;
    private FakeRequest request;
    private FakeResponse response;
    private FakeDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new BookManagementServlet();
        dispatcher = new FakeDispatcher();
        request = new FakeRequest(dispatcher);
        response = new FakeResponse();
    }

    @Test
    public void testDoGet_noAction_listsBooks() throws Exception {
        servlet.doGet(request, response);

        assertEquals("/WEB-INF/views/book-management.jsp", dispatcher.getLastForwardedPath());
        assertNotNull(request.getAttribute("books")); // books list should be set
    }

    @Test
    public void testDoGet_withMessage_setsAttribute() throws Exception {
        request.setParameter("message", "Book added successfully");

        servlet.doGet(request, response);

        assertEquals("Book added successfully", request.getAttribute("message"));
        assertEquals("/WEB-INF/views/book-management.jsp", dispatcher.getLastForwardedPath());
    }

    @Test
    public void testDoPost_addBook_invalidPrice() throws Exception {
        request.setParameter("action", "add");
        request.setParameter("title", "Java Programming");
        request.setParameter("author", "John Doe");
        request.setParameter("isbn", "1234567890");
        request.setParameter("price", "-10"); // invalid
        request.setParameter("stock", "5");

        servlet.doPost(request, response);

        assertEquals("Invalid price. Must be a number greater than 0.", request.getAttribute("error"));
        assertEquals("/WEB-INF/views/book-management.jsp", dispatcher.getLastForwardedPath());
    }

    @Test
    public void testDoPost_addBook_valid() throws Exception {
        request.setParameter("action", "add");
        request.setParameter("title", "Java Programming");
        request.setParameter("author", "John Doe");
        request.setParameter("isbn", "1234567890");
        request.setParameter("price", "100");
        request.setParameter("stock", "5");

        servlet.doPost(request, response);

        assertTrue(response.getRedirectedTo().contains("book-management?message=Book added successfully"));
    }

    // ======= Fake Classes for Testing =======

    private static class FakeRequest extends HttpServletRequestWrapper {
        private final Map<String, Object> attributes = new HashMap<>();
        private final Map<String, String> parameters = new HashMap<>();
        private final FakeDispatcher dispatcher;

        public FakeRequest(FakeDispatcher dispatcher) {
            super(new HttpServletRequestAdapter());
            this.dispatcher = dispatcher;
        }

        public void setParameter(String name, String value) {
            parameters.put(name, value);
        }

        @Override
        public String getParameter(String name) {
            return parameters.get(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new HashMap<>();
            parameters.forEach((k, v) -> map.put(k, new String[]{v}));
            return map;
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            dispatcher.setLastForwardedPath(path);
            return dispatcher;
        }
    }

    private static class FakeResponse extends HttpServletResponseWrapper {
        private final StringWriter writer = new StringWriter();
        private String redirectedTo;

        public FakeResponse() {
            super(new HttpServletResponseAdapter());
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(writer);
        }

        @Override
        public void sendRedirect(String location) {
            this.redirectedTo = location;
        }

        public String getRedirectedTo() {
            return redirectedTo;
        }
    }

    private static class FakeDispatcher implements RequestDispatcher {
        private String lastForwardedPath;

        public String getLastForwardedPath() {
            return lastForwardedPath;
        }

        public void setLastForwardedPath(String path) {
            this.lastForwardedPath = path;
        }

        @Override
        public void forward(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response)
                throws ServletException, IOException {
            // nothing to do, path is already recorded
        }

        @Override
        public void include(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response)
                throws ServletException, IOException {
            // not needed
        }
    }

    // Empty Adapters to satisfy wrapper constructors
    private static class HttpServletRequestAdapter extends HttpServletRequestWrapper {
        public HttpServletRequestAdapter() { super(new DummyRequest()); }
    }

    private static class HttpServletResponseAdapter extends HttpServletResponseWrapper {
        public HttpServletResponseAdapter() { super(new DummyResponse()); }
    }

    // Dummy classes to satisfy wrapper
    private static class DummyRequest implements HttpServletRequest {
        @Override public Object getAttribute(String s) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String s) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public ServletInputStream getInputStream() { return null; }
        @Override public String getParameter(String s) { return null; }
        @Override public Enumeration<String> getParameterNames() { return Collections.emptyEnumeration(); }
        @Override public String[] getParameterValues(String s) { return new String[0]; }
        @Override public Map<String, String[]> getParameterMap() { return Collections.emptyMap(); }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public void setAttribute(String s, Object o) {}
        @Override public void removeAttribute(String s) {}
        @Override public Locale getLocale() { return null; }
        @Override public Enumeration<Locale> getLocales() { return Collections.emptyEnumeration(); }
        @Override public boolean isSecure() { return false; }
        @Override public RequestDispatcher getRequestDispatcher(String s) { return null; }
        @Override public String getRealPath(String s) { return null; }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return null; }
        @Override public String getAuthType() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public long getDateHeader(String s) { return 0; }
        @Override public String getHeader(String s) { return null; }
        @Override public Enumeration<String> getHeaders(String s) { return Collections.emptyEnumeration(); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.emptyEnumeration(); }
        @Override public int getIntHeader(String s) { return 0; }
        @Override public String getMethod() { return null; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getContextPath() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String s) { return false; }
        @Override public Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
        @Override public HttpSession getSession(boolean b) { return null; }
        @Override public HttpSession getSession() { return null; }
        @Override public String changeSessionId() { return null; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public Collection<Part> getParts() { return null; }
        @Override public Part getPart(String name) { return null; }
        @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
    }

    private static class DummyResponse implements HttpServletResponse {
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String s) { return false; }
        @Override public String encodeURL(String s) { return null; }
        @Override public String encodeRedirectURL(String s) { return null; }
        @Override public String encodeUrl(String s) { return null; }
        @Override public String encodeRedirectUrl(String s) { return null; }
        @Override public void sendError(int i, String s) {}
        @Override public void sendError(int i) {}
        @Override public void sendRedirect(String s) {}
        @Override public void setDateHeader(String s, long l) {}
        @Override public void addDateHeader(String s, long l) {}
        @Override public void setHeader(String s, String s1) {}
        @Override public void addHeader(String s, String s1) {}
        @Override public void setIntHeader(String s, int i) {}
        @Override public void addIntHeader(String s, int i) {}
        @Override public void setStatus(int i) {}
        @Override public void setStatus(int i, String s) {}
        @Override public int getStatus() { return 0; }
        @Override public String getHeader(String s) { return null; }
        @Override public Collection<String> getHeaders(String s) { return null; }
        @Override public Collection<String> getHeaderNames() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public String getContentType() { return null; }
        @Override public ServletOutputStream getOutputStream() { return null; }
        @Override public PrintWriter getWriter() { return null; }
        @Override public void setCharacterEncoding(String s) {}
        @Override public void setContentLength(int i) {}
        @Override public void setContentLengthLong(long l) {}
        @Override public void setContentType(String s) {}
        @Override public void setBufferSize(int i) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale locale) {}
        @Override public Locale getLocale() { return null; }
    }
}