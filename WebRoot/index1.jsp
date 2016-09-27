<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Upload</title>

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
					<td><input type="file" name="file" size="20" />
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

	<hr>
	<div align="center">
		<h3>PCM文件声纹注册验证</h3>
		<form name="uploadForm" method="POST" enctype="MULTIPART/FORM-DATA"
			action="${pageContext.request.contextPath}/pcm_register">
			<table>
				<tr>
					<td>声纹注册</td>
				</tr>
				<tr>
					<td><div align="left">用户ID：</div></td>
					<td><input type="text" id="user_id" name="user_id" size="20" />
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
			action="${pageContext.request.contextPath}/pcm_validation">
			<table>
				<tr>
					<td>声纹验证</td>
				</tr>
				<tr>
					<td><div align="left">用户ID：</div></td>
					<td><input type="text" size="20" name="user_id" id="user_id" />
						<span id="msg"></span>
					</td>
				</tr>

				<tr>
					<td><div align="left">音频文件:</div></td>
					<td><input type="file" name="pcm" size="20" />
					</td>
				</tr>
				<tr>
					<td><div align="left">ivector:</div></td>
					<td><input type="file" name="ivector" size="20" />
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
