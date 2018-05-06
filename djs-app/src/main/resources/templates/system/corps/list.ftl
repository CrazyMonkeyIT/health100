<@ui.layout >
<!-- zTree -->
<link rel="stylesheet" href="${request.contextPath}/static/ztree/zTreeStyle.css" type="text/css"
      xmlns="http://www.w3.org/1999/html">
<script src="${request.contextPath}/static/ztree/jquery.ztree.all-3.5.js"></script>
<!-- My97 -->
<script src="${request.contextPath}/static/My97DatePicker/WdatePicker.js"></script>
<div class="col-xs-12">
    <!-- div.table-responsive -->

    <!-- div.dataTables_borderWrap -->
    <div>
        <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
            <div class="row">
                <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/system/corps/list" method="post">
                    <input id="pageIndex" name="pageIndex" value="${page.pageNum}" type="hidden" />
                    <div class="input-group">
                        <input type="text" name="corpsName" value="${corpsName!}" class="form-control search-query" placeholder="战队名">
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-white btn-info">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                查询
                            </button>
                        </span>
                    </div>
                    &nbsp;
                    <a onclick="showAddModal()" class="btn btn-white btn-info">
                        <i class="glyphicon glyphicon-plus"></i>
                        创建战队
                    </a>
                </form>
            </div>
            <table class="table table-striped table-bordered table-hover dataTable no-footer" >
                <thead class="thin-border-bottom">
                <tr >
                    <th >序号</th>
                    <th ><i class="normal-icon ace-icon fa fa-user"></i>战队名</th>
                    <th >战队简介</th>
                    <th >战队积分</th>
                    <th >战队头像</th>
                    <th >Banner</th>
                    <th >置顶</th>
                    <th ><i class="ace-icon fa fa-wrench"></i>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#if page.list?? && (page.list?size > 0)>
                        <#list page.list as data>
                        <tr>
                            <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                            <td><span class="blue">${data.corpsName!''}</span></td>
                            <td>${data.corpsIntroduce!''}${data.corpsIntroduce1!''}</td>
                            <td>${data.point!''}</td>
                            <td><a onclick="showImage('${data.corpsHeadImage}')"><img src="${data.corpsHeadImage!''}" style="width: 50px;height: 50px;"></a></td>
                            <td><a onclick="showImage('${data.corpsBannerImage}')"><img src="${data.corpsBannerImage!''}" style="width: 50px;height: 50px;"></a></td>
                            <td><span class="blue"><#if data.isTop==0>否<#else>是</#if></span></td>
                            <td >
                                <div class="btn-overlap btn-group">
                                    <a onclick="topCorps('${data.corpsId}','${data.isTop}');" class="btn btn-white btn-primary btn-bold"  data-rel="tooltip" title="" data-original-title="置顶" title="置顶">
                                        <i class="fa fa-exchange bigger-110 green" ></i>
                                    </a>
                                    <a onclick="showEditModal('${data.corpsName}');" class="btn btn-white btn-primary btn-bold"  data-rel="tooltip" title="" data-original-title="修改" title="修改">
                                        <i class="fa fa-pencil bigger-110 green" ></i>
                                    </a>
                                    <a onclick="deleteUser(${data.corpsId})" class="btn btn-white btn-primary btn-bold" data-rel="tooltip" title="" data-original-title="删除" title="删除">
                                        <i class="fa fa-trash-o bigger-110 red"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    <#else>
                    <tr style="text-align:center;">
                        <td colspan="6"><h4 class="green">暂无战队</h4></td>
                    </tr>
                    </#if>
                </tbody>
            </table>
            <div class="row">
                <!-- 分页begin -->
                <#if (page.pages>0)>
                    <#import "../../macro/pagination.ftl" as cast/>
                    <@cast.pagination callFunName="submitForm" />
                </#if>
                <!-- 分页end -->
            </div>
        </div>
    </div>
