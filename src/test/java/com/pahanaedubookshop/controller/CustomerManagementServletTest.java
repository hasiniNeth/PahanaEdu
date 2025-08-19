package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.dao.CustomerDAO;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class CustomerManagementServletTest {

    private CustomerManagementServlet servlet;
    private StubCustomerDAO stubDAO;

    @Before
    public void setUp() {
        stubDAO = new StubCustomerDAO();
        servlet = new CustomerManagementServlet(); // inject stub DAO
    }

    @Test
    public void testAddCustomer() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse();

        // Set parameters for adding a customer
        request.setParameter("action", "add");
        request.setParameter("fullName", "Test User");
        request.setParameter("address", "123 Street");
        request.setParameter("telephone", "123456789");

        servlet.doPost(request, response);

        // Verify that the customer was "added" to the stub DAO
        boolean found = stubDAO.getAllCustomers().stream()
                .anyMatch(c -> "Test User".equals(c.getFullName()) &&
                        "123456789".equals(c.getTelephone()));
        assertTrue("Customer should be added in DAO", found);

        // Verify redirect to the customer-management page
        assertEquals("customer-management.jsp", response.getRedirectLocation());
    }

    // ------------------- Stub DAO -------------------
    static class StubCustomerDAO extends CustomerDAO {
        private final List<Customer> customers = new ArrayList<>();

        @Override
        public void addCustomer(Customer customer) {
            customers.add(customer);
        }

        @Override
        public List<Customer> getAllCustomers() {
            return customers;
        }
    }

    // ------------------- Stub HttpServletRequest -------------------
    static class StubHttpServletRequest implements HttpServletRequest {
        private final Map<String, String> parameters = new HashMap<>();
        private final Map<String, Object> attributes = new HashMap<>();

        public void setParameter(String name, String value) { parameters.put(name, value); }

        @Override public String getParameter(String name) { return parameters.get(name); }
        @Override public Enumeration<String> getParameterNames() { return Collections.enumeration(parameters.keySet()); }
        @Override public void setAttribute(String name, Object o) { attributes.put(name, o); }
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public String getMethod() { return "POST"; }
        @Override public HttpSession getSession() { return new StubHttpSession(); }
        @Override public HttpSession getSession(boolean create) { return new StubHttpSession(); }

        // --- other unused methods ---
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public String getAuthType() { return null; }
        @Override public long getDateHeader(String s) { return 0; }
        @Override public String getHeader(String s) { return null; }
        @Override public Enumeration<String> getHeaders(String s) { return null; }
        @Override public Enumeration<String> getHeaderNames() { return null; }
        @Override public int getIntHeader(String s) { return 0; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getContextPath() { return ""; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String s) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
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
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public void removeAttribute(String name) {}
        @Override public Locale getLocale() { return Locale.ENGLISH; }
        @Override public Enumeration<Locale> getLocales() { return Collections.enumeration(Collections.singletonList(Locale.ENGLISH)); }
        @Override public boolean isSecure() { return false; }
        @Override public String getRealPath(String path) { return null; }
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
        @Override public String[] getParameterValues(String name) { return new String[]{parameters.get(name)}; }
        @Override public Map<String, String[]> getParameterMap() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public ServletInputStream getInputStream() { return null; }
    }

    // ------------------- Stub HttpServletResponse -------------------
    static class StubHttpServletResponse implements HttpServletResponse {
        private String redirectLocation;

        @Override public void sendRedirect(String location) { this.redirectLocation = location; }
        public String getRedirectLocation() { return redirectLocation; }

        @Override public PrintWriter getWriter() { return new PrintWriter(System.out); }

        // --- other unused methods ---
        @Override public String encodeURL(String url) { return url; }
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String s) { return false; }
        @Override public String encodeRedirectURL(String s) { return null; }
        @Override public String encodeUrl(String s) { return ""; }
        @Override public String encodeRedirectUrl(String s) { return ""; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void setHeader(String name, String value) {}
        @Override public void addHeader(String name, String value) {}
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public void setStatus(int sc) {}
        @Override public void setStatus(int sc, String sm) {}
        @Override public int getStatus() { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Collection<String> getHeaders(String name) { return null; }
        @Override public Collection<String> getHeaderNames() { return null; }
        @Override public int getBufferSize() { return 0; }
        @Override public void setBufferSize(int size) {}
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale loc) {}
        @Override public Locale getLocale() { return Locale.ENGLISH; }
        @Override public String getCharacterEncoding() { return ""; }
        @Override public String getContentType() { return ""; }
        @Override public ServletOutputStream getOutputStream() { return null; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setContentType(String type) {}
    }

    // ------------------- Stub HttpSession -------------------
    static class StubHttpSession implements HttpSession {
        private final Map<String,Object> attributes = new HashMap<>();
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "stub"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return null; }
        @Override public String[] getValueNames() { return new String[0]; }
        @Override public void putValue(String name, Object value) {}
        @Override public void removeAttribute(String s) { attributes.remove(s); }
        @Override public void removeValue(String name) {}
        @Override public boolean isNew() { return false; }
    }
}
