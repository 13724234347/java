<%--  --%><%@ page language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
a {
	text-decoration: blink 
}

a:link {
	text-decoration: none;
	cursor: pointer;
}

a:visited {
	text-decoration: underline ;
	cursor: pointer
}

a:active {
	text-decoration: underline ;
	cursor: pointer
}

a:hover {
	text-decoration:underline;
	cursor: pointer
}

.myclass:hover {
	text-decoration: underline ;
	cursor: pointer
}
</style>
</head>
<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
//搜索查询
function Search(){
    var pageSize6 = $("#pageSize").val();
    var page6 = 1;
    var pageCount6 = 0;
    var pageSelect6 = 1;
    var select6 = document.getElementById("value").value;
    Data(page6, pageSize6, pageCount6, pageSelect6, select6);
}
function Page(page3, pageSize3, pageCount3, pageSelect3, select3){
    if(0 == page3){
        page3 = 1;
    }
    if(0 == pageSelect3){
        pageSize3 = $("#pageSize").val();
    }
    if(1 == pageSize3){
        pageSize3 = $("#pageSize").val();
    }
    select3 = document.getElementById("value").value;
    Data(page3, pageSize3, pageCount3, pageSelect3, select3);
}
// 初始化(搜索)分页查询
function Data(page4, pageSize4, pageCount4, pageSelect4, select4){
    var page2 = page4;
    if("" == page2){
        page2 = 1;
    }
    var pageSize2 = pageSize4;
    if("" == pageSize2 || null == pageSize2){
        pageSize2 = 10;
    }
    select4 = document.getElementById("value").value;
    var select2 = select4;
    $.ajax({
        type:"get", //请求方式     对应form的  method请求
        url:"<%=request.getContextPath()%>/lndexes/query",  
        //请求路径  对应 form的action路径
        cache: false,  //是否缓存，false代表拒绝缓存
        
        data:{"page":page2,"pageSize":pageSize2, "value":select2},  //传参
        dataType: "json",   //返回值类型 
        success:function(data){
            if("1" == data){
            }else{
                var list = data.page.pt;
                var html = "";
                $("#t_body").empty();
                for(var i in list){
                    html += "<a href='"+list[i].fileUrl+"' onclick='loadFile(this)'>" + list[i].fileName+ "</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<b>"+ list[i].fileSize +"</b><br />" + 
                    "<b>"+ list[i].fileContent +"</b><br /><hr>"
                    ;
                }
                $("#t_body").append(html);
                var page1 = "";
                page1 = "总记录数:<b>" + data.page.pageSizeCount + "</b>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "总页数:<b>" + data.page.pageCount + "</b>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "当前页:<b id='inPage'>" + data.page.page + "</b>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "每页记录数:<select name='pageSize' id='pageSize' onchange='Page(" + data.page.page + ", 1, " + data.page.pageCount + ",0, " + '' + ")'>" +
                        "<option value='5' >5</option>" + 
                        "<option value='10' >10</option>" +
                        "<option value='20' >20</option>" +
                        "<option value='50' >50</option>" +
                        "</select><br />"
                ;
                if(data.page.page > 1){
                    page1 = page1 + "<button onclick='Page(1," + pageSize2 + ", " + data.page.pageCount + ",1, " + '' + ")'>首页</button>&nbsp;&nbsp;";
                    page1 = page1 + "<button onclick='Page(" + (data.page.page-1) + "," + pageSize2 + ", " + data.page.pageCount + ",1, " + '' + ")'>上一页</button>&nbsp;&nbsp;";
                }
                for (var c = 1; c < data.page.pageCount+1; c++) {
                    if(c == data.page.page){
                        page1 = page1 + "<b>"+ c + "</b>&nbsp;&nbsp;";
                    }else{
                        
                        page1 = page1 + "<button onclick='Page(" + c + "," + pageSize2 + ", " + data.page.pageCount + ",1, " + '' + ")'>"+ c + "</button>&nbsp;&nbsp;";
                    }
                }
                if(data.page.page < data.page.pageCount){
                    page1 = page1 + "<button onclick='Page(" + (data.page.page+1) + "," + pageSize2 + ", " + data.page.pageCount + ",1, " + '' + ")'>下一页</button>&nbsp;&nbsp;";
                    page1 = page1 + "<button onclick='Page(" + data.page.pageCount + "," + pageSize2 + ", " + data.page.pageCount + ",1, " + '' + ")'>尾页</button>&nbsp;&nbsp;";
                }
                
                $("#divPage").html(page1);
                $("#pageSize").val(pageSize2);// 设置下拉框默认显示值
                
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });
}



	function loadFile(t) {
		var href = $(t).attr("href");
		alert(href);
		document.getElementById("loadFilePath").value = href;
		$("#download").submit();
	}
</script>
<body>
    <input type="hidden" value="${value}" id="value" />
    
	<form action="<%=request.getContextPath()%>/lndexes/loadFile" id="download">
		<input type="hidden" name="loadFilePath" id="loadFilePath" />
	</form>
	<c:if test="${!empty value}">
		<script>
			Search();
		</script>
	</c:if>
	<div id="t_body">
    </div>
    <div id="divPage">
    </div>
	
    <h1>${error}</h1>	
</body>
</html>