</div>
<!-- 编辑用户信息begin -->
<div id="edit_user_modal" class="modal fade" style="display: none;"  data-backdrop="static" role="dialog" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>&nbsp;编辑战队</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="${request.getContextPath()}/system/corps/updateCorps" method="post">
                    <div class="form-horizontal" style="height: 500px;">
                        <!-- 用户ID -->
                        <input type="hidden" name="corpsId" />
                        <input type="hidden" id="avatar" type="text" value="/static/images/sample.png" name="corpsHeadImage" />
                        <input type="hidden" id="avatar1" type="text" value="/static/images/sample.png" name="corpsBannerImage" />
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">战队名</label>
                            <div class="col-sm-8">
                                <input name="corpsName" id="corpsName" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">战队介绍</label>
                            <div class="col-sm-8">
                                <input name="corpsIntroduce1" id="corpsIntroduce1" type="text" placeholder="slogan(18个字内)" /></br>
                                <input name="corpsIntroduce" id="corpsIntroduce" type="text" placeholder="banner介绍(20个字内)"/>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">是否特殊排行</label>
                            <div class="col-sm-8">
                                <select name="isSpecial" id="isSpecial">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">战队头像</label>
                            <div class="col-sm-8">
                                <input id="avatarSlect" type="file" style="position: absolute;float: left; z-index: 10; opacity: 0;width: 100px; height: 100px;"><br/>
                                <img id="avatarPreview" src="/static/images/sample.png" title="点击更换图片" style="position: absolute; z-index: 9; width: 100px; height: 100px">
                            </div>
                        </div>
                        <div class="form-group" style="margin-top: 100px;">
                            <label class="col-sm-4 control-label">战队Banner</label>
                            <div class="col-sm-8">
                                <input id="avatarSlect1" type="file" style="position: absolute;float: left; z-index: 10; opacity: 0;width: 200px; height: 100px;"><br/>
                                <img id="avatarPreview1" src="" title="点击更换图片" style="position: absolute; z-index: 9; width: 200px; height: 100px">
                            </div>
                        </div>

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a onclick="updateUser()" class="btn btn-white btn-info btn-bold">
                    <i class="ace-icon glyphicon glyphicon-ok blue"></i>
                    保存
                </a>
                <a class="btn btn-white btn-info btn-bold" data-dismiss="modal">
                    <i class="ace-icon glyphicon glyphicon-remove blue"></i>
                    取消
                </a>
            </div>
        </div>
    </div>
</div>
<!-- 编辑用户信息end -->
<!-- 显示图片begin -->
<div id="image_show" class="modal fade" style="display: none;"  data-backdrop="static" role="dialog" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-body">
                <img id="imageShow" src="" style="margin-left:80px;width: 400px; height: 400px">
            </div>
            <div class="modal-footer">
                <a class="btn btn-white btn-info btn-bold" data-dismiss="modal">
                    <i class="ace-icon glyphicon glyphicon-remove blue"></i>
                    取消
                </a>
            </div>
        </div>
    </div>
</div>
<!-- 显示图片end -->

