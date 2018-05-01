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
        <div class="row">
            <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/system/miniSign/list" method="post">
                <input id="pageIndex" name="pageIndex" value="${page.pageNum}" type="hidden" />
                <div class="input-group" style="display: none">
                    <span class="input-group-btn">
                            <button type="submit" class="btn btn-white btn-info">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                查询
                            </button>
                        </span>
                </div>
            </form>
        </div>
        <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
            <table class="table table-striped table-bordered table-hover dataTable no-footer" >
                <thead class="thin-border-bottom">
                <tr >
                    <th >序号</th>
                    <th ><i class="normal-icon ace-icon fa fa-user"></i>用户</th>
                    <th >上传图片</th>
                    <th ><i class="ace-icon fa fa-wrench"></i>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#if page.list?? && (page.list?size > 0)>
                        <#list page.list as data>
                        <tr>
                            <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                            <td><span class="blue">${data.nickName!''}</span></td>
                            <td><a onclick="showImage('${data.imagePath}')"><img src="${data.imagePath!''}" style="width: 50px;height: 50px;"></a></td>
                            <td >
                                <div class="btn-overlap btn-group">
                                    <a onclick="checkImage('${data.id}');" class="btn btn-white btn-primary btn-bold"  data-rel="tooltip" title="" data-original-title="确认" title="确认">
                                        <i class="glyphicon glyphicon-ok bigger-110 green" ></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    <#else>
                    <tr style="text-align:center;">
                        <td colspan="6"><h4 class="green">暂无需要审核数据</h4></td>
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
    // 分页查询
    function submitForm(index){
        $("#pageIndex").val(index);
        $("#form1").submit();
    }
    /**
     * 删除用户
     */
    function checkImage(id){
        Ewin.confirm({ message: "确定确认该数据？" }).on(function () {
            $.ajax({
                url : basePath+"/system/miniSign/checkImage",
                type : 'post',
                data : {
                    "id":id
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
    function showImage(imagePath) {
        $("#imageShow").attr('src',imagePath);
        $("#image_show").modal("show");
    }
</script>
</@ui.layout>