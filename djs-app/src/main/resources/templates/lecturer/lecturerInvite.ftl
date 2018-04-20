<@ui.layout >
<div class="col-xs-12">

    <div>
        <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <div class="row" style="display: none;">
                    <form class="form-inline" id="form1" role="form" action="${request.getContextPath()}/lecturer/invite/list" method="post">
                        <input id="pageIndex" name="pageIndex" value="${page.pageNum}" type="hidden" />
                    </form>
                </div>
                <div class="row">
                        <button onclick="showModal()" class="btn btn-white btn-info">
                            <i class="glyphicon glyphicon-plus"></i>
                            创建邀请
                        </button>
                </div>
                <table class="table table-striped table-bordered table-hover dataTable no-footer" >
                    <thead class="thin-border-bottom">
                    <tr >
                        <th >序号</th>
                        <th >讲师姓名</th>
                        <th >手机号</th>
                        <th >公司</th>
                        <th >职位</th>
                        <th >邀请码</th>
                        <th >状态</th>
                        <th ><i class="normal-icon ace-icon fa fa-clock-o"></i>创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#if page.list?? && (page.list?size > 0)>
                            <#list page.list as data>
                            <tr>
                                <td>${((page.pageNum-1) * 10) + (data_index+1)}</td>
                                <td>${data.lecturerName!''}</td>
                                <td>${data.phone!''}</td>
                                <td>${data.company!''}</td>
                                <td>${data.position!''}</td>
                                <td>${data.inviteCode!''}</td>
                                <td>
                                    <#if data.status == 'NOTALLOW'>
                                        <span class="label label-yellow label-white middle">未接受</span>
                                    </#if>
                                    <#if data.status == 'ALLOW'>
                                        <span class="label label-success label-white middle">已接受</span>
                                    </#if>
                                </td>
                                <td><#if data.createTime??>${(data.createTime?string('yyyy-MM-dd HH:mm:ss'))}</#if></td>
                            </tr>
                            </#list>
                        <#else>
                        <tr style="text-align:center;">
                           <td colspan="9"><h4 class="green">暂无记录</h4></td>
                        </tr>
                        </#if>
                    </tbody>
                </table>
            <div class="row">
                <!-- 分页begin -->
                <#if (page.pages>0)>
                    <#import "../macro/pagination.ftl" as cast/>
                    <@cast.pagination callFunName="submitForm" />
                </#if>
                <!-- 分页end -->
            </div>
        </div>
    </div>
</div>
<!-- 编辑邀请信息begin -->
<div id="invite_modal" class="modal fade" style="display: none; " data-backdrop="static" role="dialog" tabindex="-1" class="modal fade in exam_newbox">
    <div class="modal-dialog">
        <div class="modal-content"  >
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4 class="blue"><i class="fa fa-pencil"></i>&nbsp;创建邀请</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" action="${request.getContextPath()}/lecturer/invite/create" method="post">
                    <div class="form-horizontal">
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">讲师姓名</label>
                            <div class="col-sm-8">
                                <input name="lecturerName" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">手机号</label>
                            <div class="col-sm-8">
                                <input name="phone" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">讲师等级</label>
                            <div class="col-sm-8">
                                <select name="gradeId">
                                    <#list gradeList as grade>
                                        <option value="${grade.lecturerGradeId}">${grade.gradeName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">公司</label>
                            <div class="col-sm-8">
                                <input name="company" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">职位</label>
                            <div class="col-sm-8">
                                <input name="position" type="text"  />
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="col-sm-4 control-label">邀请码</label>
                            <div class="col-sm-8">
                                 <input type="hidden" name="inviteCode"/>
                                 <label id="inviteCode" style="line-height: 30px;font-weight: bold;"></label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a onclick="create()" class="btn btn-white btn-info btn-bold">
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
<!-- 编辑邀请信息end -->
<script>
jQuery(function($) {
    var inviteCode = RndNum(6);
    $("#inviteCode").html(inviteCode);
    $("input[name='inviteCode']").val(inviteCode);
});
// 分页查询
function submitForm(index){
    $("#pageIndex").val(index);
    $("#form1").submit();
}
function showModal(){
    $("#invite_modal").modal("show");
}
/**
 * 创建邀请
 */
function create(){
    $.ajax({
        url : $("#editForm").attr("action"),
        type : 'post',
        dataType:'json',
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
//产生随机数函数
function RndNum(n){
    var rnd="";
    for(var i=0;i<n;i++)
        rnd+=Math.floor(Math.random()*10);
    return rnd;
}
</script>
</@ui.layout>