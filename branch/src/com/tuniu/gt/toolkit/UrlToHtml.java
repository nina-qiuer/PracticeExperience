package com.tuniu.gt.toolkit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class UrlToHtml {
	private static final long serialVersionUID = -3616674121206024906L;

	/**
	 * @param request
	 * @param response
	 * @param url
	 *            需要转换的jsp路径（以/开头）
	 * @param filePath
	 *            需要存放html的文件路径
	 * @param fileName
	 *            html的文件名
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void jsptohtml(HttpServletRequest request,
			HttpServletResponse response, String url, String filePath,
			String fileName) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		File uploadFilePath = new File(filePath);
		// 如果该目录不存在,则创建之
		if (uploadFilePath.exists() == false) {
			uploadFilePath.mkdirs();
		} else {
		}

		ServletContext sc = request.getSession().getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {
				os.write(b);
			}
		};
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,
				"UTF-8"));
		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};
		rd.include(request, rep);

		pw.flush();
		// 把jsp输出的内容写到filePath + fileName
		FileOutputStream fos = new FileOutputStream(filePath +"/"+ fileName);
		os.writeTo(fos);
		fos.close();
	}
}