<script>
    jQuery(function($) {
        $('[data-rel=tooltip]').tooltip();
    });
    // 分页查询
    function submitForm(index){
        $("#pageIndex").val(index);
        $("#form1").submit();
    }
    /**
     * 清空表单
     */
    function clearForm(){
        $("#editForm")[0].reset();
        $("#editForm").find("input[name='corpsId']").val("");
        $("#editForm").find("input[name='corpsHeadImage']").val("");
        $("#editForm").find("input[name='corpsBannerImage']").val("");
        $("#avatarPreview").attr('src',"/static/images/sample.png");
        $("#avatarPreview1").attr('src',"/static/images/sample.png");
    }
    /**
     * 显示新增用户
     */
    function showAddModal(){
        clearForm();
        $("#edit_user_modal").modal("show");
    }

    /**
     * 更新用户信息
     */
    function updateUser(){
        var corpsId = $("#editForm").find("input[name='corpsId']").val();
        var corpsName = $("input#corpsName").val();
        var corpsIntroduce1 = $("#editForm").find("input[name='corpsIntroduce1']").val();
        var corpsIntroduce = $("#editForm").find("input[name='corpsIntroduce']").val();
        var corpsHeadImage = $("#editForm").find("input[name='corpsHeadImage']").val();
        var corpsBannerImage = $("#editForm").find("input[name='avatar1']").val();
        var isSpecial = $("#editForm").find("select[name='isSpecial']").val();
        if(!corpsName){
            alert("战队姓名必须填写");
            $("#editForm").find("input[name='corpsName']").focus();
            return false;
        }
        if(corpsName.length>6){
            alert("战队姓名过长");
            $("#editForm").find("input[name='corpsName']").focus();
            return false;
        }
        if(corpsIntroduce.length>20){
            alert("战队简介过长");
            $("#editForm").find("input[name='corpsIntroduce']").focus();
            return false;
        }
        if(corpsIntroduce1.length>18){
            alert("战队简介过长");
            $("#editForm").find("input[name='corpsIntroduce1']").focus();
            return false;
        }

        $.ajax({
            url : $("#editForm").attr("action"),
            type : 'post',
            data : $("#editForm").serialize(),
            success : function(data) {
                if(data.result){
                    $("#form1").submit();
                }else{
                    alert(data.msg);
                }
            }
        });
    }

    /**
     * 获取用户信息
     */
    function showEditModal(corpsName){
        clearForm();
        $.ajax({
            url : basePath+"/system/corps/getCorpsInfo",
            type : 'post',
            data : {
                "corpsName":corpsName
            },
            success : function(data) {
                $("#editForm").find("input[name='corpsId']").val(data.corpsId);
                $("#editForm").find("input[name='corpsName']").val(data.corpsName);
                $("#editForm").find("input[name='corpsIntroduce']").val(data.corpsIntroduce);
                $("#editForm").find("input[name='corpsIntroduce1']").val(data.corpsIntroduce1);
                $("#editForm").find("input[name='point']").val(data.point);
                $("#editForm").find("input[name='corpsHeadImage']").val(data.corpsHeadImage);
                $("#editForm").find("input[name='corpsBannerImage']").val(data.corpsBannerImage);
                $("#editForm").find("select[name='isSpecial']").val(data.isSpecial);
                $("#avatarPreview").attr('src',data.corpsHeadImage);
                $("#avatarPreview1").attr('src',data.corpsBannerImage);
                $("#edit_user_modal").modal("show");
            }
        });
    }

    /**
     * 删除用户
     */
    function deleteUser(corpsId){
        Ewin.confirm({ message: "确认要删除该战队吗？" }).on(function () {
            $.ajax({
                url : basePath+"/system/corps/deleteCorps",
                type : 'post',
                data : {
                    "corpsId":corpsId
                },
                success : function(data) {
                    if(data){
                        $("#form1").submit();
                    }else{
                        alert("操作失败，系统异常");
                    }
                }
            });
        });
    };
    function topCorps(corpsId,isTop) {
        if(isTop==1){
            Ewin.confirm({ message: "确认取消战队的置顶？" }).on(function () {
                $.ajax({
                    url : basePath+"/system/corps/corpsCancelTop",
                    type : 'post',
                    data : {
                    },
                    success : function(data) {
                        if(data){
                            $("#form1").submit();
                        }else{
                            alert("操作失败，系统异常");
                        }
                    }
                });
            });
        }else{
            Ewin.confirm({ message: "确认要置顶该战队？" }).on(function () {
                $.ajax({
                    url : basePath+"/system/corps/corpsTop",
                    type : 'post',
                    data : {
                        "corpsId":corpsId
                    },
                    success : function(data) {
                        if(data){
                            $("#form1").submit();
                        }else{
                            alert("操作失败，系统异常");
                        }
                    }
                });
            });
        }
    };
    /*图片预览*/
    $(function () {
        bindAvatar();
        bindAvatar1();
    });
    function bindAvatar() {
        $("#avatarSlect").change(function () {
            var formData=new FormData();
            formData.append('file', $("#avatarSlect")[0].files[0]);
            var fSize = 1024 * 1024 * 3;
            if($("#avatarSlect")[0].files[0].size>fSize){
                alert("图片不能大于"+fSize/1024/1024+"M");
                return;
            }
            var flg = true;
            var img = new Image;
            img.onload = function() {
                if (img.height != img.width ) {
                    alert("请上传宽高相等的图片，当前图片尺寸为：" + img.width + "*" + img.height);
                    flg = false;
                }
                if(flg==false){
                    $("#avatarPreview1").attr('src','');
                    return;
                }else{
                    $.ajax({
                        url: basePath + "/import/up/temp",
                        type: 'POST',
                        data: formData,
                        contentType: false,
                        processData: false,
                        success: function (args) {
                            /*服务器端的图片地址*/
                            $("#avatarPreview").attr('src',args[0].filePath);
                            /*预览图片*/
                            $("#avatar").val(args[0].filePath);
                            /*将服务端的图片url赋值给form表单的隐藏input标签*/
                        }
                    })
                }
            }
        })
    };
    function bindAvatar1() {
        $("#avatarSlect1").change(function () {
            var formData=new FormData();
            formData.append('file', $("#avatarSlect1")[0].files[0]);
            var fSize = 1024 * 1024 * 3;
            //限制图片宽高
            var fHeight = 250;
            var fWidth = 680;
            if($("#avatarSlect1")[0].files[0].size>fSize){
                alert("图片不能大于"+fSize/1024/1024+"M");
                return;
            }
            var flg = true;
            var img = new Image;
            img.onload = function(){
                if(img.height!=fHeight || img.width!=fWidth){
                    alert("请上传尺寸为："+fWidth+"*"+fHeight+"的图片，当前图片尺寸为："+img.width+"*"+img.height);
                    flg = false;
                }
                if(flg==false){
                    $("#avatarPreview1").attr('src','');
                    return;
                }else{
                    $.ajax({
                        url: basePath + "/import/up/temp",
                        type: 'POST',
                        data: formData,
                        contentType: false,
                        processData: false,
                        success: function (args) {
                            /*服务器端的图片地址*/
                            $("#avatarPreview1").attr('src',args[0].filePath);
                            /*预览图片*/
                            $("#avatar1").val(args[0].filePath);
                            /*将服务端的图片url赋值给form表单的隐藏input标签*/
                        }
                    })
                }
            }
            img.src=window.URL.createObjectURL($("#avatarSlect1")[0].files[0]);
        })
    };
    function showImage(imagePath) {
        $("#imageShow").attr('src',imagePath);
        $("#image_show").modal("show");
    }

</script>
</@ui.layout>