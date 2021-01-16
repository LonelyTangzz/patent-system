function login_out() {
    Cookies.remove("username");
    window.location.href = "index";
}

if (Cookies.get("username") != null && Cookies.get("username") != undefined) {
    $("#head").empty();
    var view = "<ul class=\"site-nav topmenu\"> <li> 欢迎" + Cookies.get('username') + "</li><li><a href='personInfo'><i class='fa fa-home'></i>个人中心</a></li><li><a href='javascript:login_out()'><i class='glyphicon glyphicon-log-out'></i>登出</a></li></ul>\n"
    $("#head").append(view);
}