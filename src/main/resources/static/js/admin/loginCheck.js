loadUserInfo();
function login_out() {
    Cookies.remove('adminName',);
    window.location.href = "login";
}

function loadUserInfo() {
    if (Cookies.get('adminName') != null && Cookies.get('adminName') != 'undefined') {
        document.getElementById("adminName").innerHTML = Cookies.get('adminName');
    } else {
        alert("您还未登录，请先登录!");
        window.location.href = "login";
    }
}