function showCategory(num) {
    $.ajax({
        type: "POST",
        url: "/patent/getPageCategory.action",
        dataType: "json",
        data: {
            page: num,
        },
        success: function (data) {
            total = data.total;
            if (total < 5) {
                all = total;
            } else if (total > 16) {
                all = 8;
            } else {
                all = total / 2;
            }
            jsonarray = data.Category;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>行业类别</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                head = head + "<tr>" + "<th scope=\"row\">" + jsonarray[i].id + "</th>" +
                    "<td>" + jsonarray[i].category + "</td>" + "<td>\n" +
                    "<button type='button' data-toggle=\"modal\" data-target=\"#editCategory\"\n" +
                    "class=\"btn btn-primary\" class=\"btn btn-primary\" onclick='changeName(" + jsonarray[i].id + ",\"" + jsonarray[i].category + "\")'>编辑</button>\n" +
                    "<button class=\"btn btn-secondary\" onclick='dele(" + jsonarray[i].id + ")'>删除</button>\n" +
                    "</td>";
            }
            head = head + "</tbody>";
            $("#allCategory").append(head);


            $("#simplePaging5").simplePaging({
                allPage: total,//总页数
                showPage: all,//显示页数
                startPage: 1,//第一页页码数字
                initPage: num,//加载完毕自动跳转到第n页
                initPageClick: false,//每次页面加载完毕后，是否触发initPage页的绑定事件
                first: "首页",//首页显示字符
                last: "尾页",//尾页显示字符
                prev: "«",//上一页显示字符
                next: "»",//下一页显示字符
                showTurnTo: false,//是否显示跳转按钮，false不显示，true显示
                animateType: "normal",//过渡模式：动画“animation”、跳动“jumpy”、快速移动“fast”、正常速度移动“normal”、缓慢的速度移动“slow”、异常缓慢的速度移动“verySlow”
                animationTime: 300,//animateType为animation时，动画过渡时间(ms)
                callBack: function (num) {
                    $("#allCategory").empty();
                    $("#simplePaging5").empty();
                    showCategory(num);
                }
            });
        }
    });
}

function submitAdd() {
    var typeName = $("#typeName").val();
    $.ajax({
        type: "POST",
        url: "/patent/addCategory.action",
        data: {
            typeName: typeName
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString())
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

function changeName(id, name) {
    $("#beforeName").val(name);
    $("#hidden_id").val(id);
}

function submitEditCategory() {
    var id = $("#hidden_id").val();
    var name = $("#nowName").val();
    $.ajax({
        type: "POST",
        url: "/patent/editCategory.action",
        data: {
            id: id,
            name: name
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString())
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

function dele(id) {
    $.ajax({
        type: "POST",
        url: "/patent/deleteCategory.action",
        data: {
            id: id
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString());
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

var num = 1;
$(function () {
    showCategory(num);
});