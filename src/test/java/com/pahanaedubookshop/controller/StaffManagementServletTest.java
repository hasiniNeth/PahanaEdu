package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Staff;
import com.pahanaedubookshop.service.StaffService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.Assert.*;

public class StaffManagementServletTest {

    private StaffManagementServlet servlet;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;

    @Before
    public void setUp() {
        servlet = new StaffManagementServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                super.doGet(req, resp);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                super.doPost(req, resp);
            }
        };
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    // ------------------ GET Tests ------------------
    @Test
    public void testDoGetStaffList() throws Exception {
        session.setAttribute("role", "admin");

        servlet.doGet(request, response);

        assertNotNull(request.getAttribute("staffList"));
    }

    @Test
    public void testDoGetEditStaff() throws Exception {
        session.setAttribute("role", "admin");
        request.setParameter("action", "edit");
        request.setParameter("id", "1");

        servlet.doGet(request, response);

        assertNotNull(request.getAttribute("staff"));
    }

    // ------------------ POST Tests ------------------
    @Test
    public void testAddStaff() throws Exception {
        session.setAttribute("role", "admin");
        request.setParameter("action", "add");
        request.setParameter("username", "newstaff");
        request.setParameter("password", "pass123");
        request.setParameter("fullName", "New Staff");

        servlet.doPost(request, response);

        assertEquals(302, response.getStatus());
        assertTrue(response.getRedirectLocation().contains("message=Staff added successfully"));
    }

    @Test
    public void testUpdateStaff() throws Exception {
        session.setAttribute("role", "admin");
        request.setParameter("action", "update");
        request.setParameter("id", "1");
        request.setParameter("username", "updatedstaff");
        request.setParameter("fullName", "Updated Staff");
        request.setParameter("password", "newpass");

        servlet.doPost(request, response);

        assertEquals(302, response.getStatus());
        assertTrue(response.getRedirectLocation().contains("message=Staff updated successfully"));
    }

    @Test
    public void testDeleteStaff() throws Exception {
        session.setAttribute("role", "admin");
        request.setParameter("action", "delete");
        request.setParameter("id", "1");

        servlet.doPost(request, response);

        assertEquals(302, response.getStatus());
        assertTrue(response.getRedirectLocation().contains("message=Staff deleted successfully"));
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        session.setAttribute("role", "staff"); // Not admin
        request.setParameter("action", "add");

        servlet.doPost(request, response);

        assertEquals(403, response.getStatus());
    }

    // ------------------ Fake Classes ------------------
    private static class FakeRequest implements HttpServletRequest {
        private final Map<String, String> params = new HashMap<>();
        private final FakeSession session;
        private final Map<String,Object> attributes = new HashMap<>();

        public FakeRequest(FakeSession session) {
            this.session = session;
        }

        public void setParameter(String key, String value) { params.put(key, value); }
        @Override public String getParameter(String name) { return params.get(name); }
        @Override public HttpSession getSession() { return session; }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override public HttpSession getSession(boolean create) { return session; }
        @Override public void setAttribute(String name, Object o) { attributes.put(name, o); }
        @Override public Object getAttribute(String name) { return attributes.get(name); }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override public Enumeration<String> getParameterNames() { return Collections.enumeration(params.keySet()); }
        // --- stubs ---
        @Override public String getMethod() { return "POST"; }
        @Override public RequestDispatcher getRequestDispatcher(String s) { return null; }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }

        @Override
        public long getDateHeader(String s) {
            return 0;
        }

        @Override
        public String getHeader(String s) {
            return "";
        }

        @Override
        public Enumeration<String> getHeaders(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String s) {
            return 0;
        }

        @Override public Enumeration<Locale> getLocales() { return null; }
        @Override public Locale getLocale() { return null; }
        @Override public void removeAttribute(String name) {}
        @Override public boolean isSecure() { return false; }
        @Override public String getRealPath(String path) { return null; }
        @Override public String getAuthType() { return null; }
        @Override public String getContextPath() { return null; }
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
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public javax.servlet.ServletInputStream getInputStream() { return null; }
        @Override public String[] getParameterValues(String name) { return new String[0]; }
        @Override public Map<String, String[]> getParameterMap() { return null; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public int getRemotePort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public Collection<Part> getParts() { return null; }
        @Override public Part getPart(String name) { return null; }
        @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
    }

    private static class FakeResponse implements HttpServletResponse {
        private int status;
        private String redirectLocation;

        @Override
        public void sendRedirect(String location) {
            this.redirectLocation = location;
            this.status = 302;
        }

        @Override
        public void sendError(int sc, String msg) {
            this.status = sc;
        }

        @Override
        public void sendError(int sc) {
            this.status = sc;
        }

        public int getStatus() {
            return status;
        }

        public String getRedirectLocation() {
            return redirectLocation;
        }

        // --- stubs ---
        @Override public void setContentType(String s) {}
        @Override public void setCharacterEncoding(String s) {}
        @Override public void setContentLength(int i) {}
        @Override public void setContentLengthLong(long l) {}
        @Override public PrintWriter getWriter() { return new PrintWriter(System.out); }
        @Override public void setStatus(int sc) { this.status = sc; }
        @Override public void setStatus(int sc, String sm) {}
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public String getContentType() { return null; }
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale locale) {}
        @Override public Locale getLocale() { return null; }
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String s) { return false; }
        @Override public String encodeURL(String s) { return null; }
        @Override public String encodeRedirectURL(String s) { return null; }
        @Override public String encodeUrl(String s) { return null; }
        @Override public String encodeRedirectUrl(String s) { return null; }
        @Override public void setDateHeader(String s, long l) {}
        @Override public void addDateHeader(String s, long l) {}
        @Override public void setHeader(String s, String s1) {}
        @Override public void addHeader(String s, String s1) {}
        @Override public void setIntHeader(String s, int i) {}
        @Override public void addIntHeader(String s, int i) {}
        @Override public Collection<String> getHeaders(String s) {
            List<String> strings = new ArrayList<>();
            return strings; }
        @Override public Collection<String> getHeaderNames() {
            List<String> strings = new ArrayList<>();
            return strings; }
        @Override public String getHeader(String s) { return null; }
        @Override public ServletOutputStream getOutputStream() { return null; }
    }

    private static class FakeSession implements HttpSession {
        private final Map<String,Object> attributes = new HashMap<>();
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "fake"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @SuppressWarnings("deprecation")
        @Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return null; }
        @Override public String[] getValueNames() { return new String[0]; }
        @Override public void putValue(String name, Object value) {}
        @Override public void removeAttribute(String name) {}
        @Override public void removeValue(String name) {}
        @Override public boolean isNew() { return false; }
    }
}
