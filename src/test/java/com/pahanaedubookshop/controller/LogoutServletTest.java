package com.pahanaedubookshop.controller;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.*;

public class LogoutServletTest {

    private LogoutServlet logoutServlet;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;

    @Before
    public void setUp() {
        logoutServlet = new LogoutServlet();
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    @Test
    public void testLogoutWithActiveSession() throws IOException, javax.servlet.ServletException {
        // Set some session attribute
        session.setAttribute("user", "admin");

        logoutServlet.doGet(request, response);

        // Session should be invalidated
        assertTrue(session.isInvalidated());
        // Redirect should point to login.jsp
        assertEquals("/login.jsp", response.getRedirectLocation());
    }

    @Test
    public void testLogoutWithNoSession() throws IOException, javax.servlet.ServletException {
        // Simulate request with no session
        request = new FakeRequest(null);
        response = new FakeResponse();

        logoutServlet.doGet(request, response);

        // Redirect should still point to login.jsp
        assertEquals("/login.jsp", response.getRedirectLocation());
    }

    // ---------- Fake HttpServletRequest --------------
    private static class FakeRequest implements HttpServletRequest {
        private final HttpSession session;

        public FakeRequest(HttpSession session) {
            this.session = session;
        }

        @Override
        public HttpSession getSession(boolean create) {
            return session;
        }

        @Override
        public HttpSession getSession() {
            return session;
        }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override
        public String getContextPath() {
            return "";
        }

        // Stub all other methods
        @Override public Object getAttribute(String name) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
        @Override public String getAuthType() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return Collections.emptyEnumeration(); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.emptyEnumeration(); }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return null; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
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
        @Override public String getParameter(String name) { return null; }
        @Override public Enumeration<String> getParameterNames() { return Collections.emptyEnumeration(); }
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
        @Override public <T extends javax.servlet.http.HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public javax.servlet.AsyncContext getAsyncContext() { return null; }
        @Override public javax.servlet.DispatcherType getDispatcherType() { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public javax.servlet.AsyncContext startAsync() { return null; }
        @Override public javax.servlet.AsyncContext startAsync(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse) { return null; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public int getRemotePort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public String getRealPath(String path) { return null; }
    }

    // ---------- Fake HttpServletResponse --------------
    private static class FakeResponse implements HttpServletResponse {
        private String redirectLocation;

        @Override public void sendRedirect(String location) { this.redirectLocation = location; }

        public String getRedirectLocation() { return redirectLocation; }

        // Stub all other methods
        @Override public void addCookie(Cookie cookie) {}

        @Override public boolean containsHeader(String s) { return false; }
        @Override public void setStatus(int sc) {}
        @Override public void setStatus(int i, String s) {}
        @Override public void setContentType(String type) {}
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public String getContentType() { return null; }
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(java.util.Locale loc) {}
        @Override public java.util.Locale getLocale() { return null; }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public String encodeUrl(String url) { return url; }
        @Override public String encodeRedirectUrl(String url) { return url; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void setHeader(String name, String value) {}
        @Override public void addHeader(String name, String value) {}
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public int getStatus() { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public java.util.Collection<String> getHeaders(String name) { return null; }
        @Override public java.util.Collection<String> getHeaderNames() { return null; }
        @Override public javax.servlet.ServletOutputStream getOutputStream() { return null; }
        @Override public PrintWriter getWriter() { return null; }
        @Override public void setCharacterEncoding(String s) {}
    }

    // ---------- Fake HttpSession --------------
    private static class FakeSession implements HttpSession {
        private final Map<String,Object> attributes = new HashMap<>();
        private boolean invalidated = false;

        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void invalidate() { invalidated = true; attributes.clear(); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        public boolean isInvalidated() { return invalidated; }

        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "fake"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return null; }
        @Override public String[] getValueNames() { return new String[0]; }
        @Override public void putValue(String name, Object value) {}
        @Override public void removeAttribute(String s) {}
        @Override public void removeValue(String name) {}
        @Override public boolean isNew() { return false; }
    }
}
