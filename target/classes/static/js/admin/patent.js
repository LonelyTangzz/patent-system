function loadType() {
    $.ajax({
        type: "GET",
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
    $("#allPatent").empty();
    $.ajax({
        type: "GET",
        url: "/patent/getPatentByPage.action",
        dataType: "json",
        async: false,
        data: {
            page: num,
        },
        success: function (data) {
            total = data.total;
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
                    "<td style='vertical-align: middle'>\n" + "<div class='btn-group'><button type='button' data-toggle=\"modal\" data-target=\"#changePatent\"\n" +
                    "class=\"btn btn-primary\" onclick='editPatent(\"" + jsonarray[i].id + "\")'>修改信息</button>\n" +
                    "<button type='button' class=\"btn btn-secondary\" onclick='delePatent(\"" + jsonarray[i].id + "\")'>删除本条</button></div>" + "</td>";
            }
            head = head + "</tbody>";
            $("#allPatent").append(head);
        }
    });
}

function currentPage(currentPage) {
    /*
        触发页码数位置： Page/js/jquery.z-pager.js 64行
    */
    getPatentByPage(currentPage);
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
                window.location.href = "patentControl";
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
                window.location.href = "patentControl";
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
                window.location.href = "patentControl";
            } else if (data.code.toString() == "0") {
                alert(data.msg.toString());
            }
        }
    });
}

$(document).ready(function () {
    loadType();
    getPatentByPage(1);
    $("#pager").zPager({
        totalData: total * 10, //数据总条数
        pageData: 10, //每页数据条数
        current: 1, //当前页码数
        pageStep: 8, //当前可见最多页码个数
        minPage: 5, //最小页码数，页码小于此数值则不显示上下分页按钮
        active: 'current', //当前页码样式
        prevBtn: 'pg-prev', //上一页按钮
        nextBtn: 'pg-next', //下一页按钮
        btnBool: true, //是否显示上一页下一页
        firstBtn: 'pg-first', //第一页按钮
        lastBtn: 'pg-last', //最后一页按钮
        btnShow: true, //是否显示第一页和最后一页按钮
        ajaxSetData: false, //是否使用ajax获取数据 此属性为真时需要url和htmlBox不为空
        htmlBox: $('#wraper') //ajax数据写入容器
    });
});