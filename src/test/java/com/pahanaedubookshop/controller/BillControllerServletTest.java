package com.pahanaedubookshop.controller;

import com.pahanaedubookshop.model.BillItem;
import com.pahanaedubookshop.model.Customer;
import com.pahanaedubookshop.dao.CustomerDAO;
import com.pahanaedubookshop.dao.BookDao;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.*;


import static org.junit.Assert.*;

public class BillControllerServletTest {

    private BillControllerServlet servlet;
    private FakeRequest request;
    private FakeResponse response;

    @Before
    public void setUp() {
        servlet = new BillControllerServlet();
        request = new FakeRequest();
        response = new FakeResponse();

        // Inject fake DAOs via reflection
        try {
            CustomerDAO fakeCustomerDAO = new CustomerDAO() {
                @Override
                public Customer getCustomerByAccountNumber(String accountNumber) {
                    if ("12345".equals(accountNumber)) {
                        Customer c = new Customer();
                        c.setAccountNumber("12345");
                        c.setFullName("Test Customer");
                        return c;
                    }
                    return null;
                }
            };

            BookDao fakeBookDao = new BookDao() {
                @Override
                public com.pahanaedubookshop.model.Book getBookById(int bookId) {
                    if (bookId == 1) {
                        com.pahanaedubookshop.model.Book b = new com.pahanaedubookshop.model.Book();
                        b.setBookId(1);
                        b.setPrice(100);
                        return b;
                    }
                    return null;
                }
            };

            java.lang.reflect.Field customerField = BillControllerServlet.class.getDeclaredField("customerDAO");
            customerField.setAccessible(true);
            customerField.set(servlet, fakeCustomerDAO);

            java.lang.reflect.Field bookField = BillControllerServlet.class.getDeclaredField("bookDao");
            bookField.setAccessible(true);
            bookField.set(servlet, fakeBookDao);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSearchCustomerFound() throws Exception {
        request.setParameter("action", "searchCustomer");
        request.setParameter("account", "12345");

        servlet.doGet(request, response);

        Object customer = request.getSession().getAttribute("selectedCustomer");
        assertNotNull("Customer should be found", customer);
    }

    @Test
    public void testAddToBill() throws Exception {
        request.setParameter("action", "addToBill");
        request.setParameter("bookId", "1");
        request.setParameter("quantity", "2");

        servlet.doPost(request, response);

        @SuppressWarnings("unchecked")
        List<BillItem> cart = (List<BillItem>) request.getSession().getAttribute("cart");
        assertNotNull("Cart should not be null", cart);
        assertEquals(1, cart.size());

        BillItem item = cart.get(0);
        assertEquals(1, item.getBookId());
        assertEquals(100, item.getPrice());
        assertEquals(2, item.getQuantity());
    }

    // --- Fake classes ---

    static class FakeSession implements HttpSession {
        private final Map<String, Object> attributes = new HashMap<>();
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void removeAttribute(String name) { attributes.remove(name); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "fake-session"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return getAttribute(name); }
        @Override public String[] getValueNames() { return attributes.keySet().toArray(new String[0]); }
        @Override public void putValue(String name, Object value) { setAttribute(name, value); }
        @Override public void removeValue(String name) { removeAttribute(name); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public boolean isNew() { return false; }
    }

    static class FakeRequest extends HttpServletRequestWrapper {
        private final Map<String, String> params = new HashMap<>();
        private final FakeSession session = new FakeSession();

        public FakeRequest() { super(new DummyRequest()); }

        public void setParameter(String key, String value) { params.put(key, value); }

        @Override public String getParameter(String name) { return params.get(name); }
        @Override public HttpSession getSession() { return session; }
        @Override public HttpSession getSession(boolean create) { return session; }
        @Override public RequestDispatcher getRequestDispatcher(String path) { return new DummyDispatcher(); }
    }

    static class FakeResponse extends HttpServletResponseWrapper {
        private final StringWriter writer = new StringWriter();
        public FakeResponse() { super(new DummyResponse()); }
        @Override public PrintWriter getWriter() { return new PrintWriter(writer); }
        public String getOutput() { return writer.toString(); }
    }

    static class DummyDispatcher implements RequestDispatcher {
        @Override public void forward(ServletRequest request, ServletResponse response) {}
        @Override public void include(ServletRequest request, ServletResponse response) {}
    }

    static class DummyRequest implements HttpServletRequest {
        @Override public HttpSession getSession() { return null; }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String s, String s1) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            List<Part> parts = new ArrayList<>();
            return parts;
        }

        @Override
        public Part getPart(String s) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
            return null;
        }

        @Override
        public String getAuthType() {
            return "";
        }

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

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

        @Override
        public String getMethod() {
            return "";
        }

        @Override
        public String getPathInfo() {
            return "";
        }

        @Override
        public String getPathTranslated() {
            return "";
        }

        @Override
        public String getContextPath() {
            return "";
        }

        @Override
        public String getQueryString() {
            return "";
        }

        @Override
        public String getRemoteUser() {
            return "";
        }

        @Override
        public boolean isUserInRole(String s) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return "";
        }

        @Override
        public String getRequestURI() {
            return "";
        }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return "";
        }

        @Override public HttpSession getSession(boolean create) { return null; }
        @Override public String getParameter(String name) { return null; }
        @Override public Enumeration<String> getParameterNames() { return Collections.emptyEnumeration(); }
        @Override public String[] getParameterValues(String name) { return new String[0]; }
        @Override public Map<String,String[]> getParameterMap() { return Collections.emptyMap(); }
        @Override public RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public Object getAttribute(String name) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
        @Override public void setAttribute(String name, Object o) {}
        @Override public void removeAttribute(String name) {}
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public ServletInputStream getInputStream() { return null; }
        @Override public BufferedReader getReader() { return null; }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public String getRealPath(String path) { return null; }
        @Override public Locale getLocale() { return null; }
        @Override public Enumeration<Locale> getLocales() { return Collections.emptyEnumeration(); }
        @Override public boolean isSecure() { return false; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return null; }
    }

    static class DummyResponse implements HttpServletResponse {
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeURL(String url) { return null; }
        @Override public String encodeRedirectURL(String url) { return null; }
        @Override public String encodeUrl(String url) { return null; }
        @Override public String encodeRedirectUrl(String url) { return null; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void sendRedirect(String location) {}
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
        @Override public Collection<String> getHeaders(String name) { return Collections.emptyList(); }
        @Override public Collection<String> getHeaderNames() { return Collections.emptyList(); }
        @Override public String getCharacterEncoding() { return null; }
        @Override public String getContentType() { return null; }
        @Override public ServletOutputStream getOutputStream() { return null; }
        @Override public PrintWriter getWriter() { return new PrintWriter(System.out); }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setContentType(String type) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale loc) {}
        @Override public Locale getLocale() { return null; }
    }
}
