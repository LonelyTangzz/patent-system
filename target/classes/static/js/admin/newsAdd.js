function summitAddNews() {
    $("#text").text(" ");
    if ($("#title").val() == "") {
        $("#text").text("请输入标题！");
    } else if (tinymce.get('details').getContent() == "") {
        $("#text").text("文章内容为空！");
    } else if ($("#author").val() == "") {
        $("#text").text("请输入作者！");
    } else {
        $.ajax({
            url: "/patent/addNews.action",
            type: "POST",
            dataType: "json",
            data: {
                title: $("#title").val(),
                author: $("#author").val(),
                details: tinymce.get('details').getContent(),
            },
            success: function () {
                alert("添加成功！");
                window.location.href = "newsControl";
            }
        });
    }
}

//配置富文本编辑器
tinymce.init({
    selector: '#details',
    language: 'zh_CN',
    height: 500,
    plugins: 'print preview importcss  searchreplace autolink autosave save directionality  visualblocks visualchars fullscreen image link media  template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists  wordcount   imagetools textpattern noneditable help    charmap   quickbars  emoticons',
    mobile: {
        plugins: 'print preview importcss  searchreplace autolink autosave save directionality  visualblocks visualchars fullscreen image link media  template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists  wordcount   textpattern noneditable help   charmap  quickbars  emoticons'
    },
    menu: {
        tc: {
            title: '',
            items: 'addcomment showcomments deleteallconversations'
        }
    },
    menubar: 'file edit view insert format tools table tc help',
    toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist  | forecolor backcolor casechange   removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media  template link anchor codesample | a11ycheck ltr rtl | showcomments addcomment',
    image_advtab: true,
    content_css: '//www.tiny.cloud/css/codepen.min.css',
});