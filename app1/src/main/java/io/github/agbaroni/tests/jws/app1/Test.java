package io.github.agbaroni.tests.jws.app1;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

@WebServlet(urlPatterns = { "/" })
public class Test extends HttpServlet {
    private static final long serialVersionUID = 313282715416666L;

    private static final String message = "hello from app1";

    @Override
    protected void service(HttpServletRequest request,
			   HttpServletResponse response) throws ServletException,
								IOException {
	try (var pw = response.getWriter()) {
	    pw.write(String.format("%s (%s)", message, new DigestUtils(MessageDigestAlgorithms.SHA_256).digestAsHex(message)));
	} catch (IOException e) {
	    throw e;
	} catch (Exception e) {
	    throw new ServletException(e);
	}
    }
}
