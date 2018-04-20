<@ui.layout >

<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
    <div class="row">
        <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/lecturer/grade/list" method="post">
            <a onclick="showAddModal()" class="btn btn-white btn-info">
                <i class="glyphicon glyphicon-plus"></i>
                创建等级
            </a>
        </form>
    </div>

    <table class="table table-striped table-bordered table-hover dataTable no-footer" >
        <thead class="thin-border-bottom">
        <tr >
            <th >序号a</th>
            <th >等级名称</th>
            <th >分成比例</th>
            <th >操作</th>
        </tr>
        </thead>
        <tbody>
            <#list page.list as data>
            <tr>
                <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                <td>${data.gradeName!''}</td>
                <td>${data.paymentRatio!''}</td>
                <td>
                    <a onclick="showEditModal(${data.lecturerGradeId}, '${data.gradeName}', ${data.paymentRatio})" class="btn btn-white btn-primary btn-bold"  data-rel="tooltip" title="" data-original-title="修改" title="修改">
                        <i class="fa fa-pencil bigger-110 green" ></i>
                    </a>
                    <a onclick="delGrade(${data.lecturerGradeId})" class="btn btn-white btn-primary btn-bold" data-rel="tooltip" title="" data-original-title="删除" title="删除">
                        <i class="fa fa-trash-o bigger-110 red"></i>
                    </a>
                </td>
            </tr>
            </#list>
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

<!--等级新增/修改div开始-->
<div id="edit_grade_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in exam_newbox">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>编辑等级</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="${request.getContextPath()}/lecturer/grade/edit" method="post">
                    <div class="form-horizontal">
                        <!-- 讲师等级ID -->
                        <input type="hidden" name="lecturerGradeId" />

                        <div class="form-group ">
                            <label class="col-sm-4 control-label">等级名称</label>
                            <div class="col-sm-8">
                                <input name="gradeName" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">分成比例</label>
                            <div class="col-sm-8">
                                <input name="paymentRatio" type="text"  />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a onclick="editLecturerGrade()" class="btn btn-white btn-info btn-bold">
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
<!--等级新增/修改div结束-->

<script >

/**
 * 编辑讲师等级信息时 附带上一次的值
 */
function showEditModal(lecturerGradeId, gradeName, paymentRatio){
    clearForm();
    $("#editForm").find("input[name='lecturerGradeId']").val(lecturerGradeId);
    $("#editForm").find("input[name='gradeName']").val(gradeName);
    $("#editForm").find("input[name='paymentRatio']").val(paymentRatio);
    $("#edit_grade_modal").modal("show");
}

/**
 * 编辑讲师等级信息
 */
function editLecturerGrade(){
    var gradeName = $("#editForm").find("input[name='gradeName']").val();
    var paymentRatio = $("#editForm").find("input[name='paymentRatio']").val();
    if(!gradeName){
        alert("讲师等级名称不能为空");
        $("#editForm").find("input[name='gradeName']").focus();
        return false;
    }
    if(!paymentRatio){
        alert("讲师分成比例不能为空");
        $("#editForm").find("input[name='paymentRatio']").focus();
        return false;
    }
    $.ajax({
        url : $("#editForm").attr("action"),
        type : 'post',
        data : $("#editForm").serialize(),
        success : function(data) {
            if(data){
                location.href = basePath + "/lecturer/grade/list";
            }else{
                alert("failed");
            }
        }
    });
}

/**
 * 删除讲师等级
 */
function delGrade(lecturerGradeId){
    Ewin.confirm({ message: "确认要删除该讲师等级吗？" }).on(function () {
        $.ajax({
            url: basePath + "/lecturer/grade/del",
            type: 'post',
            data: {
                "lecturerGradeId": lecturerGradeId
            },
            success: function (data) {
                if (data) {
                    location.href = basePath + "/lecturer/grade/list";
                } else {
                    alert("操作失败，系统异常");
                }
            }
        });
    });
}

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
</script>

</@ui.layout>