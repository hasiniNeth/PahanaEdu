package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class LoginServletTest {

    private LoginServlet loginServlet;
    private FakeRequest request;
    private FakeResponse response;

    @Before
    public void setUp() throws SQLException {
        AuthenticationService authService = new AuthenticationService(null);

        loginServlet = new LoginServlet() {
            protected AuthenticationService createAuthService() {
                return authService;
            }
        };

        request = new FakeRequest();
        response = new FakeResponse();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        request.setParameter("username", "admin");
        request.setParameter("password", "1234");

        loginServlet.doPost(request, response);

        assertEquals(302, response.getStatus());
        assertTrue(response.getRedirectLocation().contains("dashboard"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        request.setParameter("username", "wrong");
        request.setParameter("password", "bad");

        loginServlet.doPost(request, response);

        assertEquals(302, response.getStatus());
        assertTrue(response.getRedirectLocation().contains("login.jsp?error"));
    }

    // ---------- Fake HttpServletRequest --------------
    private static class FakeRequest implements HttpServletRequest {
        private final Map<String, String> params = new HashMap<>();
        private final HttpSession session = new FakeSession();

        public void setParameter(String key, String value) {
            params.put(key, value);
        }

        @Override public String getParameter(String name) { return params.get(name); }
        @Override public HttpSession getSession() { return session; }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override public Enumeration<String> getParameterNames() {
            return Collections.enumeration(params.keySet());
        }

        @Override
        public Cookie[] getCookies() { return new Cookie[0]; }

        // ---- unused methods (stubs) ----
        @Override public Object getAttribute(String name) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
        @Override public String getAuthType() { return null; }
        @Override public String getContextPath() { return null; }
        @Override public String getMethod() { return "POST"; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
        @Override public HttpSession getSession(boolean create) { return session; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return Collections.emptyEnumeration(); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.emptyEnumeration(); }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public void setAttribute(String name, Object o) {}
        @Override public void removeAttribute(String name) {}
        @Override public Locale getLocale() { return null; }
        @Override public Enumeration<Locale> getLocales() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public RequestDispatcher getRequestDispatcher(String s) { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public javax.servlet.ServletInputStream getInputStream() { return null; }
        @Override public String[] getParameterValues(String name) { return new String[0]; }
        @Override public Map<String, String[]> getParameterMap() { return null; }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public Collection<Part> getParts() { return null; }
        @Override public Part getPart(String name) { return null; }
        @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse) { return null; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public int getRemotePort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public String getRealPath(String path) { return null; }
    }

    // ---------- Fake HttpServletResponse --------------
    private static class FakeResponse implements HttpServletResponse {
        private int status;
        private String redirectLocation;
        private final StringWriter writer = new StringWriter();

        @Override
        public void sendRedirect(String location) {
            this.redirectLocation = location;
            this.status = 302;
        }

        @Override
        public void setDateHeader(String s, long l) {}
        @Override
        public void addDateHeader(String s, long l) {}
        @Override
        public void setHeader(String s, String s1) {}
        @Override
        public void addHeader(String s, String s1) {}
        @Override
        public void setIntHeader(String s, int i) {}
        @Override
        public void addIntHeader(String s, int i) {}
        @Override
        public PrintWriter getWriter() { return new PrintWriter(writer); }
        @Override public void setCharacterEncoding(String s) {}
        @Override public void setContentLength(int i) {}
        @Override public void setContentLengthLong(long l) {}

        public int getStatus() { return status; }
        @Override public String getHeader(String s) { return ""; }
        @Override public Collection<String> getHeaders(String s) {
            List<String> strings = new ArrayList<>();
            return strings; }
        @Override public Collection<String> getHeaderNames() {
            List<String> strings = new ArrayList<>();
            return strings; }
        public String getRedirectLocation() { return redirectLocation; }
        public String getOutput() { return writer.toString(); }

        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String s) { return false; }
        @Override public String encodeURL(String s) { return ""; }
        @Override public String encodeRedirectURL(String s) { return ""; }
        @Override public String encodeUrl(String s) { return ""; }
        @Override public String encodeRedirectUrl(String s) { return ""; }
        @Override public void sendError(int i, String s) throws IOException {}
        @Override public void sendError(int i) throws IOException {}
        @Override public void setStatus(int sc) { this.status = sc; }
        @Override public void setStatus(int i, String s) {}
        @Override public void setContentType(String type) {}
        @Override public void setBufferSize(int i) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() throws IOException {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale locale) {}
        @Override public Locale getLocale() { return null; }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public String getContentType() { return null; }
        @Override public ServletOutputStream getOutputStream() throws IOException { return null; }
    }

    // ---------- Fake HttpSession --------------
    private static class FakeSession implements HttpSession {
        private final Map<String,Object> attributes = new HashMap<>();

        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public Enumeration<String> getAttributeNames() {
            return Collections.enumeration(attributes.keySet());
        }

        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "fake"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }

        @SuppressWarnings("deprecation")
        @Override
        public HttpSessionContext getSessionContext() { return null; }

        @Override public Object getValue(String name) { return null; }
        @Override public String[] getValueNames() { return new String[0]; }
        @Override public void putValue(String name, Object value) {}
        @Override public void removeAttribute(String name) {}
        @Override public void removeValue(String name) {}
        @Override public boolean isNew() { return false; }
    }
}