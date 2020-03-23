function showUser(num) {
    $.ajax({
        type: "POST",
        url: "/patent/getPageUser.action",
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
            jsonarray = data.userList;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>账号</th>\n" +
                "<th>头像</th>\n" +
                "<th>真实姓名</th>\n" +
                "<th>性别</th>\n" +
                "<th>电话号码</th>\n" +
                "<th>邮箱</th>\n" +
                "<th>生日</th>\n" +
                "<th>所在地</th>\n" +
                "<th>创建时间</th>\n" +
                "<th>上次登录时间</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                window.sessionStorage.setItem('userList[' + i + ']', JSON.stringify(jsonarray[i]));
                head = head + "<tr>" + "<th scope=\"row\" style='vertical-align: middle'>" + jsonarray[i].id + "</th>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].username + "</td>" +
                    "<td style='vertical-align: middle' ><a onclick='changeImg(" + jsonarray[i].id + ",\"" + jsonarray[i].avatar + "\")'  data-toggle='modal'  data-target='#editUserImg'>" + "<img class='img-fluid rounded-circle shadow' src='../../" + jsonarray[i].avatar + "' style='max-width: 5rem;max-height: 5rem' />" + "</a></td>" +//这里记得改
                    "<td style='vertical-align: middle'>" + jsonarray[i].realname + "</td>";
                if (jsonarray[i].sex == 1) {
                    head = head + "<td style='vertical-align: middle'>" + "男" + "</td>";
                } else {
                    head = head + "<td style='vertical-align: middle'>" + "女" + "</td>";
                }
                head = head + "<td style='vertical-align: middle'>" + jsonarray[i].phoneNum + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].email + "</td>";
                if (jsonarray[i].birth != null) {
                    head = head + "<td style='vertical-align: middle'>" + jsonarray[i].birth.substring(0, 10) + "</td>";
                } else {
                    head = head + "<td style='vertical-align: middle'>" + jsonarray[i].birth + "</td>";
                }
                head = head + "<td style='vertical-align: middle'> " + jsonarray[i].location + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].createTime + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].loginTime + "</td>" +
                    "<td style='vertical-align: middle'>\n" + "<button type='button' data-toggle=\"modal\" data-target=\"#editUser\"\n" +
                    "class=\"btn btn-primary\" class=\"btn btn-primary\" onclick='changeInfo(\"" + i + "\")'>修改资料</button>\n" +
                    "</td>";
            }
            head = head + "</tbody>";
            $("#allUser").append(head);
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
                    showCategory(num);
                }
            });
        }
    });

}

function changeInfo(id) {
    var object = JSON.parse(sessionStorage.getItem('userList[' + id + ']'));
    $("#username").val(object.username);
    $("#realname").val(object.realname);
    if (object.sex == 1) {
        $("#male").attr("checked", "true");
    } else if (object.sex == 0) {
        $("#female").attr("checked", "true");
    }

    $("#phoneNum").val(object.phoneNum);
    $("#email").val(object.email);
    if (object.birth == null) {
        var date = new Date();
        $("#date").datetime({
            type: "static.js.tools.date",
            value: [date.getFullYear(), (date.getMonth() + 1), date.getDate()],
            success: function (res) {
                console.log(res)
            }
        })
    } else {
        $("#date").datetime({
            type: "static.js.tools.date",
            value: [parseInt(object.birth.substring(0, 4)), parseInt(object.birth.substring(5, 7)), parseInt(object.birth.substring(8, 10))],
            success: function (res) {
                console.log(res)
            }
        })
        $("#date").val(object.birth.substring(0, 10));
    }
    $("#city").val(object.location);
    $("#hidden_id").val(object.id);

    //var date = (new Date()).pattern("yyyy-MM-dd"); 03-08和3-08不知道区别
}

function submitEditUser() {
    $.ajax({
        type: "POST",
        url: "/patent/updateInfo.action",
        data:{
            id: $("#hidden_id").val(),
            realname: $("#realname").val(),
            sex: $("input[name='sex']:checked").val(),
            location: $("#city").val(),
            birth: $("#date").val(),
            phoneNum: $("#phoneNum").val(),
            email: $("#email").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString());
                window.location.href = "/patent/views/admin/userControl.html";
            }
        }
    });
}

function changeImg(id, avatar) {
    $("#BeforeImg").attr("src", "../../" + avatar);
    $("#user_id").val(id);
}

function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
var num = 1;
$(function () {
    showUser(num);
});

$("#file").change(
    function () {
        var objUrl = getObjectURL(this.files[0]);//获取文件信息
        if (objUrl) {
            $("#nowImg").attr("src", objUrl);
        }
    }
);