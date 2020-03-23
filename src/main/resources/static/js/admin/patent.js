function loadType() {
    $.ajax({
        type: "POST",
        url: "/patent/getAllCategory.action",
        dataType: "json",
        success: function (data) {
            categoryCount = data.allCategory;
            for (var i = categoryCount.length - 1; i >= 0; i--) {
                $("#category").prepend("<option value=" + categoryCount[i].id + ">" + categoryCount[i].category + "</option>");
                $("#edit_category").prepend("<option value=" + categoryCount[i].id + ">" + categoryCount[i].category + "</option>");
            }
        }
    });
}

function getPatentByPage(num) {
    $.ajax({
        type: "POST",
        url: "/patent/getPatentByPage.action",
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
            jsonarray = data.patentList;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>专利号</th>\n" +
                "<th style='width: 300px'>专利名</th>\n" +
                "<th style='width: 100px'>行业分类</th>\n" +
                "<th style='width: 150px'>持有人</th>\n" +
                "<th style='width: 150px'>价格(万元)</th>\n" +
                "<th style='width: 60px'>详情</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                window.sessionStorage.setItem('patentList[' + jsonarray[i].id + ']', JSON.stringify(jsonarray[i]));
                head = head + "<tr>" + "<th scope=\"row\" style='vertical-align: middle'>" + jsonarray[i].id + "</th>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].patentNo + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].patentName + "</td>";
                head = head + "<td style='vertical-align: middle'>" + jsonarray[i].category + "</td>";
                head = head + "<td style='vertical-align: middle'> " + jsonarray[i].owner + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].price + "</td>" +
                    "<td style='vertical-align: middle'><a onclick='patentDetails(" + jsonarray[i].id + ")' data-toggle='modal'  data-target='#detailPatent'>....</a></td>" +
                    "<td style='vertical-align: middle'>\n" + "<div class='btn-group-sm'><button type='button' data-toggle=\"modal\" data-target=\"#changePatent\"\n" +
                    "class=\"btn btn-primary\" onclick='editPatent(\"" + jsonarray[i].id + "\")'>修改信息</button>\n" +
                    "<button type='button' class=\"btn btn-secondary\" onclick='delePatent(\"" + jsonarray[i].id + "\")'>删除本条</button></div>" + "</td>";
            }
            head = head + "</tbody>";
            $("#allPatent").append(head);
            $("#simplePaging").simplePaging({
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
                    $("#allUser").empty();
                    $("#simplePaging5").empty();
                    getPatentByPage(num);
                }
            });
        }
    });
}

function submitAddPatent() {
    //这里还要添加判断条件 记得填
    $.ajax({
        type: "POST",
        url: "/patent/patentAdd.action",
        dataType: "json",
        data: {
            patent_no: $("#patent_no").val(),
            patent_name: $("#patent_name").val(),
            category: $("#category").val(),
            owner: $("#owner").val(),
            location: $("#location").val(),
            price: $("#price").val(),
            details: $("#details").val(),
            updateTime: $("#updateTime").val()
        },
        success: function (data) {
            if (data.code.toString() == "1") {
                alert(data.msg.toString());
                window.location.href = "/patent/views/admin/patentControl.html";
            } else if (data.code.toString() == "0") {
                alert(data.msg.toString());
            }
        }
    });
}

function patentDetails(id) {
    var object = JSON.parse(sessionStorage.getItem('patentList[' + id + ']'));
    $("#table_detail").empty();
    $("#patentName_detail").empty();
    $("#patentName_detail").append(object.patentName);
    detailPage = "<tr>\n" +
        "<td style=\"width: 10%\">专利号</td>\n" +
        "<td>" + object.patentNo + "</td>\n" +
        "<td style=\"width: 100px\">类别</td>\n" +
        "<td >" + object.category + "</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "<td>持有人</td>\n" +
        "<td>" + object.owner + "</td>\n" +
        "<td style=\"width: 100px\">公开日期</td>\n" +
        "<td>" + object.updatetime.substring(0, 10) + "</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "<td>价格</td>\n" +
        "<td>" + object.price + "万元" + "</td>\n" +
        "<td>地址</td>\n" +
        "<td>" + object.location + "</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "<td style=\"text-align:center;vertical-align:middle;\">摘要</td>\n" +
        "<td style=\"font-size: 13px\" colspan=\"3\">" + object.details + "</td>\n" +
        "</tr>"
    $("#table_detail").append(detailPage);
}

function changePatent() {
    $.ajax({
        type: "POST",
        url: "/patent/changePatent.action",
        dataType: "json",
        data: {
            patent_no: $("#edit_patent_no").val(),
            patent_name: $("#edit_patent_name").val(),
            category: $("#edit_category").val(),
            owner: $("#edit_owner").val(),
            location: $("#edit_location").val(),
            price: $("#edit_price").val(),
            details: $("#edit_details").val(),
            updateTime: $("#edit_updateTime").val()
        },
        success: function (data) {
            if (data.code.toString() == "1") {
                alert(data.msg.toString());
                window.location.href = "/patent/views/admin/patentControl.html";
            } else if (data.code.toString() == "0") {
                alert(data.msg.toString());
            }
        }
    });
}

function editPatent(id) {
    var object = JSON.parse(sessionStorage.getItem('patentList[' + id + ']'));
    for (var i = 0; i < categoryCount.length; i++) {
        if (object.category == categoryCount[i].category)
            choose = categoryCount[i].id;
    }
    $("#edit_patent_no").val(object.patentNo);
    $("#edit_patent_name").val(object.patentName);
    $("#edit_location").val(object.location);
    $("#edit_updateTime").val(object.updatetime.substring(0, 10));
    $("#edit_category").val(choose);
    $("#edit_owner").val(object.owner);
    $("#edit_price").val(object.price);
    $("#edit_details").val(object.details);
}

function delePatent(id) {
    $.ajax({
        type: "POST",
        url: "/patent/delePatent.action",
        dataType: "json",
        data: {
            id: id,
        },
        success: function (data) {
            if (data.code.toString() == "1") {
                alert(data.msg.toString());
                window.location.href = "/patent/views/admin/patentControl.html";
            } else if (data.code.toString() == "0") {
                alert(data.msg.toString());
            }
        }
    });
}

$(document).ready(function () {
    var num = 1;
    loadType();
    getPatentByPage(num);
});