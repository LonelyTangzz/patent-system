function submitChangePassword() {
    var befoPassword = $("#befoPassword").val();
    var nowPassword = $("#nowPassword").val();
    var repeatPassword = $("#repeatPassword").val();
    if (befoPassword == "") {
        $("#info").text("旧密码不能为空!");
        clear();
    } else if (nowPassword != repeatPassword) {
        $("#info").text("两次密码输入不一致！");
        clear();
    } else {
        $.ajax({
                type: "POST",
                url: "submitPassword.action",
                dataType: "json",
                data: {
                    username: Cookies.get("username"),
                    befoPassword: $("#befoPassword").val(),
                    nowPassword: $("#repeatPassword").val()
                },
                success: function (data) {
                    if (data.code.toString() === "0") {
                        $("#info").text(data.msg.toString());
                        clear();

                    } else {
                        $("#info").text(data.msg.toString());
                        window.location.href = "personInfo";
                    }
                }
            }
        );
    }
}
function clear() {
    $("#befoPassword").val("");
    $("#nowPassword").val("");
    $("#repeatPassword").val("");
}