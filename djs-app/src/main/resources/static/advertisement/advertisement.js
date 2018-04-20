
$(function () {
    //监听下拉框，显示/隐藏对应的输入框
   $("#advertisementType").click(function () {
       var type = $('#advertisementType option:selected').val();
       if(type == 1){
           $("#urlDiv").show();
           $("#roomDiv").hide();
           $("#lecturerDiv").hide();
       }else if(type == 2){
           $("#urlDiv").hide();
           $("#roomDiv").hide();
           $("#lecturerDiv").hide();
       }else if(type == 3){
           $("#urlDiv").hide();
           $("#roomDiv").hide();
           $("#lecturerDiv").hide();
       }else if(type == 4){
           $("#urlDiv").hide();
           $("#roomDiv").hide();
           $("#lecturerDiv").show();
       }else if(type == 5){
           $("#urlDiv").hide();
           $("#roomDiv").show();
           $("#lecturerDiv").hide();
       }else if(type == 6){
           $("#urlDiv").hide();
           $("#roomDiv").hide();
           $("#lecturerDiv").hide();
       }
   });
});

/**
 * 保存广告信息
 */
function saveAdvertisementInfo(){
    var advertisementTitle = $("#editForm").find("input[name='advertisementTitle']").val();
    if(!advertisementTitle){
        alert("广告标题不能为空！");
        return;
    }
    var advertisementDesc = $("#editForm").find("input[name='advertisementDesc']").val();
    if(!advertisementDesc){
        alert("广告描述不能为空！");
        return;
    }
    var invalidDate = $("#editForm").find("input[name='invalidDate']").val();
    if(!invalidDate){
        alert("广告到期时间不能为空！");
        return;
    }
    var type = $('#advertisementType option:selected').val();
    if(type == -1){
        alert("请选择广告类型！");
        return;
    }else if(type == 1){
        var advertisementUrl = $("#editForm").find("input[name='advertisementUrl']").val();
        if(!advertisementUrl){
            alert("请填写广告链接地址！");
            return;
        }
    }else if(type == 4){
        var lecturer = $('#lecturer option:selected').val();
        if(lecturer == -1){
            alert("请选择需要关联的讲师！");
            return;
        }
    }else if(type == 5){
        var room = $('#room option:selected').val();
        if(room == -1){
            alert("请选择需要关联的房间！");
            return;
        }
    }
    debugger;
    $("#advertisementTypeId").val(type);
    $.ajax({
        url : $("#editForm").attr("action"),
        type : 'post',
        data : $("#editForm").serialize(),
        success : function(data) {
            if(data){
                location.href = basePath + "/advertisement/list";
            }else{
                alert("error");
            }
        }
    });
}

function showEditModal(advertisementTitle){
    alert(advertisementTitle);
//    $("#editForm").find("input[name='advertisementTitle']").val(advertisementTitle);

}

/**
 * 异步上传广告图片
 */
$(function(){
    $("#upFileBtn").click(function(){
        $("#imgForm").ajaxSubmit({
            dataType: 'json',
            success: function (data) {
                alert(data[0].filePath);
                $("#advertisementImgPath").val(data[0].filePath);
            },
            error: function (msg) {
               alert(msg);
            }
        });
    });
})

/**
 * 清空表单
 */
function clearForm(){
    $("#editForm")[0].reset();
    $("#editForm").find("input[name='userId']").val("");
}

/**
 * 显示新增等级窗口
 */
function showAddModal(){
    clearForm();
    $("#edit_grade_modal").modal("show");
}

function delAdvertisement(advertisementId){
    if(window.confirm("确认要删除该广告吗？")){
        $.ajax({
            url: basePath + "/advertisement/del",
            type: 'post',
            data: {
                "advertisementId": advertisementId
            },
            success: function (data) {
                if (data) {
                    location.href = basePath + "/advertisement/list";
                } else {
                    alert("操作失败，系统异常");
                }
            }
        });
    }else{
        return;
    }
}

function stopAdvertisement(advertisementId){
    if(window.confirm("确认暂停此广告使用吗？")){
        $.ajax({
            url: basePath + "/advertisement/stop",
            type: 'post',
            data: {
                "advertisementId": advertisementId
            },
            success: function (data) {
                if (data) {
                    location.href = basePath + "/advertisement/list";
                } else {
                    alert("操作失败，系统异常");
                }
            }
        });
    }else{
        return;
    }
}