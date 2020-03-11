loadUserInfo();
//记得将LocalStorage改成sessionStorage 用Local便于测试
function login_out() {
    localStorage.clear();
    window.location.href = "/patent/views/admin/login.html";
}

function loadUserInfo() {
    if (localStorage.getItem('adminName') != null && localStorage.getItem('adminName') != 'undefined') {
        document.getElementById("adminName").innerHTML = localStorage.getItem('adminName');
    } else {
        alert("您还未登录，请先登录!");
        window.location.href = "/patent/views/admin/login.html";
    }
}