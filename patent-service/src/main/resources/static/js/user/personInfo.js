$("#file").change(
    function () {
        var objUrl = getObjectURL(this.files[0]);//获取文件信息
        if (objUrl) {
            $("#nowImg").attr("src", objUrl);
        }
    }
);
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

function changeInfo() {
    var details = document.getElementById("details");
    details.style.display="none";
    var submit_details = document.getElementById("submit_details");
    submit_details.style.display="block";
}