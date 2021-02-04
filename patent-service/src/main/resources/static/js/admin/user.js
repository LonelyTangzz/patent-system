function showUser(num) {
    $("#allUser").empty();
    $.ajax({
        type: "GET",
        url: "/getPageUser.action",
        dataType: "json",
        async: false,
        data: {
            page: num,
        },
        success: function (data) {
            total = data.total;
            jsonarray = data.userList;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>账号</th>\n" +
                "<th>头像</th>\n" +
                "<th width='70px'>真实姓名</th>\n" +
                "<th width='50px'>性别</th>\n" +
                "<th>电话号码</th>\n" +
                "<th>邮箱</th>\n" +
                "<th width='80px'>生日</th>\n" +
                "<th width='88px'>所在地</th>\n" +
                "<th width='80px'>注册时间</th>\n" +
                "<th width='80px'>上次登陆</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                window.sessionStorage.setItem('userList[' + i + ']', JSON.stringify(jsonarray[i]));
                head = head + "<tr>" + "<th scope=\"row\" style='vertical-align: middle'>" + jsonarray[i].id + "</th>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].username + "</td>" +
                    "<td style='vertical-align: middle' ><a onclick='changeImg(" + jsonarray[i].id + ",\"" + jsonarray[i].avatar + "\")'  data-toggle='modal'  data-target='#editUserImg'>" + "<img class='img-fluid rounded-circle shadow' src="+jsonarray[i].avatar+" style='max-width: 5rem;max-height: 5rem' />" + "</a></td>" +//这里记得改
                    "<td style='vertical-align: middle'>" + jsonarray[i].realName + "</td>";
                if (jsonarray[i].sex == 1) {
                    head = head + "<td style='vertical-align: middle'>" + "男" + "</td>";
                } else if (jsonarray[i].sex == 0) {
                    head = head + "<td style='vertical-align: middle'>" + "女" + "</td>";
                } else {
                    head = head + "<td style='vertical-align: middle'>" + "未定义" + "</td>";
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
                    "class=\"btn btn-primary btn-sm \" onclick='changeInfo(\"" + i + "\")'>修改资料</button>\n" +
                    "</td>";
            }
            head = head + "</tbody>";
            $("#allUser").append(head);
        }
    });
}

function currentPage(currentPage) {
    /*
        触发页码数位置： Page/js/jquery.z-pager.js 64行
    */
    showUser(currentPage);
}

function changeInfo(id) {
    var object = JSON.parse(sessionStorage.getItem('userList[' + id + ']'));
    $("#username").val(object.username);
    $("#realName").val(object.realName);
    if (object.sex == 1) {
        $("#male").attr("checked", "true");
    } else if (object.sex == 0) {
        $("#female").attr("checked", "true");
    }
    $("#phoneNum").val(object.phoneNum);
    $("#email").val(object.email);
    $("#hidden_id").val(object.id);
    if (object.birth != null)
        $("#date").val(object.birth.substring(0, 10));
    $("#location").val(object.location.toString());
}

function submitEditUser() {
    $.ajax({
        type: "POST",
        url: "/updateInfo.action",
        data: {
            id: $("#hidden_id").val(),
            realName: $("#realName").val(),
            sex: $("input[name='sex']:checked").val(),
            location: $("#location").val(),
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
                window.location.href = "userControl";
            }
        }
    });
}

function changeImg(id, avatar) {
    $("#BeforeImg").attr("src", "" + avatar);
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

$(function () {
    showUser(1);
    $("#pager").zPager({
        totalData: total * 10, //数据总条数
        pageData: 10, //每页数据条数
        current: 1, //当前页码数
        pageStep: 5, //当前可见最多页码个数
        minPage: 4, //最小页码数，页码小于此数值则不显示上下分页按钮
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

$("#file").change(
    function () {
        var objUrl = getObjectURL(this.files[0]);//获取文件信息
        if (objUrl) {
            $("#nowImg").attr("src", objUrl);
        }
    }
);