//用户获取验证码方法
function getVerifyCode() {
    $.ajax({
        type: "POST",
        url: "getVerifyCode",
        dataType: "json",
        data: {
            phoneNum: $("#phoneNum").val(),
            type: "0"
        },
        success: function (data) {
            var button = document.getElementById("verifyButton");
            if (data.status == 1) {
                button.innerText="发送成功";
            } else {
                button.innerText="发送失败";
            }
            button.href="";
        }
    });
}
//用户登陆方法
function login(){
    $.ajax({
       type: "POST",
       url: "register.action",
       dataType: "json",
       data: {
           username: $("#username").val(),
           password: $("#password").val(),
           phoneNum: $("#phoneNum").val(),
           verifyCode:$("#verifyCode").val(),
       },
       success: function (data){
           if(data.status==1){
               window.location.href="login";
           }else {
               $("#respond").text(data.msg);
           }
       }
    });
}