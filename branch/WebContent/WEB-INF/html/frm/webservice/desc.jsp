<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="frm" uri="/WEB-INF/tld/frm.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/HTML; charset=utf-8"  />
    <title>Available XMLRPC methods for this server</title>
    <style type="text/css">
      li,p { font-size: 10pt; font-family: Arial,Helvetia,sans-serif; }
      a:link { background-color: white; color: blue; text-decoration: underline; font-weight: bold; }
      a:visited { background-color: white; color: blue; text-decoration: underline; font-weight: bold; }
      table { border-collapse:collapse; width: 100% }
      table,td { padding: 5px; border: 1px solid black; }
      div.bloc { border: 1px dashed gray; padding: 10px; margin-bottom: 20px; }
      div.description { border: 1px solid black; padding: 10px; }
      span.type { background-color: white; color: gray; font-weight: normal; }
      span.paratype { background-color: white; color: gray; font-weight: normal; }
      span.name { background-color: white; color: #660000; }
      span.paraname { background-color: white; color: #336600; }
      img { border: 0px; }
      li { font-size: 12pt; }
    </style>
  </head>
  <body>
    <h1>Available HTTP methods for this server</h1> 
    <h2><a name="index">Index</a></h2>
    <ul>
       <c:forEach items="${methodMap}" var="v" varStatus="st">
       	
      <li><a href="#${frm:md5(v.key)}">${v.key}</a></li>  
      </c:forEach>
    </ul>
   
    <h2>Details</h2>
    <c:forEach items="${methodMap}" var="v" varStatus="st">
    <div class="bloc">
      <h3><a name="${frm:md5(v.key)}"><span class="type">(String)</span> <span class="name">${v.key}</span><span class="other">(</span>
       <c:forEach items="${v.value.params}" var="r" varStatus="vst">
       		<c:if test="${vst.index>0}">
       		,
       		</c:if>
      		<span class="paratype">(${r.value.type}) </span><span class="paraname">${r.value.name}</span> 
       </c:forEach>
      <span class="other">)</span></a></h3>
      <p><b>Description :</b></p>
      <div class="description">
       ${v.value.desc}  <br />
      </div>
      
      <p><b>exmaple :</b></p>
      <div class="description">
       ${v.value.example}  <br /> 
      </div>
      
       <p><b>result :</b></p>
      <div class="description">
       ${v.value.result}  <br /> 
      </div>
      
      <p><b>result data : </b></p>
      <table>
        <tr><td><b>Type</b></td><td><b>Name</b></td><td><b>Documentation</b></td></tr>
         <c:forEach items="${v.value.datas}" var="r" varStatus="vst">
        <tr><td>${r.value.type}</td><td>${r.value.name}</td><td>${r.value.desc}</td></tr>
        </c:forEach>
      </table>
      
      <p><b>Parameters : </b></p>
      <table>
        <tr><td><b>Type</b></td><td><b>Name</b></td><td><b>Documentation</b></td></tr>
         <c:forEach items="${v.value.params}" var="r" varStatus="vst">
        <tr><td>${r.value.type}</td><td>${r.value.name}</td><td>${r.value.desc}</td></tr>
        </c:forEach>
      </table>
      <p>(return to <a href="#index">index</a>)</p>
    </div>
   </c:forEach>
  </body>
</html>