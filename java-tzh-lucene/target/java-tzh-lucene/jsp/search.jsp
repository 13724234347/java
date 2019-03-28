<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style>
        .button {
            background-color: white;
            color: black;
            border: 2px solid #e7e7e7;
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 5px 15px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 10px;
            margin: 10px 20px;
            -webkit-transition-duration: 0.4s;
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 12px;
        }

        .input {
            border: 1px solid #ccc;
            padding: 10px 0px;
            border-radius: 3px;
            padding-left: 5px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
            -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
            -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
        }

        .input:focus {
            border-color: #66afe9;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
        }

        .button:hover {
            background-color: #e7e7e7;
        }

        #center{
            margin-top: 300px;
        }
    </style>
</head>
<body>
<div id="center">
    <form action="<%=request.getContextPath()%>/lndexes/queryValue" target="display">
        <input type="text" name="value" id="value" class="input" placeholder="搜你想搜,请点击右边按钮"/>
        &nbsp;
        <input type="submit" value="搜索" class="button"/>
    </form>
</div>
</body>
</html>