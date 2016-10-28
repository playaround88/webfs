<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<base href="<%=basePath%>">
  	<meta charset="utf-8" />
  	<meta name="viewport" content="width=device-width,initial-scale=1.0" />
    <title>模板</title>
    <link rel="stylesheet" type="text/css" href="http://172.17.246.79:8080/webfs/css/fileupload.css" />
  </head>
  <body>
  
  	<div class="content">
		<input id="record" type="text" value="" />
		<input type="button" id="updatePage" value="渲染更新页面" />
		<input type="button" id="queryPage" value="渲染查询页面" />
		<br />
		<input id="fileupload" type="file" name="files[]" multiple />
		<div id="files" class="files"> </div>
	</div>
	
  
	<script type="text/javascript" src="js/lib/jquery-1.12.1.min.js"></script>
	<script src="http://172.17.246.79:8080/webfs/js/fileupload/js/vendor/jquery.ui.widget.js"></script>
  	<script src="http://172.17.246.79:8080/webfs/js/fileupload/js/jquery.iframe-transport.js" ></script>
  	<script src="http://172.17.246.79:8080/webfs/js/fileupload/js/jquery.fileupload.js"></script>
  	<script src="http://172.17.246.79:8080/webfs/js/fileupload/js/jquery.fileupload-process.js "></script>
  	<script src="http://172.17.246.79:8080/webfs/js/fileupload/js/jquery.fileupload-validate.js"></script>
  	<script src="http://172.17.246.79:8080/webfs/js/artTemplate/template.js"></script>
    <script type="text/javascript">
    var webfs="http://172.17.246.79:8080/webfs";
    var webfs_catalog='rest';
	</script>
	<script type="text/javascript" src="http://172.17.246.79:8080/webfs/js/fileupload.js"></script>
	<script type="text/javascript">
	$(function() {
		// Change this to the location of your server-side upload handler:
		$('#fileupload').fileupload(fileupload.options,{
			valueId:'#record',
			tabRenderTo:'#files',
			acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		}).on('fileuploadprocessalways', function (e, data) {
			
			if(data.files.error){
				alert('错误的文件类型');
			}

		});

		
		$('#updatePage').click(function(){
			fileupload.initUpdate({
				'valueId':'#record',//初始化的字段值存放组件ID
				'fileId':'#fileupload',//文件组件的ID
				'tabRenderTo':'#files' //表格渲染到的容器ID
			});
		});
	
		$('#queryPage').click(function(){
			fileupload.initShow({
				'valueId':'#record',//初始化的字段值存放组件ID
				'tabRenderTo':'#files' //表格渲染到的容器ID
			});
		});
	});
	</script>
  </body>
</html>