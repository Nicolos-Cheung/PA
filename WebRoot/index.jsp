<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Upload</title>
<script type="text/javascript" src="/PA/js/ajex.js"></script>
<script type="text/javascript">
	document.getElementById("tel").onblur = function() {
		if (this.value == "") {
			alert("手机号不能为空");
			this.focus();
			return;
		} else {

			//异步检查用户名是否可用：失去焦点时检查用户名是否可用
			var xhr = getXHR();

			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						//TODO
						document.getElementById("msg").innerHTML = xhr.responseText;
					}

				}
			}
			xhr.open("GET",
					"${pageContext.request.contextPath}/servlet/AjexServlet?tel="
							+ this.value + "&time = " + new Date().getTime());
			xhr.send(null);

			/* //Post方式提交
			xhr.open("POST","${pageContext.request.contextPath}/servlet/ServletDemo2?"+"time = "
							+ new Date().getTime());
				//设置请求消息头:告知客户端提交的正文的MIME类型,否则serlvet中request.getParameter("username")获得的数据为NULL
			xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xhr.send("username="+this.value); */
		}
	}
</script>

</head>
<body>
	<div align="center">
		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/register">
			<table>
				<tr>
					<td>声纹注册</td>
				</tr>
				<tr>
					<td><div align="left">手机号：</div></td>
					<td><input type="text" id="tel" name="tel" size="20" />
					</td>
				</tr>
				<tr>
					<td><div align="left" size="20">音频文件:</div></td>
					<td><input type="file" name="file" size="20" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="register">
					</td>
					<td><input type="reset" name="reset" value="reset"></td>
				</tr>
			</table>
		</form>

		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/validation">
			<table>
				<tr>
					<td>声纹验证</td>
				</tr>
				<tr>
					<td><div align="left">手机号：</div></td>
					<td><input type="text" size="20" name="tel" id="tel" /> <span
						id="msg"></span>
					</td>
				</tr>

				<tr>
					<td><div align="left">音频文件:</div></td>
					<td><input type="file" name="file1" size="20" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="validation">
					</td>
					<td><input type="reset" name="reset" value="reset"></td>
				</tr>
			</table>
		</form>


		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/test">
			<table>
				<tr>
					<td>异步测试</td>
				</tr>
				<tr>
					<td><div align="left">手机号：</div></td>
					<td><input type="text" id="tel" name="tel" size="20" />
					</td>
				</tr>
				<tr>
					<td><div align="left" size="20">音频文件:</div></td>
					<td><input type="file" name="file" size="20" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="test"></td>
					<td><input type="reset" name="reset" value="reset"></td>
				</tr>
			</table>
		</form>
	</div>
	<hr />

	<div align="center">
		<h3>带版本号声纹注册验证</h3>
		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/registerV">
			<table>
				<tr>
					<td>声纹注册</td>
				</tr>
				<tr>
					<td><div align="left">手机号：</div></td>
					<td><input type="text" id="tel" name="tel" size="20" />
					</td>
				</tr>
				<tr>
					<td><div align="left" size="20">音频文件:</div></td>
					<td><input type="file" name="file" size="20" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="register">
					</td>
					<td><input type="reset" name="reset" value="reset"></td>
				</tr>
			</table>
		</form>

		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/validationV">
			<table>
				<tr>
					<td>声纹验证</td>
				</tr>
				<tr>
					<td><div align="left">手机号：</div></td>
					<td><input type="text" size="20" name="tel" id="tel" /> <span
						id="msg"></span>
					</td>
				</tr>

				<tr>
					<td><div align="left">音频文件:</div></td>
					<td><input type="file" name="file1" size="20" />
					</td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="validation">
					</td>
					<td><input type="reset" name="reset" value="reset"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
